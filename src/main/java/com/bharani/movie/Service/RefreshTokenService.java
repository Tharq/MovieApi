package com.bharani.movie.Service;

import com.bharani.movie.entity.RefreshToken;
import com.bharani.movie.entity.User;
import com.bharani.movie.repository.RefreshTokenRepository;
import com.bharani.movie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final UserRepository repository;

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken createRefreshToken(String username){
          User user = repository.findByUserName(username).orElseThrow(()->new UsernameNotFoundException("user not found"));

          RefreshToken refreshToken = user.getRefreshToken();

          if(refreshToken == null){
                refreshToken = RefreshToken.builder()
                        .refreshToken(UUID.randomUUID().toString())
                        .expirationTime(Instant.now().plusMillis(5*60*60*10000))
                        .user(user)
                        .build();

                refreshTokenRepository.save(refreshToken);
          }

          return refreshToken;
    }

    public RefreshToken verifyRefreshToken (String refreshToken){

        RefreshToken token = refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(()->new UsernameNotFoundException("token not found"));

        if(token.getExpirationTime().compareTo(Instant.now()) < 0){
            refreshTokenRepository.delete(token);
            throw new UsernameNotFoundException("token expired");
        }

        return token;
    }
}
