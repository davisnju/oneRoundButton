package com.example.myapplication;

import android.util.Log;

import io.reactivex.functions.Consumer;

public class RxTest {

    private static final String TAG = "RxTest";
    /**
     * api1 start
     * api1 end
     * response:api1 response
     * api1 complete
     * api2 start
     * api2 end
     * response:api2 response
     * api2 complete
     * request response:complete
     */

    private Consumer<Step1Request> step1Consumer = new Consumer<Step1Request>() {
        @Override
        public void accept(final Step1Request request) throws Exception {
            api1(request, new OtherCallback() {
                @Override
                public void OnResult(BaseResponse response) {
                    Log.i(TAG, "response:" + response.getResponse());
                    RxBus.getInstance().post(new Step1CompleteAction(response, request.callback));
                }
            });
        }
    };

    private Consumer<Step1CompleteAction> step1CompleteAction = new Consumer<Step1CompleteAction>() {
        @Override
        public void accept(final Step1CompleteAction request) throws Exception {
            Log.i(TAG, "api1 complete");
            Object response1 = request.getResponse();
            Callback outerCallback = request.callback;
            RxBus.getInstance().post(new Step2Request(outerCallback));
        }
    };

    private Consumer<Step2Request> step2Consumer = new Consumer<Step2Request>() {
        @Override
        public void accept(final Step2Request request2) throws Exception {
            api2(request2, new OtherCallback() {
                @Override
                public void OnResult(BaseResponse response) {
                    Log.i(TAG, "response:" + response.getResponse());
                    Callback outerCallback = request2.callback;
                    RxBus.getInstance().post(new Step2CompleteAction(response, outerCallback));
                }
            });
        }
    };

    private Consumer<Step2CompleteAction> step2CompleteAction = new Consumer<Step2CompleteAction>() {
        @Override
        public void accept(final Step2CompleteAction request2c) throws Exception {
            Log.i(TAG, "api2 complete");
            RxBus.getInstance().post(new CompleteRequest(request2c.getResponse(), request2c.callback));
        }
    };

    private Consumer<CompleteRequest> completeConsumer = new Consumer<CompleteRequest>() {
        @Override
        public void accept(final CompleteRequest request) throws Exception {
            Log.i(TAG, "Request complete. response:" + request.getResponse().getResponse());
            request.callback.OnComplete(request.getResponse());
        }
    };

    /**                                                                           ================
     * OnRequest        ->                               |bus| (Step1Request) ->  ||   1.api1   ||
     *                                                   |bus|                    ||   ↙        ||
     *     2.Step1Complete   <- (Step1CompleteAction)    |bus| <- api1 callback   <-            ||
     *                    ↘                                                       ||            ||
     *                          ->                       |bus| (Step2Request) ->  ||   3.api2   ||
     *                                                   |bus|                    ||   ↙        ||
     *          4.Step2Complete <- (Step2CompleteAction) |bus| <- api2 callback   <-            ||
     *                       ↘                                                    ||            ||
     *                            ->                     |bus|                    ||            ||
     *           5.RequestComplete  <- (CompleteRequest) |bus|                    ================
     *                   ↙
     *        callback <-
     *
     * api1 start
     * api1 end
     * response:api1 response
     * api1 complete
     * api2 start
     * api2 end
     * api2 complete
     * request response:api2 response
     *
     * reference:
     * https://blog.csdn.net/demonliuhui/article/details/82532078
     * https://www.jianshu.com/p/ca090f6e2fe2
     */
    {

        RxBus.getInstance().toObservable(Step1Request.class).subscribe(step1Consumer); // 1
        RxBus.getInstance().toObservable(Step1CompleteAction.class).subscribe(step1CompleteAction); // 2
        RxBus.getInstance().toObservable(Step2Request.class).subscribe(step2Consumer); // 3
        RxBus.getInstance().toObservable(Step2CompleteAction.class).subscribe(step2CompleteAction); // 4
        RxBus.getInstance().toObservable(CompleteRequest.class).subscribe(completeConsumer); // 5

    }

    public void OnRequest(final BaseRequest request, final Callback outerCallback) {
//        RxBus.getInstance().post(new Step1Request(request, callback));
        api1(new Step1Request(request, outerCallback), new OtherCallback() {
            @Override
            public void OnResult(BaseResponse response1) {
                Log.i(TAG, "response:" + response1.getResponse());
                RxBus.getInstance().post(new Step1CompleteAction(response1, outerCallback));
            }
        });
    }

    public void onStep1Complete(BaseResponse response1, final Callback callback) {
        Log.i(TAG, "api1 complete");
        RxBus.getInstance().post(new Step2Request(callback));
    }

    public void onStep2Complete(BaseResponse response, final Callback callback) {
        Log.i(TAG, "api2 complete");
        RxBus.getInstance().post(new CompleteRequest(new BaseResponse("complete"), callback));
    }

    /********************************************************************************
     *********************************** simulate other api *************************
     ********************************************************************************/

    public void api1(Step1Request request, final OtherCallback callback) {
        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "api1 start");
                Log.i(TAG, "api1 end");
                callback.OnResult(new BaseResponse("api1 response"));
            }
        }.start();
    }


    public void api2(BaseRequest request, final OtherCallback callback) {
        new Thread() {
            @Override
            public void run() {
                Log.i(TAG, "api2 start");
                Log.i(TAG, "api2 end");
                callback.OnResult(new BaseResponse("api2 response"));
            }
        }.start();
    }
    /********************************************************************************/

}
