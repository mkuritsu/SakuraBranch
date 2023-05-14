package io.github.itstaylz.sakurabranch;

import io.github.itstaylz.hexlib.storage.file.YamlFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MessagesConfig {

    private final YamlFile file;

    public MessagesConfig(JavaPlugin plugin) {
        plugin.saveResource("messages.yml", false);
        this.file = new YamlFile(new File(plugin.getDataFolder(), "messages.yml"));
    }

    public String getMessage(String id) {
        return this.file.getConfig().getString(id);
    }

    public void reload() {
        this.file.reload();
    }
}
