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
}
