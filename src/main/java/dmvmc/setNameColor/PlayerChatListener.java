package dmvmc.setNameColor;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {

    private final SetNameColor plugin;

    public PlayerChatListener(SetNameColor plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onAsyncChat(AsyncChatEvent event) {

        // Retrieve player name color
        Player player = event.getPlayer();
        TextColor color = plugin.getPlayerColor(player.getUniqueId());

        // Render player message with their set name color
        event.renderer((source, displayName, message, viewer) -> Component.text()
            .append(Component.text(source.getName()).color(color))
            .append(Component.text(": ").color(NamedTextColor.GRAY))
            .append(message)
            .build()
        );

    }

}
