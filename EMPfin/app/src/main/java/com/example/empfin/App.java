package com.example.empfin;

import android.app.Application;

import androidx.room.Room;

import com.example.empfin.data.AppDatabase;
import com.example.empfin.data.NoteDao;

public class App extends Application {

    private AppDatabase database;
    private NoteDao noteDao;

    private static App instance;
    public static App getInstance(){
        return instance;
    }
    @Override
    public void onCreate(){
        super.onCreate();//вызывается до начала взаимодействия пользователя и приложения
        instance=this;
        database = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class,
                "app-db-name").allowMainThreadQueries().build();

        noteDao = database.noteDao();
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public NoteDao getNoteDao() {
        return noteDao;
    }

    public void setDatabase(AppDatabase database) {
        this.database = database;
    }

    public void setNoteDao(NoteDao noteDao) {
        this.noteDao = noteDao;
    }
}