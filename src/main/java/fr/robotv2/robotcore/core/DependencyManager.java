package fr.robotv2.robotcore.core;

import co.aikar.commands.PaperCommandManager;
import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.api.dependencies.VaultAPI;

public class DependencyManager {

    private static PaperCommandManager manager;

    public static void loadDependencies() {
        DependencyManager.registerVault();
        DependencyManager.registerAcf();
    }

    private static void registerVault() {
        if(VaultAPI.initialize()) {
            StringUtil.log("&aSuccessfully hooked into Vault.");
        } else {
            StringUtil.log("&cVault couldn't be found, Please make sure Vault is installed AND a valid economy plugin.");
            RobotCore.getInstance().disablePlugin();
        }
    }

    private static void registerAcf() {
        DependencyManager.manager = new PaperCommandManager(RobotCore.getInstance());
    }

    public static PaperCommandManager getCommandManager() {
        return manager;
    }
}
