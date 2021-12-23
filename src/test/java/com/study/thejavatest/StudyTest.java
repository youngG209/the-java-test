package com.study.thejavatest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("BeforeAll!");
    }

    @AfterAll
    static void afterAll() {
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
        Study study = new Study(-1);

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

}