/*
 * Project: My recipe>
 * Assignment: assignment 1
 * Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
 * Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
 * Description: Recipe service and location service management for public, private, all and forum recipes.
 */
package ca.georgebrown.assignment01.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.georgebrown.assignment01.entity.Recipe;
import ca.georgebrown.assignment01.entity.ShoppingList;
import ca.georgebrown.assignment01.entity.User;
import ca.georgebrown.assignment01.repository.RecipeRepostory;
import ca.georgebrown.assignment01.repository.UserRepostory;

@Service
public class RecipeService {
	@Autowired
	UserRepostory userRepostory;
	@Autowired
	RecipeRepostory recipeRepostory;

	@Autowired
	ShoppingListService shoppingListService;

	public List<Recipe> findAllRecipes(Integer ownerId) {
		List<Recipe> list = (List<Recipe>) recipeRepostory.findByOwnerId(ownerId);
		setOwnerName(list);
		return list;
	}

	public List<Recipe> findAllRecipes(String searchStr, Integer ownerId) {
		List<Recipe> results = findAllRecipes(ownerId);
		List<Recipe> filtered = results.stream()
				.filter(r -> r.getName().toLowerCase().contains(searchStr.toLowerCase())).collect(Collectors.toList());
		setOwnerName(filtered);
		return filtered;
	}

	public List<Recipe> findRecipeByIsPublic(Boolean isPublic, Integer ownerId) {
		List<Recipe> results = findAllRecipes(ownerId);
		List<Recipe> filtered = results.stream().filter(r -> r.getIsPublic() == isPublic).collect(Collectors.toList());
		setOwnerName(filtered);
		return filtered;
	}

	public List<Recipe> findRecipeByIsFavourite(Boolean isFavourite, Integer ownerId) {
		List<Recipe> results = findAllRecipes(ownerId);
		List<Recipe> filtered = results.stream().filter(r -> r.getIsFavorite() == isFavourite).collect(Collectors.toList());
		setOwnerName(filtered);
		return filtered;
	}

	public List<Recipe> findRecipeByOtherUsers(Integer excludeUserId) {
		List<Recipe> results = (List<Recipe>) recipeRepostory.findAll();
		List<Recipe> filtered = results.stream().filter(r -> (r.getIsPublic() && r.getOwnerId() != excludeUserId))
				.collect(Collectors.toList());
		setOwnerName(filtered);
		return filtered;
	}

	public Recipe findRecipeById(int id) {
		Optional<Recipe> result = recipeRepostory.findById(id);
		if (result.isPresent()) {
			return result.get();
		}
		return null;
	}

	public List<Recipe> findPlannedRecipes(Integer ownerId) {
		List<Recipe> results = findAllRecipes(ownerId);
		List<Recipe> filtered = results.stream().filter(r -> !(r.getPlanDay().toLowerCase().contains("none")))
				.collect(Collectors.toList());
		setOwnerName(filtered);
		return filtered;
	}

	public List<Recipe> findRecipesByDay(String planDay, Integer ownerId) {
		List<Recipe> results = findAllRecipes(ownerId);
		List<Recipe> filtered = results.stream()
				.filter(r -> r.getPlanDay().toLowerCase().contains(planDay.toLowerCase())).collect(Collectors.toList());
		setOwnerName(filtered);
		return filtered;
	}

	public Recipe saveRecipe(Recipe recipe) {
		ShoppingList shoppingList = shoppingListService.getShoppingList();
		recipe.setShoppingList(shoppingList);
		return recipeRepostory.save(recipe);
	}

	public Recipe updateRecipe(Recipe recipe) {
		Optional<Recipe> result = recipeRepostory.findById(recipe.getId());
		if (!result.isPresent()) {
			return null;
		}
		Recipe existing = result.get();
		existing.setName(recipe.getName());
		existing.setType(recipe.getType());
		existing.setCalories(recipe.getCalories());
		existing.setCookingTime(recipe.getCookingTime());
		existing.setIngredients(recipe.getIngredients());
		existing.setEvents(recipe.getEvents());
		existing.setDescription(recipe.getDescription());
		existing.setPlanDay(recipe.getPlanDay());
		return recipeRepostory.save((existing));
	}

	public Recipe updateEvent(Recipe recipe) {
		Optional<Recipe> result = recipeRepostory.findById(recipe.getId());
		if (!result.isPresent()) {
			return null;
		}
		Recipe existing = result.get();
		existing.setEvents(recipe.getEvents());
		return recipeRepostory.save((existing));
	}

	public void deleteById(int id) {
		recipeRepostory.deleteById(id);
	}

	public Recipe removeEvent(int id) {
		Optional<Recipe> result = recipeRepostory.findById(id);
		if (!result.isPresent()) {
			return null;
		}
		Recipe existing = result.get();
		existing.setEvents("");
		return recipeRepostory.save((existing));
	}

	public List<Recipe> findRecipeByType(String type) {
		return recipeRepostory.findByType(type);
	}

	private void setOwnerName(List<Recipe> list) {
		for (Recipe recipe : list) {
			Optional<User> user = userRepostory.findById(recipe.getOwnerId());
			if (user.isPresent()) {
				recipe.setOwnerName(user.get().getFirstName() + " " + user.get().getLastName());
			}
		}
	}

	public List<Recipe> findRecipeByOtherUsers(String searchStr, int excludeUserId) {
		List<Recipe> results = (List<Recipe>) recipeRepostory.findAll();
		List<Recipe> filtered = results.stream().filter(r -> (searchStr != null && r.getIsPublic()
				&& r.getOwnerId() != excludeUserId && r.getName().toLowerCase().contains(searchStr.toLowerCase())))
				.collect(Collectors.toList());
		setOwnerName(filtered);
		return filtered;
	}

	public Recipe toggleFavorite(Recipe recipe) {
		Optional<Recipe> result = recipeRepostory.findById(recipe.getId());
		if (!result.isPresent()) {
			return null;
		}
		Recipe existing = result.get();
		if (recipe.getIsFavorite() == null) {
			existing.setIsFavorite(Boolean.TRUE);
		} else {
			existing.setIsFavorite(!recipe.getIsFavorite());
		}
		return recipeRepostory.save((existing));
	}
}
