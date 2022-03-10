package fr.robotv2.robotcore.api.serializer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
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

    public static String toString(Location location) {

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        float yaw = location.getYaw();
        float pitch = location.getPitch();
        World world = location.getWorld();
        String coma = ";";

        return x + coma + y + coma + z + coma + yaw + coma + pitch + coma + world;
    }
}