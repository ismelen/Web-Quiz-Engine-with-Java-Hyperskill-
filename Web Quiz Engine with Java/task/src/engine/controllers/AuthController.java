package engine.controllers;

import engine.domain.AppUser;
import engine.repositories.AuthRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class AuthController {
    private final AuthRepository authRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthRepository authRepository, PasswordEncoder passwordEncoder) {
        this.authRepo = authRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/api/register")
    public void register(@RequestBody @Valid RegistrationRequest data) {
        boolean exists = this.authRepo.existsByEmail(data.email);
        if (exists) throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        authRepo.save(new AppUser(
            data.email,
            passwordEncoder.encode(data.password),
            "ROLE_USER"
        ));
    }


    public record RegistrationRequest (
        @Email(regexp = ".+@.+\\..+")
        @NotBlank
        String email,

        @NotNull
        @Size(min = 5)
        String password
    ){}
}
