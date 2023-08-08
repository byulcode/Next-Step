package core.ref;

import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Junit4TestRunner {
    @Test
    public void run() throws Exception {
        Class<Junit4Test> clazz = Junit4Test.class;
        Constructor<Junit4Test> constructor = clazz.getConstructor();
        Junit4Test node = constructor.newInstance();

        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyTest.class)) {
                method.invoke(node);
            }
        }
    }
}
