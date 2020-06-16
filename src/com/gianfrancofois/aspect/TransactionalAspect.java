package com.gianfrancofois.aspect;

import com.gianfrancofois.annotation.InjectConnection;
import com.gianfrancofois.config.ConnectionManager;
import com.gianfrancofois.config.YamlConfigLoader;
import com.gianfrancofois.exception.InjectConnectionNotFoundException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Aspect
public class TransactionalAspect {

    @Around("@annotation(com.gianfrancofois.annotation.Transactional) && execution(@com.gianfrancofois.annotation.Transactional * *.*(..))")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {

        ConnectionManager connectionManager = new ConnectionManager(new YamlConfigLoader("connection.config.yml"));
        Connection connection = connectionManager.openConnection();

        Object instance = joinPoint.getThis();
        setConnectionField(instance, connection);

        Object returnValue = joinPoint.proceed();

        connection.commit();
        connection.close();

        return returnValue;
    }

    /* searches recursively the @InjectConnection annotated field */
    private Field findConnectionField(Class<?> instanceClass) {
        if (instanceClass == null) throw new InjectConnectionNotFoundException();

        List<Field> fields = Arrays.asList(instanceClass.getDeclaredFields());
        Optional<Field> fieldOptional = fields.stream()
                .filter(f -> f.isAnnotationPresent(InjectConnection.class))
                .findAny();

        return fieldOptional.orElseGet(() -> findConnectionField(instanceClass.getSuperclass()));
    }

    private void setConnectionField(Object instance, Connection connection) throws IllegalAccessException {
        Class<?> instanceClass = instance.getClass();
        Field connectionField = findConnectionField(instanceClass);
        connectionField.setAccessible(true);
        connectionField.set(instance, connection);
    }

}
