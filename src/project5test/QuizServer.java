package project5test;

import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class QuizServer {
    private static String passFileName = "Logins.txt";
    private static String quizFileName = "Quizs.txt";
    private static Object obj = new Object();

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException {

        readPassFile();
        ServerSocket serverSocket = new ServerSocket(4242);
        do {
            Socket s = serverSocket.accept();
            Runnable client = new Runnable() {
                @Override
                public void run() {
                    try {
                        clientRequests(s);
                    } catch (IOException e) {
                        System.out.println("Client Disconnected");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            new Thread(client).start();
        } while (true);
    }

    public static void clientRequests(Socket s) throws IOException, ClassNotFoundException {
        System.out.println("Client Connected");

        BufferedReader reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter writer = new PrintWriter(s.getOutputStream());

        ObjectOutputStream objectWriter = new ObjectOutputStream(s.getOutputStream());
        objectWriter.flush();

        ObjectInputStream objectReader = new ObjectInputStream(s.getInputStream());

        do {
            readPassFile();
            String request = reader.readLine().trim().replaceAll("[^a-zA-Z0-9]", "");

            System.out.println("Recived: " + request);

            if (request != null) {
                //Request QuizList
                if (request.contains("QuizList")) {
                    objectWriter.writeObject(Info.getTotalQuiz());
                    objectWriter.flush();
                    System.out.println("Sent QuizList");
                    //Request UserAndPass
                } else if (request.contains("UserAndPass")) {
                    objectWriter.writeObject(Info.getUserAndPass());
                    objectWriter.flush();
                    System.out.println("Sent UserAndPass");
                } else if (request.contains("WriteQuiz")) {
                    writeQuizFile();
                } else if (request.contains("WritePass")) {
                    writePassFile();
                } else if (request.contains("PushUP")) {

                    try {
                        synchronized (obj) {
                            Info.setUserAndPass((HashMap<String, Person>) objectReader.readObject());
                        }
                        System.out.println("Read UserAndPass");
                        writePassFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("An Error occoured. Could not update logins");
                    }
                } else if (request.contains("PushQ")) {

                    try {
                        synchronized (obj) {
                            Info.setTotalQuiz((ArrayList<Quiz>) objectReader.readObject());
                        }
                        System.out.println("Read QuizList");
                        writeQuizFile();
                    } catch (Exception e) {
                        System.out.println("An Error occoured. Could not update Quizs");
                    }
                }
            }
        } while (true);
    }

    public static synchronized void writePassFile() throws IOException {

        try {
            // Creates a BufferedWriter
            BufferedWriter output = new BufferedWriter(new FileWriter(passFileName));

            for (String name : Info.getUserAndPass().keySet()) {
                output.write(Info.getUserAndPass().get(name).toFile() + "\n");
            }
            output.flush();
            output.close();

            System.out.println("Wrote Login File with file name: " + passFileName);
        } catch (Exception e) {
            System.out.println("Error Writing File");
        }
    }

    public static synchronized void readPassFile() throws IOException {
        try {
            Path userAndPassFile = Path.of(passFileName);
            String[] userAndPassArray = Files.readString(userAndPassFile).replace("\r", "").split("\n");

            HashMap<String, Person> userAndPass = new HashMap<String, Person>();

            for (int j = 0; j < userAndPassArray.length; j++) {
                String[] temp = userAndPassArray[j].trim().split(",");
                if (temp[2].equalsIgnoreCase("y")) {
                    Teacher tempTeach = new Teacher(temp[0], temp[1]);
                    userAndPass.put(temp[0], tempTeach);
                } else if (temp[2].equalsIgnoreCase("n")) {
                    Student tempTeach = new Student(temp[0], temp[1]);
                    userAndPass.put(temp[0], tempTeach);
                }
            }
            Info.setUserAndPass(userAndPass);
            System.out.println("Read Login File with file name: " + passFileName);
        } catch (IndexOutOfBoundsException e) {
            Info.setUserAndPass(new HashMap<String, Person>());
            writePassFile();
        } catch (Exception e) {
            System.out.println("No File found. Creating new file with file name: " + passFileName);
            Info.setUserAndPass(new HashMap<String, Person>());
            writePassFile();
        }
    }

    public static void writeQuizFile() throws IOException {

        // Creates a BufferedWriter
        BufferedWriter output = new BufferedWriter(new FileWriter(quizFileName));

        for (Quiz quiz : Info.getTotalQuiz()) {
            output.write(quiz.toString() + "\n---\n");
        }
        output.flush();
        output.close();
    }
}