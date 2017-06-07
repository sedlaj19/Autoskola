package cz.sedlaj19.autoskola.storage.converter;

import cz.sedlaj19.autoskola.storage.model.User;

/**
 * Created by Honza on 7. 8. 2016.
 */
public class UserConverter {

    public static User convertToStorageModel(cz.sedlaj19.autoskola.domain.model.User user){
        User result = new User();
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setFinished(user.isFinished());
        result.setInstructor(user.isInstructor());
        result.setName(user.getName());
        result.setPhone(user.getPhone());
        result.setSurname(user.getSurname());
        result.setDeviceId(user.getDeviceId());
        return result;
    }

    public static cz.sedlaj19.autoskola.domain.model.User convertToDomainModel(User user){
        cz.sedlaj19.autoskola.domain.model.User result = new cz.sedlaj19.autoskola.domain.model.User();
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setFinished(user.isFinished());
        result.setInstructor(user.isInstructor());
        result.setName(user.getName());
        result.setPhone(user.getPhone());
        result.setSurname(user.getSurname());
        result.setDeviceId(user.getDeviceId());
        return result;
    }

}
