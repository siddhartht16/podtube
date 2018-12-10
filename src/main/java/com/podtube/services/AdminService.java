package com.podtube.services;

import com.podtube.common.UserRole;
import com.podtube.customentities.AppStatistics;
import com.podtube.models.Podcast;
import com.podtube.models.User;
import com.podtube.repositories.CategoryRepository;
import com.podtube.repositories.EpisodeRepository;
import com.podtube.repositories.PodcastRepository;
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

    @Autowired
    PodcastRepository podcastRepository;

    @Autowired
    EpisodeRepository episodeRepository;

    @Autowired
    CategoryRepository categoryRepository;

    private boolean isAdminUser(int userId){

        boolean result = false;

        if(userId<-1){
            return result;
        }

        User user = userRepository.findByIdEqualsAndUserRoleEquals(userId, UserRole.ADMIN);

        if(user==null){
            return result;
        }

        return true;
    }//isAdminUser..

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> findAllUsers(HttpSession httpSession) {

        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        //TODO: Check for if logged in user is admin
        int id = (int) httpSession.getAttribute("id");

        if(!isAdminUser(id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(userRepository.findAllByUserRoleEquals(UserRole.ADMIN), HttpStatus.OK);
    }//findAllUsers..

    @GetMapping("/admin/user/{userId}")
    public ResponseEntity<User> findUserById(HttpSession httpSession,
            @PathVariable("userId") int userId
    ) {
        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        //TODO: Check for if logged in user is admin
        int id = (int) httpSession.getAttribute("id");

        if(!isAdminUser(id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(userRepository.findByIdEqualsAndUserRoleEquals(id, UserRole.ADMIN), HttpStatus.OK);
    }//findUserById..

    @PostMapping("/admin/users")
    public ResponseEntity<User> createUser(HttpSession httpSession,
                                           @RequestBody User user) {

        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        //TODO: Check for if logged in user is admin
        int id = (int) httpSession.getAttribute("id");

        if(!isAdminUser(id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

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
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        httpSession.setAttribute("id", userToLogIn.getId());
        return new ResponseEntity<>(userToLogIn, HttpStatus.OK);
    }

    @PostMapping("/admin/logout")
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
    }

    @GetMapping("/admin/stats")
    public ResponseEntity<AppStatistics> getAppStats(HttpSession httpSession) {

        if (!ServiceUtils.isValidSession(httpSession))
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);

        //TODO: Check for if logged in user is admin
        int id = (int) httpSession.getAttribute("id");

        if(!isAdminUser(id)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        //Get number of users
        int normalUserCount = userRepository.findAllByUserRoleEquals(UserRole.ADMIN).size();
        int adminUserCount = userRepository.findAllByUserRoleEquals(UserRole.USER).size();
        int allUsersCount = normalUserCount + adminUserCount;

        //Get number of podcasts
        int numberOfPodcasts = ((List<Podcast>) podcastRepository.findAll()).size();

        //Get number of categories
        int numberOfCategories = ((List<Podcast>) podcastRepository.findAll()).size();

        //Get number of episodes
        int numberOfEpisodes = ((List<Podcast>) podcastRepository.findAll()).size();

        AppStatistics appStatistics = new AppStatistics();

        appStatistics.setNoOfAdminUsers(adminUserCount);
        appStatistics.setNoOfUsers(normalUserCount);
        appStatistics.setNoOfTotalUsers(allUsersCount);
        appStatistics.setNoOfPodcasts(numberOfPodcasts);
        appStatistics.setNoOfCategories(numberOfCategories);
        appStatistics.setNoOfEpisodes(numberOfEpisodes);

        return new ResponseEntity<>(appStatistics, HttpStatus.OK);
    }//getAppStats..
}//AdminService..
