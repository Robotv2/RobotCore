package fr.robotv2.robotcore.jobs.ui;

import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import fr.robotv2.robotcore.shared.item.ItemAPI;
import fr.robotv2.robotcore.shared.ui.GUI;
import fr.robotv2.robotcore.shared.ui.GuiAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class JobBrowseUI implements GUI {

    private final JobModule jobModule;
    public JobBrowseUI(JobModule jobModule) {
        this.jobModule = jobModule;
    }

    @Override
    public String getName(Player player, Object... objects) {
        return jobModule.getConfig().getString("gui.browse-gui.name");
    }

    @Override
    public int getSize() {
        return jobModule.getConfig().getInt("gui.browse-gui.row") * 9;
    }

    @Override
    public void contents(Player player, Inventory inv, Object... objects) {
        for(String deco : jobModule.getConfig().getStringList("gui.browse-gui.decorations")) {
            String[] args = deco.split(":");
            if(args.length == 2) {
                inv.setItem(Integer.parseInt(args[0]), this.getDeco(Material.valueOf(args[1].toUpperCase())));
            } else if(args.length == 3) {
                int from = Integer.parseInt(args[0]);
                int to = Integer.parseInt(args[1]);
                Material material = Material.valueOf(args[2]);
                for(int i = from; i <= to; i++) {
                    inv.setItem(i, getDeco(material));
                }
            }
        }

        for(Job job : jobModule.getJobs()) {
            inv.setItem(job.getSlot(), job.getItem(player));
        }
    }

    @Override
    public void onClick(Player player, Inventory inv, ItemStack current, int slot, ClickType click) {
        if(ItemAPI.hasKey(current, "job", PersistentDataType.STRING)) {
            String id = (String) ItemAPI.getKeyValue(current, "job", PersistentDataType.STRING);
            Job job = jobModule.getJob(id);
            switch (click) {
                case RIGHT -> {
                    Bukkit.dispatchCommand(player, "jobs leave " + id);
                    player.closeInventory();
                }
                case LEFT -> {
                    Bukkit.dispatchCommand(player, "jobs join " + id);
                    player.closeInventory();
                }
                case SHIFT_RIGHT -> {
                    GuiAPI.open(player, JobInfoUI.class, job);
                    player.closeInventory();
                }
            }
        }
    }

    @Override
    public void onClose(Player player, InventoryCloseEvent event) {

    }

    public ItemStack getDeco(Material material) {
        return new ItemAPI.ItemBuilder().setType(material).setName("&8").addFlags(ItemFlag.HIDE_ATTRIBUTES).build();
    }
}
