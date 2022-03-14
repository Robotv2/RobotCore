package fr.robotv2.robotcore.shared;

import fr.robotv2.robotcore.shared.config.Config;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class MessageAPI {

    private final Config config;
    private final Map<String, String> paths = new HashMap<>();

    public MessageAPI(Config config) {
        this.config = config;
        this.config.setup();
    }

    public Config getFile() {
        return config;
    }

    public String getPrefix() {
        return getPath("prefix");
    }

    public String getPath(String path) {
        if(paths.containsKey(path))
            return paths.get(path);
        else {
            String message = StringUtil.colorize(config.get().getString(path));
            paths.put(path, message);
            return message;
        }
    }

    public void clearPaths() {
        paths.clear();
    }

    public void sendPath(CommandSender sender, String path) {
        StringUtil.sendMessage(sender, getPrefix() + getPath(path), false);
    }

    public void sendMessage(CommandSender sender, String message) {
        StringUtil.sendMessage(sender, getPrefix() + message, false);
    }
}
