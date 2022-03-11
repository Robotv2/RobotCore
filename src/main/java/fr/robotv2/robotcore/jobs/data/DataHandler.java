package fr.robotv2.robotcore.jobs.data;

import fr.robotv2.robotcore.api.DataType;
import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.data.stock.MysqlData;
import fr.robotv2.robotcore.jobs.data.stock.SqlLiteData;
import fr.robotv2.robotcore.jobs.data.stock.YamlData;
import org.bukkit.configuration.file.FileConfiguration;

public class DataHandler {

    private DataType dataType = DataType.YAML;
    private JobData data;

    public void initializeStorage(JobModule jobModule, FileConfiguration configuration) {
        String DATA_TYPE = configuration.getString("data-type");
        this.setDataType(DATA_TYPE);
        StringUtil.log("Data type for the job module: " + dataType.toString());
        this.loadStorage(jobModule);
    }

    private void loadStorage(JobModule jobModule) {
        switch (dataType) {
            case YAML -> data = new YamlData(jobModule);
            case MYSQL -> data = new MysqlData(jobModule);
            case SQLITE -> data = new SqlLiteData(jobModule);
        }
        getData().load();
    }

    public void setDataType(DataType type) {
        this.dataType = type;
    }

    public void setDataType(String type) {
        if(type == null) {
            this.setDataType(DataType.YAML);
        } else {
            try {
                DataType dataType = DataType.valueOf(type.toUpperCase());
                this.setDataType(dataType);
            } catch (IllegalArgumentException exception) {
                StringUtil.log(type + " isn't a correct data-type.");
                this.setDataType(DataType.YAML);
            }
        }
    }

    public DataType getDataType() {
        return dataType;
    }

    public JobData getData() {
        return data;
    }
}
