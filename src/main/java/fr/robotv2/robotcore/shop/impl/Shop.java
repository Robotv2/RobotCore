package fr.robotv2.robotcore.shop.impl;

import fr.robotv2.robotcore.shared.config.Config;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Shop {

    private final Config config;
    private final List<ShopItem> items = new ArrayList<>();

    public void init() {
        this.loadItems(config.get());
    }

    private void loadItems(FileConfiguration configuration) {
        ConfigurationSection section = configuration.getConfigurationSection("items");
        if(section == null)
            return;
        for(String item : section.getKeys(false)) {
            ConfigurationSection itemSection = configuration.getConfigurationSection("items." + item);
            if(itemSection == null) continue;
            ShopItem shopItem =  ShopItem.getShopItem(itemSection);
            this.items.add(shopItem);
        }
    }

    public void reload() {
        this.items.clear();
        this.config.reload();
        this.init();
    }

    public Shop(Config config) {
        this.config = config;
    }

    public void build(Inventory inventory) {
        for(ShopItem item : items) {
            inventory.setItem(item.getSlot(), item.getItemToShow());
        }
    }
}
