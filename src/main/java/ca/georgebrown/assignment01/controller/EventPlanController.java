/*
 * Project: My recipe>
 * Assignment: assignment 1
 * Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
 * Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
 * Description: Event controller, maps the functions of the EventPLan service.
 */

package ca.georgebrown.assignment01.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.georgebrown.assignment01.service.RecipeService;
import ca.georgebrown.assignment01.service.UserService;
import ca.georgebrown.assignment01.entity.Recipe;
import ca.georgebrown.assignment01.service.EventPlanService;

@Controller
@RequestMapping("/events/")
public class EventPlanController {
    @Autowired
    EventPlanService eventPlanService;
    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;

    @GetMapping("/eventPlans")
	public String viewEventPlans(Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			int currentUserId = HttpSessionUtil.getCurrentUser().getId();
			model.addAttribute("currentUserId", currentUserId);
			List<Recipe> results = new ArrayList<>();
			results = recipeService.findAllRecipes(currentUserId);
			List<Recipe> filtered = results;
			filtered = results.stream()
						.filter(r -> r.getEvents().length() > 0)
						.collect(Collectors.toList());
			model.addAttribute("recipes", filtered);
			return "event-plan";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}
}
