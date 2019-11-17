package com.example.myapplication;

import android.util.Log;

public class EmbeddedCallbacks {
    private static final String TAG = "EmbeddedCallbacks";

    /**
     * OnRequest            ->    api1
     *
     *     api1 callback   <-      -
     *         onStep1Complete      ->     api2
     *
     *             api2 callback   <-       -
     *                 onStep2Complete       ->
     *                                   <- callback
     * api1 start
     * api1 end
     * response:api1 response
     * api1 complete
     * api2 start
     * api2 end
     * api2 complete
     * request response:api2 response
     */

    public void OnRequest(final BaseRequest request, final Callback callback){
        step1(new Step1Request(request, callback), new Callback() {
            @Override
            public void OnComplete(BaseResponse response) {
                Log.i(TAG, "response:" + response.getResponse());
                onStep1Complete(response, callback);
            }
        });
    }

    public void step1(Step1Request request, final Callback callback){
        new Thread(){
            @Override
            public void run(){
                Log.i(TAG, "api1 start");
                Log.i(TAG, "api1 end");
                callback.OnComplete(new BaseResponse("api1 response"));
            }
        }.start();
    }

    public void onStep1Complete(BaseResponse response, final Callback callback) {
        Log.i(TAG, "api1 complete");
        step2(new BaseRequest(), new Callback() {
            @Override
            public void OnComplete(BaseResponse response2) {
                Log.i(TAG, "response:" + response2.getResponse());
                onStep2Complete(response2, callback);
            }
        });
    }

    public void step2(BaseRequest request, final Callback callback) {
        new Thread(){
            @Override
            public void run(){
                Log.i(TAG, "api2 start");
                Log.i(TAG, "api2 end");
                callback.OnComplete(new BaseResponse("api2 response"));
            }
        }.start();
    }

    public void onStep2Complete(BaseResponse response, Callback callback) {
        Log.i(TAG, "api2 complete");
        callback.OnComplete(new BaseResponse("api2 response"));
    }

}
