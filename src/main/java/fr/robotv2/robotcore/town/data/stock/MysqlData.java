package fr.robotv2.robotcore.town.data.stock;

import com.zaxxer.hikari.HikariDataSource;
import fr.robotv2.robotcore.shared.serializer.ChunkSerializer;
import fr.robotv2.robotcore.town.data.TownData;
import fr.robotv2.robotcore.town.impl.Parcelle;
import fr.robotv2.robotcore.town.impl.Town;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.Nullable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class MysqlData implements TownData {

    protected HikariDataSource source;

    //TABLES
    private final String TOWN_TABLE = "robotcore_town_towns";
    private final String PLAYER_TABLE = "robotcore_town_players";
    private final String TERRITORY_TABLE = "robotcore_town_territory";


    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    private void createTownTable() {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + TOWN_TABLE + " ('UUID' VARCHAR(50), 'NAME' VARCHAR(50), 'CHEF_UUID' VARCHAR(100), 'BANK' DOUBLE)");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createPlayersTable() {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + PLAYER_TABLE + " ('PLAYER_UUID' VARCHAR(50), 'TOWN' VARCHAR(50))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTerritoryTable() {
        try (Connection connection = getConnection()) {
            PreparedStatement ps = connection.prepareStatement(
                    "CREATE TABLE IF NOT EXISTS " + TERRITORY_TABLE + " ('TERRITORY' VARCHAR(50), 'TOWN' VARCHAR(50))");
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    protected void createTables() {
        this.createTownTable();
        this.createPlayersTable();
        this.createTerritoryTable();
    }

    @Override
    public void init() {
        this.createTables();
    }

    @Override
    public void stop() {
        source.close();
    }

    @Override
    public UUID getTownUUID(UUID playerUUID) {
        try (Connection connection = getConnection()) {

            System.out.println(playerUUID);

            PreparedStatement ps = connection.prepareStatement("SELECT TOWN FROM " + PLAYER_TABLE + " WHERE 'PLAYER_UUID'=?");
            ps.setString(1, playerUUID.toString());
            ResultSet result = ps.executeQuery();

            if(result.next()) {
                return UUID.fromString(result.getString("TOWN"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void setTown(UUID playerUUID, @Nullable UUID townUUID) {
        try (Connection connection = getConnection()) {

            PreparedStatement ps;

            if(townUUID != null) {
                if(getTownUUID(playerUUID) == null) {
                    System.out.println("INSERT");
                    ps = connection.prepareStatement("INSERT INTO " + PLAYER_TABLE + " VALUES (?, ?)");
                    ps.setString(1, playerUUID.toString());
                    ps.setString(2, townUUID.toString());
                } else {
                    System.out.println("UPDATE");
                    ps = connection.prepareStatement("UPDATE " + PLAYER_TABLE + " SET TOWN=? WHERE 'PLAYER_UUID'=?");
                    ps.setString(1, townUUID.toString());
                    ps.setString(2, playerUUID.toString());
                }
            } else {
                ps = connection.prepareStatement("DELETE FROM " + PLAYER_TABLE + " WHERE 'PLAYER_UUID'=?");
                ps.setString(1, playerUUID.toString());
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getName(UUID townUUID) {
        try (Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement("SELECT NAME FROM " + TOWN_TABLE + " WHERE 'UUID'=?");
            ps.setString(1, townUUID.toString());
            ResultSet result = ps.executeQuery();
            if(result.next()) {
                return result.getString("NAME");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setName(UUID townUUID, String name) {
        try (Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement("UPDATE " + TOWN_TABLE + " SET NAME=? WHERE 'UUID'=?");
            ps.setString(1, name);
            ps.setString(2, townUUID.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OfflinePlayer getChef(UUID townUUID) {
        try (Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement("SELECT CHEF_UUID FROM " + TOWN_TABLE + " WHERE 'UUID'=?");
            ps.setString(1, townUUID.toString());
            ResultSet result = ps.executeQuery();
            if(result.next()) {
                UUID playerUUID = UUID.fromString(result.getString("CHEF_UUID"));
                return Bukkit.getOfflinePlayer(playerUUID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void setChef(UUID townUUID, OfflinePlayer chef) {
        try (Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement("UPDATE " + TOWN_TABLE + " SET CHEF_UUID=? WHERE 'UUID'=?");
            ps.setString(1, chef.getUniqueId().toString());
            ps.setString(2, townUUID.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<OfflinePlayer> getMembers(UUID townUUID) {
        List<OfflinePlayer> members = new ArrayList<>();

        try (Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement("SELECT PLAYER_UUID FROM " + PLAYER_TABLE + " WHERE 'TOWN'=?");
            ps.setString(1, townUUID.toString());
            ResultSet result = ps.executeQuery();

            while(result.next()) {
                UUID playerUUID = UUID.fromString(result.getString("PLAYER_UUID"));
                members.add(Bukkit.getOfflinePlayer(playerUUID));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return members;
    }

    @Override
    public void setMembers(List<OfflinePlayer> members, UUID townUUID) {

        List<UUID> current = members.stream()
                .map(OfflinePlayer::getUniqueId).collect(Collectors.toList());

        List<UUID> before = getMembers(townUUID).stream()
                .map(OfflinePlayer::getUniqueId).collect(Collectors.toList());

        for(UUID uuid : current) {
            if(!before.contains(uuid)) {
                this.setTown(uuid, townUUID);
            }
        }

        for(UUID uuid : before) {
            if(!current.contains(uuid)) {
                this.setTown(uuid, null);
            }
        }
    }

    @Override
    public double getBank(UUID townUUID) {
        try (Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement("SELECT BANK FROM " + TOWN_TABLE + " WHERE 'UUID'=?");
            ps.setString(1, townUUID.toString());
            ResultSet result = ps.executeQuery();
            if(result.next()) {
                return result.getDouble("BANK");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @Override
    public void setBank(double value, UUID townUUID) {
        try (Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement("UPDATE " + TOWN_TABLE + " SET BANK=? WHERE 'UUID'=?");
            ps.setDouble(1, value);
            ps.setString(2, townUUID.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Chunk> getTerritories(UUID townUUID) {
        Set<Chunk> territories = new HashSet<>();

        try(Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement("SELECT TERRITORY FROM " + TERRITORY_TABLE + " WHERE 'TOWN'=?");
            ps.setString(1, townUUID.toString());
            ResultSet result = ps.executeQuery();

            while(result.next()) {
                Chunk chunk = ChunkSerializer.getChunkFromString(result.getString("TERRITORY"));
                if(chunk != null)
                    territories.add(chunk);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return territories;
    }

    @Override
    public void setTerritories(Set<Chunk> currents, UUID townUUID) {
        Set<Chunk> before = getTerritories(townUUID);

        try (Connection connection = getConnection()) {

            for(Chunk chunk : currents) {
                if(!before.contains(chunk)) {
                    String serializedChunk = ChunkSerializer.getStringFromChunk(chunk);
                    PreparedStatement ps = connection.prepareStatement("INSERT INTO " + TERRITORY_TABLE + " VALUES ('?', '?')");
                    ps.setString(1, serializedChunk);
                    ps.setString(2, townUUID.toString());
                    ps.executeUpdate();
                }
            }

            for(Chunk chunk : before) {
                if(!currents.contains(chunk)) {
                    String serializedChunk = ChunkSerializer.getStringFromChunk(chunk);
                    PreparedStatement ps = connection.prepareStatement("DELETE FROM " + TERRITORY_TABLE + " WHERE 'TERRITORY'=?");
                    ps.setString(1, serializedChunk);
                    ps.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Set<Parcelle> getParcelles(UUID townUUID) {
        return null;
    }

    @Override
    public void setParcelle(Set<Parcelle> parcelles, UUID townUUID) {

    }

    @Override
    public Town createTown(UUID townUUID, UUID playerUUID, String name) {
        try (Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement("INSERT INTO " + TOWN_TABLE + " VALUES (?, ?, ?, ?)");
            ps.setString(1, townUUID.toString());
            ps.setString(2, name);
            ps.setString(3, playerUUID.toString());
            ps.setDouble(4, 0);
            ps.executeUpdate();

            this.setTown(playerUUID, townUUID);
            OfflinePlayer chef = Bukkit.getOfflinePlayer(playerUUID);

            return new Town(townUUID, name, 0, chef, new ArrayList<>(), new HashSet<>());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void deleteTown(UUID townUUID) {
        try (Connection connection = getConnection()) {

            PreparedStatement ps = connection.prepareStatement("DELETE FROM " + TOWN_TABLE + " WHERE 'UUID'=?");
            ps.setString(1, townUUID.toString());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean needAsync() {
        return true;
    }
}
