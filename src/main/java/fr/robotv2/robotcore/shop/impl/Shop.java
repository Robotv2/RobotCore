package fr.robotv2.robotcore.shop.impl;

import fr.robotv2.robotcore.shared.dependencies.VaultAPI;
import fr.robotv2.robotcore.shared.item.ItemAPI;
import fr.robotv2.robotcore.shared.ui.GUI;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class Shop implements GUI {

    private final FileConfiguration config;
    private final List<ShopItem> items = new ArrayList<>();

    public void initialize() {
        this.loadItems(config);
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

    public Shop(FileConfiguration config) {
        this.config = config;
    }

    @Override
    public String getName(Player player, Object... objects) {
        return config.getString("name", "&7Default Name");
    }

    @Override
    public int getSize() {
        return config.getInt("slot", 54);
    }

    @Override
    public void contents(Player player, Inventory inv, Object... objects) {
        for(ShopItem item : items) {
            inv.setItem(item.getSlot(), item.getItemToShow());
        }
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType click) {
        if(!ItemAPI.hasKey(current, "shop-item", PersistentDataType.INTEGER))
            return;

        ShopItem item = items.stream().filter(shopItem -> shopItem.getSlot() == slot).findAny().orElse(null);

        if(item == null)
            return;

        ShopAction action = click == ClickType.LEFT ? ShopAction.BUY : ShopAction.SELL;
        this.action(player, action, item);
    }

    @Override
    public void onClose(Player player, InventoryCloseEvent event) {}

    public void action(Player player, ShopAction action, ShopItem item) {
        switch (action) {

            case BUY -> {
                double price = item.getToBuy();
                if(price < 0) return;
                if(VaultAPI.hasEnough(player, price)) {
                    VaultAPI.takeMoney(player, price);
                    player.getInventory().addItem(item.getItemToGive());
                    player.updateInventory();
                }
            }

            case SELL -> {
                double price = item.getToSell();
                if(price < 0) return;
                ItemStack toSell = new ItemStack(item.getMaterial());
                if(player.getInventory().containsAtLeast(toSell, item.getQuantity())) {
                    toSell.setAmount(item.getQuantity());
                    player.getInventory().removeItem(toSell);
                    player.updateInventory();
                    VaultAPI.giveMoney(player, price);
                }
            }
        }
    }
}
