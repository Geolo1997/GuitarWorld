package pers.geolo.guitarworld.network;

import pers.geolo.guitarworld.model.Works;
import pers.geolo.guitarworld.util.SingletonHolder;
import pers.geolo.guitarworld.model.User;
import retrofit2.Call;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HttpUtils {

    private static ServiceFatory serviceFatory = SingletonHolder.getInstance(ServiceFatory.class);
    private static UserService userService = serviceFatory.getService(UserService.class);
    private static WorksService worksService = serviceFatory.getService(WorksService.class);

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

    public static void getWorksList(String anthor, BaseCallback<List<Works>> callback) {
        Call<ResponseBody<List<Works>>> call = worksService.getWorksList(anthor);
        call.enqueue(callback);

//        //---------------test--------------------
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<Works> worksList = new ArrayList<>();
//                for (int i = 0; i < 50; i++) {
//                    Works works = new Works();
//                    works.setAnthor(i + "号作者");
//                    works.setCreateTime(new Date());
//                    works.setTitle("钢铁是怎样炼成的");
//                    works.setContent("   " +
//                            "保尔柯察金说过。。。。。。钢铁就是这样炼成的。。。保尔柯察金说过。。。。。。钢铁就是这样炼成的。。。保尔柯察金说过。。。。。。钢铁就是这样炼成的。。。保尔柯察金说过。。。。。。钢铁就是这样炼成的。。。");
//                    worksList.add(works);
//                }
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                callback.onSuccess(worksList);
//            }
//        }).start();
//
//        //-----------------end test-----------------
    }
}
