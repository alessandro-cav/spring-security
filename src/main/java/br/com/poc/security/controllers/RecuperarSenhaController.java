package br.com.poc.security.controllers;


import br.com.poc.security.email.EmailService;
import br.com.poc.security.email.LoginRequestDTO;
import br.com.poc.security.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/recuperar")
@Tag(name = "recuperar Controller", description = "APIs relacionadas à recuperar")
public class RecuperarSenhaController {

    private final EmailService service;

    public RecuperarSenhaController(EmailService service) {
        this.service = service;
    }

    @Operation(summary = "Recuperar", description = "Endpoint para recuperar senha")
    @PostMapping("/forgot_password")
    public ResponseEntity<Void> esqueciMinhaSenha(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        this.service.esqueciMinhaSenha(loginRequestDTO);
        return ResponseEntity.ok().build();
    }
    @GetMapping
    @Operation(summary = "Demo acesso para autenticados", description = "Endpoint DemoController, para acessar depois de autenticado")
    public ResponseEntity<String> sayHello(Authentication authentication){
        return ResponseEntity.ok("funcionou , porra");
    }
}
