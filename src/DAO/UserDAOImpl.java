package DAO;

import Model.Employee;
import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl implements UserDAOI{
    private Connection connection = DBConnection.getConnection();
    @Override
    public User findByUserId(int id) {
        String sql = "select * from user where id=?";

        return null;
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM user";
        User user = null;
        ArrayList<User> userList = new ArrayList<User>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while(rs.next()){
                    User e = new User(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            new EmployeeDAOImpl().findByEmployee(rs.getInt("employee_id"))
                    );
                    userList.add(e);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList ;
    }

    @Override
    public void add(User u) {

    }

    @Override
    public void update(User e, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
