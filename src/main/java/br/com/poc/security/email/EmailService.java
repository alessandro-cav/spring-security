package br.com.poc.security.email;

import br.com.poc.security.config.JwtService;
import br.com.poc.security.repository.UserRepository;
import br.com.poc.security.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final UserRepository repository;

    private final EnviaEmail email;

    private JwtService jwtService;

    public EmailService(UserRepository repository, EnviaEmail email, JwtService jwtService) {
        this.repository = repository;
        this.email = email;
        this.jwtService = jwtService;
    }

    public void esqueciMinhaSenha(LoginRequestDTO loginRequestDTO) {
      User user =   this.repository.findByEmail(loginRequestDTO.getEmail()).get();
        Map<String, Object> extraClaims = new HashMap<>();
          String token =  Jwts
                    .builder()
                    .setClaims(extraClaims)
                    .setSubject(user.getEmail())
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + 1800000))
                    .signWith(this.jwtService.getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();

          String link = "http://localhost:8080/crm-moremac/users/reset_password?token=" + token;

           email.enviarEmail(user.getEmail(), user.getNome(), link);

    }
}
