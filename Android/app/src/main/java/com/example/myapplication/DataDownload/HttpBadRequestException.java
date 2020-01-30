package com.example.myapplication.DataDownload;

public class HttpBadRequestException extends Exception {
    public HttpBadRequestException() {
        super("Could not connect to server!");
    }
}
