package fr.robotv2.robotcore.town.listeners.flags;

import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.impl.Flag;
import fr.robotv2.robotcore.town.listeners.TownListener;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;

public class DropEvent extends TownListener {

	public DropEvent(TownModule townModule) {
		super(townModule);
	}

	@EventHandler
	public void onDrop(PlayerDropItemEvent e) {
		Chunk chunk = e.getPlayer().getChunk();
		Player player = e.getPlayer();
		Flag flag = Flag.DROP;

		if (this.needCancel(chunk, player, flag)) {
			e.setCancelled(true);
		}
	}
}
