package com.zenika.meeting_planner.Response;

public class Response<T> {
    private boolean error;
    private String message;
    private T data;

    public Response(boolean error, String message, T data) {
        this.error = error;
        this.message = message;
        this.data = data;
    }

    public Response(T data) {
        this.data = data;
        this.message="";
        this.error=false;
    }

    public Response(String message, T data) {
        this.message = message;
        this.data = data;
        this.error=false;
    }

    public Response(boolean error, String message) {
        this.error = error;
        this.message = message;
        this.data=null;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}