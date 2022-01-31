package com.splhead.mycrud.views;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.splhead.mycrud.R;
import com.splhead.mycrud.adapters.StudentAdapter;
import com.splhead.mycrud.databinding.ActivityMainBinding;
import com.splhead.mycrud.models.Student;
import com.splhead.mycrud.viewmodels.StudentViewModel;

public class MainActivity extends AppCompatActivity {
    private StudentViewModel studentViewModel;
    private StudentAdapter adapter;
    private ActivityMainBinding binding;
    ActivityResultLauncher<Intent> startForResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        setupOnActivityResultLauncher();

        binding.rvAllStudents.setLayoutManager(new LinearLayoutManager(this));
        binding.rvAllStudents.setHasFixedSize(true);

        adapter = new StudentAdapter();
        binding.rvAllStudents.setAdapter(adapter);

        binding.fabAddStudent.setOnClickListener(view -> {
            Intent intent = new Intent(this, SaveStudentActivity.class);
            startForResult.launch(intent);
        });

        studentViewModel = new ViewModelProvider(this).get(StudentViewModel.class);
        studentViewModel.getAllStudents().observe(this, students -> adapter.submitList(students));

        setupDeleteMovingToLeftOrRight();

        adapter.setOnItemClickListener(student -> {
            Intent intent = new Intent(MainActivity.this, SaveStudentActivity.class);
            intent.putExtra(SaveStudentActivity.EXTRA_ID, student.getId());
            intent.putExtra(SaveStudentActivity.EXTRA_NAME, student.getName());
            intent.putExtra(SaveStudentActivity.EXTRA_CPF, student.getCpf());
            intent.putExtra(SaveStudentActivity.EXTRA_PHONE, student.getPhone());
            intent.putExtra(SaveStudentActivity.EXTRA_CEP, student.getCep());
            startForResult.launch(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint("Digite para pesquisar");
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName())
        );

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String typedText) {
                studentViewModel.findByName(typedText).observe(MainActivity.this,
                        students -> adapter.submitList(students));
                return false;
            }
        });

        return true;
    }

    private void setupDeleteMovingToLeftOrRight() {
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                studentViewModel.delete(
                        adapter.getStudentAt(viewHolder.getAbsoluteAdapterPosition()
                        ));
                Toast.makeText(MainActivity.this, "Estudante apagado", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(binding.rvAllStudents);
    }

    // a new way to do it without deprecated methods
    private void setupOnActivityResultLauncher() {
        startForResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();

                        if (data != null) {
                            int id;
                            id = data.getIntExtra(SaveStudentActivity.EXTRA_ID, -1);
                            String name = data.getStringExtra(SaveStudentActivity.EXTRA_NAME);
                            String cpf = data.getStringExtra(SaveStudentActivity.EXTRA_CPF);
                            String phone = data.getStringExtra(SaveStudentActivity.EXTRA_PHONE);
                            String cep = data.getStringExtra(SaveStudentActivity.EXTRA_CEP);
                            Student student = new Student(name, cpf, phone, cep);

                            if (id == -1) {
                                studentViewModel.insert(student);
                                Toast.makeText(MainActivity.this,
                                        "Novo estudante salvo", Toast.LENGTH_SHORT).show();
                            } else {
                                student.setId(id);
                                studentViewModel.update(student);
                                Toast.makeText(MainActivity.this,
                                        "Estudante atualizado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
        );
    }


}