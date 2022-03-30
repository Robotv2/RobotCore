package fr.robotv2.robotcore.shared;

import com.google.common.base.Charsets;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.robotv2.robotcore.core.RobotCore;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class TranslationAPI {

    private static final Map<Locale, List<TranslationProvider>> translations = new HashMap<>();

    public static boolean isTranslationAvailable(Locale locale) {
        return translations.containsKey(locale);
    }

    private static void registerLocale(Locale locale) {

        String tagUnder = locale.toLanguageTag().replace('-', '_');
        String tagUnderLower = tagUnder.toLowerCase(locale);

        if(!isTranslationAvailable(locale)) {
            try {
                List<TranslationProvider> providers = new ArrayList<>();

                TranslationProvider provider1 = new JsonProvider( "/assets/minecraft/lang/" + tagUnderLower + ".json" );
                TranslationProvider provider2 = new JsonProvider( "/mojang-translations/" + tagUnderLower + ".json");
                TranslationProvider provider3 = new ResourceBundleProvider( "mojang-translations/" + tagUnder);

                providers.add(provider1);
                providers.add(provider2);
                providers.add(provider3);

                translations.put(locale, providers);
            } catch (Exception e) {
                StringUtil.log("&cAn error occurred while trying to register language: " + locale);
                e.printStackTrace();
            }
        }
    }

    public static String translate(Player player, String key) {
        return translate(player.locale(), key);
    }

    @Nullable
    public static String translate(Locale locale, String key) {

        if(!isTranslationAvailable(locale)) {
            TranslationAPI.registerLocale(locale);
            return null;
        }

        List<TranslationProvider> providers = translations.get(locale);

        for(TranslationProvider provider : providers) {
            String translation = provider.translate(key);
            if(translation != null) {
                return translation;
            }
        }

        return null;
    }

    private interface TranslationProvider {
        String translate(String s);
    }

    private static class ResourceBundleProvider implements TranslationProvider {

        private final ResourceBundle bundle;

        public ResourceBundleProvider(String bundlePath) {
            this.bundle = ResourceBundle.getBundle(bundlePath);
        }

        @Override
        public String translate(String s) {
            return (bundle.containsKey(s)) ? bundle.getString(s) : null;
        }
    }

    private static class JsonProvider implements TranslationProvider {

        private final Map<String, String> translations = new HashMap<>();

        public JsonProvider(String resourcePath) throws IOException {
            try (InputStreamReader rd = new InputStreamReader(RobotCore.class.getResourceAsStream(resourcePath), Charsets.UTF_8)) {
                JsonObject obj = new Gson().fromJson( rd, JsonObject.class );
                for ( Map.Entry<String, JsonElement> entries : obj.entrySet()) {
                    translations.put( entries.getKey(), entries.getValue().getAsString() );
                }
            }
        }

        @Override
        public String translate(String s) {
            return translations.get( s );
        }
    }
}
