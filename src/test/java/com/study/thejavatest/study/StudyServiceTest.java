package com.study.thejavatest.study;

import com.study.thejavatest.domain.Member;
import com.study.thejavatest.domain.Study;
import com.study.thejavatest.member.MemberService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

    @Mock
    MemberService memberService;

    @Mock
    StudyRepository studyRepository;

    @Test
    void createStudyService(@Mock MemberService memberService,
                            @Mock StudyRepository studyRepository) {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);
    }

    @Test
    void create_study_service() {

        StudyService studyService = new StudyService(memberService, studyRepository);

        assertNotNull(studyService);
    }

    @Test
    void stubbing1Test() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("lee@gamil.com");

//        값을 지정하지 않고 아무 값이나 확인 하고 싶을때 any()
        when(memberService.findById(any())).thenReturn(Optional.of(member));

        assertEquals("lee@gamil.com", memberService.findById(1L).get().getEmail());
    }

    @Test
    void stubbing2Test() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("lee@gamil.com");

        when(memberService.findById(any())).thenReturn(Optional.of(member));

        assertEquals("lee@gamil.com", memberService.findById(1L).get().getEmail());
        assertEquals("lee@gamil.com", memberService.findById(2L).get().getEmail());

        doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

        assertThrows(IllegalArgumentException.class, () -> {
           memberService.findById(1L);
        });

        memberService.validate(2L);
    }

    @Test
    void stubbing3Test() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("lee@gamil.com");

        when(memberService.findById(any()))
                .thenReturn(Optional.of(member))
                .thenThrow(new RuntimeException())
                .thenReturn(Optional.empty());

        Optional<Member> byId = memberService.findById(1L);
        assertEquals("lee@gamil.com", byId.get().getEmail());

        assertThrows(RuntimeException.class, () -> {
           memberService.findById(2L);
        });

        assertEquals(Optional.empty(), memberService.findById(3L));
    }

    @Test
    void stubbing4Test() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("lee@gamil.com");

        Study study = new Study(10, "테스트");

        // TODO memberService 객체에 findById 메소드를 1L 값으로 호출하면 Optional.of(member) 객체를 리턴하도록 Stubbing
        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        // TODO studyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 Stubbing
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertNotNull(study.getOwner());
        assertEquals(member, study.getOwner());
    }

    @Test
    void createNewStudy() {
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("lee@gamil.com");

        Study study = new Study(10, "테스트");

        when(memberService.findById(1L)).thenReturn(Optional.of(member));
        when(studyRepository.save(study)).thenReturn(study);

        studyService.createNewStudy(1L, study);

        assertNotNull(study.getOwner());
        assertEquals(member, study.getOwner());

        verify(memberService, times(1)).notify(study);

        verifyNoMoreInteractions(memberService);
//
//        verify(memberService, times(1)).notify(member);
//        verify(memberService, never()).validate(any());
//
//        InOrder inOrder = inOrder(memberService);
//        inOrder.verify(memberService).notify(study);
//
//        inOrder.verify(memberService).notify(member);
    }

    @Test
    void bddStyle() {
        // Given
        StudyService studyService = new StudyService(memberService, studyRepository);
        assertNotNull(studyService);

        Member member = new Member();
        member.setId(1L);
        member.setEmail("lee@gamil.com");

        Study study = new Study(10, "테스트");

//        when(memberService.findById(1L)).thenReturn(Optional.of(member));
//        when(studyRepository.save(study)).thenReturn(study);
        given(memberService.findById(1L)).willReturn(Optional.of(member));
        given(studyRepository.save(study)).willReturn(study);

        // When
        studyService.createNewStudy(1L, study);

        // Then
        assertEquals(member, study.getOwner());

//        verify(memberService, times(1)).notify(study);
        then(memberService).should(times(1)).notify(study);

//        verifyNoMoreInteractions(memberService);
        then(memberService).shouldHaveNoMoreInteractions();
    }

}