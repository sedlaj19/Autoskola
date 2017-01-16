package cz.sedlaj19.autoskola.domain.repository;


import cz.sedlaj19.autoskola.domain.model.User;

/**
 * A sample repository with CRUD operations on a model.
 */
public interface Repository {

    boolean insert(User model);

    boolean update(User model);

    User get(Object id);

    boolean delete(User model);
}
