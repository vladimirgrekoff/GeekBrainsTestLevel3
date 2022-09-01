package lesson7;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class TestingClass {
    public static <testClass> void start(Class<testClass>  testClass, Object  obj) throws ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        Class<testClass> tstClass = (Class<testClass>) Class.class;

        System.out.println();
        System.out.println("Тестирование класса " + testClass.getSimpleName());
        System.out.println();

//        getAnnotationMethods(testClass, obj);


        Method[] methods = testClass.getDeclaredMethods();

        checkingNumberOfMethodsOfMainAnnotations(methods);

        int size = getMethodArraySizeWithAnnotations(methods);

        Method[] sortMethods = new Method[size];

        sortingArrayOfAnnotatedMethods(methods, sortMethods);


        getSortedMethodArray(sortMethods);

        runTestSuite(obj, sortMethods);


    }



    private static void runTestSuite(Object obj, Method[] sortMethods) throws IllegalAccessException, InvocationTargetException {
        for (Method method : sortMethods) {

            if (method.isAnnotationPresent(testAnnotation.class)) {
                System.out.print(method.getName() + "(приоритет " + method.getAnnotation(testAnnotation.class).priority() + ") : ");

                method.setAccessible(true);
                method.invoke(obj);
            }
        }
    }

    private static void sortingArrayOfAnnotatedMethods(Method[] methods, Method[] sortMethods) {
        int i = 0;
        for (int j = 0; j < methods.length; j++) {
            Method method = methods[j];
            if (method.isAnnotationPresent(testAnnotation.class)) {
                sortMethods[i] = method;
                i++;
            }
        }
    }

    private static int getMethodArraySizeWithAnnotations(Method[] methods) {
        int count = 0;
        for (Method method : methods) {
            if (method.isAnnotationPresent(testAnnotation.class)) {
                count++;
            }
        }
        return count;
    }

    private static void checkingNumberOfMethodsOfMainAnnotations(Method[] methods) {
        int countBefore = 0;
        int countAfter = 0;

        for (Method method : methods) {
            if (method.isAnnotationPresent(BeforeSuite.class) && method.isAnnotationPresent(testAnnotation.class)) {
                countBefore++;
            } else if (method.isAnnotationPresent(AfterSuite.class) && method.isAnnotationPresent(testAnnotation.class)) {
                countAfter++;
            }
        }

        if (countBefore > 1) {
            throw new RuntimeException("В наборе тестов больше одного метода @BeforeSuite");
        } else if (countAfter > 1) {
            throw new RuntimeException("В наборе тестов больше одного метода @AfterSuite");
        }
    }

    private static void getSortedMethodArray(Method[] sortMethods) {
        Method temp = null;
        for (int k = 0; k < sortMethods.length; k++) {
            for (int j = k + 1; j < sortMethods.length; j++) {
                if (compareMethod(sortMethods[k], sortMethods[j]) == 1) {
                    temp = sortMethods[k];
                    sortMethods[k] = sortMethods[j];
                    sortMethods[j] = temp;
                    temp = null;
                }
            }
        }
    }

    public static int compareMethod(Method o1, Method o2){
        int priority1;
        int priority2;
        int result = 0;

        if (o1.isAnnotationPresent(BeforeSuite.class)) {
            result = -1;
        } else if (o2.isAnnotationPresent(BeforeSuite.class)) {
            result = 1;
        } else if (o1.isAnnotationPresent(AfterSuite.class)) {
            result = 1;
        } else if (o2.isAnnotationPresent(AfterSuite.class)) {
            result = -1;
        } else if (o1.isAnnotationPresent(Test.class) && o2.isAnnotationPresent(Test.class)) {
            priority1 = o1.getAnnotation(testAnnotation.class).priority();
            priority2 = o2.getAnnotation(testAnnotation.class).priority();

            if (priority1 < priority2) {
                result = -1;
            } else if (priority1 > priority2) {
                result = 1;
            }
        }
        return result;
    }
}

