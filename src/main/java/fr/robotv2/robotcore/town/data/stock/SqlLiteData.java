package fr.robotv2.robotcore.town.data.stock;

import com.zaxxer.hikari.HikariDataSource;
import fr.robotv2.robotcore.town.TownModule;

import java.io.File;
import java.io.IOException;

public class SqlLiteData extends MysqlData {
    private final TownModule townModule;
    private File database;

    public SqlLiteData(TownModule townModule) {
        this.townModule = townModule;
    }

    private void setupDatabaseFile() {
        try {
            File dbFile = new File(townModule.getPlugin().getDataFolder(), "town-module" + File.separator + "database.db");
            if(!dbFile.exists())
                dbFile.createNewFile();
            database = dbFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init() {
        setupDatabaseFile();
        source = new HikariDataSource();
        source.setMaximumPoolSize(5);
        source.setJdbcUrl("jdbc:sqlite:" + database.getPath());
        this.createTables();
    }
}
