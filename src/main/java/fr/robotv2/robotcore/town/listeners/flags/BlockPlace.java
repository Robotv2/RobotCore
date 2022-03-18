package fr.robotv2.robotcore.town.listeners.flags;

import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.impl.Flag;
import fr.robotv2.robotcore.town.listeners.TownListener;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlace extends TownListener {

	public BlockPlace(TownModule townModule) {
		super(townModule);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Chunk chunk = e.getBlock().getChunk();
		Player player = e.getPlayer();
		Flag flag = Flag.BLOCK_PLACE;

		if (this.needCancel(chunk, player, flag)) {
			e.setCancelled(true);
		}
	}
}
