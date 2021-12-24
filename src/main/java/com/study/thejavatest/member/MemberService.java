package com.study.thejavatest.member;

import com.study.thejavatest.domain.Member;

import java.util.Optional;

public interface MemberService {
    void validate(Long memberId) throws InvalidMemberException;

    Optional<Member> findById(Long memberId);
}
