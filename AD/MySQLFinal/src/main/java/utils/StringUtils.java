package utils;

public class StringUtils {
    /**
     * Check de si todas las strings que paso están vacías
     * @param stuffs strings.
     * @return Solo devuelve true si hay alguna vacía.
     */
    public static boolean isEmpty(String... stuffs) {  // Comprueba si alguna string está vacía.
        for (String s : stuffs) {
            if (s.trim().equals("")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNotInt(String... ints) {  // Bueno... eso.
        try {
            for (String s : ints) {
                Integer.parseInt(s);
            }
            return false;
        }
        catch (Exception e) {
            return true;
        }
    }
}
