package lesson6.task3;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CheckArrayTest {

    private CheckArray checkArray;

    @BeforeEach
    public void startUp() {
        System.out.println("начинается тест");
        checkArray = new CheckArray();
    }

    @AfterEach
    public void afterEach() {
        System.out.println("завершается тест");
    }

    @AfterAll
    public static void mainEnd() {
        System.out.println("ГЛАВНОЕ ОКОНЧАНИЕ");
    }

    @BeforeAll
    public static void mainStart() {
        System.out.println("ГЛАВНОЕ НАЧАЛО");
    }


    @DisplayName("Проверка таймаута")
    @Test
    void testTimeout() {
        int[] array = new int[]{1, 1, 1, 4, 4, 1, 4, 4};
        boolean expected = true;
        Assertions.assertTimeout(Duration.ofSeconds(1), () -> {
            Assertions.assertEquals(expected, checkArray.isArrayNumbersOnlyOneAndFour(array));

        });
    }


    @DisplayName("Проверка получения ИСТИННОГО значения")
    @Test
    public void testTrue() {
        int[] array = new int[]{1, 1, 1, 4, 4, 1, 4, 4};
        boolean value = checkArray.isArrayNumbersOnlyOneAndFour(array);
        Assertions.assertTrue(value);
    }

    @DisplayName("Параметризированный тест проверки получения ЛОЖНОГО значения")
    @ParameterizedTest
    @MethodSource("data")
    public void paramTestFalse(boolean expected, int[] array) {
        boolean value = checkArray.isArrayNumbersOnlyOneAndFour(array);
        Assertions.assertFalse(expected);
    }

    @DisplayName("Параметризированный тест проверки состава массива чисел")
    @ParameterizedTest
    @MethodSource("data1")
    public void paramTest(boolean expected, int[] array) {
        Assertions.assertEquals(expected, checkArray.isArrayNumbersOnlyOneAndFour(array));
    }

    static Stream<Arguments> data() {
        return Stream.of(
                Arguments.arguments(false, new int[]{1, 1, 1, 1, 1, 1, 1, 1}),
                Arguments.arguments(false, new int[]{4, 4, 4, 4, 4, 4, 4, 4}),
                Arguments.arguments(false, new int[]{1, 1, 1, 4, 4, 1, 4, 3}),
                Arguments.arguments(false, new int[0])

        );
    }

    static Stream<Arguments> data1() {
        return Stream.of(
                Arguments.arguments(true, new int[]{1, 1, 1, 4, 4, 1, 4, 4}),
                Arguments.arguments(false, new int[]{1, 1, 1, 1, 1, 1, 1, 1}),
                Arguments.arguments(false, new int[]{4, 4, 4, 4, 4, 4, 4, 4}),
                Arguments.arguments(false, new int[]{1, 1, 1, 4, 4, 1, 4, 3}),
                Arguments.arguments(false, new int[0])

        );
    }
}
