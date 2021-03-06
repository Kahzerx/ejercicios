# Configuración de sistemas y operaciones

---

## Firmware

- **BIOS (Basic Input Output System)**
    - En el primer sector del disco, la cual es una pequeña parte del disco se encuentra el boot sector, donde se encuentra el MBR(Master Boot Record), y es lo que se encarga de decirle al resto del ordenador dónde se encuentran las particiones
    - Con BIOS + MBR solo puedes tener hasta 4 particiones (hay hacks para bypassear tho).
    - Muchas limitaciones en cuanto al almacenamiento máximo y a cuánto el MBR realmente puede referenciar (hasta 2TB).

- **UEFI (Unified Extensible Firmware Interface)**
    - UEFI surge como reemplazo de BIOS, una nueva tecnología que se basa en dedicar una partición entera dedicada al boot.
    - Permite muchas más particiones en el disco y muchísima más capacidad por disco.
    - Soporte para Secure Boot.

> Ambos no son más que la forma que tenemos de interactuar entre el OS y el hardware.

> Aun con todas las limitaciones de la BIOS, existen hacky ways para bypassear dichas limitaciones.

> Desde una perspectiva de la instalación, el OS es capaz de detectar en qué modo ha sido iniciado y adaptar su disco a las peculiaridades.

---

## Bootloader

- **GRUB (GRand Unified Bootloader)**
    - También llamado GRUB "legacy"
    - Genera los archivos `menu.lst` o `grub.conf` en el directorio `/boot/grub/`, es la forma más fácil de identificar qué GRUB tienes.
    - Dificil de modificar.
    - Muestra el boot menu onBoot.

- **GRUB2**
    - Update con respecto a `GRUB` mucho más customizable y flexible, cada vez más usado.
    - Genera los archivos `grub.cfg` en el directorio `/boot/grub/`, es la forma más fácil de identificar qué GRUB tienes.
    - Customizable en `/etc/default/grub`.
    - Puede bootear desde una `ISO`, `USB`, `UUID`, etc.
    - No muestra el boot menu onBoot, esto por supuesto es customizable para que salga siempre y se puede forzar si presionas `shift` onBoot.

> No es más que la forma que tiene el ordenador de hacer la transición de la BIOS/UEFI al propio sistema operativo.

> Para actualizar la configuración de GRUB, edita el archivo `/etc/default/grub`, cuando termines ejecuta el comando `sudo update-grub`.

---

## Métodos de Boot

- **Basado en Hardware**
    - **PXE** (Preboot eXecution Environment): El Hardware, al no detectar ninguna otra forma de boot, hará consultas por dhcp hasta que este responda con una dirección donde encontrar la imagen de boot, el ordenador se la descarga (del servidor TFTP), y booteará desde ahí.
    - **iPXE**: Versión actualizada de `PXE` pero te permite descargar desde HTTP.
    - **USB**: El hardware detecta que puede bootear desde USB.
    - **CD**: El hardware detecta que puede bootear desde CD.
    - **HD**: El hardware detecta que puede bootear desde Hard Drive.
- **Basado en Software**
    - Esto ocurre una vez iniciado linux, en GRUB.
    - **ISO**: Una vez en GRUB2, este es capaz de iniciar el sistema desde una ISO.
    - Puedes seleccionar kernel.
    - Puedes ejecutar `memtest`

---

## Módulos y Archivos de Boot.

1. **BIOS/UEFI**: Primero usa `BIOS` o `UEFI` para buscar algo "iniciable" que `GRUB` o `GRUB2` proporcione para pasar a software.
2. **GRUB/GRUB2**: Proporciona un boot code que apunta al kernel.
    - En medio se ejecuta `initrd`, esto proporciona la información necesaria como información de módulos y drivers, todo preparado para que el kernel de Linux sepa llegar a todos los **modules** en el **Full Kernel**.
3. **Linux Kernel**: Es un archivo, normalmente llamado `vmlinux` o `vmlinuz`
    - `vmlinux` o `vmlinuz` son lo mismo solo que linuz se refiere a que está comprimidom, se suele usar este para ahorrar espacio.
    - Dentro de este **Linux Kernel** se encuentra el `InitramFS` (no confundir con `initrd`).
    - El **InitramFS** no es más que un File System que dentro del **Linux Kernel** para conseguir cargar todos los módulos necesarios para el **Full Kernel**. Gracias a una herramienta llamada `dracut` esto es super flexible y genérico.
4. **Full Kernel**: Una vez el kernel ha booteado, montará todos los sistemas de archivos y entonces es cuando tendrá acceso a los módulos necesarios como el ratón, el teclado, gráfica, etc. Todo esto se carga posteriormente DENTRO del kernel.

