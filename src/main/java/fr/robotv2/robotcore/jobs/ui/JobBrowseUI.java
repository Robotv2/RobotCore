package fr.robotv2.robotcore.jobs.ui;

import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import fr.robotv2.robotcore.shared.item.ItemAPI;
import fr.robotv2.robotcore.shared.ui.GUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class JobBrowseUI implements GUI {

    private final JobModule jobModule;
    private ItemStack empty;
    public JobBrowseUI(JobModule jobModule) {
        this.jobModule = jobModule;
    }

    @Override
    public String getName(Player player, Object... objects) {
        return "&7Your jobs";
    }

    @Override
    public int getSize() {
        return 9;
    }

    @Override
    public void contents(Player player, Inventory inv, Object... objects) {
        for(int i = 0; i <= 8; i++) inv.setItem(i, getEmpty());
        for(Job job : jobModule.getPlayerManager().getJobs(player)) {
            inv.setItem(job.getSlot(), job.getItem(player));
        }
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType click) {
        if(ItemAPI.hasKey(current, "job", PersistentDataType.STRING)) {
            String id = (String) ItemAPI.getKeyValue(current, "job", PersistentDataType.STRING);
            Job job = jobModule.getJob(id);

            switch (click) {
                case LEFT -> {

                }
            }
        }
    }

    @Override
    public void onClose(Player player, InventoryCloseEvent event) {

    }

    public ItemStack getEmpty() {
        if(this.empty == null)
            this.empty = new ItemAPI.ItemBuilder().setType(Material.GRAY_STAINED_GLASS_PANE).setName("&8").build();
        return this.empty;
    }
}
