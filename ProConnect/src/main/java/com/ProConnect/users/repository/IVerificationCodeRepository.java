package com.ProConnect.users.repository;

import com.ProConnect.users.domain.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IVerificationCodeRepository extends JpaRepository<VerificationCode, Long> {
}
