package de.radjaguar2005.languageAPI.commands;

import de.radjaguar2005.languageAPI.LanguageAPI;
import de.radjaguar2005.languageAPI.manager.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Map;

public class Language implements CommandExecutor {
    private final LanguageManager languageManager;
    private final FileConfiguration config;


    public Language(LanguageManager languageManager) {
        this.languageManager = languageManager;
        this.config = LanguageAPI.getInstance().getConfig();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return true;
        }

        Player player = (Player) sender;


        Map<String, Object> languages = config.getConfigurationSection("languages").getValues(false);


        if (args.length != 1) {
            player.sendMessage("Verfügbare Sprachen:");
            languages.forEach((code, name) -> player.sendMessage("- " + name + " (" + code + ")"));
            return true;
        }

        String selectedLanguage = args[0];


        if (!languages.containsKey(selectedLanguage)) {
            player.sendMessage("Ungültige Sprache! Verfügbare Sprachen:");
            languages.forEach((code, name) -> player.sendMessage("- " + name + " (" + code + ")"));
            return true;
        }

        languageManager.setPlayerLanguage(player.getUniqueId(), selectedLanguage);
        player.sendMessage("Deine Sprache wurde auf " + languages.get(selectedLanguage) + " gesetzt.");
        return true;
    }
}
