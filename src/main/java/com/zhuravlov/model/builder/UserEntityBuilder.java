package com.zhuravlov.model.builder;

import com.zhuravlov.model.entity.Role;
import com.zhuravlov.model.entity.UserEntity;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @see UserEntity builder
 */
public class UserEntityBuilder {
    private UserEntity userEntity;

    public UserEntityBuilder() {
        this.userEntity = new UserEntity();
    }

    public UserEntityBuilder setUserId(int id) {
        userEntity.setUserId(id);
        return this;
    }

    public UserEntityBuilder setFirstName(String firstName) {
        userEntity.setFirstName(firstName);
        return this;
    }

    public UserEntityBuilder setLastName(String lastName) {
        userEntity.setLastName(lastName);
        return this;
    }

    public UserEntityBuilder setEmail(String email) {
        userEntity.setEmail(email);
        return this;
    }

    public UserEntityBuilder setRoles(Set<Role> roles) {
        userEntity.setRoles(roles);
        return this;
    }

    public UserEntityBuilder setPassword(String password) {
        userEntity.setPassword(password);
        return this;
    }

    public UserEntityBuilder setAmount(BigDecimal amount) {
        userEntity.setAmount(amount);
        return this;
    }

    public UserEntity build() {
        return userEntity;
    }

    public static UserEntityBuilder getInstance() {
        return new UserEntityBuilder();
    }
}
