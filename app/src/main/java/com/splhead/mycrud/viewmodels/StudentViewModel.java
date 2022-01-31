package com.splhead.mycrud.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.splhead.mycrud.models.Student;
import com.splhead.mycrud.repositories.StudentRepository;

import java.util.List;

public class StudentViewModel extends AndroidViewModel {
    private final LiveData<List<Student>> allStudents;
    private final StudentRepository studentRepository;

    public StudentViewModel(@NonNull Application application) {
        super(application);
        studentRepository = new StudentRepository(application);
        allStudents = studentRepository.getAllStudents();
    }

    public LiveData<List<Student>> getAllStudents() {
        return allStudents;
    }

    public void insert(Student student) {
        studentRepository.insert(student);
    }

    public void update(Student student) {
        studentRepository.update(student);
    }

    public void delete(Student student) {
        studentRepository.delete(student);
    }
}
