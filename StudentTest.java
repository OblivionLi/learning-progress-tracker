package tracker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
    private static Student student;

    @BeforeEach
    void setUp() {
        student = new Student("Liviu", "Andrei", "liviuandrei@email.com", 123);
    }

    @Test
    void getEmail() {
        String studentEmail = student.getEmail();
        String expectedEmail = "liviuandrei@email.com";

        assertEquals(expectedEmail, studentEmail);
    }

    @Test
    void getUniqueId() {
        int studentUniqueId = student.getUniqueId();
        int expectedUniqueId = 123;

        assertEquals(expectedUniqueId, studentUniqueId);
    }

    @Test
    void displayCoursePoints() {
        String studentCoursesPoints = student.displayCoursePoints();
        String expectedCoursesPoints = "Java=0; DSA=0; Databases=0; Spring=0";

        assertEquals(expectedCoursesPoints, studentCoursesPoints);
    }

    @Test
    void addPoints() {
        // format for adding points is `uniqueId, JavaPoints, DSAPoints, DatabasesPoints, SpringPoints`
        String[] points = new String[]{"123", "3", "6", "23", "100"};
        String responseWhenAddingPoints = student.addPoints(points);
        String expectedResponse = "Points updated.";

        assertEquals(expectedResponse, responseWhenAddingPoints);

        String studentCoursesPoints = student.displayCoursePoints();
        String expectedCoursesPoints = "Java=3; DSA=6; Databases=23; Spring=100";

        assertEquals(expectedCoursesPoints, studentCoursesPoints);
    }
}