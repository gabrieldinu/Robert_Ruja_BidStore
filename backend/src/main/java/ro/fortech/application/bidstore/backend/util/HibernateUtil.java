package ro.fortech.application.bidstore.backend.util;

import org.hibernate.HibernateException;

import javax.persistence.Column;
import java.lang.reflect.Field;

/**
 * Created by robert.ruja on 21-Apr-17.
 */
public class HibernateUtil {

    //This only works on explicitly annotated fields with @Column
    public static String getColumNameFromField(Class entity, String fieldName) throws HibernateException {

        Class clazz = entity;
        Field field = null;

        while(clazz.getSuperclass() != null){
            try {
                field = clazz.getDeclaredField(fieldName);
                break;
            } catch (NoSuchFieldException e){
                clazz = clazz.getSuperclass();
            }
        }

        if(field !=null) {

            Column column = field.getAnnotation(Column.class);
            if(column != null) {
                return column.name().equals("") ? fieldName : column.name();
            }else{
                return fieldName;
            }
        }else{
            throw new HibernateException("Class: " + clazz.getName() + " does not have the field: " + fieldName);
        }
    }
}
