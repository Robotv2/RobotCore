package fr.robotv2.robotcore.shared.serializer;

import fr.robotv2.robotcore.shared.StringUtil;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class ChunkSerializer {
    public static Chunk getChunkFromString(String chunkStr) {
        String[] args = chunkStr.split(";");
        try {
            int x = Integer.parseInt(args[0]);
            int z = Integer.parseInt(args[1]);
            String worldName = args[2];
            World world = Bukkit.getWorld(worldName);
            if(world == null) return null;
            return world.getChunkAt(x, z);
        } catch (NumberFormatException | NullPointerException e) {
            StringUtil.log("&cUne erreur a eu lieu lors de la serialization du chunk " + chunkStr);
            StringUtil.log("&cMessage d'erreur: " + e.getMessage());
        }
        return null;
    }


    public static String getStringFromChunk(Chunk chunk) {
        String x = String.valueOf(chunk.getX());
        String y = String.valueOf(chunk.getZ());
        String worldName = chunk.getWorld().getName();
        return x + ";" + y + ";" + worldName;
    }

    public static Set<Chunk> getChunksToSet(Collection<String> chunks) {
        return chunks.stream().map(ChunkSerializer::getChunkFromString).collect(Collectors.toSet());
    }

    public static Set<String> getChunkFromSet(Collection<Chunk> chunks) {
        return chunks.stream().map(ChunkSerializer::getStringFromChunk).collect(Collectors.toSet());
    }
}
