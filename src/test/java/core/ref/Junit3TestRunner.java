package core.ref;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Junit3TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit3Test> clazz = Junit3Test.class;
        Constructor<Junit3Test> constructor = clazz.getConstructor();
        Junit3Test node = constructor.newInstance();

        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("test")) {
                method.invoke(node);
            }
        }
    }
}
