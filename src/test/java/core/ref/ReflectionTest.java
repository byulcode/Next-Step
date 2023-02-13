package core.ref;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.model.Question;
import next.model.User;

import java.util.Arrays;
import java.util.logging.StreamHandler;

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
    public void newInstanceWithConstructorArgs() {
        Class<User> clazz = User.class;
        logger.debug(clazz.getName());
    }

    @Test
    public void privateFieldAccess() {
        Class<Student> clazz = Student.class;
        logger.debug(clazz.getName());
    }
}
