package com.example.myapplication;

class Step2CompleteAction {
    Callback callback;
    private BaseResponse response;

    public Step2CompleteAction(BaseResponse response, Callback callback) {
        this.response = response;
        this.callback = callback;
    }

    public BaseResponse getResponse() {
        return response;
    }

    public void setResponse(BaseResponse response) {
        this.response = response;
    }
}
