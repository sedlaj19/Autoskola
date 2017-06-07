package cz.sedlaj19.autoskola.storage.model;


import java.util.HashMap;
import java.util.Map;

public class User {

    private String id;
    private String Name;
    private String Surname;
    private String Phone;
    private String Email;
    private boolean Finished;
    private boolean IsInstructor;
    private String DeviceId;

    public User(){}

    public User(String Name, String Surname, String Phone, String Email, boolean isInstructor) {
        this.Name = Name;
        this.Surname = Surname;
        this.Phone = Phone;
        this.Email = Email;
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

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public Map<String, Object> toMap(){
        HashMap<String, Object> out = new HashMap<>();
        out.put("id", id);
        out.put("name", getName());
        out.put("surname", getSurname());
        out.put("phone", getPhone());
        out.put("email", getEmail());
        out.put("finished", isFinished());
        out.put("instructor", isInstructor());
        out.put("deviceId", getDeviceId());
        return out;
    }

    @Override
    public String toString() {
        return "Name: " + Name + ", Surname: " + Surname + ", Email: " + Email + ", Phone: " + Phone;
    }
}
