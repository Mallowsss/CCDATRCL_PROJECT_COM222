/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package student.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

class Section {
    String name;

    public Section(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Node {
    Section section;
    Node next;

    public Node(Section section) {
        this.section = section;
    }
}

public class STUDENTMANAGEMENTSYSTEM {
    private static Node head = null;
    private static DefaultListModel<String> listModel = new DefaultListModel<>();
    private static JFrame sectionNameFrame;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createUI());
    }

    private static void createUI() {
        // Main Frame
        JFrame frame = new JFrame("Project");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Title and Subtitle
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.PINK);

        // Title Label
        JLabel titleLabel = new JLabel("STUDENT MANAGEMENT SYSTEM");
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setForeground(Color.WHITE);

        // Subtitle Label
        JLabel subtitleLabel = new JLabel("(2nd Year)");
        subtitleLabel.setFont(new Font("Times New Roman", Font.BOLD, 23));
        subtitleLabel.setHorizontalAlignment(JLabel.CENTER);
        subtitleLabel.setForeground(Color.WHITE);

        titlePanel.add(titleLabel, BorderLayout.NORTH);
        titlePanel.add(subtitleLabel, BorderLayout.SOUTH);

        // Create an empty list
        JList<String> studentList = new JList<>(listModel);
        studentList.setFont(new Font("Times New Roman", Font.ITALIC, 18));

        // Create the "Create Section" button
        JButton createSectionButton = new JButton("Create Section");
        createSectionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open a new frame to input the section name
                openSectionNameFrame();
            }
        });

        // Add components to the frame
        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(new JScrollPane(studentList), BorderLayout.CENTER);
        frame.add(createSectionButton, BorderLayout.SOUTH);

        // Add a listener to open the edit or delete section dialog
        studentList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    editOrDeleteSection(studentList);
                }
            }
        });

        frame.setVisible(true);
    }

    private static void openSectionNameFrame() {
        // Main Frame
        sectionNameFrame = new JFrame("Enter Section Name");
        sectionNameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        sectionNameFrame.setSize(400, 200);

        // Message
        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setBackground(Color.PINK);

        // Message Label
        JLabel messageLabel = new JLabel("Please input your desired section name:");
        messageLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setForeground(Color.WHITE);

        JTextField sectionNameField = new JTextField();
        JButton saveButton = new JButton("Save");

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the section name from the text field
                String sectionName = sectionNameField.getText();

                // Add a new section to the linked list
                Section newSection = new Section(sectionName);
                Node newNode = new Node(newSection);
                if (head == null) {
                    head = newNode;
                } else {
                    Node current = head;
                    while (current.next != null) {
                        current = current.next;
                    }
                    current.next = newNode;
                }

                // Update the JList
                listModel.addElement(newSection.getName());

                // Close the section name frame
                sectionNameFrame.dispose();
            }
        });

        sectionNameFrame.add(messagePanel, BorderLayout.NORTH);
        messagePanel.add(messageLabel, BorderLayout.CENTER);
        sectionNameFrame.add(sectionNameField, BorderLayout.CENTER);
        sectionNameFrame.add(saveButton, BorderLayout.SOUTH);

        sectionNameFrame.setVisible(true);
    }

    private static void editOrDeleteSection(JList<String> studentList) {
    int selectedIndex = studentList.getSelectedIndex();
    if (selectedIndex != -1) {
        String sectionName = listModel.getElementAt(selectedIndex);

        int choice = JOptionPane.showOptionDialog(null, "What do you want to do with this section?",
                "Edit or Delete Section", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                new String[] { "Edit", "Delete" }, "Edit");

        if (choice == JOptionPane.YES_OPTION) {
            // User chose to edit, open the edit frame
            EditSectionFrame editSectionFrame = new EditSectionFrame(sectionName);
            editSectionFrame.setVisible(true); // This line opens the EditSectionFrame
        } else if (choice == JOptionPane.NO_OPTION) {
            // User chose to delete, remove the section
            listModel.remove(selectedIndex);
            removeSectionFromList(sectionName);
        }
    }
}

    // A method to remove a section from the linked list
    private static void removeSectionFromList(String sectionName) {
        Node current = head;
        Node prev = null;

        while (current != null && !current.section.getName().equals(sectionName)) {
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
}
