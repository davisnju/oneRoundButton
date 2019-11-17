package com.example.myapplication;

public class BaseRequest {
    BaseResponse response;
    Callback callback;
    public BaseRequest() {

}
    public BaseRequest(BaseRequest request) {

    }

    public BaseRequest(Callback callback) {
        this.callback = callback;
    }

    public BaseResponse getResponse() {
        return response;
    }
}
