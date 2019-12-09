import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author onion
 * @date 2019/12/8 -9:32 下午
 */

public class Exam {
    @TestFactory
    Stream<DynamicTest> testStream(){
        return provideInteger().mapToObj(grade->DynamicTest.dynamicTest("grade: " + grade,
                ()->{assertTrue(grade >= 0 && grade <= 100);}));
    }
    static IntStream provideInteger(){
        return IntStream.of(90, 60, 85);
    }
}

