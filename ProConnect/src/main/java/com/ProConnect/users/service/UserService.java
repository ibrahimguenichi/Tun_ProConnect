package com.ProConnect.users.service;

import com.ProConnect.config.ApplicationProperties;
import com.ProConnect.email.EmailService;
import com.ProConnect.users.data.CreateUserRequest;
import com.ProConnect.users.data.UserEmailDTO;
import com.ProConnect.users.data.UserResponse;
import com.ProConnect.users.domain.User;
import com.ProConnect.users.domain.VerificationCode;
import com.ProConnect.users.jobs.SendWelcomeEmailJob;
import com.ProConnect.users.repository.IUserRepository;
import com.ProConnect.users.repository.IVerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.scheduling.BackgroundJob;
import org.jobrunr.scheduling.BackgroundJobRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final IVerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final ApplicationProperties applicationProperties;
    private final SpringTemplateEngine templateEngine;

    @Override
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User retrieveUser(Long userId) {
        return userRepository.findById(userId).get();
    }

    @Override
    public UserResponse addUser(CreateUserRequest request) {
        User user = new User(request);
        user = userRepository.save(user);

        //UserEmailDTO userEmailDTO = new UserEmailDTO(user);
        sendVerificationEmail(user);

        return new UserResponse(user);
    }

    private void sendVerificationEmail(User user) {
        VerificationCode verificationCode = new VerificationCode(user);
        user.setVerificationCode(verificationCode);
        verificationCodeRepository.save(verificationCode);
        log.info("Verification code: ", verificationCode.getVerificationCode());
        SendWelcomeEmailJob sendWelcomeEmailJob = new SendWelcomeEmailJob(user.getId());
        BackgroundJobRequest.enqueue(sendWelcomeEmailJob);

//        String code = user.getVerificationCode().getVerificationCode();
//
//        String verificationLink = applicationProperties.getBaseUrl() + "/api/users/verify-email?token=" + code;
//        Context thymeleafContext = new Context();
//        thymeleafContext.setVariable("user", user);
//        thymeleafContext.setVariable("verificationLink", verificationLink);
//        thymeleafContext.setVariable("applicationName", applicationProperties.getApplicationName());
//        String htmlBody = templateEngine.process("welcome-email", thymeleafContext);
//
//        emailService.sendHtmlMessage(List.of(user.getEmail()), code, htmlBody);
    }

    @Override
    public void removeUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User modifyUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean isEmailUnique(String email) {
        return userRepository.existsByEmail(email);
    }
}
