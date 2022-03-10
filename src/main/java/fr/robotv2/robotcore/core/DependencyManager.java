package fr.robotv2.robotcore.core;

import co.aikar.commands.PaperCommandManager;
import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.api.dependencies.VaultAPI;

import java.util.Locale;

public class DependencyManager {

    private static PaperCommandManager manager;

    public static void loadDependencies() {
        DependencyManager.registerVault();
        DependencyManager.loadAcf();
    }

    private static void registerVault() {
        if(VaultAPI.initialize()) {
            StringUtil.log("&aSuccessfully hooked into Vault.");
        } else {
            StringUtil.log("&cVault couldn't be found, disabling...");
            RobotCore.getInstance().disablePlugin();
        }
    }

    private static void loadAcf() {
        DependencyManager.manager = new PaperCommandManager(RobotCore.getInstance());
    }

    public static PaperCommandManager getCommandManager() {
        return manager;
    }
}
