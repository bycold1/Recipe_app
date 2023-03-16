/*
 * Project: My recipe>
 * Assignment: assignment 1
 * Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
 * Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
 * Description: User Interface to find users from other account.
 */

package ca.georgebrown.assignment01.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.georgebrown.assignment01.entity.User;

public interface UserRepostory extends JpaRepository<User, Integer> {

    Optional<User> findByUserIdAndPassword(String userId, String password);
}
