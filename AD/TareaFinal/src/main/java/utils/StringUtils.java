package utils;

public class StringUtils {
    public static boolean stringCheck(String... stuffs) {
        for (String s : stuffs) {
            if (s.trim().equals("")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isInt(String... ints) {
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
