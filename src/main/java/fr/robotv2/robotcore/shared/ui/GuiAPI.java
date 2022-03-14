package fr.robotv2.robotcore.shared.ui;

import fr.robotv2.robotcore.core.RobotCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

import static fr.robotv2.robotcore.shared.StringUtil.colorize;

public class GuiAPI implements Listener {
    private static final HashMap<Class<? extends GUI>, GUI> menus = new HashMap<>();
    private static final HashMap<UUID, GUI> players = new HashMap<>();

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        UUID playerUUID = player.getUniqueId();
        ItemStack item = e.getCurrentItem();

        if(item == null) return;
        if(!players.containsKey(playerUUID)) return;

        e.setCancelled(true);
        GUI menu = players.get(playerUUID);
        menu.onClick(player, e.getInventory(), item, e.getRawSlot(), e.getClick());
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player player = (Player) e.getPlayer();
        UUID playerUUID = e.getPlayer().getUniqueId();

        if(players.containsKey(playerUUID)) {

            GUI menu = players.get(playerUUID);
            menu.onClose(player, e);

            Bukkit.getScheduler().runTaskLater(RobotCore.getInstance(),() -> {
                if(player.getOpenInventory().getType() != InventoryType.CHEST)
                    players.remove(playerUUID);
            }, 3L);
        }
    }

    public static void addMenu(GUI gui){
        menus.put(gui.getClass(), gui);
    }

    public static void open(Player player, Class<? extends GUI> gClass, Object... objects){
        if(!menus.containsKey(gClass)) {
            RobotCore.getInstance().getLogger().warning(colorize("&cVous devez enregistrer l'inventaire avant de pouvoir l'ouvrir"));
            return;
        }

        GUI menu = menus.get(gClass);
        Inventory inv = Bukkit.createInventory(null, menu.getSize(), colorize(menu.getName(player, objects)));
        menu.contents(player, inv, objects);
        players.put(player.getUniqueId(), menu);

        Bukkit.getScheduler().runTaskLater(RobotCore.getInstance(), () -> {
            player.openInventory(inv);
        }, 2L);
    }
}
