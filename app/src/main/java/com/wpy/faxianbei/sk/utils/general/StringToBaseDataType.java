package com.wpy.faxianbei.sk.utils.general;

/**
 * Created by wpy on 16-12-12.
 * 字符串转换为基本的数据类型
 */

public class StringToBaseDataType {
    /**
     * 将Object类型对象转换为int基本类型
     * @param value object 待转换的Object对象
     * @param defaultValue 转换不成功时返回的默认值
     * @return 返回转换结束后的int值
     */
    public static final int convertToInt(Object value,int defaultValue){
        if(value==null||"".equals(value.toString().trim())){
            return defaultValue;
        }
        try{
            return Integer.valueOf(value.toString().trim());
        }catch (Exception e){
            try{
                return Double.valueOf(value.toString().trim()).intValue();
            }catch (Exception e1){
                return defaultValue;

            }
        }
    }

    /**
     * 将Object类型对象转换为float基本类型
     * @param value 待转换的Object对象
     * @param defaultValue 转换不成功时返回的默认值
     * @return 返回转换结束后的float值
     */
    public static final float convertToFloat(Object value,float defaultValue){
        if(value==null||"".equals(value.toString().trim())){
            return defaultValue;
        }
        try{
            return Float.valueOf(value.toString().trim());
        }catch (Exception e){
                return defaultValue;
        }
    }

    /**
     * 将Object类型对象转换为float基本类型
     * @param value 待转换的Object对象
     * @param defaultValue 转换不成功时返回的默认值
     * @return 返回转换结束后的double值
     */
    public static final Double convertToDouble(Object value,double defaultValue){
        if(value==null||"".equals(value.toString().trim())){
            return defaultValue;
        }
        try{
            return Double.valueOf(value.toString().trim());
        }catch (Exception e){
            return defaultValue;
        }
    }
}
