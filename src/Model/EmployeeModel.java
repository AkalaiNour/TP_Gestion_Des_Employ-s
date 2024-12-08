package Model;

import DAO.EmployeeDAOImpl;
import java.util.List;

public class EmployeeModel {
    private final EmployeeDAOImpl dao;

    public EmployeeModel(EmployeeDAOImpl dao) {
        this.dao = dao;
    }

    // Add a new employee
    public void addEmployee(Employee emp) {
        dao.add(emp);
    }

    // Retrieve all employees
    public List<Employee> getAllEmployees() {
        return dao.findAll();
    }

    // Delete an employee by ID
    public void deleteEmployee(int id) {
        dao.delete(id);
    }

    // Update an employee
    public void updateEmployee(Employee emp, int id) {
        dao.update(emp, id);
    }

    // Utility method to parse employee ID from selected string
    public static int parseEmployeeId(String selectedEmployeeString) {
        try {
            return Integer.parseInt(selectedEmployeeString.split("\\|")[1].trim());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid employee format.");
        }
    }
}
