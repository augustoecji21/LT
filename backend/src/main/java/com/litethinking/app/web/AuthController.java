package com.litethinking.app.web;

import com.litethinking.app.auth.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authManager;
  private final JwtService jwt;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody Map<String,String> body){
    var email = body.get("email");
    var password = body.get("password");
    var auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    var user = (UserDetails) auth.getPrincipal();
    var roles = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    String token = jwt.generate(user.getUsername(), roles);
    return ResponseEntity.ok(Map.of("token", token, "roles", roles));
  }
}
