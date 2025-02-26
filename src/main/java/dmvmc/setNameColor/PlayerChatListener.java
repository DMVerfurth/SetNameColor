package dmvmc.setNameColor;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {

    private final SetNameColor plugin;
    public PlayerChatListener(SetNameColor plugin) { this.plugin = plugin; }

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {

        // Retrieve player name color
        Player player = event.getPlayer();
        TextColor color = plugin.getPlayerColor(player.getUniqueId());

        // Set player display name color
        Component coloredDisplayName = Component.text(player.getName(), color);
        player.displayName(coloredDisplayName);

    }

}
