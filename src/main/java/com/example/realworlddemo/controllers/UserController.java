package com.example.realworlddemo.controllers;

import com.example.realworlddemo.dto.request.UserLoginRequest;
import com.example.realworlddemo.dto.request.RegistrationRequest;
import com.example.realworlddemo.dto.request.UserUpdateRequest;
import com.example.realworlddemo.dto.response.UserProfileResponse;
import com.example.realworlddemo.dto.response.UserResponse;
import com.example.realworlddemo.converter.UserObjectConvert;
import com.example.realworlddemo.models.Users;
import com.example.realworlddemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserObjectConvert converter;

    @Autowired
    public UserController(UserService userService, UserObjectConvert converter) {
        this.userService = userService;
        this.converter = converter;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody RegistrationRequest userReq){
        Users newUser = userService.registerNewUser(
                userReq.getUser().getEmail(),
                userReq.getUser().getUsername(),
                userReq.getUser().getPassword()
        );
        return new ResponseEntity<>(converter.entityToResponse(newUser), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserLoginRequest userReq){
        Users loginUser = userService.verifyUser(
                userReq.getUser().getEmail(),
                userReq.getUser().getPassword());
        return new ResponseEntity<>(converter.entityToResponse(loginUser), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal Users user){
        return new ResponseEntity<>(converter.entityToResponse(user), HttpStatus.OK);
    }

    @PutMapping("/user")
    ResponseEntity<UserResponse> updateUser(@AuthenticationPrincipal Users user,
                                            @RequestBody UserUpdateRequest updateUser){
        Optional.ofNullable(updateUser.getUsers().getUsername()).ifPresent(user::setUsername);
        if (updateUser.getUsers().getUsername() != null) user.setUsername(updateUser.getUsers().getUsername());
        if (updateUser.getUsers().getEmail() != null) user.setEmail(updateUser.getUsers().getEmail());
        if (updateUser.getUsers().getBio() != null) user.setBio(updateUser.getUsers().getBio());
        if (updateUser.getUsers().getImage() != null) user.setImage(updateUser.getUsers().getImage());

        return new ResponseEntity<>(converter.entityToResponse(userService.updateUser(user)), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{username}")
    ResponseEntity<UserProfileResponse> getUserProfile(@PathVariable("username") String username){
        return ResponseEntity.ok(converter.entityToUserProfileResponse(userService.findByUsername(username)));
    }

    @PostMapping("/{username}/follow")
    ResponseEntity<UserProfileResponse> followUser(@PathVariable("username") String username,
                                                   @AuthenticationPrincipal Users user){
        Users response = userService.followUser(username, user.getId());
        return ResponseEntity.ok(converter.entityToUserProfileResponse(response));
    }

    @DeleteMapping("/{username}/unfollow")
    ResponseEntity<UserProfileResponse> unfollowUser(@PathVariable("username") String username,
                                                     @AuthenticationPrincipal Users user){
        Users response = userService.unfollowUser(username, user.getId());
        return ResponseEntity.ok(converter.entityToUserProfileResponse(response));
    }
}
