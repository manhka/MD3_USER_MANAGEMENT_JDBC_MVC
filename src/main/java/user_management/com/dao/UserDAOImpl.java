package user_management.com.dao;

import user_management.com.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements IUserDAO {
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/userDataBase", "root", "123456");

        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (SQLException e) {

            e.printStackTrace();
        }
        return connection;
    }

    List<User> users;

    @Override
    public List<User> findAll() {
        users = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select*from users");
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                int age = resultSet.getInt("age");
                users.add(new User(id, name, country, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void create(User user) throws SQLException {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into users(name,country,age)values(?,?,?);")) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getCountry());
            preparedStatement.setInt(3, user.getAge());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(User user) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("update users set name = ?,country= ?, age =? where id = ?;");) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getCountry());
            statement.setInt(3, user.getAge());
            statement.setInt(4, user.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public void deleteById(int id) throws SQLException {
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement("delete from users where id = ?;");) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }

    }

    List<User> userList = new ArrayList<>();

    @Override
    public List<User> sortByName() {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select*from users order by name desc;")
        ) {

            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("rl=" + resultSet);
            while (resultSet.next()) {

                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String country = resultSet.getString("country");
                int age = resultSet.getInt("age");
                userList.add(new User(id, name, country, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }


    @Override
    public List<User> searchByCountry(String country) {
        List<User> usersSearch = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getCountry().contains(country)) {
                usersSearch.add(new User(users.get(i).getId(), users.get(i).getName(), users.get(i).getCountry(), users.get(i).getAge()));
            }
        }
        return usersSearch;
    }
}
