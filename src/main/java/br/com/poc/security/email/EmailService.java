package br.com.poc.security.email;

import br.com.poc.security.config.JwtService;
import br.com.poc.security.repository.UserRepository;
import br.com.poc.security.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class EmailService {

    private final UserRepository repository;

    private final EnviaEmail email;

    private JwtService jwtService;

    private final PasswordEncoder passwordEncod;


    //private final ModelMapper modelMapper;

    //private final ValidarCpfECnpjEEmail validarCpfECnpjEEmail;


    public EmailService(UserRepository repository, EnviaEmail email, JwtService jwtService, PasswordEncoder passwordEncod) {
        this.repository = repository;
        this.email = email;
        this.jwtService = jwtService;
        this.passwordEncod = passwordEncod;
    }

    public void esqueciMinhaSenha(LoginRequestDTO loginRequestDTO) {
        User user = this.repository.findByEmail(loginRequestDTO.getEmail()).get();
        Map<String, Object> extraClaims = new HashMap<>();
        String token = Jwts
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

    public MensagemResponseDTO resetarSenha(String token, SenhasRequestDTO senhasRequestDTO) {

        try {
            boolean expired = this.jwtService.isTokenExpired(token);

            if (!senhasRequestDTO.getSenha01().equals(senhasRequestDTO.getSenha02())) {
                throw new RuntimeException("Senhas diferentes");
            }
            final String username = this.jwtService.extractUsername(token);
            Optional<User> usuario = this.repository.findByEmail(username);

            String novaSenhaCodificada = passwordEncod.encode(senhasRequestDTO.getSenha01().trim());
            usuario.get().setSenha(novaSenhaCodificada);
            this.repository.saveAndFlush(usuario.get());

            String mensagem = "Senha alterada com sucesso.";
            return MensagemResponseDTO.getMenssagem(mensagem);

        } catch (Exception e) {
            throw new RuntimeException("Token expirado: " + e.getMessage());
        }
    }

}
