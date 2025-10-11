package org.example.factory;

import org.example.annotations.MyAutowired;
import org.example.configurator.JavaBeanConfigurator;
import org.example.lifecycle.InitializingBean;

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

    private <T> Class<? extends T> chooseImplementation(Class<T> tClass) {
        Class<? extends T> implementationClass = tClass;
        if(implementationClass.isInterface()) {
            return beanConfigurator.getImplementationClass(implementationClass);
        }
        return implementationClass;
    }

    private <T> T createBean(Class<? extends T> implementationClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return implementationClass.getDeclaredConstructor().newInstance();
    }

    private <T> T injectDependencyBean(T bean, Class<? extends T> implementationClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<Field> listField = Arrays.stream(implementationClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(MyAutowired.class)).toList();

        for(Field f : listField) {
            f.setAccessible(true);
            f.set(bean, BEAN_FACTORY.getBean(f.getType()));
        }
        return bean;
    }

    private <T> void afterPropertiesInitBean(T bean) {
        if(bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }
    }

    public <T> T getBean(Class<T> tClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<? extends T> implClass = chooseImplementation(tClass);

        T bean = injectDependencyBean(createBean(implClass), implClass);

        afterPropertiesInitBean(bean);

        return bean;
    }

}
