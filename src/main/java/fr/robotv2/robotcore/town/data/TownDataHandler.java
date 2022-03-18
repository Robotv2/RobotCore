package fr.robotv2.robotcore.town.data;

import fr.robotv2.robotcore.shared.StringUtil;
import fr.robotv2.robotcore.shared.data.DataType;
import fr.robotv2.robotcore.town.TownModule;
import fr.robotv2.robotcore.town.data.stock.MysqlData;
import org.bukkit.configuration.file.FileConfiguration;

public class TownDataHandler {
    private DataType dataType = DataType.SQLITE;
    private TownData data;

    public void initializeStorage(TownModule townModule, FileConfiguration configuration) {
        String DATA_TYPE = configuration.getString("storage.type");
        this.setDataType(DATA_TYPE);
        StringUtil.log("Data type for the job module: " + dataType.toString());
        this.loadStorage(townModule);
    }

    private void loadStorage(TownModule townModule) {
        switch (dataType) {
            case MYSQL -> new MysqlData();
        }
        getData().init();
    }

    public void setDataType(DataType type) {
        this.dataType = type;
    }

    public void setDataType(String type) {
        if(type == null || type.isBlank()) {
            this.setDataType(DataType.SQLITE);
        } else {
            try {
                DataType dataType = DataType.valueOf(type.toUpperCase());
                this.setDataType(dataType);
            } catch (IllegalArgumentException exception) {
                StringUtil.log(type + " isn't a correct data-type.");
                this.setDataType(DataType.SQLITE);
            }
        }
    }

    public DataType getDataType() {
        return dataType;
    }

    public TownData getData() {
        return data;
    }
}
