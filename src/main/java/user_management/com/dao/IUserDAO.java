package user_management.com.dao;

import user_management.com.model.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    List<User> findAll() throws SQLException;

    void create(User user) throws SQLException;

    void update(User user) throws SQLException;

    void deleteById(int id) throws SQLException;

    List<User> sortByName() throws SQLException;

    List<User> searchByCountry(String country);
}
