package com.babyTalk.base.utils.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import com.babyTalk.base.utils.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

/**
 * excel输出时Timestamp和String的转换
 */
public class TimestampConverter implements Converter<Timestamp> {

	@Override
	public Class<Timestamp> supportJavaTypeKey() {
		return Timestamp.class;
	}

	@Override
	public CellDataTypeEnum supportExcelTypeKey() {
		return CellDataTypeEnum.STRING;
	}

	@Override
	public Timestamp convertToJavaData(CellData cellData, ExcelContentProperty contentProperty,
									   GlobalConfiguration globalConfiguration) throws ParseException {

//		final String dataFormatString = cellData.getDataFormatString();
		final String dataFormatString = cellData.toString();
//		System.out.println("shijian dataFormatString   "+dataFormatString);
//		System.out.println("时间cellData.getDataFormatString()————————————————————————"+cellData.getDataFormatString());
		final Date date = DateUtils.stringToDate(dataFormatString, "yyyy-MM-dd HH:mm:ss");
		if (date == null) {
			return null;
		}
		return new Timestamp(date.getTime());
	}

	@Override
	public CellData<String> convertToExcelData(Timestamp value, ExcelContentProperty contentProperty,
	                                           GlobalConfiguration globalConfiguration) {
		return new CellData<>(DateUtils.dateToString(new Date(value.getTime()),"yyyy-MM-dd HH:mm:ss"));
	}

}