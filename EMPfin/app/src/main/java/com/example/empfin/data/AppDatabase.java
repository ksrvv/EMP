//база данных
package com.example.empfin.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.empfin.model.Note;

@Database(entities = {Note.class}, version=2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();

}
