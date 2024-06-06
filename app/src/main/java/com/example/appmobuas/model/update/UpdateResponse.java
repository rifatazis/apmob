package com.example.appmobuas.model.update;

import com.google.gson.annotations.SerializedName;

public class UpdateResponse {
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
