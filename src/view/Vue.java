package view;

import DAO.*;
import Model.Employee;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Vue extends JFrame {

    private JPanel p1;
    private JPanel p2;
    private JPanel p3;
    private JPanel p4;

    private JComboBox<String> postesComboBox;
    private JComboBox<String> roleComboBox;

    private JButton ajouter;
    private JButton modifier;
    private JButton supprimer;
    private JButton afficher;

    private JTextField tel;
    private JTextField sal;
    private JTextField nom;
    private JTextField prenom;
    private JTextField email;

    private JTable employeeTable;
    private DefaultTableModel tableModel;

    public Vue() {
        setTitle("Gestion des employes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize DAO
        EmployeeDAOImpl eImp = new EmployeeDAOImpl();

        // Panel setup
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();
        p4 = new JPanel();

        p1.setLayout(new BorderLayout());
        p2.setLayout(new GridLayout(7, 2, 10, 10));

        add(p1);
        p1.add(p2, BorderLayout.NORTH);
        p1.add(p3, BorderLayout.CENTER);
        p1.add(p4, BorderLayout.SOUTH);

        // Input fields in p2
        p2.add(new JLabel("Nom:"));
        nom = new JTextField();
        p2.add(nom);

        p2.add(new JLabel("Prenom:"));
        prenom = new JTextField();
        p2.add(prenom);

        p2.add(new JLabel("Email:"));
        email = new JTextField();
        p2.add(email);

        p2.add(new JLabel("Telephone:"));
        tel = new JTextField();
        p2.add(tel);

        p2.add(new JLabel("Salaire:"));
        sal = new JTextField();
        p2.add(sal);

        p2.add(new JLabel("Role:"));
        List<Employee.Role> roles = eImp.findAllRoles();
        String[] roleStrings = roles.stream().map(Enum::name).toArray(String[]::new);
        roleComboBox = new JComboBox<>(roleStrings);
        p2.add(roleComboBox);

        p2.add(new JLabel("Poste:"));
        List<Employee.Poste> postes = eImp.findAllPostes();
        String[] postesStrings = postes.stream().map(Enum::name).toArray(String[]::new);
        postesComboBox = new JComboBox<>(postesStrings);
        p2.add(postesComboBox);

        // Table setup in p3
        employeeTable = new JTable(); // Initialize the table
        String[] columnNames = {"ID", "Nom", "Prenom", "Tel", "Email", "Salaire", "Poste", "Role"};
        tableModel = new DefaultTableModel(columnNames, 0);
        employeeTable = new JTable(tableModel);
        employeeTable.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(employeeTable);
        p3.setLayout(new BorderLayout());
        p3.add(scrollPane, BorderLayout.CENTER);

        // Buttons in p4
        p4.setLayout(new FlowLayout());
        ajouter = new JButton("Ajouter");
        modifier = new JButton("Modifier");
        supprimer = new JButton("Supprimer");
        afficher = new JButton("Afficher");
        p4.add(ajouter);
        p4.add(modifier);
        p4.add(supprimer);
        p4.add(afficher);

        // Add action listener for "Afficher"
        afficher.addActionListener(e -> {
            // Fetch data and update table
            List<Employee> allEmployees = eImp.findAll();
            tableModel.setRowCount(0); // Clear existing rows

            for (Employee emp : allEmployees) {
                tableModel.addRow(new Object[]{
                        emp.getId(),
                        emp.getNom(),
                        emp.getPrenom(),
                        emp.getTel(),
                        emp.getEmail(),
                        String.format("%.2f", emp.getSalaire()),
                        emp.getPoste(),
                        emp.getRole()
                });
            }
        });

        setVisible(true);
    }

    public JTable getEmployeeTable() {
        return employeeTable;
    }
    

    // Getters for other components if needed
    public JComboBox<String> getPostesComboBox() {
        return postesComboBox;
    }

    public JComboBox<String> getRoleComboBox() {
        return roleComboBox;
    }

    public JButton getAjouter() {
        return ajouter;
    }

    public JButton getModifier() {
        return modifier;
    }

    public JButton getSupprimer() {
        return supprimer;
    }

    public JButton getAfficher() {
        return afficher;
    }

    public JTextField getTel() {
        return tel;
    }

    public JTextField getSal() {
        return sal;
    }

    public JTextField getNom() {
        return nom;
    }

    public JTextField getPrenom() {
        return prenom;
    }

    public JTextField getEmail() {
        return email;
    }

    public void clearInputFields() {
        nom.setText("");
        prenom.setText("");
        tel.setText("");
        email.setText("");
        sal.setText("");
        roleComboBox.setSelectedIndex(0);
        postesComboBox.setSelectedIndex(0);
    }
}
