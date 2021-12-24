package com.study.thejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

//@ExtendWith(FindSlowTestExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JUnit5StudyTest {

    @RegisterExtension
    static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

    int value = 1;

    @BeforeAll
    void beforeAll() {
        System.out.println("BeforeAll!");
    }

    @AfterAll
    void afterAll() {
        System.out.println("AfterAll!");
    }

    @BeforeEach
    void setUp() {
        System.out.println("BeforeEach!");
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach!");
    }

    @DisplayName("스터디 만들기")
    @Test
    void assertAllTest() {
        Study study = new Study(1);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + "이어야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.")
        );
    }

    @Test
    void assertThrowsTest() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        String message = exception.getMessage();
        assertEquals("limit은 0보다 커야 한다.", message);
    }

    @Test
    @Disabled
    void assertTimeoutTest() {
//        assertTimeout(Duration.ofMillis(10), () -> new Study(-10));
        assertTimeout(Duration.ofMillis(10), () -> {
            new Study(10);
            Thread.sleep(100);
        });

//        정해진 시간이 됬을 때 바로 끝남
//        Thead관련 문제가 발생 할 수 있으므로 사용에 주의의
//       assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
//            new Study(10);
//            Thread.sleep(200);
//        });
    }

    @Test
    void assumeTrueTest() {
        String test_env = System.getenv("TEST_ENV");
        System.out.println(test_env);
        assumeTrue("LOCAL".equalsIgnoreCase(test_env));

        Study study = new Study(10);
        assertThat(study.getLimit()).isGreaterThan(0);
    }

    @Test
    void assumeThatTest() {
        String test_env = System.getenv("TEST_ENV");

        assumingThat("LOCAL".equalsIgnoreCase(test_env),
                () -> {
                    System.out.println("local");
                    Study study = new Study(10);
                    assertThat(study.getLimit()).isGreaterThan(0);
                });
        assumingThat("DEV".equalsIgnoreCase(test_env),
                () -> {
                    System.out.println("dev");
                    Study study = new Study(20);
                    assertThat(study.getLimit()).isGreaterThan(0);
                });
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void enabledOnOsTest() {
        System.out.println("EnabledOnOs");
    }

    @Test
    @DisabledOnOs(OS.WINDOWS)
    void disabledOnOsTest() {
        System.out.println("DisabledOnOs");
    }

    @Test
    @EnabledOnJre(JRE.JAVA_8)
    void enabledOnJreTest() {
        System.out.println("JAVA_8");
    }

    @Test
    @EnabledOnJre(JRE.OTHER)
    void disabledOnJreTest() {
        System.out.println("JRE_OTHER");
    }

    @DisplayName("스터디 만들기 fast")
    @Tag("fast")
    @Test
    void testTaging() {
        Study study = new Study(1);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + "이어야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.")
        );
    }

    @DisplayName("스터디 만들기 slow")
    @Tag("slow")
    @Test
    void testTaging2() {
        Study study = new Study(1);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + "이어야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.")
        );
    }

    @DisplayName("스터디 만들기 fast Custom")
    @FastTest
    void CustomTag() {
        Study study = new Study(1);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + "이어야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.")
        );
    }

    @DisplayName("스터디 만들기 slow Custom")
    @SlowTest
    void CustomTag2() {
        Study study = new Study(1);

        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
                        () -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + "이어야 한다."),
                () -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야한다.")
        );
    }


    @DisplayName("반복 테스트")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition} / {totalRepetitions}")
    void repeatTest(RepetitionInfo info) {
        System.out.println("test " + info.getCurrentRepetition() + "/" + info.getTotalRepetitions());
    }

    @DisplayName("Parameter 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} stringVal = {0}")
    @ValueSource(strings = {"11111111", "22222222222", "3333333333", "444444444444"})
    void stringValueSourceTest(String s) {
        System.out.println(s);

    }

    @DisplayName("String Parameter 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} stringVal = {0}")
    @ValueSource(strings = {"11111111", "22222222222", "3333333333", "444444444444"})
//    @EmptySource
//    @NullSource
    @NullAndEmptySource
        // @NullSource 와 @EmptySource 합친것
    void NullAndEmptyTest(String s) {
        System.out.println(s);

    }

    @DisplayName("Integer Parameter 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} stringVal = {0}")
    @ValueSource(ints = {10, 20, 30})
    void intsValueSourceTest(Integer limit) {
        System.out.println(limit);

    }

    @DisplayName("Custom(domain 객체) Parameter 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} stringVal = {0}")
    @ValueSource(ints = {10, 20, 30})
    void convertValueSourceTest(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());

    }

    // public 클래스 혹은 static 인너클래스로 작성해야함
    static class StudyConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("Parameter 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} stringVal = {0}")
    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    void cvsTest(Integer limit, String name) {
        System.out.println(new Study(limit, name));

    }

    @DisplayName("Parameter 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} stringVal = {0}")
    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    void convertCvsTest(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0),
                argumentsAccessor.getString(1));
        System.out.println(study);

    }

    @DisplayName("Parameter 반복 테스트")
    @ParameterizedTest(name = "{index} {displayName} stringVal = {0}")
    @CsvSource({"10, '자바 스터디'", "20, '스프링'"})
    void convertCvsTest(@AggregateWith(StudyArgregator.class) Study study) {
        System.out.println(study);

    }

    // public 클래스 혹은 static 인너클래스로 작성해야함
    static class StudyArgregator implements ArgumentsAggregator {
        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            Study study = new Study(accessor.getInteger(0),
                    accessor.getString(1));
            return study;
        }
    }

    @Order(1)
    @Test
    void instence_Check1() {
        System.out.println(this);
        System.out.println(value++);
    }

    @Order(2)
    @Test
    void instence_Check2() {
        System.out.println(this);
        System.out.println(value++);
    }

    @Order(1)
    @Test
    void extensionTest() throws InterruptedException {
        Thread.sleep(1000L);
    }

}