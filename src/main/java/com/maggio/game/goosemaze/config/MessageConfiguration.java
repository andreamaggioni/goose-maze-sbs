package com.maggio.game.goosemaze.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class MessageConfiguration {

    public class CustomMessageSource extends  ReloadableResourceBundleMessageSource {
        public String getMessage(String message, Object... args) {
            return super.getMessage(message, args.clone(), null);
        }
    }

    @Bean
    public CustomMessageSource messageSource() {
        CustomMessageSource messageSource = new CustomMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

}
