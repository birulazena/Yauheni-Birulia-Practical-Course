package org.example.factory;

import org.example.annotations.MyAutowired;
import org.example.configurator.JavaBeanConfigurator;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class BeanFactory {

    private static final BeanFactory BEAN_FACTORY = new BeanFactory();

    private final JavaBeanConfigurator beanConfigurator;

    private BeanFactory() {
        this.beanConfigurator = new JavaBeanConfigurator();
    }

    public static BeanFactory getInstance() {
        return BEAN_FACTORY;
    }

    public <T> T getBean(Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends T> implClass = tClass;
        if(implClass.isInterface()) {
            implClass = beanConfigurator.getImplementationClass(implClass);
        }

        T bean =  implClass.getDeclaredConstructor().newInstance();

        List<Field> listField = Arrays.stream(implClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(MyAutowired.class)).toList();

        for(Field f : listField) {
            f.setAccessible(true);
            f.set(bean, BEAN_FACTORY.getBean(f.getType()));
        }

        return bean;
    }

}
