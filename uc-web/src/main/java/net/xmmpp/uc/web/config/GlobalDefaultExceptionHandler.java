package net.xmmpp.uc.web.config;

import net.xmmpp.uc.common.dto.Response;
import net.xmmpp.uc.common.utils.BizException;
import net.xmmpp.uc.common.utils.ResponseMessageEnum;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;


@RequestMapping("/error")
@RestControllerAdvice
public class GlobalDefaultExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);


    @ExceptionHandler
    @ResponseBody
    public Response<?> handler(HttpServletResponse httpServletResponse, Exception e) {
        LOGGER.error("handler | exception : ", e);
        return new Response<>(ResponseMessageEnum.SERVICE_ERROR);
    }

    @RequestMapping("404")
    public Response<String> handle404Error(HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(200);
        return new Response<>(ResponseMessageEnum.REQUEST_ERROR);
    }

    @RequestMapping("500")
    public Response<String> handle500Error(HttpServletResponse httpServletResponse) {
        httpServletResponse.setStatus(200);
        return new Response<>(ResponseMessageEnum.SERVICE_ERROR);
    }

    @Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            ErrorPage error404 = new ErrorPage(HttpStatus.NOT_FOUND, "/error/404");
            ErrorPage error500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500");
            factory.addErrorPages(error404, error500);
        };
    }

    @ExceptionHandler(value = BizException.class)
    @ResponseBody
    public Response<?> handler(BizException e) {
        LOGGER.warn("handler | exception : ", e);
        return new Response<>(e);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            MissingServletRequestParameterException.class,
            MethodArgumentTypeMismatchException.class,
            HttpMessageNotReadableException.class})
    @ResponseBody
    public Response<?> handler(Exception e) {

        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException argExce = (MethodArgumentNotValidException) e;
            String msg = argExce.getBindingResult().getFieldError().getDefaultMessage();
            return new Response<>(ResponseMessageEnum.INVALID_REQUEST_PARAMETER.getCode(), msg);
        }

        LOGGER.warn("handler | exception : ", e);
        return new Response<>(ResponseMessageEnum.INVALID_REQUEST_PARAMETER);
    }

    @ExceptionHandler(value = {
            PersistenceException.class
    })
    @ResponseBody
    public Response<?> handleDBException(Exception e) {
        LOGGER.error("handleDBException | exception: ", e);
        return new Response<>(ResponseMessageEnum.DB_ACCESS_ERROR);
    }
}
