package com.arno.blog.framework.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.Validate;

import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

/**
 * 反射工具類
 *
 * @author 楊德石
 */
@Slf4j
public class ReflectionUtils {

    private static final String SETTER_PREFIX = "set";

    private static final String GETTER_PREFIX = "get";

    private static final String CGLIB_CLASS_SEPARATOR = "$$";

    /**
     * 調用Getter方法.
     */
    public static Object invokeGetter(Object obj, String propertyName) {
        String getterMethodName = GETTER_PREFIX + StringUtils.capitalize(propertyName);
        return invokeMethod(obj, getterMethodName, new Class[]{}, new Object[]{});
    }

    /**
     * 調用Setter方法, 僅匹配方法名。
     */
    public static void invokeSetter(Object obj, String propertyName, Object value) {
        String setterMethodName = SETTER_PREFIX + StringUtils.capitalize(propertyName);
        invokeMethodByName(obj, setterMethodName, new Object[]{value});
    }

    /**
     * 直接讀取對象屬性值, 無視private/protected修飾符, 不經過getter函數.
     */
    public static Object getFieldValue(final Object obj, final String fieldName) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("沒有找到 [" + fieldName + "] 屬性。對象為 [" + obj + "]");
        }

        Object result = null;
        try {
            result = field.get(obj);
        } catch (IllegalAccessException e) {
            log.error("不可能拋出的異常{}", e.getMessage());
        }
        return result;
    }

    /**
     * 直接設置對象屬性值, 無視private/protected修飾符, 不經過setter函數.
     */
    public static void setFieldValue(final Object obj, final String fieldName, final Object value) {
        Field field = getAccessibleField(obj, fieldName);

        if (field == null) {
            throw new IllegalArgumentException("沒有找到 [" + fieldName + "] 屬性。對象為 [" + obj + "]");
        }

        try {
            field.set(obj, value);
        } catch (IllegalAccessException e) {
            log.error("不可能拋出的異常:{}", e.getMessage());
        }
    }

    /**
     * 直接調用對象方法, 無視private/protected修飾符. 用於一次性調用的情況，否則應使用getAccessibleMethod()函數獲得Method後反復調用. 同時匹配方法名+參數類型，
     */
    public static Object invokeMethod(final Object obj, final String methodName, final Class<?>[] parameterTypes, final Object[] args) {
        Method method = getAccessibleMethod(obj, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("沒有找到 [" + methodName + "] 方法。對象為 [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 直接調用對象方法, 無視private/protected修飾符， 用於一次性調用的情況，否則應使用getAccessibleMethodByName()函數獲得Method後反復調用.
     * 只匹配函數名，如果有多個同名函式呼叫第一個。
     */
    public static Object invokeMethodByName(final Object obj, final String methodName, final Object[] args) {
        Method method = getAccessibleMethodByName(obj, methodName);
        if (method == null) {
            throw new IllegalArgumentException("沒有找到 [" + methodName + "] 方法。對象為 [" + obj + "]");
        }

        try {
            return method.invoke(obj, args);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 迴圈向上轉型, 獲取對象的DeclaredField, 並強制設置為可訪問.
     * <p>
     * 如向上轉型到Object仍無法找到, 返回null.
     */
    public static Field getAccessibleField(final Object obj, final String fieldName) {
        Validate.notNull(obj, "對象不能為空");
        Validate.notBlank(fieldName, "欄位名不能為空");
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                Field field = superClass.getDeclaredField(fieldName);
                makeAccessible(field);
                return field;
            } catch (NoSuchFieldException e) {
                // Field不在當前類定義,繼續向上轉型
            }
        }
        return null;
    }

    /**
     * 迴圈向上轉型, 獲取對象的DeclaredMethod,並強制設置為可訪問. 如向上轉型到Object仍無法找到, 返回null. 匹配函數名+參數類型。
     * <p>
     * 用於方法需要被多次調用的情況. 先使用本函數先取得Method,然後調用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethod(final Object obj, final String methodName,
                                             final Class<?>... parameterTypes) {
        Validate.notNull(obj, "對象不能為空");
        Validate.notBlank(methodName, "方法名不能為空");

        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
                .getSuperclass()) {
            try {
                Method method = searchType.getDeclaredMethod(methodName, parameterTypes);
                makeAccessible(method);
                return method;
            } catch (NoSuchMethodException e) {
                // Method不在當前類定義,繼續向上轉型
            }
        }
        return null;
    }

    /**
     * 迴圈向上轉型, 獲取對象的DeclaredMethod,並強制設置為可訪問. 如向上轉型到Object仍無法找到, 返回null. 只匹配函數名。
     * <p>
     * 用於方法需要被多次調用的情況. 先使用本函數先取得Method,然後調用Method.invoke(Object obj, Object... args)
     */
    public static Method getAccessibleMethodByName(final Object obj, final String methodName) {
        Validate.notNull(obj, "對象不能為空");
        Validate.notBlank(methodName, "方法名不能為空");

        for (Class<?> searchType = obj.getClass(); searchType != Object.class; searchType = searchType
                .getSuperclass()) {
            Method[] methods = searchType.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    makeAccessible(method);
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 改變private/protected的方法為public，儘量不調用實際改動的語句，避免JDK的SecurityManager抱怨。
     */
    @SuppressWarnings("unused")
	public static void makeAccessible(Method method) {
        boolean flag = (!Modifier.isPublic(method.getModifiers()) || !Modifier.isPublic(method.getDeclaringClass().getModifiers()))
                && !method.isAccessible();
        if (false) {
            method.setAccessible(true);
        }
    }

    /**
     * 改變private/protected的成員變數為public，儘量不調用實際改動的語句，避免JDK的SecurityManager抱怨。
     */
    public static void makeAccessible(Field field) {
        boolean flag = (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())
                || Modifier.isFinal(field.getModifiers())) && !field.isAccessible();
        if (flag) {
            field.setAccessible(true);
        }
    }

    /**
     * 通過反射, 獲得Class定義中聲明的泛型參數的類型, 注意泛型必須定義在父類處 如無法找到, 返回Object.class
     */
    public static <T> Class<T> getClassGenericType(final Class clazz) {
        return getClassGenericType(clazz, 0);
    }

    /**
     * 通過反射，設置指定對象中擁有指定注解的值
     */
    public static void setFieldValueByAnnotation(final Object obj, final Class annotation, final Object value) {
        List<Field> fields = getFields(obj.getClass());
        for (Field field : fields) {
            if (field.isAnnotationPresent(annotation)) {
                // 存在此注解，賦值
                setFieldValue(obj, field.getName(), value);
            }
        }
    }

    /**
     * 通過反射, 獲得Class定義中聲明的父類的泛型參數的類型. 如無法找到, 返回Object.class.
     * 如public UserDao extends HibernateDao<User,Long>
     */
    public static Class getClassGenericType(final Class clazz, final int index) {

        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            log.warn(clazz.getSimpleName() + "的父類不是 ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if ((index >= params.length) || (index < 0)) {
            log.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: "
                    + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            log.warn(clazz.getSimpleName() + " 沒有設置泛型");
            return Object.class;
        }

        return (Class) params[index];
    }

    /**
     * 獲取CGLib處理過後的實體的原類.
     */
    public static Class<?> getUserClass(Object instance) {
        Validate.notNull(instance, "Instance不能為空");
        Class<?> clazz = instance.getClass();
        if ((clazz != null) && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
            Class<?> superClass = clazz.getSuperclass();
            if ((superClass != null) && !Object.class.equals(superClass)) {
                return superClass;
            }
        }
        return clazz;

    }

    /**
     * 將反射時的checked exception轉換為unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        if ((e instanceof IllegalAccessException) || (e instanceof IllegalArgumentException)
                || (e instanceof NoSuchMethodException)) {
            return new IllegalArgumentException(e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException("Unexpected Checked Exception.", e);
    }

    /**
     * 遞迴所有Field
     *
     * @param clazz
     * @return
     */
    public static List<Field> getFields(Class<?> clazz) {
        List<Field> fields = Lists.newArrayList();
        Class<?> current = clazz;
        while (!current.getName().equals(Object.class.getName())) {
            getFields(fields, current);
            current = current.getSuperclass();
        }
        return fields;
    }

    /**
     * 遞迴獲取指定的欄位值，包括子屬性，屬性之間用.分隔
     *
     * @param paramsObject
     * @param key
     * @return
     */
    public static Object getFieldValueWithChild(Object paramsObject, String key) {
        Object value = null;
        Class<?> paramClass = paramsObject.getClass();
        Field field;
        if (StringUtils.isNotBlank(key)) {
            // 使用.截取
            String[] keys = StringUtils.split(key, ".", 2);
            if (keys.length > 0) {
                // 獲取
                try {
                    field = paramClass.getDeclaredField(StringUtils.trim(keys[0]));
                    if (field == null) {
                        return null;
                    }
                    if (keys.length == 1) {
                        return ReflectionUtils.getFieldValue(paramsObject, field.getName());
                    }
                    if (keys.length > 1) {
                        // 大於1，有點，說明是匹配子級屬性
                        Object childObject = ReflectionUtils.getFieldValue(paramsObject, field.getName());
                        return getFieldValueWithChild(childObject, keys[1]);
                    }
                } catch (NoSuchFieldException e) {
                    // 沒有找到屬性，說明該類中沒有該屬性
                    return null;
                }
            }
        }
        return value;
    }

    private static void getFields(List<Field> fields, Class<?> clazz) {
        fields.addAll(Arrays.asList(clazz.getDeclaredFields()));
    }
}