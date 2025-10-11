package org.example.context;

import org.example.annotations.MyComponent;
import org.example.annotations.MyScan;
import org.example.annotations.MyScope;
import org.example.factory.BeanFactory;
import org.example.scanner.ConfigScanner;
import org.reflections.Reflections;

import java.beans.Introspector;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MiniApplicationContext {

    private final BeanFactory beanFactory;

    private final Map<String, Object> singletonBeans;

    public MiniApplicationContext(Class<?> configClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        beanFactory = BeanFactory.getInstance();
        singletonBeans = new HashMap<>();
        createSingletonBeans(configClass);
    }

    private void createSingletonBeans(Class<?> configClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        MyScan scan = configClass.getAnnotation(MyScan.class);

        Reflections reflections = new Reflections(scan.value());

        Set<Class<?>> classesSet = reflections.getTypesAnnotatedWith(MyComponent.class);

        for(var cl : classesSet) {
            if(cl.isAnnotationPresent(MyScope.class)) {
                MyScope scope = cl.getAnnotation(MyScope.class);
                if(scope.value().equals("prototype")) {
                    continue;
                }
            }

            putOnMap(singletonBeans, cl);
        }
    }

    private <T> void putOnMap(Map<String, Object> objectMap,  Class<T> objectClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        T obj = beanFactory.getBean(objectClass);
        objectMap.put(Introspector.decapitalize(objectClass.getSimpleName()), obj);
    }

    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> tClass) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if(tClass.isAnnotationPresent(MyScope.class)) {
            MyScope scope = tClass.getAnnotation(MyScope.class);
            if(scope.value().equals("prototype")) {
                return beanFactory.getBean(tClass);
            }
        }

        return (T) singletonBeans.get(Introspector.decapitalize(tClass.getSimpleName()));
    }

}
