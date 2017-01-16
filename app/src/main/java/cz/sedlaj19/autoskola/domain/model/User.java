package cz.sedlaj19.autoskola.domain.model;


import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable{

    public static final String USER_NAME = "userName";
    public static final String USER_SURNAME = "userSurname";
    public static final String USER_PHONE = "userPhone";
    public static final String USER_EMAIL = "userEmail";
    public static final String USER_FINISHED = "userFinished";
    public static final String USER_IS_INSTRUCTOR = "userIsInstructor";
    public static final String USER_RIDES = "userRides";

    private String id;
    private String Name;
    private String Surname;
    private String Phone;
    private String Email;
    private ArrayList<Ride> Rides;
    private boolean Finished;
    private boolean IsInstructor;

    public User(){}

    public User(String Name, String Surname, String Phone, String Email, boolean isInstructor) {
        this.Name = Name;
        this.Surname = Surname;
        this.Phone = Phone;
        this.Email = Email;
        this.Finished = false;
        this.IsInstructor = isInstructor;
    }

    public User(String Name, String Surname, String Phone, String Email,
                ArrayList<Ride> Rides, boolean isInstructor){
        this.Name = Name;
        this.Surname = Surname;
        this.Phone = Phone;
        this.Email = Email;
        this.Rides = Rides;
        this.Finished = false;
        this.IsInstructor = isInstructor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String Surname) {
        this.Surname = Surname;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public List<Ride> getRides() {
        return Rides;
    }

    public void setRides(ArrayList<Ride> rides) {
        Rides = rides;
    }

    public boolean isFinished() {
        return Finished;
    }

    public void setFinished(boolean finished) {
        Finished = finished;
    }

    public boolean isInstructor() {
        return IsInstructor;
    }

    public void setInstructor(boolean instructor) {
        IsInstructor = instructor;
    }

    @Override
    public String toString() {
        String rds = "";
        for(Ride r : Rides){
            rds += r.toString() + "\n";
        }
        return "Name: " + Name + ", Surname: " + Surname + ", Email: " + Email + ", Phone: " + Phone +
                ", Rides: \n" + rds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME, this.Name);
        bundle.putString(USER_SURNAME, this.Surname);
        bundle.putString(USER_PHONE, this.Phone);
        bundle.putString(USER_EMAIL, this.Email);
        bundle.putBoolean(USER_FINISHED, this.Finished);
        bundle.putBoolean(USER_IS_INSTRUCTOR, this.IsInstructor);
        bundle.putParcelableArrayList(USER_RIDES, this.Rides);
        parcel.writeBundle(bundle);
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>(){

        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };

    private User(Parcel parcel){
        Bundle bundle = parcel.readBundle(User.class.getClassLoader());
        this.Name = bundle.getString(USER_NAME);
        this.Surname = bundle.getString(USER_SURNAME);
        this.Phone = bundle.getString(USER_PHONE);
        this.Email = bundle.getString(USER_EMAIL);
        this.Finished = bundle.getBoolean(USER_FINISHED);
        this.IsInstructor = bundle.getBoolean(USER_IS_INSTRUCTOR);
        this.Rides = bundle.getParcelableArrayList(USER_RIDES);
    }
}
