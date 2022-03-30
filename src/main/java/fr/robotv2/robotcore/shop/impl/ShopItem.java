package fr.robotv2.robotcore.shop.impl;

import fr.robotv2.robotcore.shared.item.ItemAPI;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ShopItem {

    private final static Map<ConfigurationSection, ShopItem> cache = new HashMap<>();

    public static ShopItem getShopItem(ConfigurationSection section) {
        if(cache.containsKey(section)) return cache.get(section);

        Material material = Material.valueOf(section.getString("material", "STONE"));
        int slot = section.getInt("slot", 0);
        int quantity = section.getInt("quantity", 1);
        double toSell = section.getDouble("sell-price", -1);
        double toBuy = section.getDouble("buy-price", -1);
        ShopItem item = new ShopItem(material, slot, quantity, toSell, toBuy);
        cache.put(section, item);

        return item;
    }

    private final Material material;
    private final int quantity;
    private final int slot;
    private final double toSell;
    private final double toBuy;

    public ShopItem(Material material, int slot, int quantity, double toSell, double toBuy) {
        this.material = material;
        this.quantity = quantity;
        this.slot = slot;
        this.toSell = toSell;
        this.toBuy = toBuy;
    }

    public Material getMaterial() {
        return material;
    }

    public int getSlot() {
        return slot;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getToSell() {
        return toSell;
    }

    public double getToBuy() {
        return toBuy;
    }

    private ItemStack item;
    public ItemStack getItemToShow() {
        if(item == null)
            item = new ItemAPI.ItemBuilder().setType(material).build();
        return item;
    }
}
