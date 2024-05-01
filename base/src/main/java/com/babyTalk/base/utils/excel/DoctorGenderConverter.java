package com.babyTalk.base.utils.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.babyTalk.base.exception.ExcelDataException;

/**
 * （1男 2女 3未知
 */
public class DoctorGenderConverter implements Converter<Integer> {
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
//        System.out.println("cellData.toString()"+cellData.toString());
        if (statusDes == null) {
//            System.out.println("-----------"+statusDes);
            throw new ExcelDataException("excel状态错误");
        }
//        System.out.println("性别statusDes_____________________"+statusDes);
        if(statusDes.equals("男")){
            return 1;
        }else if(statusDes.equals("女")){
            return 2;
        } else {
            return 0;
        }



    }

    @Override
    public CellData<String> convertToExcelData(Integer integer, ExcelContentProperty excelContentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if(integer==1){
            return new CellData<>("男");
        }else if(integer==2){
            return new CellData<>("女");
        }else {
            throw new ExcelDataException("excel状态错误");
        }
    }
}
