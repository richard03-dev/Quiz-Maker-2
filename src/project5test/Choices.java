package project5test;

import java.io.Serializable;

/**
 * Choices
 * <p>
 * This program creates a choice object.
 *
 * @author Richard Silvester,
 * Aaron Neman,
 * Varun Rao lab sec O01
 * @version April 11, 2022
 */

public class Choices implements Serializable {
    private static final long serialVersionUID = 1L;

    private String letter;
    private String text;
    private boolean correct;

    public Choices(String letter, String text, boolean correct) {
        this.letter = letter;
        this.text = text;
        this.correct = correct;
    }

    public Choices(String letter, String text) {
        this.letter = letter;
        this.text = text;
    }

    public String getLetter() {
        return this.letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean isCorrect() {
        return this.correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String toString() {
        return this.letter + ") " + this.text;
    }


}