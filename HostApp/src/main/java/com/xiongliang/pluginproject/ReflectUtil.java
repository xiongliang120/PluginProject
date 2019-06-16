package com.xiongliang.pluginproject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtil {
    /**
     * 反射构造函数
     * @param className
     * @param paraType
     * @param paraValues
     * @return
     */
    public static Object createObject(String className, Class[] paraType, Object[] paraValues){
        try{
            Class temp = Class.forName(className);
            Constructor c2 = temp.getDeclaredConstructor(paraType);
            c2.setAccessible(true);
            Object object = c2.newInstance(paraValues);
            return object;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射调用实例方法
     * @param obj
     * @param methodName
     * @param paraType
     * @param paraValues
     * @return
     */
    public static Object invokeInstanceMethod(Object obj, String methodName, Class[] paraType, Object[] paraValues){
        try{
            if(obj != null){
                Method method = obj.getClass().getDeclaredMethod(methodName,paraType); //在指定类中获取指定的方法
                method.setAccessible(true);
                Object result = method.invoke(obj,paraValues);
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 反射调用静态方法
     * @param className
     * @param methodName
     * @param paramType
     * @param paraValues
     * @return
     */
    public static Object invokeStaticMethod(String className, String methodName, Class[] paramType, Object[] paraValues){
        try{
            Class temp = Class.forName(className);
            Method method = temp.getDeclaredMethod(methodName,paramType);
            method.setAccessible(true);
            Object result = method.invoke(null,paraValues);
            return result;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射获取实例字段
     * @param className
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldObject(String className, Object obj, String fieldName){
        try{
            Class temp = Class.forName(className);
            Field field = temp.getDeclaredField(fieldName);
            field.setAccessible(true);

            Object fieldObject = field.get(obj);
            return fieldObject;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static Object getFieldObject(Object obj, String fieldName){
        try{
            Class temp = obj.getClass();
            Field field = temp.getDeclaredField(fieldName);
            field.setAccessible(true);

            Object fieldObject = field.get(obj);
            return fieldObject;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 反射获取静态字段
     * @param className
     * @param fieldName
     * @return
     */
    public static Object getStaticFieldObject(String className, String fieldName){
        return getFieldObject(className,null,fieldName);
    }

    /**
     * 反射设置实例字段值
     * @param className
     * @param obj
     * @param fieldName
     * @param fieldValue
     */
    public static void setFileObject(String className, Object obj, String fieldName, Object fieldValue){
        try{
            Class temp = Class.forName(className);
            Field field = temp.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj,fieldValue);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 反射设置实例字段值
     * @param obj
     * @param fieldName
     * @param fieldValue
     */
    public static void setFileObject(Class mClass, Object obj, String fieldName, Object fieldValue){
        try{
            Field field = mClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj,fieldValue);
        }catch (Exception e){
            e.printStackTrace();
        }
    }




    /**
     * 反射设置静态字段值
     * @param className
     * @param fieldName
     * @param fieldValue
     */
    public static void setStaticFieldObject(String className, String fieldName, String fieldValue){
        setFileObject(className,null,fieldName,fieldValue);
    }



}
