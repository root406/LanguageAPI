package de.radjaguar2005.languageAPI.manager;

import de.radjaguar2005.languageAPI.database.MySQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LanguageManager {
    private final MySQL mysql;

    public LanguageManager(MySQL mysql) {
        this.mysql = mysql;
    }

    // Methode zum Setzen der Sprache eines Spielers
    public void setPlayerLanguage(UUID uuid, String language) {
        try (Connection connection = mysql.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "REPLACE INTO player_languages (uuid, language) VALUES (?, ?)")) {
            statement.setString(1, uuid.toString());
            statement.setString(2, language);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methode zum Abrufen der Sprache eines Spielers
    public String getPlayerLanguage(UUID uuid) {
        String language = "en_US"; // Standard-Sprache
        try (Connection connection = mysql.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT language FROM player_languages WHERE uuid = ?")) {
            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                language = resultSet.getString("language");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return language;
    }
}
