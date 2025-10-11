package org.example.scanner;
import org.example.annotations.MyConfig;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

import java.util.Set;

public class ConfigScanner {
    public static Class<?> findConfClass() {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages(""));

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(MyConfig.class);

        if(classes.size() != 1) {
            throw new RuntimeException();
        }

        return classes.iterator().next();

    }
}
