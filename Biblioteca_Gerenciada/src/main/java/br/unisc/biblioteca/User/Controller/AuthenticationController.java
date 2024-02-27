package br.unisc.biblioteca.User.Controller;

import br.unisc.biblioteca.User.Banco.User;
import br.unisc.biblioteca.User.DTOs.AuthenticationDTO;
import br.unisc.biblioteca.User.DTOs.UsernameResponseDTO;
import br.unisc.biblioteca.User.DTOs.RegisterDTO;
import br.unisc.biblioteca.User.Infra.Security.SecurityFilter;
import br.unisc.biblioteca.User.Infra.Security.TokenService;
import br.unisc.biblioteca.User.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private TokenService tokenService;
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new UsernameResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.repository.findByUsername(data.username()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.username(), encryptedPassword, data.role());

        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/userInfo")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        var token = securityFilter.recoverToken(request);
        if (token != null) {
            var email = tokenService.validateToken(token);
            UserDetails user = repository.findByUsername(email);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.notFound().build();
            }
        }else{
            return ResponseEntity.badRequest().body("Token inválido");
        }
    }
}
