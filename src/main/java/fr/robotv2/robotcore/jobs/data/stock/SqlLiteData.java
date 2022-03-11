package fr.robotv2.robotcore.jobs.data.stock;

import com.zaxxer.hikari.HikariDataSource;
import fr.robotv2.robotcore.jobs.JobModule;

import java.io.File;
import java.io.IOException;

public class SqlLiteData extends MysqlData {

    private final JobModule jobModule;
    private File database;

    public SqlLiteData(JobModule jobModule) {
        super(jobModule);
        this.jobModule = jobModule;
    }

    private void setupDatabaseFile() {
        try {
            File dbFile = new File(jobModule.getPlugin().getDataFolder(), "job-module" + File.separator + "database.db");
            if(!dbFile.exists())
                dbFile.createNewFile();
            database = dbFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load() {
        setupDatabaseFile();
        source = new HikariDataSource();
        source.setMaximumPoolSize(5);
        source.setJdbcUrl("jdbc:sqlite:" + database.getPath());
        this.createMainTable();
    }
}
