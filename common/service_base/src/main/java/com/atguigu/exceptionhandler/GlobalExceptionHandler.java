package com.atguigu.exceptionhandler;


import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//Exception是兜底的，先去找特定异常

@ControllerAdvice
@Slf4j//异常信息输出到文件的注解
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) //指定出现什么异常执行这个方法
    @ResponseBody//为了能够返回数据
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }


    //特定异常处理   用到少，因为不知道会遇到具体那个异常
    @ExceptionHandler(ArithmeticException.class) //指定出现什么异常执行这个方法
    @ResponseBody//为了能够返回数据
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }


    //处理自定义异常
    @ExceptionHandler(GuliException.class) //指定出现什么异常执行这个方法
    @ResponseBody//为了能够返回数据

    public R error(GuliException e){
        log.error(e.getMessage());//把信息写到日志
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
