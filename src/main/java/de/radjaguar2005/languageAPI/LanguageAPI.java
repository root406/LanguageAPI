package de.radjaguar2005.languageAPI;

import de.radjaguar2005.languageAPI.commands.Language;
import de.radjaguar2005.languageAPI.database.MySQL;
import de.radjaguar2005.languageAPI.manager.LanguageManager; // Importiere LanguageManager
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class LanguageAPI extends JavaPlugin {
    private MySQL mysql;
    private LanguageManager languageManager;
    private static LanguageAPI instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();


        String host = getConfig().getString("mysql.host");
        int port = getConfig().getInt("mysql.port");
        String database = getConfig().getString("mysql.database");
        String username = getConfig().getString("mysql.username");
        String password = getConfig().getString("mysql.password");

        mysql = new MySQL(host, port, database, username, password);

        try {
            mysql.connect();
            setupDatabase();

            languageManager = new LanguageManager(mysql);
            getCommand("language").setExecutor(new Language(languageManager));
            System.out.println("LanguageAPI Plugin wurde erfolgreich aktiviert.");
        } catch (SQLException e) {
            e.printStackTrace();
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        try {
            mysql.disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupDatabase() throws SQLException {
        try (Connection connection = mysql.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS player_languages (" +
                    "uuid VARCHAR(36) PRIMARY KEY, " +
                    "language VARCHAR(10)" +
                    ");");
        }
    }

    public MySQL getMySQL() {
        return mysql;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public static LanguageAPI getInstance() {
        return instance;
    }
}
