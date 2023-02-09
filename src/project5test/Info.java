package project5test;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;

/**
 * Info
 * <p>
 * This program stores all of our data
 * from the user.
 *
 * @author Richard Silvester,
 * Aaron Neman,
 * Varun Rao lab sec O01
 * @version April 11, 2022
 */

public class Info implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static ArrayList<Person> persons = new ArrayList<Person>();
    private static HashMap<String, Person> userAndPass;
    private static ArrayList<Quiz> totalQuiz = new ArrayList<>();


    public static ArrayList<Person> getPersons() {
        return persons;
    }

    public static void addPerson(Person person) {
        persons.add(person);
    }

    public static HashMap<String, Person> getUserAndPass() {
        return userAndPass;
    }

    public static void setUserAndPass(HashMap<String, Person> map) {
        userAndPass = map;
    }

    public static void setTotalQuiz(ArrayList<Quiz> quizs) {
        totalQuiz = quizs;
    }

    public static ArrayList<Quiz> getTotalQuiz() {
        return totalQuiz;
    }
}
