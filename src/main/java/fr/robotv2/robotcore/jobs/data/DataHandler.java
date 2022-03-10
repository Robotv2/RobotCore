package fr.robotv2.robotcore.jobs.data;

import fr.robotv2.robotcore.api.StringUtil;
import fr.robotv2.robotcore.api.config.ConfigAPI;
import fr.robotv2.robotcore.jobs.JobModule;
import fr.robotv2.robotcore.jobs.data.stock.YamlData;
import fr.robotv2.robotcore.api.DataType;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class DataHandler {

    private DataType dataType = DataType.YAML;
    private JobData data;

    public void initializeStorage(JobModule jobModule, FileConfiguration configuration) {
        String DATA_TYPE = configuration.getString("data-type");
        if(DATA_TYPE != null) {
            try {
                DataType dataType = DataType.valueOf(DATA_TYPE.toUpperCase());
                this.setDataType(dataType);
            } catch (IllegalArgumentException exception) {
                StringUtil.log(DATA_TYPE + " isn't a correct data-type.");
            }
        }
        StringUtil.log("&fData type for the job module: &e" + dataType.toString());
        switch (dataType) {
            case YAML -> {
                data = new YamlData(jobModule, ConfigAPI.getConfig("job-module" + File.separator + "data"));
            }
        }
    }

    public void setDataType(DataType type) {
        this.dataType = type;
    }

    public DataType getDataType() {
        return dataType;
    }

    public JobData getData() {
        return data;
    }
}
