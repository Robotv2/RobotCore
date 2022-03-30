package fr.robotv2.robotcore.shared;

import net.md_5.bungee.api.chat.TranslatableComponent;

public class TranslationAPI {

    public static String translate(String key) {
        return new TranslatableComponent(key).toLegacyText();
    }
}
