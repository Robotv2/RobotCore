package fr.robotv2.robotcore.town.listeners.flags;

import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.impl.Flag;
import fr.robotv2.robotcore.town.impl.Town;
import fr.robotv2.robotcore.town.listeners.TownListener;
import org.bukkit.Chunk;
import org.bukkit.block.Block;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class TntEvent extends TownListener {

    public TntEvent(TownModule townModule) {
        super(townModule);
    }

    @EventHandler
    public void onTnt(EntityExplodeEvent e) {
        if(!(e.getEntity() instanceof TNTPrimed)) {
            return;
        }

        List<Block> blocks = e.blockList();
        Iterator<Block> it = blocks.iterator();

        while (it.hasNext()) {
            Block block = it.next();
            Chunk chunk = block.getChunk();

            Optional<Town> optionalTown = getTownModule().getChunkManager().getTown(chunk);
            if (optionalTown.isEmpty()) return;
            Town town = optionalTown.get();

            if(!Flag.TNT.isEnabledFor(town))
                it.remove();
        }
    }
}
