package fr.robotv2.robotcore.jobs.enums;

public enum JobType {
    MINER("Mineur"),
    LUMBERJACK("Bûcheron"),
    FARMER("Fermier"),
    HUNTER("Chasseur"),
    DIGGER("Terraformeur"),
    BUILDER("Constructeur");

    String name;
    JobType(String name) {
        this.name = name;
    }

    public String getFunctionalName() {
        return this.toString();
    }

    public String getTranslatedName() {
        return this.name;
    }

}
