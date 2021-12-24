package com.study.thejavatest.study;

import com.study.thejavatest.domain.Member;
import com.study.thejavatest.member.InvalidMemberException;
import com.study.thejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void create_study_service() {

        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }
}