package com.imperion.learn.LMS.AppConfig;

import com.imperion.learn.LMS.AuditAwareAuth.AuditorAuth;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")//very important ref is below method of audit aware
public class appConf {
    @Bean
    ModelMapper getMapper(){
        return new ModelMapper();
    }

    @Bean
    AuditorAware auditorAware(){//bean name auditoraware method name use it above to enable audition
        return new AuditorAuth();
        //now create this Auditor autth and implement AuditorAware in auth package
        //next in controller copy paste
        //audit reader code
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
