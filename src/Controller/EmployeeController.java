package Controller;

import Model.Employee;
import Model.EmployeeModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

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
        
        

        // Afficher (Display) button listener
        view.getAfficher().addActionListener(e -> {
            List<Employee> allEmployees = model.getAllEmployees();
            List<String> employeeStrings = new ArrayList<>();
            employeeStrings.add(String.format(
                    "| %-5s | %-15s | %-15s | %-15s | %-25s | %-10s | %-20s | %-10s |",
                    "ID", "Nom", "Prenom", "Tel", "Email", "Salaire", "Poste", "Role"
            ));
            for (Employee emp : allEmployees) {
                employeeStrings.add(emp.toString());
            }
            String[] employeeArray = employeeStrings.toArray(new String[0]);
        
            // Replace the JList
            JList<String> updatedList = new JList<>(employeeArray);
            updatedList.addListSelectionListener(view.getEmployeeList().getListSelectionListeners()[0]); // Re-add listener
        
            view.setEmployeeList(updatedList);
            JPanel p3 = view.getP3();
            p3.removeAll();
            p3.add(new JScrollPane(updatedList));
            p3.revalidate();
            p3.repaint();
        });
        

        // Delete (Supprimer) button listener
        view.getSupprimer().addActionListener(e -> {
            String selectedEmployeeString = view.getEmployeeList().getSelectedValue();
            if (selectedEmployeeString == null) {
                JOptionPane.showMessageDialog(view, "No employee selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                int id = EmployeeModel.parseEmployeeId(selectedEmployeeString);
                int confirm = JOptionPane.showConfirmDialog(view, "Are you sure you want to delete this employee?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    model.deleteEmployee(id);
                    JOptionPane.showMessageDialog(view, "Employee deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    view.clearInputFields(); // Clear the input fields
                    view.getAfficher().doClick();
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Update (Modifier) button listener
        view.getModifier().addActionListener(e -> {
            String selectedEmployeeString = view.getEmployeeList().getSelectedValue();
            if (selectedEmployeeString == null) {
                JOptionPane.showMessageDialog(view, "No employee selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        
            try {
                int id = EmployeeModel.parseEmployeeId(selectedEmployeeString);
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
        

        // Employee list selection listener to populate fields with selected employee data
        view.getEmployeeList().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedEmployeeString = view.getEmployeeList().getSelectedValue();
                if (selectedEmployeeString == null || selectedEmployeeString.trim().isEmpty()) {
                    return;
                }
        
                try {
                    int id = EmployeeModel.parseEmployeeId(selectedEmployeeString);
                    Employee selectedEmployee = model.getAllEmployees()
                            .stream()
                            .filter(emp -> emp.getId() == id)
                            .findFirst()
                            .orElse(null);
        
                    if (selectedEmployee != null) {
                        // Populate the fields with employee details
                        view.getNom().setText(selectedEmployee.getNom());
                        view.getPrenom().setText(selectedEmployee.getPrenom());
                        view.getTel().setText(selectedEmployee.getTel());
                        view.getEmail().setText(selectedEmployee.getEmail());
                        view.getSal().setText(String.valueOf(selectedEmployee.getSalaire()));
                        view.getRoleComboBox().setSelectedItem(selectedEmployee.getRole().name());
                        view.getPostesComboBox().setSelectedItem(selectedEmployee.getPoste().name());
                    }
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(view, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
    }
}
