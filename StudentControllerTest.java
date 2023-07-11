package tracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StudentControllerTest {
    static StudentController studentController;

    @BeforeEach
    void setUp() {
        studentController = new StudentController();
    }

    @Test
    void addStudentWithIncorrectFirstName() {
        String input = "3John Doe jdoe@yahoo.com";
        String response = studentController.addStudent(input);
        String expectedResponse = "Incorrect first name.";

        assertEquals(expectedResponse, response);
    }

    @Test
    void addStudentWithIncorrectLastName() {
        String input = "John Do''e jdoe@yahoo.com";
        String response = studentController.addStudent(input);
        String expectedResponse = "Incorrect last name.";

        assertEquals(expectedResponse, response);
    }

    @Test
    void addStudentWithIncorrectEmail() {
        String input = "John Doe jdoe@yahoocom";
        String response = studentController.addStudent(input);
        String expectedResponse = "Incorrect email.";

        assertEquals(expectedResponse, response);
    }

    @Test
    void addStudentWithDuplicatedEmail() {
        String input = "John Doe jdoe@yahoo.com";
        studentController.addStudent(input);

        String response = studentController.addStudent(input);
        String expectedResponse = "This email is already taken.";

        assertEquals(expectedResponse, response);
    }

    @Test
    void addStudentWithValidCredentials() {
        String input = "John Doe jdoe@yahoo.com";
        String response = studentController.addStudent(input);
        String expectedResponse = "The student has been added.";

        assertEquals(expectedResponse, response);
    }

    @Test
    void studentsListSize() {
        int response = studentController.studentsListSize();

        assertEquals(0, response);

        String input = "John Doe jdoe@yahoo.com";
        studentController.addStudent(input);

        int response2 = studentController.studentsListSize();

        assertEquals(1, response2);
    }

    @Test
    void listStudentsUniqueIds() {
        List<Integer> response = studentController.listStudentsUniqueIds();

        assertEquals(0, response.size());

        String input = "John Doe jdoe@yahoo.com";
        studentController.addStudent(input);

        List<Integer> response2 = studentController.listStudentsUniqueIds();

        // 10000 is the first student uniqueId
        assertEquals(10000, response2.get(0));
    }

    @Test
    void addStudentPointsWithStudentNotFound() {
        String studentsPoints = "123 5 5 5 5";
        String response = studentController.addStudentPoints(studentsPoints);
        String expectedResponse = "No student is found for id=123.";

        assertEquals(expectedResponse, response);
    }

    @Test
    void addStudentPointsWithInvalidFormatMultipleValues() {
        String studentsPoints = "123 5 5 5 5 7";
        String response = studentController.addStudentPoints(studentsPoints);
        String expectedResponse = "Incorrect points format.";

        assertEquals(expectedResponse, response);
    }

    @Test
    void addStudentPointsWithInvalidFormatZeroOrNegativeValues() {
        String studentsPoints = "123 -3 0 5 5";
        String response = studentController.addStudentPoints(studentsPoints);
        String expectedResponse = "Incorrect points format.";

        assertEquals(expectedResponse, response);
    }

    @Test
    void addStudentPointsWithInvalidFormatValuesAreNotNumbers() {
        String studentsPoints = "123 abc 2 5 5";
        String response = studentController.addStudentPoints(studentsPoints);
        String expectedResponse = "Incorrect points format.";

        assertEquals(expectedResponse, response);
    }

    @Test
    void addStudentPointsWithValidData() {
        String input = "John Doe jdoe@yahoo.com";
        studentController.addStudent(input);

        String studentsPoints = "10000 7 2 5 5";
        String response = studentController.addStudentPoints(studentsPoints);
        String expectedResponse = "Points updated.";

        assertEquals(expectedResponse, response);
    }

    @Test
    void displayStudentInfo() {
        String input = "John Doe jdoe@yahoo.com";
        studentController.addStudent(input);

        String response = studentController.displayStudentInfo(10000);
        String expectedResponse = "10000 points: Java=0; DSA=0; Databases=0; Spring=0";

        assertEquals(expectedResponse, response);
    }

    @Test
    void displayStudentInfoForNotFound() {
        String response = studentController.displayStudentInfo(10000);
        String expectedResponse = "No student is found for id=10000.";

        assertEquals(expectedResponse, response);
    }

    @Test
    void getCoursesStatisticsWithAnEmptyList() {
        Map<String, String> response = studentController.getCoursesStatistics();

        boolean allEmpty = false;
        for (var statistic : response.entrySet()) {
            if (statistic.getValue().equalsIgnoreCase("n/a")) {
                allEmpty = true;
            }
        }

        assertTrue(allEmpty);
    }

    @Test
    void getCoursesStatisticsWithOneStudent() {
        String input = "John Doe jdoe@yahoo.com";
        studentController.addStudent(input);

        String studentsPoints = "10000 7 2 5 5";
        String response = studentController.addStudentPoints(studentsPoints);
        String expectedResponse = "Points updated.";

        assertEquals(expectedResponse, response);

        Map<String, String> response2 = studentController.getCoursesStatistics();

        boolean allEmpty = false;
        for (var statistic : response2.entrySet()) {
            if (statistic.getValue().equalsIgnoreCase("n/a")) {
                allEmpty = true;
            }
        }

        assertTrue(allEmpty);
    }
}