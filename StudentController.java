package tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentController {
    private final List<Student> students;

    public StudentController() {
        this.students = new ArrayList<>();
    }

    public String addStudent(String credentials) {
        String[] credentialsParts = credentials.split(" ");

        String studentFirstName = this.getStudentFirstName(credentialsParts[0]);
        if (studentFirstName == null) {
            return "Incorrect first name.";
        }

        String studentLastName = this.extractLastName(credentialsParts);
        if (!this.isLastNameValid(studentLastName)) {
            return "Incorrect last name.";
        }

        String email = credentialsParts[credentialsParts.length - 1];
        if (!this.isEmailValid(email)) {
            return "Incorrect email.";
        }

        for (var student : this.students) {
            if (student.getEmail().equals(email)) {
                return "This email is already taken.";
            }
        }

        int uniqueId = this.getStudentUniqueId();

        this.students.add(new Student(studentFirstName, studentLastName, email, uniqueId));
        return "The student has been added.";
    }

    private int getStudentUniqueId() {
        if (this.students.isEmpty()) {
            return 10000;
        }

        return this.students.get(this.students.size() - 1).getUniqueId() + 1;
    }

    private String getStudentFirstName(String credential) {
        if (!this.isFirstNameValid(credential)) {
            return null;
        }

        return credential;
    }

    private boolean isFirstNameValid(String firstName) {
        if (firstName.length() < 2) {
            return false;
        }

        if (!this.isFormatValid(firstName)) {
            return false;
        }

        if (firstName.charAt(0) == '-'
                || firstName.charAt(0) == '\''
                || firstName.charAt(firstName.length() - 1) == '-'
                || firstName.charAt(firstName.length() - 1) == '\''
        ) {
            return false;
        }

        return !this.hasAdjacentCharacters(firstName);
    }

    private boolean isFormatValid(String word) {
        String regex = "^[A-Za-z' -]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(word);

        return matcher.find();
    }

    private boolean hasAdjacentCharacters(String word) {
        String regex = "[-']{2}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(word);

        return matcher.find();
    }

    private String extractLastName(String[] parts) {
        StringBuilder lastNameBuilder = new StringBuilder();
        for (int i = 1; i < parts.length - 1; i++) {
            if (i != 1) {
                lastNameBuilder.append(" ");
            }
            lastNameBuilder.append(parts[i]);
        }
        return lastNameBuilder.toString();
    }

    private boolean isLastNameValid(String lastName) {
        if (lastName.length() < 2) {
            return false;
        }

        if (!this.isFormatValid(lastName)) {
            return false;
        }

        if (lastName.charAt(0) == '-'
                || lastName.charAt(0) == '\''
                || lastName.charAt(lastName.length() - 1) == '-'
                || lastName.charAt(lastName.length() - 1) == '\''
        ) {
            return false;
        }

        if (this.hasAdjacentCharacters(lastName)) {
            return false;
        }

        return true;
    }

    private boolean isEmailValid(String email) {
        String emailRegex = "^[^@]+@[^@]+\\.[A-Za-z0-9]+$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        return emailPattern.matcher(email).matches();
    }

    public int studentsListSize() {
        return this.students.size();
    }

    public List<Integer> listStudentsUniqueIds() {
        if (this.students.isEmpty()) {
            return new ArrayList<>();
        }

        List<Integer> studentsUniqueIds = new ArrayList<>();
        for (var student : this.students) {
            studentsUniqueIds.add(student.getUniqueId());
        }

        return studentsUniqueIds;
    }

    public String addStudentPoints(String studentPoints) {
        String[] studentPointsParts = studentPoints.split(" ");
        if (studentPointsParts.length != 5) {
            return "Incorrect points format.";
        }

        for (int i = 1; i < studentPointsParts.length; i++) {
            try {
                if (Integer.parseInt(studentPointsParts[i]) < 0) {
                    return "Incorrect points format.";
                }
            } catch (NumberFormatException e) {
                return "Incorrect points format.";
            }
        }

        try {
            int studentUniqueId = Integer.parseInt(studentPointsParts[0]);
            for (var student : this.students) {
                if (student.getUniqueId() == studentUniqueId) {
                    return student.addPoints(studentPointsParts);
                }
            }
        } catch (NumberFormatException e) {
            return "No student is found for id=" + studentPointsParts[0] + ".";
        }

        return "No student is found for id=" + studentPointsParts[0] + ".";
    }

    public String displayStudentInfo(int studentUniqueId) {
        for (var student : this.students) {
            if (student.getUniqueId() == studentUniqueId) {
                return studentUniqueId + " points: " + student.displayCoursePoints();
            }
        }

        return "No student is found for id=" + studentUniqueId + ".";
    }

    public void displayCourseStudentsStatistics(String courseName) {
        String firstLetter = courseName.substring(0, 1).toUpperCase();
        String formattedCourseName = firstLetter + courseName.substring(1).toLowerCase();

        if (formattedCourseName.equalsIgnoreCase("dsa")) {
            formattedCourseName = formattedCourseName.toUpperCase();
        }

        List<String> studentsStatistics = Statistics.statisticsForCourse(this.students, formattedCourseName);

        System.out.println(formattedCourseName);
        System.out.println(String.format("%-8s%9s%12s", "id", "points", "completed"));
        for (var studentStatistics : studentsStatistics) {
            String[] studentStatisticsParts = studentStatistics.split(":");

            if (studentStatisticsParts[1].equalsIgnoreCase("0")) {
                continue;
            }

            String studentInfo = String.format("%-8s%8s%12s",
                    studentStatisticsParts[0],
                    studentStatisticsParts[1],
                    studentStatisticsParts[2]);

            System.out.println(studentInfo);
        }
    }

    public Map<String, String> getCoursesStatistics() {
        return Statistics.getCoursesStatistics(this.students);
    }

    public void notifyStudentForCompletion() {
        Map<Student, List<String>> listOfStudentsToBeNotified = new HashMap<>();

        for (var student : this.students) {
            for (var studentCourses : student.getStudentCoursesPoints().entrySet()) {
                String courseName = studentCourses.getKey();
                int coursePoints = studentCourses.getValue();

                if (courseName.equalsIgnoreCase("Java")
                        && coursePoints >= Statistics.javaCoursePointsToComplete) {
                    this.addCourseToList(listOfStudentsToBeNotified, student, "Java");
                }

                if (courseName.equalsIgnoreCase("DSA")
                        && coursePoints >= Statistics.dsaCoursePointsToComplete) {
                    this.addCourseToList(listOfStudentsToBeNotified, student, "DSA");
                }

                if (courseName.equalsIgnoreCase("Databases")
                        && coursePoints >= Statistics.databasesCoursePointsToComplete) {
                    this.addCourseToList(listOfStudentsToBeNotified, student, "Databases");
                }

                if (courseName.equalsIgnoreCase("Spring")
                        && coursePoints >= Statistics.springCoursePointsToComplete) {
                    this.addCourseToList(listOfStudentsToBeNotified, student, "Spring");
                }
            }
        }

        for (var studentToNotify : listOfStudentsToBeNotified.entrySet()) {
            Student student = studentToNotify.getKey();
            List<String> completedCourses = studentToNotify.getValue();

            for (String course : completedCourses) {
                System.out.println("To: " + student.getEmail());
                System.out.println("Re: Your Learning Progress");
                System.out.printf("Hello, %s %s! You have accomplished our %s course!\n",
                        student.getFirstName(),
                        student.getLastName(),
                        course
                );

                student.resetCoursePoints(course);
            }
        }

        System.out.printf("Total %d students have been notified.\n",
                listOfStudentsToBeNotified.size()
        );
    }

    private void addCourseToList(Map<Student, List<String>> map, Student student, String courseName) {
        List<String> courses = map.getOrDefault(student, new ArrayList<>());
        courses.add(courseName);
        map.put(student, courses);
    }
}