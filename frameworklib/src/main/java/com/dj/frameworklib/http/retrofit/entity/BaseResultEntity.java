package com.dj.frameworklib.http.retrofit.entity;

/**
 * Created by dengjun on 2019/3/25.
 * 返回值基类
 * {
 *     "data":T,
 *
 *     "ResponseMessage": {
 *        "message": {
 *           "en": "success",
 *           "zh_CN": "操作成功"
 *         },
 *        "status": "success"
 *      }
 * }
 *
 */

public class BaseResultEntity<T> {
    /**
     * T 如果是[]，对应ArrayList
     * T 如果是{}，对应JavaBean
     * T 如果是""，对应String
     */
    private T data;
    private ResponseMessageEntity ResponseMessage;

    /**
     * 服务器返回的数据是否成功
     * @return
     */
    public boolean isSuccess(){
        if(ResponseMessage == null){
            return false;
        }

        return "success".equals(ResponseMessage.status);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ResponseMessageEntity getResponseMessage() {
        return ResponseMessage;
    }

    public void setResponseMessage(ResponseMessageEntity responseMessage) {
        ResponseMessage = responseMessage;
    }

    public static class ResponseMessageEntity{
        private MessageEntity message;
        private String status;

        public MessageEntity getMessage() {
            return message;
        }

        public void setMessage(MessageEntity message) {
            this.message = message;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


    public static class MessageEntity{
        private String en;
        private String zh_CN;

        public String getEn() {
            return en;
        }

        public void setEn(String en) {
            this.en = en;
        }

        public String getZh_CN() {
            return zh_CN;
        }

        public void setZh_CN(String zh_CN) {
            this.zh_CN = zh_CN;
        }
    }
}
