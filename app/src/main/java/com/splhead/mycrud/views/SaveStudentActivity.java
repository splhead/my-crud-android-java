package com.splhead.mycrud.views;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.splhead.mycrud.R;
import com.splhead.mycrud.databinding.ActivitySaveStudentBinding;
import com.splhead.mycrud.models.Address;
import com.splhead.mycrud.util.MaskEditUtils;
import com.splhead.mycrud.util.RetrofitClient;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveStudentActivity extends AppCompatActivity {
    public static final String TAG = SaveStudentActivity.class.getSimpleName();
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
            String cep = intent.getStringExtra(EXTRA_CEP);
            binding.etCEP.setText(cep);

            if (!cep.trim().isEmpty()) {
                binding.tvAddress.setText(R.string.Loading_address);
                getAddress(cep);
            }
        } else {
            setTitle("Novo Estudante");
        }
    }


    private void getAddress(String cep) {
        Call<Address> call = new RetrofitClient().getAddressService().getAddress(cep);
        call.enqueue(new Callback<Address>() {

            @Override
            public void onResponse(@NonNull Call<Address> call, @NonNull Response<Address> response) {
                Address address = response.body();
                if (address != null) {
                    if (address.getLogradouro() == null) {
                        binding.tvAddress.setText(R.string.CEP_is_invalid);
                    } else {
                        binding.tvAddress.setText(address.toString());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<Address> call, @NonNull Throwable t) {
                Log.e(TAG, "Erro ao buscar endereço\n" + t.getMessage());
            }
        });
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