package com.zenika.meeting_planner.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response<T> {
    private boolean error;
    private String message;
    private T data;
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


}