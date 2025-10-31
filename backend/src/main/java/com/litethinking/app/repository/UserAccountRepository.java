package com.litethinking.app.repository;

import com.litethinking.app.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Integer> {
  Optional<UserAccount> findByEmail(String email);
}
