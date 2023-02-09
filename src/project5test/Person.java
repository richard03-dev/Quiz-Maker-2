package project5test;

import java.io.Serializable;

/**
 * Person
 * <p>
 * This program creates the person object
 *
 * @author Richard Silvester,
 * Aaron Neman,
 * Varun Rao lab sec O01
 * @version April 11, 2022
 */

public class Person implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private String password;

    public Person(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toFile() {
        if (this instanceof Teacher)
            return getName() + "," + getPassword() + ",y";
        return getName() + "," + getPassword() + ",n";
    }

}
