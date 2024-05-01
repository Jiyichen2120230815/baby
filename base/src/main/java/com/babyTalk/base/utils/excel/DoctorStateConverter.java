package com.babyTalk.base.utils.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.babyTalk.base.exception.ExcelDataException;

/**
 * （1审核通过启用 2待审核 3审核不通过 4禁用
 */
public class DoctorStateConverter implements Converter<Integer> {
    @Override
    public Class<Integer> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(CellData cellData, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
//        String statusDes=cellData.getDataFormatString();
        String statusDes=cellData.toString();
        if (statusDes == null) {
            throw new ExcelDataException("excel状态错误");
        }
//        System.out.println("状态statusDes——————————————"+statusDes);

        if(statusDes.equals("启用")){
            return 1;
        }else if(statusDes.equals("禁用")){
            return 4;
        }else {
            return 0;
        }
    }

    @Override
    public CellData<String> convertToExcelData(Integer integer, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if(integer==1){
            return new CellData<>("启用");
        }else if(integer==4){
            return new CellData<>("禁用");
        }else {
            throw new ExcelDataException("excel状态错误");
        }
    }
}
