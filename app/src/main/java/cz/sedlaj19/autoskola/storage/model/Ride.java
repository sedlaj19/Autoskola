package cz.sedlaj19.autoskola.storage.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Honza on 6. 8. 2016.
 */
public class Ride {

    private long Date;
    private String Car;
    private String Instructor;
    private String Student;
    private boolean Completed;
    private String Notes;

    public Ride(){}

    public Ride(String car, long date, String instructor, String student, String notes){
        this.Car = car;
        this.Date = date;
        this.Instructor = instructor;
        this.Completed = false;
        this.Student = student;
        this.Notes = notes;
    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }

    public String getCar() {
        return Car;
    }

    public void setCar(String car) {
        Car = car;
    }

    public String getInstructor() {
        return Instructor;
    }

    public void setInstructor(String instructor) {
        Instructor = instructor;
    }

    public boolean isCompleted() {
        return Completed;
    }

    public void setCompleted(boolean completed) {
        Completed = completed;
    }

    public String getStudent() {
        return Student;
    }

    public void setStudent(String student) {
        Student = student;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    @Override
    public String toString() {
        return "Car: " + Car + ", Date: " + new Date(Date) + ", Instructor: " + Instructor
                ;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("date", Date);
        result.put("car", Car);
        result.put("instructor", Instructor);
        result.put("completed", Completed);
        result.put("student", Student);
        result.put("notes", Notes);
        return result;
    }
}
