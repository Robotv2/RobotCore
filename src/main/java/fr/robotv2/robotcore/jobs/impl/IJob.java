package fr.robotv2.robotcore.jobs.impl;

import fr.robotv2.robotcore.jobs.enums.Action;
import fr.robotv2.robotcore.jobs.enums.JobType;
import fr.robotv2.robotcore.jobs.events.EntityKillByPlayerEvent;
import fr.robotv2.robotcore.jobs.events.HarvestBreakEvent;
import fr.robotv2.robotcore.jobs.events.HarvestPlaceEvent;
import fr.robotv2.robotcore.jobs.events.PlayerKillByPlayerEvent;
import org.bukkit.ChatColor;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.Set;

public abstract class IJob {

    public abstract JobType getType();

    public abstract Set<Action> getActions();

    public abstract ChatColor getChatColor();

    public void handleBreak(BlockBreakEvent event) {
        throw new IllegalArgumentException("This method has to be override");
    }

    public void handlePlace(BlockPlaceEvent event) {
        throw new IllegalArgumentException("This method has to be override");
    }

    public void handleHarvestBreak(HarvestBreakEvent event) {
        throw new IllegalArgumentException("This method has to be override");
    }

    public void handleHarvestPlant(HarvestPlaceEvent event) {
        throw new IllegalArgumentException("This method has to be override");
    }

    public void handlePlayerKillByPlayer(PlayerKillByPlayerEvent event) {
        throw new IllegalArgumentException("This method has to be override");
    }

    public void handleEntityKillByPlayer(EntityKillByPlayerEvent event) {
        throw new IllegalArgumentException("This method has to be override");
    }
}
