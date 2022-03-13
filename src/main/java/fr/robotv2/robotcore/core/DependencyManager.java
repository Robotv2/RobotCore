package fr.robotv2.robotcore.core;

import co.aikar.commands.PaperCommandManager;
import fr.robotv2.robotcore.shared.StringUtil;
import fr.robotv2.robotcore.shared.config.ConfigAPI;
import fr.robotv2.robotcore.shared.dependencies.VaultAPI;
import fr.robotv2.robotcore.shared.ui.GuiAPI;

public class DependencyManager {

    private static PaperCommandManager manager;

    public static void loadDependencies() {
        DependencyManager.registerConfigAPI();
        DependencyManager.registerVault();
        DependencyManager.registerAcf();
    }

    private static void registerVault() {
        if(VaultAPI.initialize()) {
            StringUtil.log("&aSuccessfully hooked into Vault.");
        } else {
            StringUtil.log("&cVault couldn't be found, Please make sure Vault AND a valid economy plugin are installed");
            RobotCore.getInstance().disablePlugin();
        }
    }

    private static void registerAcf() {
        DependencyManager.manager = new PaperCommandManager(RobotCore.getInstance());
    }

    private static void registerConfigAPI() {
        ConfigAPI.init(RobotCore.getInstance());
    }

    private static void registerUI() {
        RobotCore.getInstance().getServer().getPluginManager().registerEvents(new GuiAPI(), RobotCore.getInstance());
    }

    public static PaperCommandManager getCommandManager() {
        return manager;
    }
}
