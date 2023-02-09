package project5test;

import javax.swing.*;


import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Login {

    private static PrintWriter writer;
    private static ObjectInputStream objectReader;
    private static HashMap<String, Person> userAndPass;

    public Login(PrintWriter writer, ObjectInputStream objectReader, HashMap<String, Person> userAndPass) throws IOException {
        this.writer = writer;
        this.objectReader = objectReader;
        this.userAndPass = userAndPass;
    }

    public static Person loginMenu() {
        do {
            String username = JOptionPane.showInputDialog(null, "Enter Username:",
                    "Login", JOptionPane.QUESTION_MESSAGE);
            if (username == null) {
                return new Person("empty", "empty");
            }
            String password = JOptionPane.showInputDialog(null, "Enter Password:",
                    "Login", JOptionPane.QUESTION_MESSAGE);
            if (password == null) {
                return new Person("empty", "empty");
            }

            if (checkLogin(username, password)) {
                return userAndPass.get(username);
            }
            JOptionPane.showMessageDialog(null, "Username and Password do not match.", "Login",
                    JOptionPane.ERROR_MESSAGE);
        } while (true);
    }

    public static void signupMenu() throws IOException {
        boolean checker1 = true;
        do {
            String username = "";
            boolean checker = true;
            boolean cont = true;
            while (checker) {
                username = JOptionPane.showInputDialog(null, "Enter Username:",
                        "Login", JOptionPane.QUESTION_MESSAGE);
                try {
                    if (username.isEmpty() || username.isBlank() || username == null) {
                        JOptionPane.showMessageDialog(null,
                                "Please enter a valid username.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        checker = true;
                    } else {
                        checker = false;
                    }
                } catch (NullPointerException e) {
                    cont = false;
                    checker = false;
                    checker1 = false;
                }
            }
            String password = "";
            checker = true;
            while (checker && cont) {
                password = JOptionPane.showInputDialog(null, "Enter Password:",
                        "Login", JOptionPane.QUESTION_MESSAGE);
                try {
                    if (password.isEmpty() || password.isBlank() || password == null) {
                        JOptionPane.showMessageDialog(null,
                                "Please enter a valid password.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                        checker = true;
                    } else {
                        checker = false;
                    }
                } catch (NullPointerException e) {
                    cont = false;
                    checker = false;
                    checker1 = false;
                }
            }
            if (cont) {
                String teacherOrStudent;
                int teachInput = 2;
                do {
                    String[] options2 = {"Teacher", "Student"};
                    teachInput = JOptionPane.showOptionDialog(null, "Are you a Teacher?", "Student",
                            JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options2, options2[1]);

                    if (!(teachInput == 0 || teachInput == 1)) {
                        JOptionPane.showMessageDialog(null, "Please select a choice", "Login",
                                JOptionPane.OK_OPTION);
                    }
                    break;
                } while (true);

                if (createLogin(username, password, teachInput)) {
                    JOptionPane.showMessageDialog(null, "Account Created!", "Login",
                            JOptionPane.OK_OPTION);
                    break;
                }
                JOptionPane.showMessageDialog(null, "Username Taken", "Login",
                        JOptionPane.OK_OPTION);
            }
        } while (checker1);
    }

    public static boolean createLogin(String username, String password, int teachInput) throws IOException {
        if (userAndPass.get(username) != null && username != null) {
            return false;
        }

        if (teachInput == 0) {
            Teacher teach = new Teacher(username, password);
            Info.addPerson(teach);
            userAndPass.put(username, teach);
        }
        if (teachInput == 1) {
            Student stu = new Student(username, password);
            Info.addPerson(stu);
            userAndPass.put(username, stu);
        }

        return true;
    }

    public static boolean checkLogin(String username, String password) {
        try {
            return password.equals(userAndPass.get(username).getPassword());
        } catch (Exception e) {
            return false;
        }
    }

    public static void setUserAndPass(HashMap<String, Person> map) {
        userAndPass = map;
    }

    public static HashMap<String, Person> getUserAndPass() {
        return userAndPass;
    }
}
