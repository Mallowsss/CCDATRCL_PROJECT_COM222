/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package student.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class StudentNode {
    String studentName;
    StudentNode next;

    public StudentNode(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentName() {
        return studentName;
    }
}

public class EditSectionFrame extends JFrame {
    private StudentNode head = null;
    private DefaultListModel<String> studentListModel;

    public EditSectionFrame(String sectionName) {
        setTitle("Edit Section");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());

        // Create a panel for the content
        JPanel editPanel = new JPanel(new BorderLayout());
        editPanel.setBackground(Color.PINK);

        // Title Label
        JLabel titleLabel = new JLabel("MASTERLIST");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);

        editPanel.add(titleLabel, BorderLayout.NORTH);

        // Create the "Add Student" button
        JButton addButton = new JButton("Add");

        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openStudentNameFrame();
            }
        });

        // Create an empty list for students
        studentListModel = new DefaultListModel<>();
        JList<String> masterList = new JList<>(studentListModel);
        masterList.setFont(new Font("Times New Roman", Font.BOLD, 18));

        // Create the "Delete Student" button
        JButton deleteButton = new JButton("Delete");

       
       deleteButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = masterList.getSelectedIndex();
            if (selectedIndex != -1) {
                String studentName = studentListModel.getElementAt(selectedIndex);

                int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this student?", "Delete Student",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (choice == JOptionPane.YES_OPTION) {
                    studentListModel.remove(selectedIndex);
                    removeStudentFromList(studentName);
                }
            }
        }
    });


        // Create the "Sort A-Z" button
        JButton sortAZButton = new JButton("A-Z");

        // ActionListener for the "Sort A-Z" button
        sortAZButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sortStudentNames();
            }
        });

        // Create the "Total" button
        JButton totalButton = new JButton("Total");

        // ActionListener for the "Total" button
        totalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTotalStudents();
            }
        });

        // Create the "Search" button
        JButton searchButton = new JButton("Search");
        // Input field for search
        JTextField searchField = new JTextField(); 

        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = searchField.getText();
                searchStudentList(query);
            }
        });
        
        // Create the "Back" button
        JButton backButton = new JButton("Back");

       
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               
                dispose();
            }
        });

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 6));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(sortAZButton);
        buttonPanel.add(totalButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(searchField);
        buttonPanel.add(backButton);

        // Add components to the frame
        editPanel.add(new JScrollPane(masterList), BorderLayout.CENTER);
        editPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(editPanel, BorderLayout.CENTER);
    }

    private void openStudentNameFrame() {
        JFrame studentNameFrame = new JFrame("Enter Student Name");
        studentNameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        studentNameFrame.setSize(400, 200);

        // Message
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBackground(Color.PINK);

        // Message Label
        JLabel messageLabel = new JLabel("Please input the student's name:");
        messageLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setForeground(Color.WHITE);

        JTextField studentNameField = new JTextField();
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String studentName = studentNameField.getText();
                if (!studentName.isEmpty()) {
                    addStudentToList(studentName);
                    studentListModel.addElement(studentName);
                    studentNameField.setText(""); // Clear the input field
                    studentNameFrame.dispose();
                }
            }
        });

        studentNameFrame.add(messagePanel, BorderLayout.NORTH);
        messagePanel.add(messageLabel, BorderLayout.CENTER);
        studentNameFrame.add(studentNameField, BorderLayout.CENTER);
        studentNameFrame.add(saveButton, BorderLayout.SOUTH);

        studentNameFrame.setVisible(true);
    }

    private void addStudentToList(String studentName) {
        StudentNode newStudent = new StudentNode(studentName);
        newStudent.next = head;
        head = newStudent;
    }

    private void removeStudentFromList(String studentName) {
    StudentNode current = head;
    StudentNode prev = null;

    while (current != null && !current.studentName.equals(studentName)) {
        prev = current;
        current = current.next;
    }

    if (current != null) {
        if (prev != null) {
            prev.next = current.next;
        } else {
            head = current.next;
        }
    }
}


    private void sortStudentNames() {
    head = quickSort(head);
    updateListModel();
}

private StudentNode quickSort(StudentNode node) {
    if (node == null || node.next == null) {
        return node;
    }

    StudentNode pivot = node;
    StudentNode less = null;
    StudentNode equal = null;
    StudentNode greater = null;

    StudentNode current = node;
    while (current != null) {
        if (current.studentName.compareTo(pivot.studentName) < 0) {
            if (less == null) {
                less = new StudentNode(current.studentName);
            } else {
                StudentNode temp = less;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = new StudentNode(current.studentName);
            }
        } else if (current.studentName.compareTo(pivot.studentName) == 0) {
            if (equal == null) {
                equal = new StudentNode(current.studentName);
            } else {
                StudentNode temp = equal;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = new StudentNode(current.studentName);
            }
        } else {
            if (greater == null) {
                greater = new StudentNode(current.studentName);
            } else {
                StudentNode temp = greater;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = new StudentNode(current.studentName);
            }
        }
        current = current.next;
    }

    less = quickSort(less);
    greater = quickSort(greater);

    StudentNode sorted = less;
    StudentNode temp = less;
    while (temp != null && temp.next != null) {
        temp = temp.next;
    }

    if (temp != null) {
        temp.next = equal;
    } else {
        sorted = equal;
    }

    temp = equal;
    while (temp != null && temp.next != null) {
        temp = temp.next;
    }

    if (temp != null) {
        temp.next = greater;
    }

    return sorted;
}

private void updateListModel() {
    studentListModel.clear();
    StudentNode current = head;
    while (current != null) {
        studentListModel.addElement(current.studentName);
        current = current.next;
    }
}


    private void showTotalStudents() {
        JOptionPane.showMessageDialog(this, "Total students: " + studentListModel.size(), "Total Students", JOptionPane.INFORMATION_MESSAGE);
    }

    private boolean binarySearch(String query) {
        StudentNode current = head;
        while (current != null) {
            int cmp = query.compareTo(current.studentName);
            if (cmp == 0) {
                return true; // Found the student's name
            } else if (cmp < 0) {
                return false; // The list is sorted, and the name is not in the list
            }
            current = current.next;
        }
        return false; // Name not found
    }

    private void searchStudentList(String query) {
        if (binarySearch(query)) {
            JOptionPane.showMessageDialog(this, "Student is in the list: " + query, "Search Result", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Student not found: " + query, "Search Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditSectionFrame frame = new EditSectionFrame("");
            frame.setVisible(true);
        });
    }
}

