package tracker;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private final StudentController studentController;

    public UserInterface(Scanner scanner) {
        this.scanner = scanner;
        this.studentController = new StudentController();
    }

    public void boot() {
        System.out.println("Learning Progress Tracker");
        while (true) {
            String input = this.scanner.nextLine();
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Bye!");
                break;
            }

            if (input.equalsIgnoreCase("back")) {
                System.out.println("Enter 'exit' to exit the program.");
                continue;
            }

            if (input.isBlank()) {
                System.out.println("no input");
                continue;
            }

            switch (input.toLowerCase()) {
                case "add students":
                    this.addStudents();
                    break;
                case "add points":
                    this.addPointsToStudent();
                    break;
                case "list":
                    this.listStudents();
                    break;
                case "find":
                    this.findStudent();
                    break;
                case "statistics":
                    this.displayStatistics();
                    break;
                case "notify":
                    this.studentController.notifyStudentForCompletion();
                    break;
                default:
                    System.out.println("Unknown command.");
                    break;
            }
        }
    }

    private void displayStatistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        this.displayAllStatistics();

        while (true) {
            String userInput = this.scanner.nextLine();
            if (userInput.equalsIgnoreCase("back")) {
                break;
            }

            switch (userInput.toLowerCase()) {
                case "java":
                case "dsa":
                case "databases":
                case "spring":
                    this.studentController.displayCourseStudentsStatistics(userInput.toLowerCase());
                    break;
                default:
                    System.out.println("Unknown course.");
                    break;
            }
        }
    }

    private void displayAllStatistics() {
        System.out.println("Most popular: " + this.studentController.getCoursesStatistics().get("mostPopular"));
        System.out.println("Least popular: " + this.studentController.getCoursesStatistics().get("leastPopular"));
        System.out.println("Highest activity: " + this.studentController.getCoursesStatistics().get("highestActivity"));
        System.out.println("Lowest activity: " + this.studentController.getCoursesStatistics().get("lowestActivity"));
        System.out.println("Easiest course: " + this.studentController.getCoursesStatistics().get("easiestCourse"));
        System.out.println("Hardest course: " + this.studentController.getCoursesStatistics().get("hardestCourse"));
    }

    private void addPointsToStudent() {
        System.out.println("Enter an id and points or 'back' to return:");
        while (true) {
            String userInput = this.scanner.nextLine();
            if (userInput.equalsIgnoreCase("back")) {
                break;
            }

            System.out.println(this.studentController.addStudentPoints(userInput));
        }
    }

    private void findStudent() {
        System.out.println("Enter an id or 'back' to return:");
        while (true) {
            String userInput = this.scanner.nextLine();
            if (userInput.equalsIgnoreCase("back")) {
                break;
            }

            try {
                int uniqueId = Integer.parseInt(userInput);
                System.out.println(this.studentController.displayStudentInfo(uniqueId));
            } catch (NumberFormatException e) {
                System.out.println("Please provide a valid student id.");
            }
        }
    }

    private void addStudents() {
        System.out.println("Enter student credentials or 'back' to return:");
        while (true) {
            String userInput = this.scanner.nextLine();
            if (userInput.equalsIgnoreCase("back")) {
                System.out.println("Total " + this.studentController.studentsListSize() + " students have been added.");
                break;
            }

            String[] userInputParts = userInput.split(" ");
            if (userInputParts.length < 3) {
                System.out.println("Incorrect credentials.");
                continue;
            }

            System.out.println(this.studentController.addStudent(userInput));
        }
    }

    private void listStudents() {
        List<Integer> studentsUniqueIds = this.studentController.listStudentsUniqueIds();
        if (studentsUniqueIds.isEmpty()) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("Students:");
        for (var studentUniqueId : studentsUniqueIds) {
            System.out.println(studentUniqueId);
        }
    }
}
