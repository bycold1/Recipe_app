/*
 * Project: My recipe>
 * Assignment: assignment 1
 * Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
 * Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
 * Description: Registration, log in page and user management during session services.
 */
package ca.georgebrown.assignment01.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.georgebrown.assignment01.entity.User;
import ca.georgebrown.assignment01.service.UserService;

@Controller
@RequestMapping("/")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping("/")
	public String vistorHome() {
		return "redirect:/user/login";
	}

	@GetMapping("/user/login")
	public String loginForm() {
		return "login";
	}

	@GetMapping("/user/logout")
	public String logout(Model model) {
		HttpSessionUtil.removeCurrentUser();
		model.addAttribute("logout", Boolean.TRUE);
		return "login";
	}

	@PostMapping("/user/auth")
	public String search(@RequestParam("userId") String userId, @RequestParam("password") String password,
			Model model) {
		User user = userService.findByUserIdAndPassword(userId, password);
		if (user != null) {
			model.addAttribute("validUser", Boolean.TRUE);
			model.addAttribute("user", user);
			model.addAttribute("userFullName", user.getFirstName() + " " + user.getLastName());
			HttpSessionUtil.setCurrentUser(user);
			return "index";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid userId or password. Try again.");
			return "login";
		}

	}

	@GetMapping("/user/registration")
	public String launchAddUserPage(Model model) {
		model.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/user/addUser")
	public String createUser(Model model, User user) {
		userService.addUser(user);
		model.addAttribute("newUser", Boolean.TRUE);
		return "login";
	}

	@GetMapping("/user/home")
	public String userHome(Model model, @RequestParam Integer id) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			return "index";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@GetMapping("/user/viewProfile")
	public String viewProfile(Model model, @RequestParam Integer id) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			return "profile";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}

	}

	@GetMapping("/user/edit/{id}")
	public String launchEditPage(Model model, @PathVariable("id") int id) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			return "edit-user";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@GetMapping("/user/reset/{id}")
	public String launchResetPage(Model model, @PathVariable("id") int id) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			return "edit-password";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	@PostMapping("/user/editProfile")
	public String updateRecipe(User user, Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			userService.editUser(user);
			model.addAttribute("userUpdated", Boolean.TRUE);
			return "redirect:/";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

		@PostMapping("/user/resetPassword")
	public String resetPassword(User user, Model model) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			userService.resetPassword(user);
			model.addAttribute("userUpdated", Boolean.TRUE);
			return "redirect:/";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}


	// @PostMapping("/user/updateUser")
	// public String updatedUser(Model model, User user) {
	// 	if (HttpSessionUtil.isValidUser()) {
	// 		model.addAttribute("user", HttpSessionUtil.getCurrentUser());
	// 		model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
	// 		userService.updateUser(user);
	// 		return "redirect:/";
	// 	} else {
	// 		model.addAttribute("error", Boolean.TRUE);
	// 		model.addAttribute("errorMessage", "Invalid Page Access.");
	// 		return "login";
	// 	}
	// }

	@GetMapping("/user/delete/{id}")
	public String deleteUser(Model model, @PathVariable("id") int id) {
		if (HttpSessionUtil.isValidUser()) {
			model.addAttribute("user", HttpSessionUtil.getCurrentUser());
			model.addAttribute("currentUserId", HttpSessionUtil.getCurrentUser().getId());
			userService.deleteById(id);
			return "redirect:/";
		} else {
			model.addAttribute("error", Boolean.TRUE);
			model.addAttribute("errorMessage", "Invalid Page Access.");
			return "login";
		}
	}

	
}
