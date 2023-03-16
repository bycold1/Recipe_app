/*
 * Project: My recipe>
 * Assignment: assignment 1
 * Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
 * Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
 * Description: Interface of recipe list for other users to see.
 */

package ca.georgebrown.assignment01.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.georgebrown.assignment01.entity.Recipe;

public interface RecipeRepostory extends JpaRepository<Recipe, Integer> {

	List<Recipe> findByType(String type);

	List<Recipe> findByOwnerId(Integer ownerId);

}
