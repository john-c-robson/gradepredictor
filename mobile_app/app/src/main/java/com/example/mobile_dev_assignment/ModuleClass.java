package com.example.mobile_dev_assignment;

public class ModuleClass {

    int id;
    int projectID;
    String modName;
    String modCode;
    int modMark;
    String modDate;

    public ModuleClass(int id, int projectID, String modName, String modCode, int modMark, String modDate) {
        this.id = id;
        this.projectID = projectID;
        this.modName = modName;
        this.modCode = modCode;
        this.modMark = modMark;
        this.modDate = modDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getModName() {
        return modName;
    }

    public void setModName(String modName) {
        this.modName = modName;
    }

    public String getModCode() {
        return modCode;
    }

    public void setModCode(String modCode) {
        this.modCode = modCode;
    }

    public int getModMark() {
        return modMark;
    }

    public void setModMark(int modMark) {
        this.modMark = modMark;
    }

    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }
}
