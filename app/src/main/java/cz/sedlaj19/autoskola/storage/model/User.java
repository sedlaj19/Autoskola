package cz.sedlaj19.autoskola.storage.model;


public class User {

    private String id;
    private String Name;
    private String Surname;
    private String Phone;
    private String Email;
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

    @Override
    public String toString() {
        return "Name: " + Name + ", Surname: " + Surname + ", Email: " + Email + ", Phone: " + Phone;
    }
}
