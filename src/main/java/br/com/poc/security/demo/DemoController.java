package br.com.poc.security.demo;


import br.com.poc.security.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo-controller")
@RequiredArgsConstructor
public class DemoController {

    @GetMapping
    public ResponseEntity<String> sayHello(Authentication authentication){

        Object autenticacao = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("autenticacao: " + autenticacao + "\n");

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Principal: " + principal + "\n");

        User userDetails = (User) authentication.getPrincipal();
        System.out.println("User nome " + userDetails.getNome() + "\n");

        User usuario = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Usuario  " + userDetails.getNome());

        return ResponseEntity.ok("hello from segured endpoint");
    }

}
