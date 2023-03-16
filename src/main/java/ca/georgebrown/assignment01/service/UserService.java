/*
 * Project: My recipe>
 * Assignment: assignment 1
 * Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
 * Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
 * Description: User service and user location management / for forum.
 */
package ca.georgebrown.assignment01.service;

import ca.georgebrown.assignment01.entity.User;
import ca.georgebrown.assignment01.repository.UserRepostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    UserRepostory UserRepostory;


    public User findByUserIdAndPassword(String userId, String password){
        Optional<User> result = UserRepostory.findByUserIdAndPassword(userId, password);
        if(result.isPresent()){
            return result.get();
        }
        return null;
    }

    public List<User> findAllUsers(){
        return (List<User>) UserRepostory.findAll();
    }

    public User addUser(User User){
        return UserRepostory.save(User);
    }

    public User editUser(User user){
        Optional<User> result = UserRepostory.findById(user.getId());
        if(!result.isPresent()){
            return null;
        }
        User existing = result.get();
        //DO NOT CHANGE username
        existing.setFirstName(user.getFirstName());
        existing.setLastName(user.getLastName());
        existing.setEmail(user.getEmail());
        return UserRepostory.save((existing));
    }

    public User resetPassword(User user){
        Optional<User> result = UserRepostory.findById(user.getId());
        if(!result.isPresent()){
            return null;
        }
        User existing = result.get();
        //DO NOT CHANGE username
        existing.setPassword(user.getPassword());
        return UserRepostory.save((existing));
    }

    public void deleteById(int id){
        UserRepostory.deleteById(id);
    }
}
