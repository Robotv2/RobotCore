package fr.robotv2.robotcore.api;

import fr.robotv2.robotcore.api.config.Config;
import org.bukkit.command.CommandSender;

public class MessageAPI {

    private Config config;
    private String prefix;

    public MessageAPI(Config config) {
        this.config = config;
    }

    public void setFile(Config config) {
        this.config = config;
    }

    public Config getFile(Config config) {
        return config;
    }

    public void setPrefix(String prefix) {
        this.prefix = StringUtil.colorize(prefix);
    }

    public String getPrefix() {
        return prefix;
    }

    public String getPath(String path) {
        return StringUtil.colorize(config.get().getString(path));
    }

    public void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(message);
    }
}
