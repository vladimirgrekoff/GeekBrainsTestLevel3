package lesson6.task2;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.Duration;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ReturnArrayTest {
    private ReturnArray returnArray;

    @BeforeEach
    public void startUp() {
        System.out.println("начинается тест");
        returnArray = new ReturnArray();
    }

    @AfterEach
    public void afterEach(){
        System.out.println("завершается тест");
    }

    @AfterAll
    public static void mainEnd(){
        System.out.println("ГЛАВНОЕ ОКОНЧАНИЕ");
    }

    @BeforeAll
    public static void mainStart(){
        System.out.println("ГЛАВНОЕ НАЧАЛО");
    }

    @DisplayName("Проверка исключения 1")
    @Test
    void testException1() {
        int[] array = new int[]{1, 2, 5, 6, 2, 3, 8, 1, 7};
        Assertions.assertThrows(RuntimeException.class, () -> returnArray.ReturnArrayNumbersAfterLast4(array));
    }

    @DisplayName("Проверка исключения 2")
    @Test
    void testException2() {
        int[] array = new int[0];
        Assertions.assertThrows(RuntimeException.class, () -> returnArray.ReturnArrayNumbersAfterLast4(array));
    }



    @DisplayName("Проверка таймаута")
    @Test
    public void testTimeout() {
        int[] array = new int[]{1, 2, 4, 6, 2, 3, 8, 1, 7};
        int[] expected = new int[]{6, 2, 3, 8, 1, 7};
        Assertions.assertTimeout(Duration.ofSeconds(1), () -> {
            Assertions.assertArrayEquals(expected, returnArray.ReturnArrayNumbersAfterLast4(array));

        });
    }

    @DisplayName("Параметризированный тест получения массива чисел после последней четверки")
    @ParameterizedTest
    @MethodSource("data")
    void paramTest(int[] expected, int[] array) {
        int[] actual;
        Assertions.assertArrayEquals(expected, actual = returnArray.ReturnArrayNumbersAfterLast4(array));
    }

    static Stream<Arguments> data() {
        return Stream.of(
                Arguments.arguments(new int[]{1, 7}, new int[]{1, 2, 4, 4, 2, 3, 4, 1, 7}),
                Arguments.arguments(new int[]{3, 8, 1, 7}, new int[]{1, 2, 4, 4, 4, 3, 8, 1, 7}),
                Arguments.arguments(new int[]{5, 6, 2, 3, 8, 1, 7}, new int[]{1, 4, 5, 6, 2, 3, 8, 1, 7}),
                Arguments.arguments(new int[]{}, new int[]{1, 4, 5, 6, 2, 3, 8, 1, 4})
        );
    }
}