/*
 * Project: My shoppingList>
 * Assignment: assignment 1
 * Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
 * Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
 * Description: ShoppingListRepository service and location service management for public, private, all and forum shoppingLists.
 */

package ca.georgebrown.assignment01.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.georgebrown.assignment01.entity.Recipe;
import ca.georgebrown.assignment01.entity.ShoppingList;
import ca.georgebrown.assignment01.repository.ShoppingListRepository;

@Service
public class ShoppingListService {
	@Autowired
	ShoppingListRepository shoppingListRepository;

	public ShoppingList getShoppingList() {
		Optional<ShoppingList> result = shoppingListRepository.findById(1);
		if (result.isPresent()) {
			return result.get();
		} else {
			shoppingListRepository.save(new ShoppingList());
			result = shoppingListRepository.findById(1);
			if (result.isPresent()) {
				return result.get();
			}
			return null;
		}
	}

	public ShoppingList findById(int id) {
		Optional<ShoppingList> result = shoppingListRepository.findById(id);
		if (result.isPresent()) {
			return result.get();
		}
		return null;
	}

	public ShoppingList save(ShoppingList shoppingList) {
		return shoppingListRepository.save(shoppingList);
	}

	public ShoppingList update(ShoppingList shoppingList) {
		Optional<ShoppingList> result = shoppingListRepository.findById(shoppingList.getId());
		if (!result.isPresent()) {
			return null;
		}
		ShoppingList existing = result.get();
		existing.setRecipes(shoppingList.getRecipes());
		return shoppingListRepository.save((existing));
	}

	public void deleteById(int id) {
		shoppingListRepository.deleteById(id);
	}

	public boolean addToShoppingList(Recipe recipe) {
		ShoppingList shoppingList = getShoppingList();
		Set<Recipe> recipes = shoppingList.getRecipes();
		recipes.add(recipe);
		shoppingList.setRecipes(recipes);
		ShoppingList result = update(shoppingList);
		return (result != null);
	}

	public boolean removeFromShoppingList(Recipe recipe) {
		ShoppingList shoppingList = getShoppingList();
		Set<Recipe> recipes = shoppingList.getRecipes();
		recipes.remove(recipe);
		shoppingList.setRecipes(recipes);
		ShoppingList result = update(shoppingList);
		return (result != null);
	}
}
