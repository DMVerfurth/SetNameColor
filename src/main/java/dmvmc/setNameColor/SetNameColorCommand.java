package dmvmc.setNameColor;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetNameColorCommand implements CommandExecutor {

    private final SetNameColor plugin;

    public SetNameColorCommand(SetNameColor plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Only allow players to set their name color
        if (!(sender instanceof Player player)) {
            return true;
        }

        if (!sender.hasPermission("setnamecolor.set")) {
            sender.sendMessage(Component.text("You do not have permission to use this command!", NamedTextColor.RED));
            return true;
        }

        // Ensure a value has been passed
        if (args.length < 1) {
            sender.sendMessage("Usage: /setnamecolor <color|#rrggbb>");
            return true;
        }

        // Convert input to TextColor
        String input = args[0].toLowerCase();
        TextColor color = NamedTextColor.NAMES.value(input);
        if (color == null) {
            input = input.startsWith("#") ? input : "#" + input;
            color = TextColor.fromHexString(input);
        }

        // Check for successful TextColor conversion
        if (color == null) {
            sender.sendMessage("Usage: /setnamecolor <color|#rrggbb>");
            return true;
        }

        // Set player color and send success message
        plugin.setPlayerColor(player.getUniqueId(), color);
        player.sendMessage(Component.text()
                .append(Component.text("You have set your name color to ").color(NamedTextColor.WHITE))
                .append(Component.text(input).color(color))
                .append(Component.text("!").color(NamedTextColor.WHITE)));

        return true;

    }

}
