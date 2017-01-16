package cz.sedlaj19.autoskola.domain.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.sedlaj19.autoskola.domain.model.Ride;
import cz.sedlaj19.autoskola.domain.model.User;

/**
 * Created by Honza on 7. 8. 2016.
 */
public class Container {

    private static Container ourInstance = new Container();

    private List<User> students;
    private List<User> instructors;
    private List<Ride> rides;
    private User loggedInUser;
    private boolean isRideChange;
    private int updatedRidePosition;

    public static Container getInstance() {
        return ourInstance;
    }

    private Container() {
        students = new ArrayList<>();
        instructors = new ArrayList<>();
        rides = new ArrayList<>();
        isRideChange = false;
    }

    // LOGGED IN USER
    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    // END OF LOGGED IN USER

    // STUDENTS
    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public void clearStudents(){
        this.students.clear();
    }

    public void addStudent(User student){
        this.students.add(student);
    }
    // END OF STUDENTS

    // INSTRUCTORS
    public List<User> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<User> instructors) {
        this.instructors = instructors;
    }

    public void clearInstructors(){
        this.instructors.clear();
    }

    public void addInstructor(User instructor){
        this.instructors.add(instructor);
    }
    // END OF INSTRUCTORS

    // RIDES

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(List<Ride> rides) {
        this.rides = rides;
        Collections.sort(rides);
    }

    public void clearRides(){
        this.rides.clear();
    }

    public void addRide(Ride ride){
        this.rides.add(ride);
        Collections.sort(rides);
    }

    public int updateRide(Ride ride){
        int size = rides.size();
        for(int i = 0; i < size; i++){
            Ride r = rides.get(i);
            if(r.getId().equals(ride.getId())){
                rides.set(i, ride);
                return i;
            }
        }
        Collections.sort(rides);
        return -1;
    }

    public boolean isRideChange() {
        return isRideChange;
    }

    public void setRideChange(boolean rideChange) {
        isRideChange = rideChange;
    }

    public int getUpdatedRidePosition() {
        return updatedRidePosition;
    }

    public void setUpdatedRidePosition(int updatedRidePosition) {
        this.updatedRidePosition = updatedRidePosition;
    }

// END OF RIDES

    public String getUserNameByKey(String key){
        for(User user : this.instructors){
            if(user.getId().equals(key)){
                return user.getName() + " " + user.getSurname();
            }
        }
        for(User user : this.students){
            if(user.getId().equals(key)){
                return user.getName() + " " + user.getSurname();
            }
        }
        return "Unknown user";
    }
}
