package com.babyTalk.base.exception;

/**
 * 表格数据错误
 */
public class ExcelDataException extends Exception{
    public ExcelDataException(){
        super();
    }

    public ExcelDataException(String message){
        super(message);
    }
}
