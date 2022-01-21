
package net.xmmpp.uc.common.dto;

import net.xmmpp.uc.common.utils.BizException;
import net.xmmpp.uc.common.utils.ResponseMessageEnum;

import java.util.Optional;


public class Response<T> {

    private int code;
    private String message;
    private T data;

    public Response(ResponseMessageEnum messageEnum) {
        this.code = messageEnum.getCode();
        this.message = messageEnum.getMessage();
    }

     public Response(int code, String message) {
        this.code = code;
        this.message = message;
    }



    public Response(BizException ratelException) {
        this.code = ratelException.getCode();
        this.message = ratelException.getMessage();
    }

    public Response(T data) {
        this(ResponseMessageEnum.OK);
        this.data = data;
    }

    public Response(ResponseMessageEnum messageEnum, T data) {
        this(messageEnum);
        this.data = data;
    }

    public Response(Optional<T> data) {
        if (data.isPresent()) {
            code = ResponseMessageEnum.OK.getCode();
            this.data = data.get();
        } else {
            code = ResponseMessageEnum.REQUEST_ERROR.getCode();
        }
    }

    public Response(Throwable t) {
        this(ResponseMessageEnum.SERVICE_ERROR);
        this.message = t.getMessage();
    }

    public static final Response<Boolean> OK = new Response<>(true);

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
