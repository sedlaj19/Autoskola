package cz.sedlaj19.autoskola.storage.converter;


import java.util.Date;

import cz.sedlaj19.autoskola.utils.Converter;
import cz.sedlaj19.autoskola.storage.model.Ride;

/**
 * Created by Honza on 7. 8. 2016.
 */
public class RideConverter {

    public static Ride convertToStrorageModel(cz.sedlaj19.autoskola.domain.model.Ride ride){
        Ride result = new Ride();
        result.setCar(ride.getCar());
        result.setCompleted(ride.isCompleted());
        result.setDate(ride.getDateMillis());
        result.setInstructor(ride.getInstructor());
        result.setStudent(ride.getStudent());
        result.setNotes(ride.getNotes());
        return result;
    }

    public static cz.sedlaj19.autoskola.domain.model.Ride convertToDomainModel(Ride ride){
        cz.sedlaj19.autoskola.domain.model.Ride result = new cz.sedlaj19.autoskola.domain.model.Ride();
        result.setCar(ride.getCar());
        result.setCompleted(ride.isCompleted());
        result.setDateMillis(ride.getDate());
        result.setInstructor(ride.getInstructor());
        result.setStudent(ride.getStudent());
        result.setNotes(ride.getNotes());

        Date d = new Date(ride.getDate());
        result.setDate(Converter.convertDateAndTimeToString(d));

        return result;
    }
}
