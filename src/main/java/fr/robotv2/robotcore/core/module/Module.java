package fr.robotv2.robotcore.core.module;

import org.bukkit.plugin.java.JavaPlugin;

public interface Module {
    void onEnable(JavaPlugin plugin);
    void onDisable();
}
