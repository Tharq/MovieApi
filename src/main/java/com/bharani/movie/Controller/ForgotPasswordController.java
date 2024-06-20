package com.bharani.movie.Controller;

import com.bharani.movie.Service.EmailService;
import com.bharani.movie.Service.MailBody;
import com.bharani.movie.Utils.ChangePassword;
import com.bharani.movie.entity.ForgotPassword;
import com.bharani.movie.entity.User;
import com.bharani.movie.repository.ForgotPasswordRepository;
import com.bharani.movie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Random;

@RestController
@RequestMapping("/forgotPassword")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ForgotPasswordController {

    private final UserRepository userRepository;

    private final EmailService emailService;

    private final ForgotPasswordRepository forgotPasswordRepository;

    private final PasswordEncoder passwordEncoder;

    @PostMapping("/verifyEmail/{email}")

    public String verify(@PathVariable String email){
        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("email not found"));

        Integer otp = otp();
        MailBody mail = MailBody.builder()
                .to(email)
                .subject("OTP  for forget password request")
                .text("This is OTP for your forgot password request: "+otp)
                .build();

        ForgotPassword forgotPassword = ForgotPassword.builder()
                .otp(otp)
                .ExpirationDate(new Date(System.currentTimeMillis()+70*1000))
                .user(user)
                .build();

        emailService.sendSimpleMessage(mail);

        forgotPasswordRepository.save(forgotPassword);

        return "OTP sent successfully";
    }

    @PostMapping("/verifyOtp/{otp}/{email}")

    public String verifyOtp(@PathVariable Integer otp,@PathVariable String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));

        ForgotPassword fp = forgotPasswordRepository.findByOtpAndUser(otp,user)
                .orElseThrow(()->new UsernameNotFoundException("invalid otp"));

        if(fp.getExpirationDate().before(Date.from(Instant.now()))){
            forgotPasswordRepository.deleteById(fp.getFId());
            return "OTP Expired";
        }

        return "OTP Verified";
    }

    @PostMapping("/updatePassword/{email}")
    public String UpdatePassword(@RequestBody ChangePassword password,@PathVariable String email){
        if(!Objects.equals(password.password(),password.repeatPassword())){
            return "Enter Password Again";
        }

        String newPassword = passwordEncoder.encode(password.password());
        userRepository.updatePassword(email,newPassword);

        return "password updated successfully";
    }

    private Integer otp(){

        Random random = new Random();

        return random.nextInt(100000,999999);
    }
}
