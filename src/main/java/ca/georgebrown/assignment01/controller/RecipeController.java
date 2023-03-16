/*
 * Project: My recipe>
 * Assignment: assignment 1
 * Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
 * Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
 * Description: Recipe controller, In charge of recipe management / mapping > add , update, delete, search / filter.
 */

package ca.georgebrown.assignment01.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.georgebrown.assignment01.entity.Recipe;
import ca.georgebrown.assignment01.service.EventPlanService;
import ca.georgebrown.assignment01.service.RecipeService;
import ca.georgebrown.assignment01.service.ShoppingListService;
import ca.georgebrown.assignment01.service.UserService;

@Controller
@RequestMapping("/recipe")
public class RecipeController {
	@Autowired
	ShoppingListService shoppingListService;
	@Autowired
	RecipeService recipeService;
	@Autowired
	UserService userService;
	@Autowired
	EventPlanService eventPlanService;

	@GetMapping("/viewRecipes")
	public String viewRecipes(Model model, @RequestParam String publicOrPrivate) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			int currentUserId = HttpSessionUtil.getCurrentUser().getId();
			model.addAttribute("currentUserId", currentUserId);
			List<Recipe> list = new ArrayList<>();
			if (publicOrPrivate.equalsIgnoreCase("public")) {
				list = recipeService.findRecipeByIsPublic(Boolean.TRUE, currentUserId);
			} else if (publicOrPrivate.equalsIgnoreCase("private")) {
				list = recipeService.findRecipeByIsPublic(Boolean.FALSE, currentUserId);
			} else {
				list = recipeService.findAllRecipes(currentUserId);
			}
			model.addAttribute("recipes", list);
			return "my-recipes";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}

	}

	@GetMapping("/findRecipes")
	public String findRecipes(Model model, @RequestParam String forumType) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			List<Recipe> list = recipeService.findRecipeByOtherUsers(HttpSessionUtil.getCurrentUser().getId());
			model.addAttribute("recipes", list);
			return "public-recipes";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}

	}

	@PostMapping("/search")
	public String search(@RequestParam("searchStr") String searchStr, Model model) {
		if (HttpSessionUtil.isValidUser()) {

			model.addAttribute("user", HttpSessionUtil.getCurrentUser());

			int currentUserId = HttpSessionUtil.getCurrentUser().getId();
			model.addAttribute("currentUserId", currentUserId);

			model.addAttribute("searchStr", searchStr);
			model.addAttribute("recipes", recipeService.findAllRecipes(searchStr, currentUserId));
			return "my-recipes";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@PostMapping("/publicSearch")
	public String publicSearch(@RequestParam("searchStr") String searchStr, Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			int currentUserId = HttpSessionUtil.getCurrentUser().getId();
			model.addAttribute("currentUserId", currentUserId);

			List<Recipe> list = recipeService.findRecipeByOtherUsers(searchStr, currentUserId);

			model.addAttribute("searchStr", searchStr);
			model.addAttribute("recipes", list);
			return "public-recipes";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@GetMapping("/favouriteRecipe")
	public String favouriteRecipe(Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			int currentUserId = HttpSessionUtil.getCurrentUser().getId();
			model.addAttribute("currentUserId", currentUserId);

			List<Recipe> list = recipeService.findRecipeByIsFavourite(Boolean.TRUE, currentUserId);

			//model.addAttribute("searchStr", searchStr);
			model.addAttribute("recipes", list);
			return "favourite-list";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}


	@GetMapping("/addRecipeForm")
	public String addRecipeForm(Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			Recipe recipe = new Recipe();
			model.addAttribute("recipe", recipe);
			return "add-recipe";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@GetMapping("/viewCookBook")
	public String viewCookBook(Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			return "cookbook";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@PostMapping("/saveRecipe")
	public String saveRecipe(Recipe recipe, Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			recipe.setOwnerId(HttpSessionUtil.getCurrentUser().getId());
			recipe.setCreatedDate(new Date());
			recipeService.saveRecipe(recipe);
			model.addAttribute("recipeAdded", Boolean.TRUE);
			return "redirect:/recipe/viewRecipes?publicOrPrivate=all";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@GetMapping("/viewIngredients/{id}")
	public String viewIngredients(Model model, @PathVariable("id") Integer id) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			Recipe recipe = recipeService.findRecipeById(id);
			if (recipe != null) {
				model.addAttribute("recipe", recipe);
				return "viewIngredients";
			}
		}
		model.addAttribute("error", Boolean.TRUE);
		model.addAttribute("errorMessage", "Invalid Page Access.");
		return "login";

	}

	@GetMapping("/recipeDetails/{id}")
	public String recipeDetails(Model model, @PathVariable("id") Integer id) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			Recipe recipe = recipeService.findRecipeById(id);
			if (recipe != null) {
				model.addAttribute("recipe", recipe);
				Boolean showAddToShoppingListButton = true;
				if (recipe.getIsInShoppingList() != null && recipe.getIsInShoppingList() == true) {
					showAddToShoppingListButton = false;
				}
				model.addAttribute("showAddToShoppingListButton", showAddToShoppingListButton);
				model.addAttribute("showRemoveFromShoppingListButton", !showAddToShoppingListButton);
				return "recipe";
			}
		}
		model.addAttribute("error", Boolean.TRUE);
		model.addAttribute("errorMessage", "Invalid Page Access.");
		return "login";

	}

	@GetMapping("/edit/{id}")
	public String launchEditPage(Model model, @PathVariable("id") int id) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			model.addAttribute("recipe", recipeService.findRecipeById(id));
			return "edit-recipe";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}

	}

	@GetMapping("/update/{id}")
	public String launchUpdatePage(Model model, @PathVariable("id") int id) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			model.addAttribute("recipe", recipeService.findRecipeById(id));
			return "edit-event";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}

	}

	@GetMapping("/removeFromShoppingList/{id}")
	public String removeFromShoppingList(Model model, @PathVariable("id") int id) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());

			Recipe recipe = recipeService.findRecipeById(id);
			boolean success = shoppingListService.removeFromShoppingList(recipe);
			if (success) {
				recipe.setIsInShoppingList(false);
				recipeService.updateRecipe(recipe);
			}
			return "redirect:/recipe/recipeDetails/" + id;
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}

	}

	@GetMapping("/addToShoppingList/{id}")
	public String addToShoppingList(Model model, @PathVariable("id") int id) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());

			Recipe recipe = recipeService.findRecipeById(id);
			boolean added = shoppingListService.addToShoppingList(recipe);
			if (added) {
				recipe.setIsInShoppingList(true);
				recipeService.updateRecipe(recipe);
			}
			return "redirect:/recipe/recipeDetails/" + id;
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}

	}

	@PostMapping("/updateRecipe")
	public String updatedRecipe(Recipe recipe, Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			recipeService.updateRecipe(recipe);
			model.addAttribute("recipeUpdated", Boolean.TRUE);
			return "edit-recipe";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@PostMapping("/updateEvent")
	public String updatedEvent(Recipe recipe, Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			recipeService.updateEvent(recipe);
			model.addAttribute("recipeUpdated", Boolean.TRUE);
			return "redirect:/events/eventPlans";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@GetMapping("/delete/{id}")
	public String deleteRecipe(@PathVariable("id") int id, Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			recipeService.deleteById(id);
			return "redirect:/recipe/viewRecipes?publicOrPrivate=all";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@GetMapping("/remove/{id}")
	public String removeRecipe(@PathVariable("id") int id, Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			recipeService.removeEvent(id);
			return "redirect:/events/eventPlans";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@GetMapping("/toggleFavorite/{id}")
	public String toggleFavorite(Model model, @PathVariable("id") int id) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			recipeService.toggleFavorite(recipeService.findRecipeById(id));
			return "redirect:/recipe/viewRecipes?publicOrPrivate=all";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@GetMapping("/viewMealPlan")
	public String viewMealPlan(Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			int currentUserId = HttpSessionUtil.getCurrentUser().getId();
			model.addAttribute("currentUserId", currentUserId);
			List<Recipe> list = new ArrayList<>();
			list = recipeService.findPlannedRecipes(currentUserId);
			model.addAttribute("recipes", list);
			return "my-meal-plan";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}

	}

	@PostMapping("/findByDay")
	public String viewRecipesByDay(Model model, @RequestParam("searchStr") String searchStr) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			int currentUserId = HttpSessionUtil.getCurrentUser().getId();
			model.addAttribute("currentUserId", currentUserId);
			model.addAttribute("searchStr", searchStr);
			model.addAttribute("recipes", recipeService.findRecipesByDay(searchStr, currentUserId));
			return "my-meal-plan";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}
}
