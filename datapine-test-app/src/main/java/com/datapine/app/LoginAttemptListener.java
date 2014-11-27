package com.datapine.app;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
public class LoginAttemptListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private static final Logger log = Logger.getLogger(LoginAttemptListener.class);

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        Object userName = event.getAuthentication().getPrincipal();
        Object credentials = event.getAuthentication().getCredentials();
        log.error("Failed login using USERNAME [" + userName + "]");
        log.error("Failed login using PASSWORD [" + credentials + "]");
    }
}