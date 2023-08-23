package com.example.mobile_dev_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProjectDBO extends SQLiteOpenHelper {

    public static final String PROJECT_TABLE = "PROJECT_TABLE";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_COURSE = "COURSE";
    public static final String MODULE_TABLE = "MODULE_TABLE";
    public static final String COLUMN_PROJECTID = "PROJECTID";
    public static final String COLUMN_MNAME = "MODNAME";
    public static final String COLUMN_MCOURSE = "MODCOURSE";
    public static final String COLUMN_MMARK = "MODMARK";
    public static final String COLUMN_MDATE = "MODDATE";


    public ProjectDBO(@Nullable Context context) {
        super(context, "project.db", null, 1);
    }

    // this is called the first time a database is accessed. There should be code in here to create a new db..
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_PROJECT_TABLE = "CREATE TABLE " + PROJECT_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " " + COLUMN_NAME + " TEXT, " + COLUMN_COURSE + " TEXT)";

        String CREATE_MODULE_TABLE = "CREATE TABLE " + MODULE_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PROJECTID + " INTEGER NOT NULL, "
                + COLUMN_MNAME + " TEXT NOT NULL, " + COLUMN_MCOURSE + " TEXT NOT NULL, " + COLUMN_MMARK +
                " INTEGER NOT NULL, " + COLUMN_MDATE + " DATE NOT NULL)";

        db.execSQL(CREATE_PROJECT_TABLE);
        db.execSQL(CREATE_MODULE_TABLE);
    }

    // this is called if the database version number changes. It prevents previous users apps from breaking when
    // you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //fairly sure something should be here, but i have no idea currently
    }

    public boolean addProject(ProjectClass projectClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, projectClass.getName());
        cv.put(COLUMN_COURSE, projectClass.getCourse());

        long insert = db.insert(PROJECT_TABLE, null, cv);

        if (insert == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }

    public boolean addModule(ModuleClass moduleClass) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PROJECTID, moduleClass.getProjectID());
        cv.put(COLUMN_MNAME, moduleClass.getModName());
        cv.put(COLUMN_MCOURSE, moduleClass.getModCode());
        cv.put(COLUMN_MMARK, moduleClass.getModMark());
        cv.put(COLUMN_MDATE, moduleClass.getModDate());

        long insert = db.insert(MODULE_TABLE, null, cv);

        if (insert == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }

    }
    public boolean moduleUpdate(ModuleClass moduleClass, int modID) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_PROJECTID, moduleClass.getProjectID());
        cv.put(COLUMN_MNAME, moduleClass.getModName());
        cv.put(COLUMN_MCOURSE, moduleClass.getModCode());
        cv.put(COLUMN_MMARK, moduleClass.getModMark());
        cv.put(COLUMN_MDATE, moduleClass.getModDate());

        long insert = db.update(MODULE_TABLE, cv, "ID=?", new String [] {String.valueOf(modID)});

        if (insert == -1) {
            db.close();
            return false;
        } else {
            db.close();
            return true;
        }
    }


    public int getProjectCount() {

        int count;

        String queryString = "SELECT COUNT(*) FROM " + PROJECT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        } else {
            count = 0;
        }
        cursor.close();
        db.close();
        return count;
    }
    public ArrayList<ProjectClass> getAllProjects(){
        ArrayList<ProjectClass> projectClasses = new ArrayList<>();
        ProjectClass projectClass;

        String queryString = "SELECT ID, NAME, COURSE FROM " + PROJECT_TABLE;
        Log.i("sql", queryString);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String projectName = cursor.getString(1);
                String projectCourse = cursor.getString(2);

                projectClass = new ProjectClass(id, projectName,projectCourse);
                projectClasses.add(projectClass);
                //this can be reused to create a arraylist of objects using the .add(modClass)

            } while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();

        return projectClasses;

    }

    //
    public ProjectClass getProjectDetails(int i) {

        ProjectClass projectClass;

        String queryString = "SELECT ID, NAME, COURSE FROM " + PROJECT_TABLE + " WHERE ID = " + (i);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()){
            do {
                int projectID = cursor.getInt(0);
                String projectname = cursor.getString(1);
                String projectcourse = cursor.getString(2);

                projectClass = new ProjectClass(projectID,projectname,projectcourse);
                //this can be reused to create a arraylist of objects using the .add(modClass)

            } while (cursor.moveToNext());
        }
        else{
            projectClass = new ProjectClass(-1,"error","error");
        }
        cursor.close();
        db.close();

        return projectClass;
    }

    public int getProjectID (String q) {

        int id;

        String queryString = "SELECT ID, NAME FROM " + PROJECT_TABLE  + " WHERE NAME = " + "'"+q+"'";
        Log.i("sql", queryString);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(0);
            } while (cursor.moveToNext());
        }
        else{
            id = -1;
        }
        cursor.close();
        db.close();

        return id;
    }

    public ModuleClass getModuleDetails(int modID)
    {
        ModuleClass moduleClass;

        String queryString = "SELECT PROJECTID, MODNAME, MODCOURSE, MODMARK, MODDATE FROM " + MODULE_TABLE  + " WHERE ID = " + modID;
        Log.i("sql", queryString);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do {
                int projectID = cursor.getInt(0);
                String modName = cursor.getString(1);
                String modCode = cursor.getString(2);
                int modMark = cursor.getInt(3);
                String modDate = cursor.getString(4);

                moduleClass = new ModuleClass(modID, projectID,modName,modCode, modMark,modDate);

            } while (cursor.moveToNext());
        }
        else{
            moduleClass = new ModuleClass(-1,-1,"error","error",-1,"error");
        }
        cursor.close();
        db.close();

        return moduleClass;
    }

    public int getModuleCount(int id){
        int count;

        String queryString = "SELECT COUNT(*) FROM " + MODULE_TABLE + " WHERE PROJECTID = " + id;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            count = cursor.getInt(0);
        } else {
            count = 0;
        }
        cursor.close();
        db.close();
        return count;
    }

    public ArrayList<ModuleClass> getAllModules(int id){
        ArrayList<ModuleClass> moduleClasses = new ArrayList<>();
        ModuleClass moduleClass;

        String queryString = "SELECT ID, PROJECTID, MODNAME, MODCOURSE, MODMARK, MODDATE FROM " + MODULE_TABLE  + " WHERE PROJECTID = " + id + " ORDER BY MODDATE ASC";
        Log.i("sql", queryString);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do {
                int modID = cursor.getInt(0);
                int projectID = cursor.getInt(1);
                String modName = cursor.getString(2);
                String modCode = cursor.getString(3);
                int modMark = cursor.getInt(4);
                String modDate = cursor.getString(5);

                moduleClass = new ModuleClass(modID, projectID, modName, modCode, modMark, modDate);
                moduleClasses.add(moduleClass);

            } while (cursor.moveToNext());
        }
        else{

        }
        cursor.close();
        db.close();

        return moduleClasses;

    }
    public void deleteModule (int modID){
        Log.i("Deleting Module Entry","Deleted.");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MODULE_TABLE, "ID=?", new String[]{String.valueOf(modID)});
        db.close();
    }
    public void deleteProject (int projectID){
        Log.i("Deleting Project Entry","Deleted.");
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(MODULE_TABLE, "PROJECTID=?", new String[]{String.valueOf(projectID)});
        db.delete(PROJECT_TABLE, "ID=?", new String[]{String.valueOf(projectID)});
        db.close();
    }


}