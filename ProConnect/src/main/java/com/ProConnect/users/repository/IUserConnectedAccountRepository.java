package com.ProConnect.users.repository;

import com.ProConnect.users.domain.UserConnectedAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IUserConnectedAccountRepository extends JpaRepository<UserConnectedAccount, Long> {
    @Query("SELECT a FROM UserConnectedAccount a WHERE a.provider = :provider AND a.providerId = :providerId")
    Optional<UserConnectedAccount> findByProviderAndProviderId(@Param("provider") String provider, @Param("providerId") String providerId);
}
