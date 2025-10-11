package org.example.annotations;

public @interface MyScope {
    String value() default "singleton";
}
