package editor.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

// No tengo ni idea de por qué he metido esto.
public class LanguageUtils {
    private static Map<String, String> translationMap;
    public static String currentLanguage;

    public static boolean trySetLanguage(String language) {
        String lang;
        try {
            // Sacar el contenido del json que contiene el idioma.
            lang = IOUtils.toString(Objects.requireNonNull(LanguageUtils.class.getClassLoader().getResourceAsStream(String.format("assets/editor/lang/%s.json", language))), StandardCharsets.UTF_8);
        }
        catch (NullPointerException | IOException e) {
            return false;
        }

        // Serialización y convertir en array para acceder facilmente con la key correcta.
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
        Type type = new TypeToken<Map<String, String>>() {}.getType();
        Map<String, String> map1 = gson.fromJson(lang, type);

        currentLanguage = language;
        translationMap = map1;
        return true;
    }

    public static String getTranslation(String translationKey) {
        return translationMap.get(translationKey);
    }
}
