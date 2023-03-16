/*
 * Project: My recipe>
 * Assignment: assignment 1
 * Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
 * Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
 * Description: Events service; manages events listed in the event plan.
 */

package ca.georgebrown.assignment01.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.georgebrown.assignment01.entity.Recipe;
import ca.georgebrown.assignment01.entity.EventPlan;
import ca.georgebrown.assignment01.repository.EventPlanRepository;

@Service
public class EventPlanService {
    @Autowired
    EventPlanRepository eventPlanRepository;

    public EventPlan getEventPlan() {
        Optional<EventPlan> result = eventPlanRepository.findById(1);
        if (result.isPresent()) {
            return result.get();
        } else {
            eventPlanRepository.save(new EventPlan());
            result = eventPlanRepository.findById(1);
            if (result.isPresent()) {
                return result.get();
            }
            return null;
        }
    }

    public EventPlan findById(int id) {
        Optional<EventPlan> result = eventPlanRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    public EventPlan save(EventPlan eventPlan) {
        return eventPlanRepository.save(eventPlan);
    }

    public EventPlan update(EventPlan eventPlan) {
        Optional<EventPlan> result = eventPlanRepository.findById(eventPlan.getId());
        if (!result.isPresent()) {
            return null;
        }
        EventPlan existing = result.get();
        existing.setRecipes(eventPlan.getRecipes());
        return eventPlanRepository.save((existing));
    }

    public void deleteById(int id) {
        eventPlanRepository.deleteById(id);
    }

    public boolean addToEventPlan(Recipe recipe) {
        EventPlan eventPlan = getEventPlan();
        Set<Recipe> recipes = eventPlan.getRecipes();
        recipes.remove(recipe);
        eventPlan.setRecipes(recipes);
        EventPlan result = update(eventPlan);
        return (result != null);
    }

    public boolean removeFromEventPlan(Recipe recipe) {
        EventPlan eventPlan = getEventPlan();
        Set<Recipe> recipes = eventPlan.getRecipes();
        recipes.remove(recipe);
        eventPlan.setRecipes(recipes);
        EventPlan result = update(eventPlan);
        return (result != null);
    }
}
