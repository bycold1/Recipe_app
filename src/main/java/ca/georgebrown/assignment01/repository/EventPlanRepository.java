/*
 * Project: My recipe>
 * Assignment: assignment 1
 * Author(s): Juan Carlos Rojas , Ferdous Azizi, Jorrel Tigbayan , Dennis Varghese
 * Student Number: 101202014 , 101315548, 101329925 , 101020193
 * Date:
 * Description: Events interface for users to see their events.
 */

package ca.georgebrown.assignment01.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.georgebrown.assignment01.entity.EventPlan;

public interface EventPlanRepository extends JpaRepository<EventPlan, Integer> {
    
}
