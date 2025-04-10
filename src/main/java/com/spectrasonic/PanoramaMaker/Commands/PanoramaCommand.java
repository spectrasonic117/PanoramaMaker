package com.spectrasonic.PanoramaMaker.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import com.spectrasonic.PanoramaMaker.Managers.PanoramaManager;
import com.spectrasonic.Utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@CommandAlias("panoramamaker|pm")
@RequiredArgsConstructor
public class PanoramaCommand extends BaseCommand {

    private final PanoramaManager panoramaManager;

    @Default
    public void onDefault(Player player) {
        MessageUtils.sendMessage(player, "<red>Usage: /pm make | stop | reload");
    }

    @Subcommand("make")
    @CommandPermission("panoramamaker.make")
    public void onMake(Player player) {
        if (panoramaManager.isPlayerActive(player)) {
            MessageUtils.sendMessage(player, "<red>You already have an active panorama session!");
            return;
        }

        if (!panoramaManager.startPanorama(player)) {
            MessageUtils.sendMessage(player, "<dark_purple><b>Wait until the other player finishes his panorama!");
        }
    }

    @Subcommand("stop")
    @CommandPermission("panoramamaker.stop")
    public void onStop(Player player) {
        panoramaManager.stopPanorama(player);
        MessageUtils.sendMessage(player, "<green>Panorama sequence stopped!");
    }

    @Subcommand("reload")
    @CommandPermission("panoramamaker.reload")
    public void onReload() {
        if (panoramaManager != null) {
            panoramaManager.getConfigManager().reloadConfig();
            MessageUtils.sendConsoleMessage("<green>Configuration reloaded!");
            } else {
                MessageUtils.sendConsoleMessage("<red>PanoramaManager is not initialized.");
        }
    }
}
