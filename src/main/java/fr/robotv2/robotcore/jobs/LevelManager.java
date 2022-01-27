package fr.robotv2.robotcore.jobs;

import fr.robotv2.robotcore.jobs.enums.JobType;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LevelManager {

    private final Map<UUID, Map<JobType, Integer>> levels = new HashMap<>();
    private final Map<UUID, Map<JobType, Double>> experiences = new HashMap<>();

    public void initializePlayer(Player player) {
        //TODO: Data management system.

        if(!levels.containsKey(player.getUniqueId())) {
            levels.put(player.getUniqueId(), new HashMap<>());
        }

        if(!experiences.containsKey(player.getUniqueId())) {
            experiences.put(player.getUniqueId(), new HashMap<>());
        }
    }

    public int getLevel(Player player, JobType type) {
        return levels.get(player.getUniqueId()).get(type);
    }

    public void setLevel(Player player, JobType type, Integer level) {
        Map<JobType, Integer> result = levels.get(player.getUniqueId());
        result.put(type, level);
        levels.put(player.getUniqueId(), result);
    }

    public Double getExp(Player player, JobType type) {
        return experiences.get(player.getUniqueId()).get(type);
    }

    public void setExp(Player player, JobType type, Double exp) {
        Map<JobType, Double> result = experiences.get(player.getUniqueId());
        result.put(type, exp);
        experiences.put(player.getUniqueId(), result);
    }

    public void giveExp(Player player, JobType type, Double value) {
        setExp(player, type, getExp(player, type) + value);
    }

    public Double getExpNeeded(Player player, JobType type) {
        int level = this.getLevel(player, type);
        return (level * 30.5D) * (level * level);
    }

    public boolean canLevelUp(Player player, JobType type) {
        return this.getExp(player, type) >= getExp(player, type);
    }
}
