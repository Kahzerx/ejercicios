package utils;

public class StringUtils {
    // Clase de helpers para strings.
    public static boolean stringCheck(String... stuffs) {  // Comprueba si alguna string está vacía.
        for (String s : stuffs) {
            if (s.trim().equals("")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInt(String... ints) {  // Bueno... eso.
        try {
            for (String s : ints) {
                Integer.parseInt(s);
            }
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
