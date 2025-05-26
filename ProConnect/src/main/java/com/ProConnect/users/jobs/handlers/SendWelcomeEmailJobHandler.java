package com.ProConnect.users.jobs.handlers;

import com.ProConnect.config.ApplicationProperties;
import com.ProConnect.email.EmailService;
import com.ProConnect.users.data.UserEmailDTO;
import com.ProConnect.users.domain.User;
import com.ProConnect.users.domain.VerificationCode;
import com.ProConnect.users.jobs.SendWelcomeEmailJob;
import com.ProConnect.users.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jobrunr.jobs.lambdas.JobRequestHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import com.ProConnect.users.repository.IVerificationCodeRepository;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class SendWelcomeEmailJobHandler implements JobRequestHandler<SendWelcomeEmailJob> {
    private final IUserRepository userRepository;
    private final IVerificationCodeRepository verificationCodeRepository;
    private final SpringTemplateEngine templateEngine;
    private final EmailService emailService;
    private final ApplicationProperties applicationProperties;

    @Override
    @Transactional
    public void run(SendWelcomeEmailJob sendWelcomEmailJob) throws Exception {
        log.info("SendWelcomeEmailJob started");
        try {
            User user = userRepository.findById(sendWelcomEmailJob.getUserId()).orElseThrow(() -> new RuntimeException("User not found with id: " + sendWelcomEmailJob.getUserId()));
            log.info("Sending welcome email to user with id: {}", user.getId());
            if (user.getVerificationCode() != null && !user.getVerificationCode().isEmailSent()) {
                log.info("Start the sending");
                sendWelcomeEmail(user, user.getVerificationCode());
            } else {
                log.info("Not sending");
            }
        } catch (Exception e){
            log.error("Error running sen email job", e);
            throw e;
        }
    }

    private void sendWelcomeEmail(User user, VerificationCode code) {
        String verificationLink = applicationProperties.getBaseUrl() + "/api/users/verify-email?token=" + code.getVerificationCode();
        Context thymeleafContext = new Context();
        thymeleafContext.setVariable("user", user);
        thymeleafContext.setVariable("verificationLink", verificationLink);
        thymeleafContext.setVariable("applicationName", applicationProperties.getApplicationName());
        String htmlBody = templateEngine.process("welcome-email", thymeleafContext);
        emailService.sendHtmlMessage(List.of(user.getEmail()), "Welcome to our platform", htmlBody);
        code.setEmailSent(true);
        verificationCodeRepository.save(code);
    }

}
