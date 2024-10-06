package com.imperion.learn.LMS.AuditAwareAuth;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAuth implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        //u need to implement spring security here
        //so just igore here for now
//        return Optional.empty();
        //enable jpa auditing anot in congig
        return Optional.of("Poorna Chandra S");
    }
}
