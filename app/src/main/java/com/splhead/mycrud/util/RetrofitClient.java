package com.splhead.mycrud.util;

import com.splhead.mycrud.services.AddressService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private final Retrofit retrofit;

    public RetrofitClient() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public AddressService getAddressService() {
        return this.retrofit.create(AddressService.class);
    }
}
