package com.spectrasonic.PanoramaMaker.Managers;

import com.spectrasonic.PanoramaMaker.Config.ConfigManager;
import com.spectrasonic.Utils.MessageUtils;
import com.spectrasonic.Utils.SoundUtils;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
public class PanoramaManager {

    private final ConfigManager configManager;
    private final Set<UUID> activePlayers = new HashSet<>();
    private BukkitTask panoramaTask;

    public boolean startPanorama(Player player) {
        if (activePlayers.contains(player.getUniqueId())) {
            return false;
        }

        activePlayers.add(player.getUniqueId());
        runPanoramaSequence(player);
        return true;
    }

    public void stopPanorama(Player player) {
        if (panoramaTask != null) {
            panoramaTask.cancel();
        }
        activePlayers.remove(player.getUniqueId());
    }

    private void runPanoramaSequence(Player player) {
    panoramaTask = new BukkitRunnable() {
        int step = 0;

        @Override
        public void run() {
            switch (step) {
                case 0:
                    initialInstructions(player);
                    break;
                case 1:
                    takeScreenshot(player, 0, 0, 0);
                    break;
                case 2:
                    takeScreenshot(player, 90, 0, 1);
                    break;
                case 3:
                    takeScreenshot(player, -180, 0, 2);
                    break;
                case 4:
                    takeScreenshot(player, -90, 0, 3);
                    break;
                case 5:
                    takeScreenshot(player, -180, -90, 4);
                    break;
                case 6:
                    takeScreenshot(player, 0, 90, 5);
                    break;
                case 7:
                    finishSequence(player);
                    this.cancel();
                    return;
            }
            step++;
        }
    }.runTaskTimer(configManager.getPlugin(), 0L, configManager.getWaitTimeTicks());
}

    private void initialInstructions(Player player) {
        MessageUtils.sendMessage(player, configManager.getConfig().getString("messages.initial-instructions"));
    }

    private void takeScreenshot(Player player, float yaw, float pitch, int index) {
        Location loc = player.getLocation();
        loc.setYaw(yaw);
        loc.setPitch(pitch);
        player.teleport(loc);
        MessageUtils.sendMessage(player, configManager.getConfig().getString("messages.screenshot-instruction")
                .replace("%index%", String.valueOf(index)));
        SoundUtils.playerSound(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
    }

    private void finishSequence(Player player) {
        MessageUtils.sendMessage(player, configManager.getConfig().getString("messages.finished"));
        SoundUtils.playerSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        activePlayers.remove(player.getUniqueId());
    }

    public boolean isPlayerActive(Player player) {
        return activePlayers.contains(player.getUniqueId());
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
