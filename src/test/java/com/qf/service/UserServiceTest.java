package com.qf.service;

import com.qf.AcTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class UserServiceTest extends AcTest{

    @Autowired
    private UserService userService;
    @Test
    public void checkUsername() {
        Integer count = userService.checkUsername("admin");
        System.out.println(count);
    }
}