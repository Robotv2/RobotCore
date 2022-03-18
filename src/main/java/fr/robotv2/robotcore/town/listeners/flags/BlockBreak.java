package fr.robotv2.robotcore.town.listeners.flags;

import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.impl.Flag;
import fr.robotv2.robotcore.town.listeners.TownListener;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;


public class BlockBreak extends TownListener {

	public BlockBreak(TownModule townModule) {
		super(townModule);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e) {

		Chunk chunk = e.getBlock().getChunk();
		Player player = e.getPlayer();
		Flag flag = Flag.BLOCK_BREAK;

		if(this.needCancel(chunk, player, flag)) {
			e.setCancelled(true);
		}
	}
}
