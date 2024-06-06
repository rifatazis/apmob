package com.example.appmobuas.model.profil;

import com.google.gson.annotations.SerializedName;

public class ProfilData {

    @SerializedName("name")
    private String name;

    @SerializedName("bio")
    private String bio;

    @SerializedName("id")
    private String id;

    @SerializedName("username")
    private String username;

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setBio(String bio){
        this.bio = bio;
    }

    public String getBio(){
        return bio;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }
}