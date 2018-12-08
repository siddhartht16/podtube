package com.podtube.services;

import com.podtube.common.UserRole;
import com.podtube.models.User;
import com.podtube.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@CrossOrigin(origins="*", allowedHeaders = "*", allowCredentials = "true")
public class AdminService {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> findAllUsers(HttpSession httpSession) {

        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //TODO: Check for if logged in user is admin

        return new ResponseEntity<>(userRepository.findAllByUserRoleEquals(UserRole.ADMIN), HttpStatus.OK);
    }//findAllUsers..

    @GetMapping("/admin/user/{userId}")
    public ResponseEntity<User> findUserById(HttpSession httpSession,
            @PathVariable("userId") int userId
    ) {
        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //TODO: Check for if logged in user is admin

        User user = userRepository.findByIdEqualsAndUserRoleEquals(userId, UserRole.ADMIN);

        if(user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }//findUserById..

    @PostMapping("/admin/users")
    public ResponseEntity<User> createUser(HttpSession httpSession,
                                           @RequestBody User user) {

        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        //TODO: Check for if logged in user is admin

        try {
            //Set as admin
            user.setUserRole(UserRole.ADMIN);
            User newUser = userRepository.save(user);
            return new ResponseEntity<>(newUser, HttpStatus.OK);
        }
        catch (RuntimeException ex){
            //TODO: Log this
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/admin/login")
    public ResponseEntity<User> login(HttpSession httpSession, @RequestBody User user) {
        User userToLogIn = userRepository.findUserByUsernameEqualsAndPasswordEqualsAndUserRoleEquals(
                user.getUsername(),
                user.getPassword(),
                UserRole.ADMIN);

        if(userToLogIn==null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        httpSession.setAttribute("id", userToLogIn.getId());
        return new ResponseEntity<>(userToLogIn, HttpStatus.OK);
    }

    @PostMapping("/admin/logout")
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
    }
}//AdminService..
