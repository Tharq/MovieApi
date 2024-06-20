package com.bharani.movie.Controller;


import com.bharani.movie.Service.AuthService;
import com.bharani.movie.Service.JwtService;
import com.bharani.movie.Service.RefreshTokenService;
import com.bharani.movie.dto.AuthResponse;
import com.bharani.movie.dto.LoginRequest;
import com.bharani.movie.dto.RefreshTokenRequest;
import com.bharani.movie.dto.RegisterRequest;
import com.bharani.movie.entity.RefreshToken;
import com.bharani.movie.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    private final AuthService service;

    private final RefreshTokenService refreshTokenService;

    private final JwtService jwtService;

    @GetMapping("/test")
    public String test(){
        return "hello world";
    }

    @PostMapping("/register")

    public AuthResponse register(@RequestBody RegisterRequest request){
        return service.register(request);
    }

    @PostMapping("/login")

    public AuthResponse login(@RequestBody LoginRequest loginRequest){
        return service.login(loginRequest);
    }

    @PostMapping("/refresh")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequest request){
       RefreshToken token = refreshTokenService.verifyRefreshToken(request.getRefreshToken());

       User user = token.getUser();

       String accessToken = jwtService.generateToken(user);

       return AuthResponse.builder()
               .refreshToken(token.getRefreshToken())
               .accessToken(accessToken)
               .build();

    }
}
