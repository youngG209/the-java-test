package com.study.thejavatest.member;

import com.study.thejavatest.domain.Member;
import com.study.thejavatest.domain.Study;

import java.util.Optional;

public interface MemberService {
    void validate(Long memberId);

    Optional<Member> findById(Long memberId);

    void notify(Study save);

    void notify(Member member);
}
