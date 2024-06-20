package com.bharani.movie.Service;

import com.bharani.movie.dto.AuthResponse;
import com.bharani.movie.dto.LoginRequest;
import com.bharani.movie.dto.RegisterRequest;
import com.bharani.movie.entity.Role;
import com.bharani.movie.entity.User;
import com.bharani.movie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request){
        var user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .userName(request.getUserName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
       User savedUser = userRepository.save(user);

       String token = jwtService.generateToken(savedUser);

       var refreshToken = refreshTokenService.createRefreshToken(savedUser.getUsername());

       return AuthResponse.builder()
               .refreshToken(refreshToken.getRefreshToken())
               .accessToken(token)
               .role(savedUser.getRole())
               .build();

    }

    public AuthResponse login(LoginRequest loginRequest){
       authenticationManager.authenticate(
               new UsernamePasswordAuthenticationToken(
                       loginRequest.getUserName(),
                       loginRequest.getPassword()
               )
       );

       var user = userRepository.findByUserName(loginRequest.getUserName())
               .orElseThrow(()->new UsernameNotFoundException("user not found"));
       var accessToken = jwtService.generateToken(user);

       var refreshToken = refreshTokenService.createRefreshToken(loginRequest.getUserName());

       return AuthResponse.builder()
               .accessToken(accessToken)
               .refreshToken(refreshToken.getRefreshToken())
               .role(user.getRole())
               .build();
    }
}
