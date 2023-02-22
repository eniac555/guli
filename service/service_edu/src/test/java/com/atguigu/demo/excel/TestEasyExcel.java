package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
        //实现Excel写的操作
        //设置写入的文件夹地址和Excel文件名称

        //String filename = "D:\\Java\\write.xlsx";

        //调用esayexcel里面的方法实现写操作

        //EasyExcel.write(filename, DemoDate.class).sheet("学生列表").doWrite(getDate());
        //两个参数：文件名  实体类名称

        //实现读操作
        String filename = "D:\\Java\\write.xlsx";
        EasyExcel.read(filename, DemoDate.class, new ExcelListener()).sheet().doRead();

    }

    //创建一个方法，返回list集合
    private static List<DemoDate> getDate() {
        List<DemoDate> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoDate date = new DemoDate();
            date.setSno(i);
            date.setSname("lucy" + i);
            list.add(date);
        }
        return list;
    }
}
