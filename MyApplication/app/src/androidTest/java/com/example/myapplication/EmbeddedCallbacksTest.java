package com.example.myapplication;

import android.util.Log;

import org.junit.Test;

public class EmbeddedCallbacksTest {
    @Test
    public void test() {
        new EmbeddedCallbacks().OnRequest(new BaseRequest(), new Callback() {
            @Override
            public void OnComplete(BaseResponse response) {
                Log.i("test", "request response:" + response.getResponse());
            }
        });
    }

}