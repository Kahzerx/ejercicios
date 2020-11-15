import os
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy import create_engine, Column, String, Integer, Boolean, Float


engine = create_engine('sqlite:///database/TFC.db', echo=False)
Base = declarative_base()


def tryCreateDatabase():
    tryCreatedir()
    Base.metadata.create_all(engine)


def tryCreatedir():
    if not os.path.exists('database'):
        os.makedirs('database')


class Usuarios(Base):
    __tablename__ = 'Usuarios'

    id = Column(Integer, primary_key=True, autoincrement=True)
    nombre = Column(String, nullable=False)
    contraseña = Column(String, nullable=False)
    fecha = Column(String, nullable=False)

    def __init__(self, nombre, contraseña, fecha):
        self.nombre = nombre
        self.contraseña = contraseña
        self.fecha = fecha


class Clientes(Base):
    __tablename__ = 'clientes'

    NIF = Column(String, primary_key=True)
    nombre = Column(String, nullable=False)
    calle = Column(String, nullable=False)
    telefono = Column(String, nullable=False)
    telefono2 = Column(String, nullable=True)
    mail = Column(String, nullable=True)

    def __init__(self, NIF, nombre, calle, telefono, telefono2, mail):
        self.NIF = NIF
        self.nombre = nombre
        self.calle = calle
        self.telefono = telefono
        self.telefono2 = telefono2
        self.mail = mail


class Proveedores(Base):
    __tablename__ = 'proveedores'

    NIF = Column(String, primary_key=True)
    nombre = Column(String, nullable=False)
    calle = Column(String, nullable=False)
    telefono = Column(String, nullable=False)
    telefono2 = Column(String, nullable=True)
    mail = Column(String, nullable=True)

    def __init__(self, NIF, nombre, calle, telefono, telefono2, mail):
        self.NIF = NIF
        self.nombre = nombre
        self.calle = calle
        self.telefono = telefono
        self.telefono2 = telefono2
        self.mail = mail


class Partidas(Base):
    __tablename__ = 'partidas'

    numPartida = Column(Integer, primary_key=True)
    fechaAlta = Column(String, nullable=True)
    tipo = Column(String, nullable=True)
    centroVenta = Column(String, nullable=True)
    numMatadero = Column(String, nullable=True)
    proveedor = Column(String, nullable=True)
    numExplot = Column(String, nullable=True)
    paisNac = Column(String, nullable=True)
    paisSacr = Column(String, nullable=True)
    tipoAnimal = Column(String, nullable=True)
    totalAnimales = Column(Integer, nullable=True)
    deNum = Column(Integer, nullable=True)
    aNum = Column(Integer, nullable=True)
    totalKgBrutos = Column(Float, nullable=True)
    porcentajeOreo = Column(Float, nullable=True)
    totalKgNetos = Column(Float, nullable=True)
    importeTotalCosto = Column(Float, nullable=True)
    notas = Column(String, nullable=True)
    clavePagada = Column(Boolean, nullable=True)
    claveSituacion = Column(String, nullable=True)


    def __init__(self, numPartida, fechaAlta, tipo, centroVenta, numMatadero, proveedor, numExplot, paisNac, paisSacr, tipoAnimal, totalAnimales, deNum, aNum,
    totalKgBrutos, porcentajeOreo, totalKgNetos, importeTotalCosto, notas, clavePagada, claveSituacion):
        self.numPartida = numPartida
        self.fechaAlta = fechaAlta
        self.tipo = tipo
        self.centroVenta = centroVenta
        self.numMatadero = numMatadero
        self.proveedor = proveedor
        self.numExplot = numExplot
        self.paisNac = paisNac
        self.paisSacr = paisSacr
        self.tipoAnimal = tipoAnimal
        self.totalAnimales = totalAnimales
        self.deNum = deNum
        self.aNum = aNum
        self.totalKgBrutos = totalKgBrutos
        self.porcentajeOreo = porcentajeOreo
        self.totalKgNetos = totalKgNetos
        self.importeTotalCosto = importeTotalCosto
        self.notas = notas
        self.clavePagada = clavePagada
        self.claveSituacion = claveSituacion


class Almacen(Base):
    __tablename__ = 'almacen'

    numCanal = Column(Integer, primary_key=True)
    peso = Column(Float, nullable=True)
    fechaSacr = Column(String, nullable=True)
    tipoAnimal = Column(String, nullable=True)

    def __init__(self, numCanal, peso, fechaSacr, tipoAnimal):
        self.numCanal = numCanal
        self.peso = peso
        self.fechaSacr = fechaSacr
        self.tipoAnimal = tipoAnimal


class Albaran(Base):
    __tablename__ = 'albaran'

    codigo = Column(Integer, primary_key=True)
    codigoProv = Column(String, nullable=False)
    numPedido = Column(String, nullable=False)
    fechaAlb = Column(String, nullable=True)
    codigoAnimal = Column(Integer, nullable=False)
    pesoAnimal = Column(Float, nullable=True)
    precioAnimalEuroKg = Column(Float, nullable=True)
    importeSinIva = Column(Float, nullable=True)
    porcentajeIva = Column(String, nullable=True)
    importeConIva = Column(String, nullable=True)

    def __init__(self, codigo, codigoProv, numPedido, fechaAlb, codigoAnimal, pesoAnimal, precioAnimalEuroKg,
    importeSinIva, porcentajeIva, importeConIva):
        self.codigo = codigo
        self.codigoProv = codigoProv
        self.numPedido = numPedido
        self.fechaAlb = fechaAlb
        self.codigoAnimal = codigoAnimal
        self.pesoAnimal = pesoAnimal
        self.precioAnimalEuroKg = precioAnimalEuroKg
        self.importeSinIva = importeSinIva
        self.porcentajeIva = porcentajeIva
        self.importeConIva = importeConIva


if __name__ == "__main__":
    tryCreateDatabase()

