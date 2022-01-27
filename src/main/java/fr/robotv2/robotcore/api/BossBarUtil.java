package fr.robotv2.robotcore.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BossBarUtil {

    private static final Map<UUID, BossBar> bossBars = new HashMap<>();

    public static BossBar createOrGetBar(Player player) {
        if(bossBars.get(player.getUniqueId()) == null) {
            BossBar bar = Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID);
            bossBars.put(player.getUniqueId(), bar);
            return bar;
        } else {
            return bossBars.get(player.getUniqueId());
        }
    }

    public static void clearPlayer(Player player) {
        bossBars.remove(player.getUniqueId());
    }

    public static BarColor toBarColor(ChatColor color) {
        try {
            return BarColor.valueOf(color.toString());
        } catch(Exception e) {
            return BarColor.WHITE;
        }
    }
}
