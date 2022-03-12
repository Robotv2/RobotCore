package fr.robotv2.robotcore.jobs.data.stock;

import com.zaxxer.hikari.HikariDataSource;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.data.JobData;
import fr.robotv2.robotcore.jobs.impl.job.Job;
import fr.robotv2.robotcore.jobs.impl.job.JobId;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class MysqlData implements JobData {

    private final JobModule jobModule;
    private final String TABLE_PLAYER_DATA = "robotcore_jobs_players";

    protected HikariDataSource source;

    public MysqlData(JobModule jobModule) {
        this.jobModule = jobModule;
    }

    protected void createMainTable() {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                     "CREATE TABLE IF NOT EXISTS " + TABLE_PLAYER_DATA + " ('UUID' VARCHAR(50), 'PLAYER_NAME' VARCHAR(50), 'JOB_ID' VARCHAR(100), 'LEVEL' INT, 'EXP' DOUBLE, 'ENABLED' BOOLEAN)");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    @Override
    public void load() {
        //Setup hikari
        source = new HikariDataSource();
        source.setMaximumPoolSize(5);
        source.setJdbcUrl("jdbc:mysql://" +
                jobModule.getConfig().getString("storage.mysql-credentials.host") + ":" +
                jobModule.getConfig().getString("storage.mysql-credentials.port") + "/" +
                jobModule.getConfig().getString("storage.mysql-credentials.database"));
        source.addDataSourceProperty("user", jobModule.getConfig().getString("storage.mysql-credentials.username"));
        source.addDataSourceProperty("password", jobModule.getConfig().getString("storage.mysql-credentials.password"));
        source.addDataSourceProperty("useSSL", jobModule.getConfig().getBoolean("storage.mysql-credentials.use-ssl"));

        this.createMainTable();
    }

    @Override
    public void initPlayer(UUID playerUUID) {}

    @Override
    public void close() {
        if(source.isClosed()) return;
        source.close();
    }

    @Override
    public int getLevel(UUID playerUUID, JobId id) {
        try(Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT LEVEL FROM " + TABLE_PLAYER_DATA + " WHERE (UUID=? and JOB_ID=?)");
            statement.setString(1, playerUUID.toString());
            statement.setString(2, id.toString());
            ResultSet result = statement.executeQuery();
            if(result.next())
                return result.getInt("LEVEL");

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }

    @Override
    public double getExp(UUID playerUUID, JobId id) {
        try(Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT EXP FROM " + TABLE_PLAYER_DATA + " WHERE (UUID=? and JOB_ID=?)");
            statement.setString(1, playerUUID.toString());
            statement.setString(2, id.toString());
            ResultSet result = statement.executeQuery();
            if(result.next())
                return result.getDouble("EXP");

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return 0;
    }

    public boolean isEnabled(UUID playerUUID, JobId id) {
        try(Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT ENABLED FROM " + TABLE_PLAYER_DATA + " WHERE (UUID=? and JOB_ID=?)");
            statement.setString(1, playerUUID.toString());
            statement.setString(2, id.toString());
            ResultSet result = statement.executeQuery();
            if(result.next())
                return result.getBoolean("ENABLED");

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return false;
    }

    @Override
    public Set<Job> getActiveJobs(UUID playerUUID) {
        Set<Job> jobs = new HashSet<>();

        try(Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT JOB_ID FROM " + TABLE_PLAYER_DATA + " WHERE (UUID=? AND ENABLED=?)");
            statement.setString(1, playerUUID.toString());
            statement.setBoolean(2, true);
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                String jobId = result.getString("JOB_ID");
                if(jobModule.exist(jobId)) {
                    Job job = jobModule.getJob(jobId);
                    jobs.add(job);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return jobs;
    }

    @Override
    public Set<Job> getJobs(UUID playerUUID) {
        Set<Job> jobs = new HashSet<>();

        try(Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("SELECT JOB_ID FROM " + TABLE_PLAYER_DATA + " WHERE (UUID=?)");
            statement.setString(1, playerUUID.toString());
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                String jobId = result.getString("JOB_ID");
                if(jobModule.exist(jobId)) {
                    Job job = jobModule.getJob(jobId);
                    jobs.add(job);
                }
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return jobs;
    }

    @Override
    public void setLevel(UUID playerUUID, JobId id, int value) {
        try(Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("UPDATE " + TABLE_PLAYER_DATA + " SET LEVEL=? WHERE (UUID=? AND JOB_ID=?)");
            statement.setInt(1, value);
            statement.setString(2, playerUUID.toString());
            statement.setString(3, id.toString());
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void setExp(UUID playerUUID, JobId id, double value) {
        try(Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("UPDATE " + TABLE_PLAYER_DATA + " SET EXP=? WHERE (UUID=? AND JOB_ID=?)");
            statement.setDouble(1, value);
            statement.setString(2, playerUUID.toString());
            statement.setString(3, id.toString());
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void setEnabled(UUID playerUUID, JobId id, boolean value) {
        try (Connection connection = getConnection()) {

            PreparedStatement statement = connection.prepareStatement("UPDATE " + TABLE_PLAYER_DATA + " SET ENABLED=? WHERE (UUID=? AND JOB_ID=?)");
            statement.setBoolean(1, value);
            statement.setString(2, playerUUID.toString());
            statement.setString(3, id.toString());
            statement.executeUpdate();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void setActiveJobs(UUID playerUUID, Set<Job> jobs) {
        Set<Job> jobsRegistered = getJobs(playerUUID);

        for(Job job : jobs) {
            if(!jobsRegistered.contains(job)) {
                try(Connection connection = getConnection()) {
                    PreparedStatement statement = connection.prepareStatement("INSERT INTO " + TABLE_PLAYER_DATA + " VALUES (?, ?, ?, ?, ?, ?)");
                    statement.setString(1, playerUUID.toString());
                    statement.setString(2, Bukkit.getOfflinePlayer(playerUUID).getName());
                    statement.setString(3, job.getJobId().toString());
                    statement.setInt(4, 0);
                    statement.setDouble(5, 0D);
                    statement.setBoolean(6, true);
                    statement.executeUpdate();
                } catch (SQLException exception) {
                    exception.printStackTrace();
                }
            }
        }

        for(Job job : jobsRegistered) {
            this.setEnabled(playerUUID, job.getJobId(), jobs.contains(job));
        }
    }

    @Override
    public boolean needAsync() {
        return true;
    }
}
