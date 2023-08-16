package com.amazon.sqs.model;

public class SchemaVersion {

    public SchemaVersion(String newSchema, String newSchemaVersion, String oldSchema, String oldSchemaVersion) {
        this.newSchema = newSchema;
        this.newSchemaVersion = newSchemaVersion;
        this.oldSchema = oldSchema;
        this.oldSchemaVersion = oldSchemaVersion;
    }

    private String newSchema;
    private String newSchemaVersion;
    private String oldSchema;
    private String oldSchemaVersion;

    public String getNewSchema() {
        return newSchema;
    }

    public void setNewSchema(String newSchema) {
        this.newSchema = newSchema;
    }

    public String getNewSchemaVersion() {
        return newSchemaVersion;
    }

    public void setNewSchemaVersion(String newSchemaVersion) {
        this.newSchemaVersion = newSchemaVersion;
    }

    public String getOldSchema() {
        return oldSchema;
    }

    public void setOldSchema(String oldSchema) {
        this.oldSchema = oldSchema;
    }

    public String getOldSchemaVersion() {
        return oldSchemaVersion;
    }

    public void setOldSchemaVersion(String oldSchemaVersion) {
        this.oldSchemaVersion = oldSchemaVersion;
    }

}
