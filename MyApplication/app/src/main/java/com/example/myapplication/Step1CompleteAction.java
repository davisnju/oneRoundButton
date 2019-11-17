package com.example.myapplication;

class Step1CompleteAction {
    Callback callback;
    private Object response;

    public Step1CompleteAction(BaseResponse response, Callback callback) {
        this.callback=callback;
        this.response = response;
    }

    public Object getResponse() {
        return response;
    }

    public void setResponse(BaseResponse response) {
        this.response = response;
    }
}
