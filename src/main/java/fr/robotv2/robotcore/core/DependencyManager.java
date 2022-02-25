package fr.robotv2.robotcore.core;

import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.api.dependencies.VaultAPI;

public class DependencyManager {

    public static void loadDependencies() {
        DependencyManager.registerVault();
    }

    private static void registerVault() {
        if(VaultAPI.initialize()) {
            StringUtil.log("&aSuccessfully hooked into Vault.");
        } else {
            StringUtil.log("&cVault couldn't be found, disabling...");
            RobotCore.getInstance().getServer().getPluginManager().disablePlugin(RobotCore.getInstance());
        }
    }
}
