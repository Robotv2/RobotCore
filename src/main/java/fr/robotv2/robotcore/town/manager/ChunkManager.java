package fr.robotv2.robotcore.town.manager;

import fr.robotv2.robotcore.town.impl.Town;
import org.bukkit.Chunk;

import java.util.Optional;

public record ChunkManager(TownManager townManager) {

    public Optional<Town> getTown(Chunk chunk) {
        return townManager.getTowns()
                .stream()
                .filter(town -> town.getTerritories().contains(chunk))
                .findFirst();
    }

    public boolean isClaim(Chunk chunk) {
        return getTown(chunk).isPresent();
    }

    public void claim(Chunk chunk, Town town) {
        if(!isClaim(chunk))
            town.addChunk(chunk);
    }

    public void unclaim(Chunk chunk, Town town) {
        if(isClaim(chunk) && town.getTerritories().contains(chunk))
            town.removeChunk(chunk);
    }
}
