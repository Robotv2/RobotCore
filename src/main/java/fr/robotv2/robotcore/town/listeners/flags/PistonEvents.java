package fr.robotv2.robotcore.town.listeners.flags;

import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.impl.Flag;
import fr.robotv2.robotcore.town.impl.Town;
import fr.robotv2.robotcore.town.listeners.TownListener;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import java.util.List;
import java.util.Optional;

public class PistonEvents extends TownListener {

	public PistonEvents(TownModule townModule) {
		super(townModule);
	}

	@EventHandler
	public void extend(BlockPistonExtendEvent e) {
		List<Block> blocks = e.getBlocks();
		Block piston = e.getBlock();
		BlockFace dir = e.getDirection();
		
		if(isInChunkExtend(blocks, piston, dir)) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void retract(BlockPistonRetractEvent e) {
		List<Block> blocks = e.getBlocks();
		Block piston = e.getBlock();
		
		if(isInChunkRetract(blocks, piston)) {
			e.setCancelled(true);
		}
	}

	public boolean isInChunkExtend(List<Block> blocks, Block piston, BlockFace face) {
		for(Block block : blocks) {
			if(block.getRelative(face).getType() != Material.AIR)
				continue;

			Chunk chunk = block.getRelative(face).getChunk();
			if(piston.getChunk().equals(chunk)) continue;

			Optional<Town> optionalTown = getTownModule().getChunkManager().getTown(chunk);
			if (optionalTown.isEmpty()) return false;
			Town town = optionalTown.get();

			if(!Flag.PISTON_EXTEND.isEnabledFor(town)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInChunkRetract(List<Block> blocks, Block piston) {
		for(Block block : blocks) {
			Chunk chunk = block.getChunk();
			if(piston.getChunk().equals(chunk)) continue;

			Optional<Town> optionalTown = getTownModule().getChunkManager().getTown(chunk);
			if (optionalTown.isEmpty()) return false;
			Town town = optionalTown.get();

			if(!Flag.PISTON_EXTRACT.isEnabledFor(town)) {
				return true;
			}
		}
		return false;
	}
}
