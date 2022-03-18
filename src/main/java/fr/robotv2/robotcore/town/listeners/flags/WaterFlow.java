package fr.robotv2.robotcore.town.listeners.flags;

import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.impl.Flag;
import fr.robotv2.robotcore.town.impl.Town;
import fr.robotv2.robotcore.town.listeners.TownListener;
import org.bukkit.Chunk;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockFromToEvent;

import java.util.Optional;

public class WaterFlow extends TownListener {

	public WaterFlow(TownModule townModule) {
		super(townModule);
	}

	@EventHandler
	public void onWaterFlow(BlockFromToEvent e) {
		Chunk chunk = e.getToBlock().getChunk();
		if(e.getBlock().getChunk().equals(chunk)) return;

		Optional<Town> optionalTown = getTownModule().getChunkManager().getTown(chunk);
		if (optionalTown.isEmpty()) return;
		Town town = optionalTown.get();

		if(!Flag.BLOCK_FROM_OUTSIDE.isEnabledFor(town)) {
			e.setCancelled(true);
		}
	}
}
