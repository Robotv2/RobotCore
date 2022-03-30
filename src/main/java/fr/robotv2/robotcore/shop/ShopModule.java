package fr.robotv2.robotcore.shop;

import fr.robotv2.robotcore.core.module.Module;
import fr.robotv2.robotcore.shared.StringUtil;
import fr.robotv2.robotcore.shared.config.Config;
import fr.robotv2.robotcore.shared.config.ConfigAPI;
import fr.robotv2.robotcore.shop.impl.Shop;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public class ShopModule implements Module {

    private final Map<String, Shop> shops = new HashMap<>();
    private final Config config = ConfigAPI.getConfig("shop-module" + File.separator + "config");
    private JavaPlugin plugin;

    @Override
    public void onEnable(JavaPlugin plugin) {
        this.plugin = plugin;
        this.registerShopsFromDir();
    }

    @Override
    public void onDisable() {}

    //<- GETTERS ->

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public Shop getShop(File file) {
        return getShop(file.getName());
    }

    public Shop getShop(String name) {
        return shops.get(name.toLowerCase());
    }

    public Collection<Shop> getShops() {
        return shops.values();
    }

    public Set<String> getShopsName() {
        return shops.keySet();
    }

    private File[] getShopFiles() {
        File directory = new File(plugin.getDataFolder() + File.separator + "shop-module" + File.separator + "shops");
        if(!directory.exists()) {
            this.registerDefaultShops();
            return getShopFiles();
        } else if(directory.listFiles() != null){
            return directory.listFiles();
        } else {
            return null;
        }
    }

    //<- REGISTRATION ->

    private void registerShopsFromDir() {
        if(getShopFiles() == null) return;
        Arrays.stream(getShopFiles()).filter(File::isFile).forEach(this::registerShop);
    }

    private void registerDefaultShops() {
        ConfigAPI.getConfig("shop-module" + File.separator + "shops" + "default").setup();
    }

    public void registerShop(File file) {
        try {
            FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);
            Shop shop = new Shop(configuration);
            shop.initialize();
            shops.put(file.getName().replace(".yml", "").toLowerCase(), shop);
        } catch (Exception exception) {
            StringUtil.log("&cAn error occurred while trying to load the shop for the file: " + file.getName());
            StringUtil.log("&cError message: " + exception.getMessage());
            exception.printStackTrace();
        }
    }

    //<- CONFIGURATION ->

    public FileConfiguration getConfiguration() {
        return config.get();
    }

    public void saveConfiguration() {
        config.save();
    }

    public void reloadConfiguration() {
        config.reload();
    }
}
