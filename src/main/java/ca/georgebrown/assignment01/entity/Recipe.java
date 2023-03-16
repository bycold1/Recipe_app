/*
 * Project: My recipe>
 * Assignment: assignment 1
 * Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
 * Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
 * Description: Recipe information constructor and location management of recipes.
 */
package ca.georgebrown.assignment01.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column
	private Integer ownerId;

	@Column
	private Date createdDate;
	@Column
	private String name;
	@Column
	private String ingredients;
	@Column
	private String events;
	@Column
	private String description;
	@Column
	private double calories;
	@Column
	private String cookingTime;
	@Column
	private String type;
	@Column
	private Boolean isPublic;
	@Column
	private String descriptionDisplay;
	@Column
	private Boolean isFavorite = false;
	@Column
	private Boolean isInShoppingList = false;

	@Column
	private String planDay = "None";

	@ManyToOne
	ShoppingList shoppingList;

	@ManyToOne
	EventPlan eventPlan;

	// not stored in database
	private String ownerName;
	private String publicOrPrivate;
	private String ingredientsDisplay;

	// Constructor
	public Recipe() {
		super();
	}

	// getters and setters

	public String getIsInShoppingListDisplay() {
		return (isInShoppingList != null && isInShoppingList) ? "Yes" : "No";
	}

	public ShoppingList getShoppingList() {
		return shoppingList;
	}

	public void setShoppingList(ShoppingList shoppingList) {
		this.shoppingList = shoppingList;
	}

	public String getIsFavoriteDisplay() {
		return (isFavorite != null && isFavorite) ? "Yes" : "No";
	}

	public Boolean getIsFavorite() {
		return this.isFavorite;
	}

	public void setIsFavorite(Boolean isFavorite) {
		this.isFavorite = isFavorite;
	}

	public Boolean getIsInShoppingList() {
		return isInShoppingList;
	}

	public void setIsInShoppingList(Boolean isInShoppingList) {
		this.isInShoppingList = isInShoppingList;
	}

	public void toggleFavorite() {
		this.isFavorite = !isFavorite;
	}

	public String getPublicOrPrivate() {
		return isPublic ? "Public" : "Private";
	}

	public void setPublicOrPrivate(String publicOrPrivate) {
		this.publicOrPrivate = publicOrPrivate;
	}

	public Boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getDescriptionDisplay() {
		if (description == null) {
			return "";
		}
		return description;
	}

	public void setDescriptionDisplay(String descriptionDisplay) {
		this.descriptionDisplay = descriptionDisplay;
	}

	public String getIngredientsDisplay() {
		if (ingredients == null) {
			return "";
		}
		return ingredients;
	}

	public void setIngredientsDisplay(String ingredientsDisplay) {
		this.ingredientsDisplay = ingredientsDisplay;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public int getId() {
		return id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCalories() {
		return calories;
	}

	public void setCalories(double calories) {
		this.calories = calories;
	}

	public String getCookingTime() {
		return cookingTime;
	}

	public void setCookingTime(String cookingTime) {
		this.cookingTime = cookingTime;
	}

	public String getPlanDay() {
		return planDay;
	}

	public void setPlanDay(String planDay) {
		this.planDay = planDay;
	}

	public String getEvents() {
		return events;
	}

	public void setEvents(String events) { this.events = events; }
}
