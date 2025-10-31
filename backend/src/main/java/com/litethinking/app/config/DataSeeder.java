package com.litethinking.app.config;

import com.litethinking.app.domain.Role;
import com.litethinking.app.domain.UserAccount;
import com.litethinking.app.repository.RoleRepository;
import com.litethinking.app.repository.UserAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

  private final RoleRepository roleRepo;
  private final UserAccountRepository userRepo;
  private final PasswordEncoder encoder;

  @Override
  public void run(String... args) {
    Role admin = roleRepo.findByName("ROLE_ADMIN").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_ADMIN")));
    Role externo = roleRepo.findByName("ROLE_EXTERNO").orElseGet(() -> roleRepo.save(new Role(null, "ROLE_EXTERNO")));

    if (userRepo.findByEmail("admin@demo.com").isEmpty()) {
      var u = new UserAccount(null, "admin@demo.com", encoder.encode("Admin123*"), true, Set.of(admin));
      userRepo.save(u);
    }
    if (userRepo.findByEmail("visitante@demo.com").isEmpty()) {
      var u = new UserAccount(null, "visitante@demo.com", encoder.encode("Visitante123*"), true, Set.of(externo));
      userRepo.save(u);
    }
  }
}
