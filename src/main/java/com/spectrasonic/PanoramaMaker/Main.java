package com.spectrasonic.PanoramaMaker;

import co.aikar.commands.PaperCommandManager;
import com.spectrasonic.PanoramaMaker.Commands.PanoramaCommand;
import com.spectrasonic.PanoramaMaker.Config.ConfigManager;
import com.spectrasonic.PanoramaMaker.Managers.PanoramaManager;
import com.spectrasonic.Utils.MessageUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private ConfigManager configManager;
    private PanoramaManager panoramaManager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        panoramaManager = new PanoramaManager(configManager);

        registerCommands();
        MessageUtils.sendStartupMessage(this);
    }

    @Override
    public void onDisable() {
        MessageUtils.sendShutdownMessage(this);
    }

    private void registerCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new PanoramaCommand(panoramaManager));
    }
}
