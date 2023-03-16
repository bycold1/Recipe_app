/*
 * Project: My recipe>
 * Assignment: assignment 1
 * Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
 * Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
 * Description: Shopping List controller, maps the functions of the ShoppingList service.
 */

package ca.georgebrown.assignment01.controller;

import ca.georgebrown.assignment01.entity.Recipe;
import ca.georgebrown.assignment01.service.RecipeService;
import ca.georgebrown.assignment01.service.ShoppingListService;
import ca.georgebrown.assignment01.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shopping/")
public class ShoppingListController {
    @Autowired
    ShoppingListService shoppingListService;
    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;

    @GetMapping("/shoppingList")
    public String viewShoppingList(Model model) {
        if (HttpSessionUtil.isValidUser()) {
            model.addAttribute("user", HttpSessionUtil.getCurrentUser());
            int currentUserId = HttpSessionUtil.getCurrentUser().getId();
            model.addAttribute("currentUserId", currentUserId);
            List<Recipe> results = new ArrayList<>();
            results = recipeService.findAllRecipes(currentUserId);
            List<Recipe> filtered = results;
            filtered = results.stream().filter(r -> r.getIsInShoppingList() == true).collect(Collectors.toList());
            model.addAttribute("recipes", filtered);
            return "shopping-list";
        } else {
            model.addAttribute("error", Boolean.TRUE);
            model.addAttribute("errorMessage", "Invalid Page Access.");
            return "login";
        }
    }
}