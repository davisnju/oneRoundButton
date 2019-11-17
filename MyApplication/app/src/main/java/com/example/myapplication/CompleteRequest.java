package com.example.myapplication;

public class CompleteRequest extends BaseRequest {
    public CompleteRequest(BaseResponse response, Callback callback) {
        this.response = response;
        this.callback = callback;
    }
}
