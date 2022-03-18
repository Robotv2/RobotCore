package fr.robotv2.robotcore.town.serializer;

import fr.robotv2.robotcore.shared.serializer.LocationSerializer;
import fr.robotv2.robotcore.town.impl.Parcelle;
import org.bukkit.Location;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ParcelleSerializer {
    public static Parcelle fromString(String parcelle) {
        String[] args = parcelle.split("!");
        Location firstBound = LocationSerializer.fromString(args[0]);
        Location secondBound = LocationSerializer.fromString(args[1]);
        UUID townUuid = UUID.fromString(args[2]);
        return new Parcelle(firstBound, secondBound, townUuid);
    }

    public static String toString(Parcelle parcelle) {
        String firstBound = LocationSerializer.toString(parcelle.getFirstBound());
        String secondBound = LocationSerializer.toString(parcelle.getSecondBound());
        UUID townUuid = parcelle.getOwner();
        return firstBound + "!" + secondBound + "!" + townUuid.toString();
    }

    public static Set<Parcelle> fromStrings(Collection<String> parcelles) {
        return parcelles.stream().map(ParcelleSerializer::fromString).collect(Collectors.toSet());
    }

    public static List<String> toStrings(Collection<Parcelle> parcelles) {
        return parcelles.stream().map(ParcelleSerializer::toString).collect(Collectors.toList());
    }
}
