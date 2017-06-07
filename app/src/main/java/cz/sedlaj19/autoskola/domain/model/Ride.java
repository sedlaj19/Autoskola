package cz.sedlaj19.autoskola.domain.model;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.util.Date;

import cz.sedlaj19.autoskola.utils.Converter;
import is.stokkur.dateutils.DateUtils;

/**
 * Created by Honza on 6. 8. 2016.
 */
public class Ride implements Parcelable, Comparable<Ride>{

    private static final String RIDE_ID = "rideId";
    private static final String RIDE_DATE = "rideDate";
    private static final String RIDE_DATE_MILLIS = "rideDateMillis";
    private static final String RIDE_CAR = "rideCar";
    private static final String RIDE_INSTRUCTOR = "rideInstructor";
    private static final String RIDE_STUDENT = "rideStudent";
    private static final String RIDE_COMPLETED = "rideCompleted";
    private static final String RIDE_NOTES = "rideNotes";

    private String id;
    private String Date;
    private long DateMillis;
    private String Car;
    private String Instructor;
    private String Student;
    private String Notes;
    private boolean Completed;

    public Ride(){}

    public Ride(String car, long dateMillis,
                String instructor, String student, String notes){
        this.Car = car;
        this.Date = DateUtils.formatTimestampDateAndTime(dateMillis, DateFormat.FULL, DateFormat.SHORT);
        this.Instructor = instructor;
        this.Completed = false;
        this.DateMillis = dateMillis;
        this.Student = student;
        this.Notes = notes;
    }

    public Ride(String id, String car, long dateMillis,
                String instructor, String student, String notes){
        this.id = id;
        this.Car = car;
        this.Date = DateUtils.formatTimestampDateAndTime(dateMillis, DateFormat.FULL, DateFormat.SHORT);
        this.Instructor = instructor;
        this.Completed = false;
        this.DateMillis = dateMillis;
        this.Student = student;
        this.Notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
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

    public long getDateMillis() {
        return DateMillis;
    }

    public void setDateMillis(long dateMillis) {
        DateMillis = dateMillis;
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

    public String getDateHoursMinutes(){
        return Converter.convertTimeToString(new Date(getDateMillis()));
    }

    public int getUniqueId(){
        long modulo = DateMillis % 100000000;
        return (int) modulo;
    }

    @Override
    public String toString() {
        return "Car: " + Car + ", Date: " + Date + ", Instructor: " + Instructor;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(RIDE_ID, this.id);
        bundle.putString(RIDE_DATE, this.Date);
        bundle.putLong(RIDE_DATE_MILLIS, this.DateMillis);
        bundle.putString(RIDE_CAR, this.Car);
        bundle.putString(RIDE_INSTRUCTOR, this.Instructor);
        bundle.putString(RIDE_STUDENT, this.Student);
        bundle.putBoolean(RIDE_COMPLETED, this.Completed);
        bundle.putString(RIDE_NOTES, this.Notes);
        parcel.writeBundle(bundle);
    }

    public static final Parcelable.Creator<Ride> CREATOR = new Parcelable.Creator<Ride>(){

        @Override
        public Ride createFromParcel(Parcel parcel) {
            return new Ride(parcel);
        }

        @Override
        public Ride[] newArray(int i) {
            return new Ride[i];
        }
    };

    private Ride(Parcel parcel){
        Bundle bundle = parcel.readBundle(Ride.class.getClassLoader());
        this.id = bundle.getString(RIDE_ID);
        this.Date = bundle.getString(RIDE_DATE);
        this.DateMillis = bundle.getLong(RIDE_DATE_MILLIS);
        this.Car = bundle.getString(RIDE_CAR);
        this.Instructor = bundle.getString(RIDE_INSTRUCTOR);
        this.Student = bundle.getString(RIDE_STUDENT);
        this.Completed = bundle.getBoolean(RIDE_COMPLETED);
        this.Notes = bundle.getString(RIDE_NOTES);
    }

    @Override
    public int compareTo(Ride ride) {
        if(getDateMillis() < ride.getDateMillis()){
            return -1;
        }else if(getDateMillis() > ride.getDateMillis()){
            return 1;
        }
        return 0;
    }
}
