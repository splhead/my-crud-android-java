package com.splhead.mycrud.models;

import androidx.annotation.NonNull;

public class Address {
    private String cep, logradouro, complemento, bairro, ibge, ddd, localidade, uf;

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getIbge() {
        return ibge;
    }

    public void setIbge(String ibge) {
        this.ibge = ibge;
    }

    public String getDdd() {
        return ddd;
    }

    public void setDdd(String ddd) {
        this.ddd = ddd;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @NonNull
    @Override
    public String toString() {
        return "Logradouro: " + logradouro
                + "\nBairro: " + bairro
                + "\nComplemento: " + complemento
                + "\nCidade: " + localidade
                + "\nEstado: " + uf
                + "\nDDD: " + ddd
                + "\nIBGE: " + ibge;

    }
}
