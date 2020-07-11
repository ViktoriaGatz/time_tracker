package ru.example.been;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import ru.example.entity.user.User;

@Configuration
public class AppContext {

    private static final Logger log = Logger.getLogger(AppContext.class.getName());
/*
    @Bean
    @Lazy
    @Scope("prototype")
    public User userBean() {
        log.info("Been User create");
        return new User();
    }

    // @EnableTransactionManagement
*/
}