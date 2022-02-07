package fr.robotv2.robotcore.jobs.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;

public class MatUtil {

    public static boolean isCrops(Material material) {
        return switch (material) {
            case WHEAT, CARROT, BEETROOT, MELON_STEM, PUMPKIN -> true;
            default -> false;
        };
    }

    public static boolean isSeed(Material material) {
        return switch (material) {
            case BEETROOT_SEEDS, MELON_SEEDS, PUMPKIN_SEEDS, WHEAT_SEEDS, CARROT -> true;
            default -> false;
        };
    }

    public static boolean isFullyGrown(Block block) {
        BlockData data = block.getBlockData();
        if (isCrops(block.getType()) && data instanceof Ageable age) {
            return age.getAge() == age.getMaximumAge();
        } else {
            return false;
        }
    }
}
