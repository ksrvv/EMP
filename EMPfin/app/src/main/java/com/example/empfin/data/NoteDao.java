//описывает интерфейс (ЧТО мы будем делать с данными)
package com.example.empfin.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.empfin.model.Note;

import java.util.List;

//data-access object
@Dao
public interface NoteDao {
    //выбор всех записей таблицы
    @Query("SELECT * FROM Note")
    List<Note> getAll();
    //выбор всех записей таблицы в формате LiveData
    @Query("SELECT * FROM Note")
    LiveData<List<Note>> getAllLiveData();
    //: означает автоматическую подстановку даных по соотв. имени
    @Query("SELECT * FROM Note WHERE uid IN (:noteIds)")
    List<Note> loadAllByIds(int[] noteIds);
    //выборка по id
    @Query("SELECT * FROM Note WHERE uid = :uid  LIMIT 1")
    Note findById (int uid);
    //в ситуации когда мы хотим вставить заметку с id который уже сущ. произойдет перезапись
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete (Note note);
}
