package com.example.myapplication;

import android.util.Log;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

public class RxBusTest {

    @Test
    public void test() {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Callback callback = new Callback() {
            @Override
            public void OnComplete(BaseResponse response) {
                Log.i("test", "request response:" + response.getResponse());
                Assert.assertEquals("api2 response", response.getResponse());
                countDownLatch.countDown();
            }
        };
        new RxTest().OnRequest(new BaseRequest(), callback);
        try {
            Log.d("test", "processing");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
