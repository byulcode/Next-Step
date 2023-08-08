package core.ref;

import next.model.Question;
import next.model.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

public class ReflectionTest {
    private static final Logger logger = LoggerFactory.getLogger(ReflectionTest.class);

    @Test
    public void showClass() {
        Class<Question> clazz = Question.class;
        logger.debug(clazz.getName());

        Arrays.stream(clazz.getDeclaredFields()).forEach(f -> logger.debug("constructor : {}", f));
        Arrays.stream(clazz.getDeclaredConstructors()).forEach(c -> logger.debug("field : {}", c));
        Arrays.stream(clazz.getDeclaredMethods()).forEach(m -> logger.debug("method : {}", m));
    }

    @Test
    public void newInstanceWithConstructorArgs() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());

        Constructor constructor = clazz.getConstructor(new Class[]{String.class, String.class, String.class, String.class});

        User user = (User) constructor.newInstance("byulId", "byulPwd", "byul", "byul@email.com");
        logger.debug("User : {}", user);
    }

    @Test
    public void privateFieldAccess() throws NoSuchFieldException, IllegalAccessException {
        Class<Student> clazz = Student.class;
        Student student = new Student();
        Field field = clazz.getDeclaredField("name");
        field.setAccessible(true);

        assertThat(field.get(student)).isNull();
        assertThat(student.getName()).isNull();

        field.set(student, "별이");

        assertThat(field.get(student)).isEqualTo("별이");
        assertThat(student.getName()).isEqualTo("별이");
    }
}
