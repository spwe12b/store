package com.me.store.util;



import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.core.type.WritableTypeId;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang3.StringUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.SimpleDateFormat;

//json与对象类型相互转化
public class JsonUtil {
    private static final Logger logger= LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper objectMapper=new ObjectMapper();
    static {
        //序列化
        //对象的所有字段全部列入
        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        //取消默认转换timestamps形式
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //忽略空Bean转json的错误
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        //所有日期格式都统一为:yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_PATTERN));
        //反序列化
        //忽略在json中存在但在java对象中不存在
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
    }
    public static <T> String obj2String(T obj){
        if(obj==null){
            return null;
        }
        try{
           return obj instanceof String?(String)obj:objectMapper.writeValueAsString(obj);
        }catch (IOException e){
           logger.error("Parse object to String error",e);
           return null;
       }
    }
    public static <T> String obj2StringPretty(T obj){
        if(obj==null){
            return null;
        }
        try{
            return obj instanceof String?(String)obj:objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        }catch (IOException e){
            logger.error("Parse object to String error",e);
            return null;
        }
    }
    public static <T> T string2Obj(String str,Class<T> clazz){
        if(StringUtils.isEmpty(str)||clazz==null){
            return null;
        }
        try {
            return clazz.equals(String.class) ? (T)str : objectMapper.readValue(str, clazz);
        }catch (IOException e){
            logger.error("Parse String to object error",e);
            return null;
        }
    }
    public static <T> T string2Obj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str)||typeReference==null){
            return null;
        }
        try {
            return typeReference.getType().equals(String.class)?(T)str:objectMapper.readValue(str,typeReference);
        }catch (IOException e){
            logger.error("Parse String to object error",e);
            return null;
        }
    }
    public static <T> T string2Obj(String str, Class<?> collectionClass,Class<?>...elementClasses) {
        JavaType javaType=objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try{
            return objectMapper.readValue(str,javaType);
        }catch (Exception e){
            logger.warn("Parse String to Object error", e);
            return null;
        }
    }

}

