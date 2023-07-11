package tracker;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student {
    private final int uniqueId;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final Map<String, Integer> coursePoints;

    public Student(String firstName, String lastName, String email, int uniqueId) {
        this.uniqueId = uniqueId;

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        this.coursePoints = new HashMap<>();
        this.addDefaultCoursePoints();
    }

    private void addDefaultCoursePoints() {
        this.coursePoints.put("Java", 0);
        this.coursePoints.put("DSA", 0);
        this.coursePoints.put("Databases", 0);
        this.coursePoints.put("Spring", 0);
    }

    public String getEmail() {
        return this.email;
    }

    public int getUniqueId() {
        return this.uniqueId;
    }

    public int getCoursePoints(String courseName) {
        return this.coursePoints.get(courseName);
    }

    public void resetCoursePoints(String courseName) {
        this.coursePoints.put(courseName, 0);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public Map<String, Integer> getStudentCoursesPoints() {
        return this.coursePoints;
    }

    public String displayCoursePoints() {
        StringBuilder coursesInfo = new StringBuilder();
        for (var course : this.coursePoints.entrySet()) {
            String courseName = course.getKey();
            int coursePoints = course.getValue();

            coursesInfo.append(courseName).append("=").append(coursePoints);
            if (!courseName.equals("Spring")) {
                coursesInfo.append("; ");
            }
        }

        return coursesInfo.toString();
    }

    public String addPoints(String[] points) {
        this.coursePoints.put("Java", this.coursePoints.get("Java") + Integer.parseInt(points[1]));
        this.coursePoints.put("DSA", this.coursePoints.get("DSA") + Integer.parseInt(points[2]));
        this.coursePoints.put("Databases", this.coursePoints.get("Databases") + Integer.parseInt(points[3]));
        this.coursePoints.put("Spring", this.coursePoints.get("Spring") + Integer.parseInt(points[4]));

        return "Points updated.";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return uniqueId == student.uniqueId && Objects.equals(email, student.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueId, email);
    }
}
