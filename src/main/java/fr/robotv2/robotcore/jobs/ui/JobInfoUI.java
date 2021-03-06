package fr.robotv2.robotcore.jobs.ui;

import fr.robotv2.robotcore.jobs.impl.job.Job;
import fr.robotv2.robotcore.jobs.impl.job.JobAction;
import fr.robotv2.robotcore.shared.TranslationAPI;
import fr.robotv2.robotcore.shared.item.HeadUtil;
import fr.robotv2.robotcore.shared.item.ItemAPI;
import fr.robotv2.robotcore.shared.ui.GUI;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.Material;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class JobInfoUI implements GUI {

    private ItemStack empty;

    @Override
    public String getName(Player player, Object... objects) {
        Job job = (Job) objects[0];
        return "&8» " + job.getName();
    }

    @Override
    public int getSize() {
        return 54;
    }

    @Override
    public void contents(Player player, Inventory inv, Object... objects) {
        for(int i = 0; i < getSize(); i++)
            inv.setItem(i, getEmpty());

        Job job = (Job) objects[0];
        Configuration configuration = job.getConfigurationFile();

        int count = 0;
        for(JobAction action : job.getActions()) {
            ConfigurationSection section = configuration.getConfigurationSection("actions." + action.toString());
            if(section == null) return;
            for(String key : section.getKeys(false)) {
                ConfigurationSection valueSection = section.getConfigurationSection(key);
                if(valueSection == null) continue;
                double money = valueSection.getDouble("money");
                double exp = valueSection.getDouble("exp");
                ItemStack keyItem = getKeyItem(player, key, action, money, exp);
                if(keyItem == null) continue;
                inv.setItem(count, keyItem);
            }
        }
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType click) {}

    @Override
    public void onClose(Player player, InventoryCloseEvent event) {}

    public ItemStack getEmpty() {
        if(this.empty == null)
            this.empty = new ItemAPI.ItemBuilder().setType(Material.GRAY_STAINED_GLASS_PANE).setName("&8").build();
        return this.empty;
    }

    public ItemStack getKeyItem(Player player, String value, JobAction action, double money, double exp) {
        value = value.toUpperCase();
        String key = null;
        ItemStack item = null;

        try {
            EntityType entityType = EntityType.valueOf(value);
            key = entityType.translationKey();
            item = HeadUtil.getHead(entityType);
        } catch (IllegalArgumentException exception) {
            try {
                Material material = Material.valueOf(value);
                key = material.translationKey();
                item = new ItemStack(material);
            } catch (IllegalArgumentException ignored) {
            }
        }

        if(item == null)
            return null;

        ItemAPI.ItemBuilder builder = ItemAPI.toBuilder(item);
        return builder.setName("&8» &e" + this.getNameFromKey(key))
                .setLore(
                        "&8&m&l-----------",
                        "&eAction &8- &f" + action.getTranslation(),
                        "&eMoney &8- &f" + money + "$",
                        "&eExp &8- &f" + exp,
                        "&8&m&l-----------").build();
    }

    private String getNameFromKey(String key) {
        String translation = TranslationAPI.translate(key);
        return translation.substring(0, 1).toUpperCase() + translation.substring(1);
    }
}
