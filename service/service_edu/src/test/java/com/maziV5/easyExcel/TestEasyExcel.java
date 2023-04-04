package com.maziV5.easyExcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args) {
/*        //设置写入文件夹地址和Excel名称
        String file = "D:\\1.xlsx";

        //调用easyexcel
        EasyExcel.write(file, DemoData.class).sheet("学生列表").doWrite(getList());*/

        //excel读操作
        String file = "D:\\1.xlsx";

        EasyExcel.read(file, DemoData.class,new ExcelListener()).sheet().doRead();

    }

    private static List<DemoData> getList(){
        ArrayList<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setSno(i);
            data.setSname("tom" + i);
            list.add(data);
        }

        return list;
    }
}
