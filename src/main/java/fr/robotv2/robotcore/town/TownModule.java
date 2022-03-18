package fr.robotv2.robotcore.town;

import fr.robotv2.robotcore.core.module.Module;
import fr.robotv2.robotcore.shared.MessageAPI;
import fr.robotv2.robotcore.shared.config.ConfigAPI;
import fr.robotv2.robotcore.town.data.TownDataHandler;
import fr.robotv2.robotcore.town.listeners.flags.*;
import fr.robotv2.robotcore.town.manager.ChunkManager;
import fr.robotv2.robotcore.town.manager.PlayerManager;
import fr.robotv2.robotcore.town.manager.TownManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class TownModule implements Module {

    private JavaPlugin plugin;

    private TownManager townManager;
    private PlayerManager playerManager;
    private ChunkManager chunkManager;
    private TownDataHandler dataHandler;

    private MessageAPI townMessage;

    @Override
    public void onEnable(JavaPlugin plugin) {
        this.plugin = plugin;

        this.townManager = new TownManager(this);
        this.playerManager = new PlayerManager(townManager);
        this.chunkManager = new ChunkManager(townManager);
        this.dataHandler = new TownDataHandler();

        this.townMessage = new MessageAPI(ConfigAPI.getConfig("town-module" + File.separator + "messages"));

        this.loadListeners();
    }

    @Override
    public void onDisable() {}

    public void onReload() {}

    //<-- GETTERS -->

        //<-- PLUGIN -->

    public JavaPlugin getPlugin() {
        return plugin;
    }

        //<-- CLASSES -->

    public TownManager getTownManager() {
        return townManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public ChunkManager getChunkManager() {
        return chunkManager;
    }

    public MessageAPI getTownMessage() {
        return townMessage;
    }

    public TownDataHandler getDataHandler() {
        return dataHandler;
    }

    //<-- LOADERS -->

    public void loadListeners() {
        new BlockBreak(this);
        new BlockPlace(this);
        new DropEvent(this);
        new EntityDamage(this);
        new InteractEvent(this);
        new PickupEvent(this);
        new PistonEvents(this);
        new TntEvent(this);
        new WaterFlow(this);
    }
}
