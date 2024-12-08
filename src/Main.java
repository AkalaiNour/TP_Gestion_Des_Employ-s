import Controller.EmployeeController;
import DAO.EmployeeDAOImpl;
import Model.EmployeeModel;
import view.Vue;

public class Main {
    public static void main(String[] args) {
        EmployeeDAOImpl dao = new EmployeeDAOImpl();
        EmployeeModel model = new EmployeeModel(dao);
        Vue view = new Vue();

        // Pass the model and view to the controller
        new EmployeeController(model, view);
    }
}
