package org.example.configurator;

import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class JavaBeanConfigurator {

    private final Reflections reflections;

    public JavaBeanConfigurator() {
        reflections = new Reflections(new ConfigurationBuilder().forPackages(""));
    }

    public <T> Class<? extends T> getImplementationClass(Class<T> interfaceClass) {
        Set<Class<? extends T>> implClasses = reflections.getSubTypesOf(interfaceClass);

        if(implClasses.size() != 1) {
            throw new RuntimeException();
        }

        return implClasses.iterator().next();
    }

}
