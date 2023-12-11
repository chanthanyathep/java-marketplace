package com.prior.marketplace.controller.rest;

import com.prior.marketplace.model.ResponseModel;
import com.prior.marketplace.model.UserModel;
import com.prior.marketplace.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PostMapping(path = "/insertUser")
    public ResponseModel<String> insertUser(@RequestBody UserModel userModel){
        return this.userService.insertUser(userModel);
    }

    @PostMapping(path = "/updateBalance")
    public ResponseModel<String> updateBalance(@RequestBody UserModel userModel){
        return this.userService.updateBalance(userModel);
    }

    @GetMapping(path = "/getAllUser")
    public ResponseModel<List<UserModel>> getAllUser(UserModel userModel){
       return this.userService.getAllUser();
    }
}
