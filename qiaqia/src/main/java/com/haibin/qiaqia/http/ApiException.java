package com.haibin.qiaqia.http;

/**
 * Created by invinjun on 2016/6/2.
 */
public class ApiException extends RuntimeException {

    public static final int ERROR_KEY = 404;
    public static final int WRONG_PARAMS = 500;

    public ApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    public ApiException(String detailMessage) {
        super(detailMessage);
    }

    /**
     * 由于服务器传递过来的错误信息直接给用户看的话，用户未必能够理解
     * 需要根据错误码对错误信息进行一个转换，在显示给用户
     * @param code
     * @return
     */
    private static String getApiExceptionMessage(int code){
        String message = "";
        switch (code) {
            case ERROR_KEY:
                message = "密钥错误";
                break;
            case WRONG_PARAMS:
                message = "参数错误";
                break;
            default:
                message = "未知错误";

        }
        return message;
    }
}
