package fr.robotv2.robotcore.town.listeners.flags;

import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.impl.Flag;
import fr.robotv2.robotcore.town.listeners.TownListener;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvent extends TownListener {

	public InteractEvent(TownModule townModule) {
		super(townModule);
	}

	@EventHandler
	public void interact(PlayerInteractEvent e) {
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Block block = e.getClickedBlock();

			if(block == null) return;

			Chunk chunk = block.getRelative(e.getBlockFace()).getChunk();
			Player player = e.getPlayer();
			Flag flag = Flag.INTERACT;

			if(this.needCancel(chunk, player, flag)) {
				e.setCancelled(true);
			}
		}
	}
}
