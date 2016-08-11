package com.woniukeji.jianguo.eventbus;

import android.content.Context;

import com.google.gson.Gson;
import com.woniukeji.jianguo.http.BackgroundSubscriber;
import com.woniukeji.jianguo.http.HttpMethods;
import com.woniukeji.jianguo.http.ProgressSubscriber;
import com.woniukeji.jianguo.http.SubscriberOnNextListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.LCChatProfileProvider;
import cn.leancloud.chatkit.LCChatProfilesCallBack;

/**
 * Created by wli on 15/12/4.
 * 实现自定义用户体系
 */
public class CustomUserProvider implements LCChatProfileProvider {

  private static CustomUserProvider customUserProvider;
  private static SubscriberOnNextListener<List<LCChatKitUser>> lcChatKitUserSubscriberOnNextListener;
  private static LCChatProfilesCallBack mCallBack;
  private static Context applicationContext;
  public synchronized static CustomUserProvider getInstance(Context applicationContext) {
    if (null == customUserProvider) {
      customUserProvider = new CustomUserProvider();
      lcChatKitUserSubscriberOnNextListener=new SubscriberOnNextListener<List<LCChatKitUser>>() {
        @Override
        public void onNext(List<LCChatKitUser> lcChatKitUser) {
          mCallBack.done(lcChatKitUser,null);
        }
      };
    }
    return customUserProvider;
  }

  private CustomUserProvider() {
  }

  private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();

  // 此数据均为 fake，仅供参考
  static {
    partUsers.add(new LCChatKitUser("Tom", "Tom", "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"));
    partUsers.add(new LCChatKitUser("Jerry", "Jerry", "http://www.avatarsdb.com/avatars/jerry.jpg"));
    partUsers.add(new LCChatKitUser("Harry", "Harry", "http://www.avatarsdb.com/avatars/young_harry.jpg"));
    partUsers.add(new LCChatKitUser("William", "William", "http://www.avatarsdb.com/avatars/william_shakespeare.jpg"));
    partUsers.add(new LCChatKitUser("Bob", "Bob", "http://www.avatarsdb.com/avatars/bath_bob.jpg"));
  }

  @Override
  public void fetchProfiles(Context context, List<String> list, LCChatProfilesCallBack callBack) {
//    HttpMethods.getInstance().getTalkUser(new NoProgressSubscriber<LCChatKitUser>(lcChatKitUserSubscriberOnNextListener,applicationContext,new ProgressDialog(applicationContext)),list.get(0));
    Map map=new HashMap();
    map.put("login_id",list);
    Gson gson=new Gson();
    String s = gson.toJson(map);
    HttpMethods.getInstance().getTalkUser(new BackgroundSubscriber<List<LCChatKitUser>>(lcChatKitUserSubscriberOnNextListener,context),s);
    mCallBack=callBack;

//    List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
//    for (String userId : list) {
//      for (LCChatKitUser user : partUsers) {
//        if (user.getUserId().equals(userId)) {
//          userList.add(user);
//          break;
//        }
//      }
//    }
//    callBack.done(userList, null);
  }

  public List<LCChatKitUser> getAllUsers() {
    return partUsers;
  }
}
