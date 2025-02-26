package dmvmc.setNameColor;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
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
        if (!(sender instanceof Player targetPlayer))
            return true;

        // Check permission to set own color
        if (!sender.hasPermission("setnamecolor.set")) {
            sender.sendMessage(Component.text("You do not have permission to use this command!", NamedTextColor.RED));
            return true;
        }

        // Ensure a value has been passed
        if (args.length < 1 || args.length > 2) {
            sender.sendMessage("Usage: /setnamecolor <color|#rrggbb> [playerName]");
            return true;
        }

        // Check permission to set other color
        if (args.length == 2 && !sender.hasPermission("setnamecolor.setOther")) {
            sender.sendMessage(Component.text("You do not have permission to use set other players colors!", NamedTextColor.RED));
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
            sender.sendMessage(Component.text("Invalid color format! Please use a named color or hex code.", NamedTextColor.RED));
            return true;
        }

        // Find specified player
        if (args.length == 2) targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            sender.sendMessage(Component.text("Player not found!", NamedTextColor.RED));
            return true;
        }

        // Set player color and send success message
        plugin.setPlayerColor(targetPlayer.getUniqueId(), color);
        sender.sendMessage(Component.text()
                .append(Component.text("You have set " + ((args.length == 2) ?  args[1] + "'s" : "your") + " name color to ", NamedTextColor.WHITE))
                .append(Component.text(input, color))
                .append(Component.text("!", NamedTextColor.WHITE)));

        return true;

    }

}
