package cz.sedlaj19.autoskola.domain.model;

/**
 * Created by Honza on 25. 1. 2017.
 */

public class Car {

    private String id;
    private String name;

    public Car(){}

    public Car(String name){
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