> Uno de los principales problemas es: "qué ocurriría si el **Linux Kernel** no es capaz de montar uno de los módulos porque no sabe como?", es por eso que existe `initrd`.

> La idea es que en lugar de tener un **Linux Kernel** modificado y cargado para cada sistema, tenemos un **Linux Kernel** genérico común con un `initrd` preparado para los módulos que necesite.

> Podemos encontrar los archivos de `vmlinuz` o `initrd` en el directorio `/boot`. Dentro encontraremos un `System.map` el cual indica al **Linux Kernel** dónde encontrar los modules redirigiendo a `lib/modules` ahí es donde podemos encotrar los distintos módulos.

---

## Kernel Panic

Los **Kernel Panic** pueden deberse a:

- **Hardware**: Puede ser que uno de tus componentes físicos esté dañado o defectuoso, en este caso la solución es reemplazarlo.

- **Software**: Puede ocurrir que haya un fallo durante una actualización del sistema en estas situaciones hay una cosa que podemos hacer:
    - Linux por estas situaciones, mantiene el **Kernel** antiguo, para que podamos iniciar nuestro sistema con el antiguo enel caso de **Kernel Panic**.
    - Necesitaremos reiniciar el sistema, entrar en GRUB e iniciar nuestro OS en ajustes avanzados con el antiguo **Kernel**.
    - Posteriormente deberemos eliminar el antiguo kernel.

> En el peor de los casos podemos bootear desde un **USB**, acceder a nuestros discos y extraer los datos personales que no queramos perder.

---

## Cargar Kernel Modules

Podemos configurar manualmente la carga de los módulos que queramos. Aunque esto no es necesario ya que el propio sistema detecta el hardware y sabe qué tiene que importar, puede ser útil en determinadas situaciones.

Para ello podemos editar el archivo `/etc/modules`, todo lo aquí introducido se cargará durante el boot. Por ejemplo `e1000` (Modulo de una intel Network). Si dicho módulo tuviera dependencias, el propio sistema es capaz de detectarlo y cargarlos.

Puede ser que una de las dependecias de nuestro módulo no nos interesa que lo use, pero por defecto usa el que no queremos, para esto existe una `blacklist`, para añadir algo a la `blacklist` nos vamos a `/etc/modproble.d` aquí encontraremos muchos archivos y todos los que terminen en `.conf` realmente se detecta, no importa el archivo, pero hay unas convenciones.

---

## Manipular Kernel Modules

Al ser modular significa que podemos cargar solo lo necesario, haciendo el **Kernel** increiblemente eficiente. Para hacer esto de forma correcta e importar los **modules** de forma correcta tenemos las siguientes opciones:

- **insmod**
    - Muy básico.
    - Debes enviarle la `full path`.
    - No hace check de dependencias de aquellos **modules** que queremos importar.
    - Si falla no va a dar un log mostrando qué lo ha causado.
- **modprobe**
    - Mucho más avanzado y eficiente.
    - Puede recibir únicamente el nombre del **module**.
    - Determina todas las dependencias y las carga.
    - Necesita una guía para encontrar todas las dependencias y **modules**, pero hay un programa que lo hace por nosotros, llamado `depmod` y recreará el mapa que indica donde encontrar las dependencias.

> "Por qué si quiera **insmod** existe?" fácil, **modprobe** ejecuta los comandos correctos de **insmod**, automatiza y hace eficiente el proceso de **insmod**, es una especie de front-end inteligente.

> Podemos cargar un **module** con **insmod**: `insmod /lib/modules/{versionKernel}/kernel/drivers/net/{moduleQueTeInteresa}.ko`. Pero probablemente falle debido a que necesita dependencias, aunque el error no te dará ningún tipo de información de cómo proceder.

> Sin embargo, así cargamos un **module** con **modprobe**: `modprobe {moduleQueTeInteresa}`.

> Para revisar los **modules** instalados usamos el comando `lsmod`.

> Si queremos eliminar un **module** podemos usar el comando `rmmod {module}`, puede que de un error en el caso de que estemos intentando eliminar una depencia de otro **module**, pero al menos te da el nombre de dicho **module** para que puedas eliminarlo antes.

> Si nos descargamos nuevos **modules** deberemos ponerlos en la carpeta que corresponde dentro de nuestro kernel como se muestra en la `full path` del comando mencionado previamente de **insmod**, deberemos remapear los **modules** para que nuestro sistema sea capaz de detectarlos, para ello ejecutaremos el comando `depmod`. De esta forma **modprobe** podrá encontrar su ruta y sus dependencias al momento de instalarlo.