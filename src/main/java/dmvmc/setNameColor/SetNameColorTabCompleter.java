package dmvmc.setNameColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SetNameColorTabCompleter implements TabCompleter {

    private static final List<String> COLOR_NAMES = Arrays.asList(
            "red", "green", "blue", "yellow", "aqua", "gold", "gray", "black", "white", "purple", "pink",
            "#FF0000", "#00FF00", "#0000FF", "#FFFF00", "#00FFFF", "#FFD700",
            "#808080", "#000000", "#FFFFFF", "#800080", "#FFC0CB"
    );

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {

        // Suggest color names and hex codes
        if (args.length == 1 && sender.hasPermission("setnamecolor.set"))
            return COLOR_NAMES.stream()
                    .filter(color -> color.toLowerCase().startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());

        // Suggest users
        if (args.length == 2 && sender.hasPermission("setnamecolor.setOther"))
            return Bukkit.getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.toLowerCase().startsWith(args[1].toLowerCase()))
                    .collect(Collectors.toList());

        return Collections.emptyList();

    }

}
