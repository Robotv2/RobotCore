package fr.robotv2.robotcore.shared.serializer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LocationSerializer {

    public static Location fromString(@Nullable String location) {

        if(location == null)
            return null;

        String[] args = location.split(";");
        double x = Double.parseDouble(args[0]);
        double y = Double.parseDouble(args[1]);
        double z = Double.parseDouble(args[2]);
        float yaw = Float.parseFloat(args[3]);
        float pitch = Float.parseFloat(args[4]);
        World world = Bukkit.getWorld(args[5]);

        return new Location(world, x, y, z, yaw, pitch);
    }

    public static String toString(@NotNull Location location) {

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        String world = location.getWorld().getName();
        String coma = ";";

        return x + coma + y + coma + z + coma + yaw + coma + pitch + coma + world;
    }
}
