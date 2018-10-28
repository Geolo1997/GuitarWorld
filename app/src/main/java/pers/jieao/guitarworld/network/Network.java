package pers.jieao.guitarworld.network;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;
import pers.jieao.guitarworld.model.Message;

public class Network {

    public static int sendMessage(Message message) {
        // TODO Auto-generated method stub
        return 0;
    }


    public static void loginRequest(String id, String password, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callback.onSuccess();
            }
        }).start();

    }
}
