package pers.geolo.guitarworld.network;

import pers.geolo.guitarworld.util.SingletonHolder;
import pers.geolo.guitarworld.model.User;
import retrofit2.Call;

public class HttpUtils {

    private static ServiceFatory serviceFatory = SingletonHolder.getInstance(ServiceFatory.class);
    private static UserService userService = serviceFatory.getService(UserService.class);

    public static void login(String username, String password, BaseCallback<Void> callback) {
        Call<ResponseBody<Void>> call = userService.login(username, password);
        call.enqueue(callback);
    }

    public static void register(String username, String password, String email, BaseCallback<Void> callback) {
        Call<ResponseBody<Void>> call = userService.register(username, password, email);
        call.enqueue(callback);
    }

    public static void getMyProfile(BaseCallback<User> callback) {
        Call<ResponseBody<User>> call = userService.getMyProfile();
        call.enqueue(callback);
    }

    public static void saveMyProfile(User user, BaseCallback<Void> callback) {
        Call<ResponseBody<Void>> call = userService.saveMyProfile(user);
        call.enqueue(callback);
    }
}
