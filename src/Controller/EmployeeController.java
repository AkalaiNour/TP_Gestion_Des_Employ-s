package Controller;

import Model.Employee;
import Model.EmployeeModel;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import view.Vue;

public class EmployeeController {
    private final EmployeeModel model;
    private final Vue view;

    public EmployeeController(EmployeeModel model, Vue view) {
        this.model = model;
        this.view = view;

        initializeListeners();
    }

    private void initializeListeners() {
        // Add (Ajouter) button listener
        view.getAjouter().addActionListener(e -> {
            try {
                Employee emp = new Employee(
                        0,
                        view.getNom().getText(),
                        view.getPrenom().getText(),
                        view.getTel().getText(),
                        view.getEmail().getText(),
                        Double.parseDouble(view.getSal().getText()),
                        Employee.Role.valueOf((String) view.getRoleComboBox().getSelectedItem()),
                        Employee.Poste.valueOf((String) view.getPostesComboBox().getSelectedItem())
                );
                model.addEmployee(emp);
                JOptionPane.showMessageDialog(view, "Employee added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                view.clearInputFields(); // Clear the input fields
                view.getAfficher().doClick(); // Refresh the employee list
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid salary value!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Display (Afficher) button listener
        view.getAfficher().addActionListener((ActionEvent e) -> {
            // Get all employees from the model
            List<Employee> allEmployees = model.getAllEmployees();

            // Define column names
            String[] columnNames = {"ID", "Nom", "Prenom", "Tel", "Email", "Salaire", "Poste", "Role"};

            // Populate rows with employee data
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
            for (Employee emp : allEmployees) {
                tableModel.addRow(new Object[]{
                        emp.getId(),
                        emp.getNom(),
                        emp.getPrenom(),
                        emp.getTel(),
                        emp.getEmail(),
                        emp.getSalaire(),
                        emp.getPoste(),
                        emp.getRole()
                });
            }

            // Update table in the view
            JTable table = view.getEmployeeTable();
            table.setModel(tableModel);
        });

        // Delete (Supprimer) button listener
        view.getSupprimer().addActionListener(e -> {
            JTable table = view.getEmployeeTable();
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "No employee selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int id = (int) table.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this employee?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                model.deleteEmployee(id);
                JOptionPane.showMessageDialog(view, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                view.getAfficher().doClick(); // Refresh the employee list
            }
        });

        // Update (Modifier) button listener
        view.getModifier().addActionListener(e -> {
            JTable table = view.getEmployeeTable();
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "No employee selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int id = (int) table.getValueAt(selectedRow, 0);
                Employee emp = new Employee(
                        id,
                        view.getNom().getText(),
                        view.getPrenom().getText(),
                        view.getTel().getText(),
                        view.getEmail().getText(),
                        Double.parseDouble(view.getSal().getText()),
                        Employee.Role.valueOf((String) view.getRoleComboBox().getSelectedItem()),
                        Employee.Poste.valueOf((String) view.getPostesComboBox().getSelectedItem())
                );
                model.updateEmployee(emp, id);
                JOptionPane.showMessageDialog(view, "Employee updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                view.clearInputFields(); // Clear the input fields
                view.getAfficher().doClick(); // Refresh the employee list
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view, "Invalid salary value!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Table selection listener to populate fields with selected employee data
        view.getEmployeeTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                JTable table = view.getEmployeeTable();
                int selectedRow = table.getSelectedRow();
                if (selectedRow == -1) {
                    return;
                }

                view.getNom().setText((String) table.getValueAt(selectedRow, 1));
                view.getPrenom().setText((String) table.getValueAt(selectedRow, 2));
                view.getTel().setText((String) table.getValueAt(selectedRow, 3));
                view.getEmail().setText((String) table.getValueAt(selectedRow, 4));
                view.getSal().setText(String.valueOf(table.getValueAt(selectedRow, 5)));
                view.getRoleComboBox().setSelectedItem(table.getValueAt(selectedRow, 7).toString());
                view.getPostesComboBox().setSelectedItem(table.getValueAt(selectedRow, 6).toString());
            }
        });
    }
}
