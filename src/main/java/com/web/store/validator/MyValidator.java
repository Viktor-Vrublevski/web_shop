package com.web.store.validator;

import com.web.store.entity.User;
import com.web.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
@PropertySource("/validation.properties")
public class MyValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmpty(errors,"login","required.field");
        if (user.getLogin().length()< 4|| user.getLogin().length() > 40){
            errors.rejectValue("login","size.username.field");
        }
        if (userService.findByUsername(user.getLogin())!=null){
            errors.rejectValue("login","exist.user");
        }
        ValidationUtils.rejectIfEmpty(errors,"password","required.field");
        if (user.getPassword().length()< 4 || user.getPassword().length()>50){
            errors.rejectValue("password","size.password.field");
        }
        if (user.getConfirmPassword().length()< 4 || user.getConfirmPassword().length()>50){
            errors.rejectValue("confirmPassword","size.password.field");
        }
    }
}
