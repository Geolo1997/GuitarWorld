package pers.geolo.guitarworld.network;

import pers.geolo.guitarworld.model.Works;
import pers.geolo.guitarworld.util.SingletonHolder;
import pers.geolo.guitarworld.model.User;
import retrofit2.Call;

import java.util.List;

public class HttpUtils {

    private static APIFactory APIFactory = SingletonHolder.getInstance(APIFactory.class);
    private static UserAPI userAPI = APIFactory.getAPI(UserAPI.class);
    private static WorksAPI worksAPI = APIFactory.getAPI(WorksAPI.class);

    public static void login(String username, String password, BaseCallback<Void> callback) {
        Call<ResponseBody<Void>> call = userAPI.login(username, password);
        call.enqueue(callback);
    }

    public static void register(String username, String password, String email, BaseCallback<Void> callback) {
        Call<ResponseBody<Void>> call = userAPI.register(username, password, email);
        call.enqueue(callback);
    }

    public static void getMyProfile(BaseCallback<User> callback) {
        Call<ResponseBody<User>> call = userAPI.getMyProfile();
        call.enqueue(callback);
    }

    public static void saveMyProfile(User user, BaseCallback<Void> callback) {
        Call<ResponseBody<Void>> call = userAPI.saveMyProfile(user);
        call.enqueue(callback);
    }

    public static void getWorksList(String author, BaseCallback<List<Works>> callback) {
        Call<ResponseBody<List<Works>>> call = worksAPI.getWorksList(author);
        call.enqueue(callback);

//        //---------------test--------------------
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                List<Works> worksList = new ArrayList<>();
//                for (int i = 0; i < 50; i++) {
//                    Works works = new Works();
//                    works.setAuthor(i + "号作者");
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

    public static void getWorks(int id, BaseCallback<Works> callback) {
        Call<ResponseBody<Works>> call = worksAPI.getWorks(id);
        call.enqueue(callback);
    }

    public static void publishWorks(Works works, BaseCallback<Void> callback) {
        Call<ResponseBody<Void>> call = worksAPI.publishWorks(works);
        call.enqueue(callback);
    }
}
