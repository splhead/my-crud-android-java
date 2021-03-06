package com.splhead.mycrud.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.splhead.mycrud.models.Student;

import java.util.List;

@Dao
public interface StudentDao {
    @Insert
    void insert(Student student);

    @Update
    void update(Student student);

    @Query("SELECT * FROM student")
    LiveData<List<Student>> getAll();

    @Query("SELECT * FROM student WHERE name LIKE '%' || :name || '%'")
    LiveData<List<Student>> findByName(String name);

    @Delete
    void delete(Student student);
}
