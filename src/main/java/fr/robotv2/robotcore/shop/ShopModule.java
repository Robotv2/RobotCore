package fr.robotv2.robotcore.shop;

import fr.robotv2.robotcore.core.module.Module;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class ShopModule implements Module {

    public String PATH_TO_SHOPS_DIRECT;

    @Override
    public void onEnable(JavaPlugin plugin) {
        this.PATH_TO_SHOPS_DIRECT = plugin.getDataFolder() + File.separator + "shop-module";
    }

    @Override
    public void onDisable() {

    }
}
