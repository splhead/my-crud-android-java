package com.splhead.mycrud.services;

import com.splhead.mycrud.models.Address;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AddressService {

    @GET("{cep}/json")
    Call<Address> getAddress(@Path("cep") String cep);
}
