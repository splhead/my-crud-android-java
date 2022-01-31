package com.splhead.mycrud.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.splhead.mycrud.models.Student;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Student.class}, version = 1, exportSchema = false)
public abstract class MyCrudDatabase extends RoomDatabase {
    public abstract StudentDao studentDao();

    private static volatile MyCrudDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MyCrudDatabase getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyCrudDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyCrudDatabase.class, "my_crud")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
