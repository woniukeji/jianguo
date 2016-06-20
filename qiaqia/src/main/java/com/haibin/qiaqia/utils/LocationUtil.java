//package com.haibin.qiaqia.utils;
//
//import android.content.Context;
//
//
//
//import de.greenrobot.event.EventBus;
//
///**
// * Created by Jaren on 2016/4/1.
// */
//public class LocationUtil  {
//
////    private static TencentLocationRequest locationRequest;
////    private static TencentLocationManager locationManager;
//
//
//    public static boolean start(Context context,TencentLocationListener listener){
////        locationRequest = getDefaultLocationRequest();
////        locationManager = TencentLocationManager.getInstance(context);
////        int error = locationManager.requestLocationUpdates(locationRequest,listener);
////
////        if(error == 0) return true;
////        LogUtils.e("TencentLocation","位置监听器注册失败，错误码："+error);
//        return false;
//    }
//
//    /**
//     * 请求定位的主要方法，默认回调方法为在定位完成之后用EventBus发送一个CityEvent实例
//     * 定位完成后自动移除位置监听器
//     * @param context
//     * @return 监听器是否注册成功
//     */
//    public static boolean start(Context context){
//        return start(context, new TencentLocationListener() {
//            @Override
//            public void onLocationChanged(TencentLocation tencentLocation, int error, String reason) {
//                CityEvent city = new CityEvent();
//                city.city = new CityBannerEntity.ListTCityEntity();
//                if(TencentLocation.ERROR_OK == error){
//                    // 定位成功
//                    String cityCode = tencentLocation.getCityCode();
//                    if(!StringUtils.isBlank(cityCode)){
//                        city.city.setId(Integer.parseInt(cityCode));
//                    }
//                    String cityName=tencentLocation.getCity().substring(0,tencentLocation.getCity().lastIndexOf("市"));
//                    city.city.setCity(cityName);
//                    LogUtils.i("TencentLocation","定位成功，城市码："+city.city.getId()+"；城市名："+city.city.getCity());
//                } else {
//                    // 定位失败
//                    city.city.setId(0);
//                    LogUtils.w("TencentLocation","定位失败，错误码："+error+"；原因："+reason);
//                }
//                city.isGPS=true;
//                EventBus.getDefault().post(city);
//                // 移除位置监听器
//                locationManager.removeUpdates(this);
//            }
//
//            @Override
//            public void onStatusUpdate(String s, int i, String s1) {
//
//            }
//        });
//    }
//
//    public static TencentLocationRequest getDefaultLocationRequest(){
//        if(locationRequest == null){
//            // 请求的位置信息包括经纬度，位置所处的中国大陆行政区划（REQUEST_LEVEL_ADMIN_AREA）
//            return TencentLocationRequest.create().setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_ADMIN_AREA);
//        }
//        return locationRequest;
//    }
//
//}
