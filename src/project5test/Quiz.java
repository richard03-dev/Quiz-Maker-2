package project5test;

import java.util.*;
import javax.swing.JOptionPane;
import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * Quiz
 * <p>
 * This program creates the quiz object
 * where the the grading is also done.
 *
 * @author Richard Silvester,
 * Aaron Neman,
 * Varun Rao lab sec O01
 * @version April 11, 2022
 */


public class Quiz implements Serializable {
    private static final long serialVersionUID = 1L;
    String quizNum;
    int numQuestions;
    int mcqOption;
    Question[] quizzes;
    private static HashMap<String, Person> grades;
    String timeStamp;
    Random randomizer = new Random();

    public Quiz(String quizNum, int numQuestions, int mcqOption, Question[] quizzes) {
        this.quizNum = quizNum;
        this.numQuestions = numQuestions;
        this.mcqOption = mcqOption;
        this.quizzes = quizzes;

    }

    public String getQuizNum() {
        return quizNum;
    }

    public void setQuizNum(String quizNum) {
        this.quizNum = quizNum;
    }

    public int getNumQuestions() {
        return numQuestions;
    }

    public void setNumQuestions(int numQuestions) {
        this.numQuestions = numQuestions;
    }

    public int getMcqOption() {
        return mcqOption;
    }

    public void setMcqOption(int mcqOption) {
        this.mcqOption = mcqOption;
    }

    public Question[] getQuizzes() {
        return quizzes;
    }

    public void setQuestion(Question[] quizzes) {
        this.quizzes = quizzes;
    }

    public ArrayList<Choices> quizList() {
        Question[] finalQuiz = getQuizzes();
        Collections.shuffle(Arrays.asList(finalQuiz));
        ArrayList<Choices> responses = new ArrayList<Choices>();
        for (int i = 0; i < finalQuiz.length; i++) {
            

            boolean checker = true;

            while (checker) {
                String quiz = "Please select an answer\nQuestion: \n" + finalQuiz[i].toString();
                String response = JOptionPane.showInputDialog(null, quiz,
                        "Student", JOptionPane.QUESTION_MESSAGE);
                int counter = 0;
                for (int j = 0; j < finalQuiz[i].getText().length; j++) {
                    if (finalQuiz[i].getText()[j].getLetter().equalsIgnoreCase(response)) {
                        responses.add(finalQuiz[i].getText()[j]);
                    } else {
                        counter++;
                    }
                }
                if (finalQuiz[i].getText().length == counter || response == null) {
                    JOptionPane.showMessageDialog(null, "Enter a valid answer choice:", "Student",
                            JOptionPane.ERROR_MESSAGE);
                    checker = true;
                } else {
                    checker = false;
                }
            }


        }
        return responses;
    }

    public String toString(ArrayList<Choices> studentResponses) {
        String output = "";
        int counter = 0;
        int total = studentResponses.size();
        for (int i = 0; i < studentResponses.size(); i++) {
            if (studentResponses.get(i).isCorrect()) {
                counter++;
            }
        }
        double answer = ((counter * 1.0) / (total * 1.0)) * 100;
        double answer2 = Math.round(answer);
        int score = (int) answer2;
        output += "You got " + counter + "/" + total + " correct\n";
        output += "Your score is " + score + "%!\n";
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("E, MMM dd yyyy HH:mm:ss");
        String formattedDate = myDateObj.format(myFormatObj);
        output += formattedDate;
        timeStamp = formattedDate;
        return output;
    }

    public String teacherToString(ArrayList<Choices> studentResponses) {
        String output = "";
        int counter = 0;
        int total = studentResponses.size();
        for (int i = 0; i < studentResponses.size(); i++) {
            if (studentResponses.get(i).isCorrect()) {
                counter++;
            }
        }
        double answer = ((counter * 1.0) / (total * 1.0)) * 100;
        double answer2 = Math.round(answer);
        int score = (int) answer2;
        output += "Score: " + counter + "/" + total + " = " + score + "%!\n" + timeStamp;
        return output;
    }

}