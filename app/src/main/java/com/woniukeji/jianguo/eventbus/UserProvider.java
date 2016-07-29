//package com.woniukeji.jianguo.eventbus;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Created by Administrator on 2016/7/21.
// */
//
//public class UserProvider implements LCChatProfileProvider {
////    private static UserProvider customUserProvider;
//
//    public synchronized static UserProvider getInstance() {
//        if (null == customUserProvider) {
//            customUserProvider = new UserProvider();
//        }
//        return customUserProvider;
//    }
//
//    private UserProvider() {
//    }
//
//    private static List<LCChatKitUser> partUsers = new ArrayList<LCChatKitUser>();
//
//    // 此数据均为模拟数据，仅供参考
//    static {
//        partUsers.add(new LCChatKitUser("Tom", "Tom", "http://www.avatarsdb.com/avatars/tom_and_jerry2.jpg"));
//        partUsers.add(new LCChatKitUser("Jerry", "Jerry", "http://www.avatarsdb.com/avatars/jerry.jpg"));
//        partUsers.add(new LCChatKitUser("Harry", "Harry", "http://www.avatarsdb.com/avatars/young_harry.jpg"));
//        partUsers.add(new LCChatKitUser("William", "William", "http://www.avatarsdb.com/avatars/william_shakespeare.jpg"));
//        partUsers.add(new LCChatKitUser("Bob", "Bob", "http://www.avatarsdb.com/avatars/bath_bob.jpg"));
//    }
//
//    @Override
//    public void fetchProfiles(List<String> list, LCChatProfilesCallBack callBack) {
//        List<LCChatKitUser> userList = new ArrayList<LCChatKitUser>();
//        for (String userId : list) {
//            for (LCChatKitUser user : partUsers) {
//                if (user.getUserId().equals(userId)) {
//                    userList.add(user);
//                    break;
//                }
//            }
//        }
//        callBack.done(userList, null);
//    }
//
//    public List<LCChatKitUser> getAllUsers() {
//        return partUsers;
//    }
//}
