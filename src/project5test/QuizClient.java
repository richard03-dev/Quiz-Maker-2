package project5test;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class QuizClient {
	
	private static String filename;
    private static BufferedReader reader;
    private static PrintWriter writer;
    private static ObjectOutputStream objectWriter;
    private static ObjectInputStream objectReader;
    private static Login login;


    public static void main (String[] main) throws IOException, ClassNotFoundException {
        String hostName = "localhost";
        int port = 4242;
        boolean checker = true;

        while (checker) {
            
            //Ask for host name and for a port and set these variables to them
            hostName = JOptionPane.showInputDialog(null, "Enter the host name",
                    "Login", JOptionPane.QUESTION_MESSAGE);
            String portNum = JOptionPane.showInputDialog(null, "Enter the port number",
                    "Login", JOptionPane.QUESTION_MESSAGE);
            port = Integer.parseInt(portNum);
            
            try {
                Socket s = new Socket(hostName, port);
                checker = false;

                reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
                writer = new PrintWriter(s.getOutputStream());
                writer.flush();

                objectWriter = new ObjectOutputStream(s.getOutputStream());
                objectWriter.flush();

                objectReader = new ObjectInputStream(s.getInputStream());

                login = new Login(writer, objectReader, getUserAndPass());
                HashMap<String, String> grades = new HashMap<String, String>();

                do {
                	pullLogin();
                    Person person = runLogin();
                    ArrayList<Quiz> quizList = new ArrayList<Quiz>();
                    //TEACHER
                                    
                    if (person != null) {
                        if (person instanceof Teacher) {
                    		int teacherInput = 0;
                    		boolean check = true;
                    		while (check) {
                    			quizList = getQuizList();
                    			teacherInput = -1;
                    			while (teacherInput < 0) {
                        			String[] options1 = {"Create Quiz", "Edit a Quiz", "Logout", "Delete Account"};
                                    teacherInput = JOptionPane.showOptionDialog(null, "Welcome Teacher", "Teacher",
            						                                          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
            						                                          null, options1, options1[3]);
                                    if (teacherInput < 0) {
                                    	JOptionPane.showMessageDialog(null,
                                                "Please select a choice", "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                        		}
                                if (teacherInput == 0) {
                                	boolean checker1 = true;
                                	int numQuiz = 0;
                                	while (checker1) {
                                		try {
                                			numQuiz = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                    "How Many Quizzes Would you like to create?", JOptionPane.QUESTION_MESSAGE));
                                			if (numQuiz == 0) {
                                				JOptionPane.showMessageDialog(null,
                                                        "Please enter a number greater than 0", "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                			} else {
                                				checker1 = false;
                                			}
                                		} catch (NullPointerException e) {
                                			JOptionPane.showMessageDialog(null,
                                                    "Please enter a number", "Error",
                                                    JOptionPane.ERROR_MESSAGE);
                                		}
                                	}
                                	Quiz[] quizzes = new Quiz[numQuiz];
                                	for (int i = 0; i < numQuiz; i++) {
                                		int numChoice = 0;
                                	   	String quiz = "";
                                    	checker = true;
                                    	while (checker) {
                                    		quiz = JOptionPane.showInputDialog(null,
                                                    "Please enter the quiz name",
                                                    JOptionPane.QUESTION_MESSAGE);
                                    		try {
                                    			if (quiz.isEmpty() || quiz.isBlank() || quiz == null) {
                                        			JOptionPane.showMessageDialog(null,
                                                            "Please enter a valid name.", "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                                        			checker = true;
                                        		} else {
                                        			checker = false;
                                        		}
                                    		} catch (NullPointerException e) {
                                    			JOptionPane.showMessageDialog(null,
                                                        "Please enter a name.", "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                    		}
                                    	}
                                    	checker1 = true;
                                    	int numQuest = 0;
                                    	while (checker1) {
                                    		try {
                                    			numQuest = Integer.parseInt(JOptionPane.showInputDialog(null,
                                    					"Please enter the number of Questions ", JOptionPane.QUESTION_MESSAGE));
                                    			if (numQuiz == 0) {
                                    				JOptionPane.showMessageDialog(null,
                                                            "Please enter a number greater than 0", "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                                    			} else {
                                    				checker1 = false;
                                    			}
                                    		} catch (NullPointerException e) {
                                    			JOptionPane.showMessageDialog(null,
                                                        "Please enter a number", "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                    		}
                                    	}
                                    	Question[] questions = new Question[numQuest];
                                    	for (int j = 0; j < numQuest; j++) {
                                    		numChoice = 0;
                                    		checker1 = true;
                                        	while (checker1) {
                                        		try {
                                        			numChoice = Integer.parseInt(JOptionPane.showInputDialog(null,
                                        					"Please enter the number of multiple choice answers for Question " + (j + 1), JOptionPane.QUESTION_MESSAGE));
                                        			if (numQuiz == 0) {
                                        				JOptionPane.showMessageDialog(null,
                                                                "Please enter a number greater than 0", "Error",
                                                                JOptionPane.ERROR_MESSAGE);
                                        			} else {
                                        				checker1 = false;
                                        			}
                                        		} catch (NullPointerException e) {
                                        			JOptionPane.showMessageDialog(null,
                                                            "Please enter a number", "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                                        		}
                                        	}
                                        	Choices[] choices = new Choices[numChoice];
                                        	String question = "";
                                        	checker = true;
                                        	while (checker) {
                                        		question = JOptionPane.showInputDialog(null,
                                                        "Please enter the question",
                                                        JOptionPane.QUESTION_MESSAGE);
                                        		try {
                                        			if (question.isEmpty() || question.isBlank() || question == null) {
                                            			JOptionPane.showMessageDialog(null,
                                                                "Please enter a valid name.", "Error",
                                                                JOptionPane.ERROR_MESSAGE);
                                            			checker = true;
                                            		} else {
                                            			checker = false;
                                            		}
                                        		} catch (NullPointerException e) {
                                        			JOptionPane.showMessageDialog(null,
                                                            "Please enter a name.", "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                                        		}
                                        	}
                                        	for (int k = 0; k < numChoice; k++) {
                                        		String letter = "";
                                        		checker = true;
                                        		while (checker) {
                                            		letter = JOptionPane.showInputDialog(null,
                                                            "Please enter the letter of the prompt for choice " + (k + 1),
                                                            JOptionPane.QUESTION_MESSAGE);
                                            		try {
                                            			if (question.isEmpty() || question.isBlank() || question == null) {
                                                			JOptionPane.showMessageDialog(null,
                                                                    "Please enter a letter.", "Error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                			checker = true;
                                                		} else {
                                                			checker = false;
                                                		}
                                            		} catch (NullPointerException e) {
                                            			JOptionPane.showMessageDialog(null,
                                                                "Please enter a letter.", "Error",
                                                                JOptionPane.ERROR_MESSAGE);
                                            		}
                                            	}
                                        		String prompt = "";
                                        		checker = true;
                                        		while (checker) {
                                            		prompt = JOptionPane.showInputDialog(null,
                                                            "Please enter the multiple choice prompt for choice " + (k + 1),
                                                            JOptionPane.QUESTION_MESSAGE);
                                            		try {
                                            			if (prompt.isEmpty() || prompt.isBlank() || prompt == null) {
                                                			JOptionPane.showMessageDialog(null,
                                                                    "Please enter a prompt.", "Error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                			checker = true;
                                                		} else {
                                                			checker = false;
                                                		}
                                            		} catch (NullPointerException e) {
                                            			JOptionPane.showMessageDialog(null,
                                                                "Please enter a prompt.", "Error",
                                                                JOptionPane.ERROR_MESSAGE);
                                            		}
                                            	}
                                        		teacherInput = -1;
                                                while (teacherInput == -1) {
                                                    String[] options2 = {"Yes", "No"};
                                                    teacherInput = JOptionPane.showOptionDialog(null, "Is this answer correct?", "Teacher",
                            						                                          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                            						                                          null, options2, options2[1]);
                                                    if (teacherInput != 0 && teacherInput != 1) {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Please enter Yes or No", "Error",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                }
                                                boolean answer = false;
                                                if (teacherInput == 0) {
                                                    answer = true;
                                                }
                                                choices[k] = new Choices(letter, prompt, answer);
                                        	}
                                        	questions[j] = new Question(question, choices);
                                    	}
                                    	quizzes[i] = new Quiz(quiz, numQuest, numChoice, questions);
                                	}
                                	for (int n = 0; n < quizzes.length; n++) {
                                		quizList.add(quizzes[n]);
                                	}
                                	pushQuizs(quizList);
                                } else if (teacherInput == 1) {
                                	String editResponse = "";
                                    int editR = 0;
                                    quizList = getQuizList();
                                    do {
                                    	ArrayList<Quiz> quizzes = getQuizList();
                                    	String quizInput = "What quiz would you like to edit?";
            							for (int i = 0; i < quizzes.size(); i++) {
            								quizInput += "\n" + (i + 1) + " " + quizzes.get(i).getQuizNum();
            							}
            							int quizNum = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                quizInput,
                                                JOptionPane.QUESTION_MESSAGE));
            		    				String nameQuiz = quizzes.get(quizNum - 1).getQuizNum();
            		    				for (int i = 0; i < quizzes.size(); i++) {
            		    					if (quizzes.get(i).getQuizNum().equals(nameQuiz)) {
            		    						int userS = -1;
                                                while (userS == -1) {
                                                	String[] options2 = {"Quiz Name", "Question", "Multiple Choice Responses", "Quit"};
                    								userS = JOptionPane.showOptionDialog(null, "Which part of the exam would you like to change?", "Teacher",
                    								                                          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    								                                          null, options2, options2[2]);
                                                    if (userS < 0 || userS > 3) {
                                                        JOptionPane.showMessageDialog(null,
                                                                "Please enter a choice.", "Error",
                                                                JOptionPane.ERROR_MESSAGE);
                                                    }
                                                }
                                                if (userS == 0) {
                                                	String newName = JOptionPane.showInputDialog(null,
                                                            "New name for the quiz: ",
                                                            JOptionPane.QUESTION_MESSAGE);
                                                    quizzes.get(i).setQuizNum(newName);
                                                } else if (userS == 1) {
                                                	Question[] finalQuiz = quizzes.get(i).getQuizzes();
                                                    int questionNum = 0;
                                                    while (questionNum == 0) {
                                                        questionNum = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                                "What question number do you want to change: ",
                                                                JOptionPane.QUESTION_MESSAGE));

                                                        if (questionNum == 0 || questionNum > finalQuiz.length) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Please enter a valid question number", "Error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }

                                                    }
                                                    String newResponse = (JOptionPane.showInputDialog(null,
                                                            "What do you want to change the question to",
                                                            JOptionPane.QUESTION_MESSAGE));
                                                    finalQuiz[questionNum - 1].setQuestionText(newResponse);
                                                } else if (userS == 2) {
                                                	Question[] finalQuiz = quizzes.get(i).getQuizzes();
                                                    int questionNum = 0;
                                                    while (questionNum == 0 || questionNum > finalQuiz.length) {
                                                        System.out.println("What question number do you want to change: ");
                                                        questionNum = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                                "What question number do you want to change: ",
                                                                JOptionPane.QUESTION_MESSAGE));
                                                        if (questionNum == 0 || questionNum > finalQuiz.length) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Please enter a valid question number", "Error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }
                                                    }
                                                    Choices[] choices = finalQuiz[questionNum - 1].getText();
                                                    int mcqOption = 0;
                                                    while (mcqOption == 0 || mcqOption > choices.length) {
                                                        mcqOption = Integer.parseInt(JOptionPane.showInputDialog(null,
                                                                "WWhat multiple choice option would you like to change",
                                                                JOptionPane.QUESTION_MESSAGE));

                                                        if (mcqOption == 0 || mcqOption > choices.length) {
                                                            JOptionPane.showMessageDialog(null,
                                                                    "Please enter a valid question number", "Error",
                                                                    JOptionPane.ERROR_MESSAGE);
                                                        }

                                                    }

                                                    System.out.println("What would you like the new response to be");
                                                    String newResponse = (JOptionPane.showInputDialog(null,
                                                            "What would you like the new response to be",
                                                            JOptionPane.QUESTION_MESSAGE));
                                                    choices[mcqOption - 1].setText(newResponse);
                                                } else if (userS == 3) {
                                                	break;
                                                } else {
                                                	JOptionPane.showMessageDialog(null,
                                                            "Please enter a valid quiz", "Error",
                                                            JOptionPane.ERROR_MESSAGE);
                                                }
                                               
                                                   
                                                  
                                                }
            		    						
            		    				}
            		    				pushQuizs(quizList);
            		    				int c = 0;
                                        while (c == 0) {
                                        	String[] options2 = {"Yes", "No"};
            								editR = JOptionPane.showOptionDialog(null, "Would you like to edit another quiz?", "Teacher",
            								                                          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
            								                                          null, options2, options2[1]);
                                           
                                            if (!(editR == 0 || editR == 1)) {
                                                JOptionPane.showMessageDialog(null,
                                                        "Please enter Yes or No", "Error",
                                                        JOptionPane.ERROR_MESSAGE);
                                            } else {
                                                c = 1;
                                            }
                                        }

                                    } while (editR == 0);
                                } else if (teacherInput == 2) {
                                	break;
                                } else if (teacherInput == 3) {
                                	if (deleteAccount(person))
                                        break;
                                } 
                                
                    		}
                    		
                       } else if (person instanceof Student) {
                    		int finalInput = 0;
                    		do {
                    			ArrayList<Quiz> totalQuiz = getQuizList();
        						String[] options1 = {"Take Quiz", "Logout", "Delete Account"};
        						int studentInput = JOptionPane.showOptionDialog(null, "Welcome Student", "Student",
        						                                          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
        						                                          null, options1, options1[2]);
        						if(studentInput == 0) {
        							String quizInput = "What quiz would you like to take?";
        							for (int i = 0; i < totalQuiz.size(); i++) {
        								quizInput += "\n" + (i + 1) + " " + totalQuiz.get(i).getQuizNum();
        							}
        							int quizNum = Integer.parseInt(JOptionPane.showInputDialog(null,
                                            quizInput,
                                            JOptionPane.QUESTION_MESSAGE));
        		    				String quiz = totalQuiz.get(quizNum - 1).getQuizNum();
        		    				ArrayList<Choices> studentResponses = new ArrayList<Choices>(); 
        		    				for (int i = 0; i < totalQuiz.size(); i++) {
        		    					if (totalQuiz.get(i).getQuizNum().equals(quiz)) {
        		    						studentResponses = totalQuiz.get(i).quizList();
        		    						JOptionPane.showMessageDialog(null, totalQuiz.get(i).toString(studentResponses), "Student", 
        		    	        	                JOptionPane.INFORMATION_MESSAGE);
        		    						String grade = totalQuiz.get(i).teacherToString(studentResponses);
        		    						grades.put(person.getName(), grade);
        		    						JOptionPane.showMessageDialog(null, "Quiz Done!", "Student", 
                		    		                JOptionPane.INFORMATION_MESSAGE);
        		    						break;
        		    					}
        		    				}
        		    				finalInput = 0;
        		    				while (finalInput == -1) {
        		    					String[] options2 = {"Yes", "No"};
        								finalInput = JOptionPane.showOptionDialog(null, "Would you like to take another quiz", "Student",
        								                                          JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
        								                                          null, options2, options2[1]);								
        			            	}
        			            } else if (studentInput == 1) {
        			            	break;
        			            } else if (studentInput == 2) {
        			            	if (deleteAccount(person))
                                        break;
        			            } else {
        			            	JOptionPane.showMessageDialog(null, "Please select an answer", "Student", 
        		    		                JOptionPane.ERROR_MESSAGE);
        			            }
        					} while (finalInput == 0);
                        }
                    }  else {
                    	break;
                    }
        		} while (true);
        	} catch (NullPointerException e) {
            	JOptionPane.showMessageDialog(null, "Please enter the correct user name and password.", "Login", 
    	                JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
            	e.printStackTrace();

            }
         }
    }
    public static ArrayList<Quiz> getQuizList() throws IOException, ClassNotFoundException {
        writer.write("QuizList");
        writer.println();
        writer.flush();
        try {
            return (ArrayList<Quiz>) objectReader.readObject();
        } catch (Exception e) {
            return null;
        }
    }
    public static HashMap<String, Person> getUserAndPass() throws IOException, ClassNotFoundException {
        writer.write("UserAndPass");
        writer.println();
        writer.flush();
        try {
            return ((HashMap<String, Person>) objectReader.readObject());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static void pullLogin() throws IOException, ClassNotFoundException {
        try {
            login.setUserAndPass(getUserAndPass());
        } catch (Exception e) {

        }
    }
    public static void pushLogin() throws IOException, ClassNotFoundException {
        writer.write("PushUP");
        writer.println();
        writer.flush();

        objectWriter.writeObject(login.getUserAndPass());
        objectWriter.flush();
    }
    public static void pushQuizs(ArrayList<Quiz> quizs) throws IOException, ClassNotFoundException {
        writer.write("PushQ");
        writer.println();
        writer.flush();

        objectWriter.writeObject(quizs);
        objectWriter.flush();
    }
    public static Person runLogin() throws IOException, ClassNotFoundException {

        int choice;
        do {
            String[] options2 = {"Login", "Signup", "Quit"};
            choice = JOptionPane.showOptionDialog(null, "Welcome!", "Login",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, options2, options2[1]);

            if (choice == 0) {
                return login.loginMenu();
            } else if (choice == 1) {
                login.signupMenu();
                pushLogin();
            } else if (choice == 2) {
                return null;
            } else {
                JOptionPane.showMessageDialog(null, "Please select an answer", "Login",
                        JOptionPane.ERROR_MESSAGE);
            }

        } while (true);
    }

    public static boolean deleteAccount(Person person) throws IOException, ClassNotFoundException {
        while (true) {
            String[] options3 = {"Yes", "No"};
            int deleteAccount = JOptionPane.showOptionDialog(null, "Are you sure you want to delete your account?", "Student",
                                                      JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                                      null, options3, options3[1]);
            if (deleteAccount == 0) {
                JOptionPane.showMessageDialog(null,
                        "Deleting Account...", "Error",
                        JOptionPane.OK_OPTION);
                try {
                    login.getUserAndPass().remove(person.getName());
                } catch (Exception e) { }
                pushLogin();
                return true;
            } else if (deleteAccount == 1) {
                return false;
            } else {
                JOptionPane.showMessageDialog(null,
                        "Please enter a valid answer.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}