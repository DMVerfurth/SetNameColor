package dmvmc.setNameColor;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class SetNameColor extends JavaPlugin {

    private final Map<UUID, TextColor> playerColors = new HashMap<>();

    private File playerColorsFile;
    private FileConfiguration playerColorsConfig;

    @Override
    public void onEnable() {

        // Create/load existing player colors file
        createPlayerColorsFile();
        loadPlayerColors();

        // Register command and chat listener
        getCommand("setnamecolor").setExecutor(new SetNameColorCommand(this));
        getCommand("setnamecolor").setTabCompleter(new SetNameColorTabCompleter());
        getServer().getPluginManager().registerEvents(new PlayerChatListener(this), this);
        getServer().getScheduler().runTaskTimer(this, this::savePlayerColors, 6000L, 6000L);
        getLogger().info("Enabled SetNameColor");

    }

    @Override
    public void onDisable() {
        savePlayerColors();
        getLogger().info("Disabled SetNameColor");
    }

    public TextColor getPlayerColor(UUID uuid) {
        return playerColors.getOrDefault(uuid, NamedTextColor.WHITE);
    }

    public void setPlayerColor(UUID uuid, TextColor color) {
        playerColors.put(uuid, color);
    }

    private void createPlayerColorsFile() {

        // Create data folder if none exists
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }

        // Create player color yml if none exists
        playerColorsFile = new File(getDataFolder(), "playerColors.yml");
        if (!playerColorsFile.exists()) {
            try {
                playerColorsFile.createNewFile();
            } catch (IOException e) {
                getLogger().severe("Could not create file for playerColors.yml");
            }
        }

        // Loads yml file configuration
        playerColorsConfig = YamlConfiguration.loadConfiguration(playerColorsFile);

    }

    private void loadPlayerColors() {

        // Check for saved player colors
        if (!playerColorsConfig.contains("playerColors")) { return; }

        // Load each player color entry
        for (String key : playerColorsConfig.getConfigurationSection("playerColors").getKeys(false)) {

            // Retrieve player and color
            UUID uuid = UUID.fromString(key);
            String hex = playerColorsConfig.getString("playerColors." + key);
            if (hex == null || !hex.startsWith("#") || hex.length() != 7) { continue; }

            // Set player and color
            TextColor color = TextColor.fromHexString(hex);
            playerColors.put(uuid, color);

        }

    }

    private void savePlayerColors() {

        // Check for player colors file configuration
        if (playerColorsConfig == null || playerColorsFile == null) { return; }

        // Set each player color
        playerColorsConfig.set("playerColors", null);
        for (Map.Entry<UUID, TextColor> entry : playerColors.entrySet()) {

            String hex = String.format("#%06X", entry.getValue().value());
            playerColorsConfig.set("playerColors." + entry.getKey(), hex);

        }

        // Save colors to file
        try {
            playerColorsConfig.save(playerColorsFile);
        } catch (IOException e) {
            getLogger().severe("Could not save playerColors.yml");
        }

    }

}
