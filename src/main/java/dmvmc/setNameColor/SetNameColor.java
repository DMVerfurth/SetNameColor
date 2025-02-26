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
import java.util.Optional;
import java.util.UUID;

public final class SetNameColor extends JavaPlugin {

    private final Map<UUID, TextColor> playerColors = new HashMap<>();
    private File playerColorsFile;
    private FileConfiguration playerColorsConfig;

    @Override
    public void onEnable() {

        // Register command and chat listener
        setupConfig();
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

    private void setupConfig() {
        createDataFolder();
        createPlayerColorsFile();
        loadPlayerColors();
    }

    private void createDataFolder() {
        if (!getDataFolder().exists() && getDataFolder().mkdirs()) {
            getLogger().info("Created data folder for SetNameColor");
        }
    }

    private void createPlayerColorsFile() {

        // Create player color yml if none exists
        playerColorsFile = new File(getDataFolder(), "playerColors.yml");
        if (!playerColorsFile.exists()) {
            try {
                if (playerColorsFile.createNewFile()) {
                    getLogger().info("Created playerColors.yml");
                }
            } catch (IOException e) {
                getLogger().severe("Could not create playerColors.yml");
            }
        }

        // Loads yml file configuration
        playerColorsConfig = YamlConfiguration.loadConfiguration(playerColorsFile);

    }

    private void loadPlayerColors() {

        // Check for saved player colors
        if (!playerColorsConfig.contains("playerColors")) return;

        playerColorsConfig.getConfigurationSection("playerColors")
                .getKeys(false)
                .forEach(key -> {
                    UUID uuid = UUID.fromString(key);
                    Optional.ofNullable(playerColorsConfig.getString("playerColors." + key))
                            .filter(this::isValidHexColor)
                            .map(TextColor::fromHexString)
                            .ifPresent(color -> playerColors.put(uuid, color));
                });

    }

    private void savePlayerColors() {

        // Check for player colors file configuration
        if (playerColorsConfig == null || playerColorsFile == null) return;

        // Set each player color
        playerColorsConfig.set("playerColors", null);
        playerColors.forEach((uuid, color) ->
                playerColorsConfig.set("playerColors." + uuid, color.asHexString()));

        // Save colors to file
        try {
            playerColorsConfig.save(playerColorsFile);
        } catch (IOException e) {
            getLogger().severe("Could not save playerColors.yml");
        }

    }

    private boolean isValidHexColor(String hex) {
        return hex != null && hex.matches("^#[0-9a-fA-F]{6}$");
    }

}
