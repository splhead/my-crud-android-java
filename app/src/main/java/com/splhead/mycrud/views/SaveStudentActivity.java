package com.splhead.mycrud.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.splhead.mycrud.R;
import com.splhead.mycrud.databinding.ActivitySaveStudentBinding;
import com.splhead.mycrud.util.MaskEditUtils;

import java.util.Objects;

public class SaveStudentActivity extends AppCompatActivity {
    public static final String EXTRA_ID = "com.splhead.mycrud.EXTRA_ID";
    public static final String EXTRA_NAME = "com.splhead.mycrud.EXTRA_NAME";
    public static final String EXTRA_CPF = "com.splhead.mycrud.EXTRA_CPF";
    public static final String EXTRA_PHONE = "com.splhead.mycrud.EXTRA_PHONE";
    public static final String EXTRA_CEP = "com.splhead.mycrud.EXTRA_CEP";

    private ActivitySaveStudentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySaveStudentBinding.inflate(getLayoutInflater());
        View v = binding.getRoot();
        setContentView(v);

        binding.etCPF.addTextChangedListener(
                MaskEditUtils.mask(binding.etCPF, MaskEditUtils.FORMAT_CPF));

        binding.etPhone.addTextChangedListener(
                MaskEditUtils.mask(binding.etPhone, MaskEditUtils.FORMAT_PHONE)
        );

        binding.etCEP.addTextChangedListener(
                MaskEditUtils.mask(binding.etCEP, MaskEditUtils.FORMAT_CEP)
        );

        binding.bSave.setOnClickListener(view -> saveStudent());

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_close);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Alterando estudante");
            binding.etName.setText(intent.getStringExtra(EXTRA_NAME));
            binding.etCPF.setText(intent.getStringExtra(EXTRA_CPF));
            binding.etPhone.setText(intent.getStringExtra(EXTRA_PHONE));
            binding.etCEP.setText(intent.getStringExtra(EXTRA_CEP));
        } else {
            setTitle("Novo Estudante");
        }
    }

    private void saveStudent() {
        String name = binding.etName.getText().toString();
        String cpf = binding.etCPF.getText().toString();
        String phone = binding.etPhone.getText().toString();
        String cep = binding.etCEP.getText().toString();

        if (name.trim().isEmpty() || cpf.trim().isEmpty()
                || phone.trim().isEmpty() || cep.trim().isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_NAME, name);
        data.putExtra(EXTRA_CPF, cpf);
        data.putExtra(EXTRA_PHONE, phone);
        data.putExtra(EXTRA_CEP, cep);

        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();
    }
}