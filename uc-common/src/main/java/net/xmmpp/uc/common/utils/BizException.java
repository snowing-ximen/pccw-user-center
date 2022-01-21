/**
 * -------------------------------------------------------------------------------------------------
 * File    :  GroupException.java
 * Author  :  Nan Xuejiao <xuejiao@bmxlabs.com>
 * Purpose :
 * Created : 28 Oct 2018 by Nan Xuejiao <xuejiao@bmxlabs.com>
 * -------------------------------------------------------------------------------------------------
 * <p>
 * Copyright (C) 2018-2019   MaxIM.Top
 * <p>
 * You may obtain a copy of the licence at http://www.maxim.top/LICENCE-MAXIM.md
 * <p>
 * -------------------------------------------------------------------------------------------------
 */

package net.xmmpp.uc.common.utils;


import java.text.MessageFormat;

public class BizException extends RuntimeException {


    private ResponseMessageEnum responseMessageEnum;
    private Object[] objects;

    public BizException(String message) {
        super(message);
    }

    public BizException(ResponseMessageEnum responseMessageEnum) {
        super(responseMessageEnum.getMessage());
        this.responseMessageEnum = responseMessageEnum;
    }

    public BizException(ResponseMessageEnum responseMessageEnum, Object... objects) {
        super(MessageFormat.format(responseMessageEnum.getMessage(), objects));
        this.responseMessageEnum = responseMessageEnum;
        this.objects = objects;
    }

    @Override
    public String getMessage() {
        return MessageFormat.format(responseMessageEnum.getMessage(), objects);
    }

    public int getCode() {
        return this.responseMessageEnum.getCode();
    }
}
