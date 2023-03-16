/*
* Project: My recipe>
		* Assignment: assignment 1
		* Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
		* Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
		* Description: If user log out and try to go back to a link > go back to the log in page.
*/
package ca.georgebrown.assignment01.controller;

import ca.georgebrown.assignment01.entity.User;

public class HttpSessionUtil {

	private static User currentUser;

	public static void setCurrentUser(User user) {
		currentUser = user;
	}

	public static void removeCurrentUser() {
		currentUser = null;
	}

	public static User getCurrentUser() {
		return currentUser;
	}

	public static boolean isValidUser() {
		return currentUser != null;
	}

}
