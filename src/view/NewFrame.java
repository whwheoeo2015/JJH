package view;

import model.Budget;
import model.Description;
import model.GBC;
import model.Project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.BorderLayout;
import java.math.BigDecimal;

import static model.Project.createProject;

/**
 * This class creates New window so that users can create new project with
 * their own input, name of project, spending limit, and description of project.
 */
public class NewFrame implements GUIFrame {

    private final JFrame frame;

    private final JTextField nameBox;

    private final JTextField budgetBox;

    private final JTextArea descriptionBox;

    private String userName;

    private String userBudget;

    private String userDescription;

    public NewFrame() {
        frame = new JFrame("Crafty Companion - New");
        userBudget = null;
        userDescription = null;
        userName = null;
        nameBox = new JTextField(20);
        budgetBox = new JTextField(20);
        descriptionBox = new JTextArea(10,20);
        descriptionBox.setLineWrap(true);
        start();
    }

    private void start() {
        //Show everything I want it to show.
        frame.setLayout(new BorderLayout());
        frame.add(displayText(),BorderLayout.CENTER);
        frame.add(buttonCreator(),BorderLayout.SOUTH);
        frame.add(greetingPanel(), BorderLayout.NORTH);
        //Set the size
        frame.pack();
        frame.setResizable(false);
        //Center it.
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //Make it show up.
        frame.setVisible(true);
    }
    /**
     *  Method to create button
     *  @return panel
     */
    private JPanel buttonCreator() {
        JPanel panel = new JPanel();
        panel.setSize(frame.getSize());
        panel.setBackground(BACKGROUND_COLOR);
        panel.setLayout(new FlowLayout());
        panel.add(createButton());
        panel.add(cancelButton());

        return panel;
    }

    /**
     * Method to create panel to display questions and to get user input
     * @return panel
     */
    private JPanel displayText() {
        //Initiate the panel
        JPanel panel = new JPanel();
        panel.setSize(frame.getSize());
        panel.setBackground(BACKGROUND_COLOR);
        LayoutManager layout = new GridBagLayout();
        panel.setLayout(layout);
        //Put in the greeting.
        GBC gbc = new GBC(1, 0, 3, 1);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GBC.NORTH;

        gbc = new GBC(1, 1);
        gbc.ipady = 20;
        gbc.ipadx = 20;
        panel.add(nameQ(), gbc);

        gbc = new GBC(1, 3);
        gbc.ipady = 20;
        gbc.ipadx = 20;
        panel.add(budgetQ(), gbc);

        gbc = new GBC(1, 5);
        gbc.ipady = 40;
        gbc.ipadx = 20;
        panel.add(descriptionM(), gbc);

        gbc = new GBC(1, 2);
        budgetBox.setFont(BODY_FONT);
        panel.add(nameBox, gbc);

        gbc = new GBC(1, 4);
        budgetBox.setFont(BODY_FONT);
        panel.add(budgetBox, gbc);

        gbc = new GBC(1, 6);
        descriptionBox.setFont(BODY_FONT);
        panel.add(descriptionBox, gbc);

        return panel;
    }

    /**
     *  Method to create panel for greeting
     * @return panel
     */
    private JPanel greetingPanel() {

        JPanel panel = new JPanel();
        panel.setSize(frame.getSize());
        panel.setBackground(BACKGROUND_COLOR);
        panel.add(greeting());

        return panel;
    }

    /**
     * method to create displaying message
     * @return greeting
     */
    private JLabel greeting() {
        JLabel greeting = new JLabel("You are creating your new project!");
        greeting.setAlignmentX(Component.CENTER_ALIGNMENT);
        greeting.setFont(HEADING_FONT);
        greeting.setBackground(BACKGROUND_COLOR);
        greeting.setForeground(FOREGROUND_COLOR);
        return greeting;
    }

    /**
     * method to create question message
     * @return nameQ
     */
    private JLabel nameQ() {
        JLabel nameQ = textArea("What is your name of your project?", JLabel.LEFT_ALIGNMENT,
                BODY_FONT);
        return nameQ;
    }
    /**
     * method to create question message
     * @return budgetQ
     */
    private JLabel budgetQ() {
        JLabel budgetQ = textArea("What is your spending limit?", JLabel.LEFT_ALIGNMENT,
                BODY_FONT);
        return budgetQ;
    }
    /**
     * method to create question message
     * @return descriptionM
     */
    private JLabel descriptionM() {
        JLabel descriptionM = textArea("Write any description of your project please.", JTextArea.LEFT_ALIGNMENT,
                BODY_FONT);
        return descriptionM;
    }

    /**
     * method to add create button with how it is supposed to work
     * @return create
     */
    private JButton createButton() {
        JButton create = new JButton("Create");
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAction();
            }
        });
        create.setBackground(FOREGROUND_COLOR);
        create.setForeground(BACKGROUND_COLOR);
        return create;
    }
    /**
     * method to add cancel button with how it is supposed to work
     * @return cancel
     */
    private JButton cancelButton() {
        JButton cancel = new JButton("cancel");
        cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelAction();
            }
        });
        cancel.setBackground(FOREGROUND_COLOR);
        cancel.setForeground(BACKGROUND_COLOR);
        return cancel;
    }

    /**
     * method to add action of cancel button as it is clicked by user
     */
    private void cancelAction(){
        int result = JOptionPane.showConfirmDialog(null, "Do you want to cancel?");

        if(result == JOptionPane.YES_OPTION){
            frame.dispose();
            new WelcomeFrame();
        }

    }
    /**
     * method to add action of create button as it is clicked by user
     */
    private void createAction() {
        boolean hasFailed = false;
        userName = nameBox.getText();
        userBudget = budgetBox.getText();
        userDescription = descriptionBox.getText();

        int countDot = 0;

        //it catches the invalid input if user enter negative number and non-numeric value.
        for(int i = 0; i < userBudget.length(); i++){
            if((userBudget.charAt(i) <48 && userBudget.charAt(i) != 46) || (userBudget.charAt(i) > 57 && userBudget.charAt(i) != 46)){
                JOptionPane.showMessageDialog(null, "Budget Error: You entered invalid budget!");
                hasFailed = true;
            } else if (userBudget.charAt(i) == 46){
                countDot++;
            }

            if(countDot > 1){
                JOptionPane.showMessageDialog(null, "Budget Error: You can't have more than 1 dot!");
                hasFailed = true;
            }
        }

        if (!hasFailed) {
            BigDecimal theBudget = new BigDecimal(userBudget);



            if(createProject(userName, theBudget, userDescription)) {
                Budget mybudget = new Budget("data/" + userName + "/", theBudget);
                Description myDescription = new Description("data/" + userName, userDescription);
                mybudget.writeToTXT();
                myDescription.writeToTXT();

                frame.dispose();
//            new WelcomeFrame();
            }
        }
        else {
            hasFailed = false;
        }
    }

    private JLabel textArea(String text, float alignmentX, Font font) {
        JLabel textArea = new JLabel(text);
        textArea.setAlignmentX(alignmentX);
        textArea.setFont(font);
        textArea.setBackground(BACKGROUND_COLOR);
        textArea.setForeground(FOREGROUND_COLOR);
        return textArea;
    }
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new NewFrame();
            }
        });
    }
}