package fr.robotv2.robotcore.jobs.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.BlockData;

public class MatUtil {

    public static boolean isCrops(Material material) {
        switch (material) {
            case WHEAT:
            case CARROT:
            case BEETROOT:
            case MELON_STEM:
            case PUMPKIN:
                return true;
        }
        return false;
    }

    public static boolean isSeed(Material material) {
        switch (material) {
            case BEETROOT_SEEDS:
            case MELON_SEEDS:
            case PUMPKIN_SEEDS:
            case WHEAT_SEEDS:
            case CARROT:
                return true;
        }
        return false;
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
