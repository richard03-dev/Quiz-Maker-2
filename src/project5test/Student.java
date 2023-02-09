package project5test;

import java.util.*;

/**
 * Student
 * <p>
 * This program creates the student object
 * that extends person.
 *
 * @author Richard Silvester,
 * Aaron Neman,
 * Varun Rao lab sec O01
 * @version April 11, 2022
 */

public class Student extends Person {

    public Student(String name, String password) {
        super(name, password);
    }

    public String toString(Quiz quiz) {
        String results = "";

        return results;
    }

    //toString for correct responses from student

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("What quiz would you like to take?");
        String quiz = scan.nextLine();

    }

}
