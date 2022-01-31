package com.splhead.mycrud.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.splhead.mycrud.db.MyCrudDatabase;
import com.splhead.mycrud.db.StudentDao;
import com.splhead.mycrud.models.Student;

import java.util.List;

public class StudentRepository {
    private StudentDao studentDao;
    private LiveData<List<Student>> allStudents;

    public StudentRepository(Application application) {
        MyCrudDatabase database = MyCrudDatabase.getInstance(application);
        studentDao = database.studentDao();
        allStudents = studentDao.getAll();
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public void insert(Student student) {
        MyCrudDatabase.databaseWriteExecutor.execute(() -> studentDao.insert(student));
    }

    public void update(Student student) {
        MyCrudDatabase.databaseWriteExecutor.execute(() -> studentDao.update(student));
    }

    public void delete(Student student) {
        MyCrudDatabase.databaseWriteExecutor.execute(() -> studentDao.delete(student));
    }
}
