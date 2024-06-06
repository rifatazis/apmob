package com.example.appmobuas.model.profil;

import com.google.gson.annotations.SerializedName;

public class Profil{

    @SerializedName("data")
    private ProfilData profilData;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setData(ProfilData profilData){
        this.profilData = profilData;
    }

    public ProfilData getData(){
        return profilData;
    }

    public void setError(boolean error){
        this.error = error;
    }

    public boolean isError(){
        return error;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}