package fr.robotv2.robotcore.shop.impl;

import fr.robotv2.robotcore.core.RobotCore;
import fr.robotv2.robotcore.core.module.ModuleType;
import fr.robotv2.robotcore.shared.TranslationAPI;
import fr.robotv2.robotcore.shared.item.ItemAPI;
import fr.robotv2.robotcore.shop.ShopModule;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopItem {

    private final static Map<ConfigurationSection, ShopItem> cache = new HashMap<>();
    public static ShopItem getShopItem(ConfigurationSection section) {
        if(cache.containsKey(section))
            return cache.get(section);

        Material material = Material.valueOf(section.getString("material", "STONE"));
        int slot = section.getInt("slot", 0);
        int quantity = section.getInt("quantity", 1);
        double toSell = section.getDouble("sell-price", -1);
        double toBuy = section.getDouble("buy-price", -1);
        ShopItem item = new ShopItem(material, slot, quantity, toSell, toBuy);
        cache.put(section, item);

        return item;
    }

    private final ShopModule shopModule = RobotCore.getInstance()
            .getModuleRegistry()
            .getModule(ShopModule.class, ModuleType.SHOP);

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

    public ItemStack getItemToShow() {
        return new ItemAPI.ItemBuilder()
                .setName(translatedName())
                .setLore(translatedLore())
                .setType(material)
                .setAmount(quantity)
                .setKey("shop-item", 1)
                .build();
    }

    public ItemStack getItemToGive() {
        return new ItemStack(material, quantity);
    }

    private String translatedName() {
        String translation = TranslationAPI.translate(material.translationKey());
        String format = shopModule.getConfiguration().getString("gui.item.name", "&7%type%");
        return format.replace("%type%", translation);
    }

    private List<String> translatedLore() {
        List<String> formats = shopModule.getConfiguration().getStringList("gui.item.lore");
        return formats.stream().map(this::translatedLore).collect(Collectors.toList());
    }

    private String translatedLore(String format) {
        String cantSign = shopModule.getConfiguration().getString("gui.cant-sign", "&c✗");
        return format
                .replace("%buy-price%", toBuy >= 0 ? String.valueOf(toBuy) : cantSign)
                .replace("%sell-price%", toSell >= 0 ? String.valueOf(toSell) : cantSign)
                .replace("%quantity%", String.valueOf(quantity));
    }
}
