package net.mikoto.yukino.model.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstantiableObject {
    private String type;
    private String[] paramTypes;
    private InstantiableObject[] objectValues = null;
    private Object[] values = null;

    @NotNull
    public static Object instance(@NotNull InstantiableObject instantiableObject) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(instantiableObject.getType());

        Class<?>[] paramClasses = new Class[instantiableObject.getParamTypes().length];
        for (int i = 0; i < instantiableObject.getParamTypes().length; i++) {
            paramClasses[i] = Class.forName(instantiableObject.getParamTypes()[i]);
        }

        Constructor<?> constructor = clazz.getConstructor(paramClasses);

        if (instantiableObject.getObjectValues() != null && instantiableObject.getObjectValues().length > 0) {
            Object[] paramValues = new Object[instantiableObject.getObjectValues().length];
            for (int i = 0; i < instantiableObject.getObjectValues().length; i++) {
                paramValues[i] = instance(instantiableObject.getObjectValues()[i]);
            }
            return constructor.newInstance(paramValues);
        } else {
            return constructor.newInstance(instantiableObject.getValues());
        }
    }
}
