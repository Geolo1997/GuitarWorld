package pers.jieao.guitarworld.network;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import pers.jieao.guitarworld.model.Message;

public class Network {

    public static int sendMessage(Message message) {
        // TODO Auto-generated method stub
        return 0;
    }


    public static void loginRequest(final String id, final String password, final Callback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (id.equals("Jieao") && password.equals("Jieao")) {
                    callback.onSuccess();
                } else {
                    callback.onFailure();
                }
            }
        }).start();

    }

    public static void getCreationList(String userId, okhttp3.Callback callback) {
        try {
            callback.onResponse(null, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
