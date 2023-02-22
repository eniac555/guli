package com.atguigu.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.util.ConverterUtils;

import java.util.Map;

public class ExcelListener extends AnalysisEventListener<DemoDate> {

    //逐行读取
    @Override
    public void invoke(DemoDate date, AnalysisContext analysisContext) {
        System.out.println("****" + date);
    }

    //读取完后做的事情
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    //读取表头
    @Override
    public void invokeHead(Map<Integer, CellData> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

}
