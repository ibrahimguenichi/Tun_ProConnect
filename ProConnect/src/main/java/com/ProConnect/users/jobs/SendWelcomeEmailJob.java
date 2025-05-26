package com.ProConnect.users.jobs;

import com.ProConnect.users.data.UserEmailDTO;
import com.ProConnect.users.jobs.handlers.SendWelcomeEmailJobHandler;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jobrunr.jobs.lambdas.JobRequest;
import org.jobrunr.jobs.lambdas.JobRequestHandler;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SendWelcomeEmailJob implements JobRequest{
    //private UserEmailDTO userEmail;
    private Long userId;

    @Override
    public Class<? extends JobRequestHandler> getJobRequestHandler() {
        return SendWelcomeEmailJobHandler.class;
    }
}
