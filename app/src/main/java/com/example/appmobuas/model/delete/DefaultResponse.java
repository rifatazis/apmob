package com.example.appmobuas.model.delete;

import com.google.gson.annotations.SerializedName;

public class DefaultResponse {
    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }
}
