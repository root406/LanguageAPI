# LanguageAPI

A LanguageAPI plugin for Spigot to simplify developers' ability to query a player's language.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Example](#examples)

## Features

- Easily set and get the player's language.
- Supports multiple languages.
- Integrates with MySQL for persistent language storage.

## Installation

1. Download the latest release from the [Releases](https://github.com/yourusername/LanguageAPI/releases) page.
2. Place the JAR file into the `plugins` directory of your Spigot server.
3. Restart the server to load the plugin.
4. Configure the MySQL settings in `config.yml`.

## Usage

Once the plugin is installed, players can set their language using the following command:
/language

## Examples

JoinListener: 

```java
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        String playerLanguage = LanguageAPI.getInstance().getLanguageManager().getPlayerLanguage(player.getUniqueId());

        String welcomeMessage = TranslationManager.getMessage(playerLanguage, "welcome");
        player.sendMessage(welcomeMessage);
    }
```

FileManager: 

```java
public static void loadLanguages(JavaPlugin plugin) {
        String[] languages = {"en_US", "de_DE", "en_GB"}; // Füge hier weitere Sprachen hinzu

        for (String lang : languages) {
            try {
                File langFile = new File(plugin.getDataFolder(), "messages_" + lang + ".properties");

                // Überprüfen, ob die Sprachdatei existiert, andernfalls erstellen
                if (!langFile.exists()) {
                    try (InputStream in = plugin.getResource("messages_" + lang + ".properties")) {
                        if (in != null) {
                            Files.copy(in, langFile.toPath());
                        }
                    }
                }

                // Lade die Sprachdatei
                Map<String, String> messages = new HashMap<>();
                try (BufferedReader reader = new BufferedReader(new FileReader(langFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split("=", 2);
                        if (parts.length == 2) {
                            messages.put(parts[0].trim(), parts[1].trim());
                        }
                    }
                }
                languageMessages.put(lang, messages);
            } catch (IOException e) {
                plugin.getLogger().log(Level.SEVERE, "Fehler beim Laden der Sprachdatei: " + lang, e);
            }
        }
    }

    public static String getMessage(String languageCode, String messageKey) {
        return languageMessages.getOrDefault(languageCode, languageMessages.get("en_US")).get(messageKey);
    }
```
