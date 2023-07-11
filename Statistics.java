package tracker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Statistics {
    public final static int javaCoursePointsToComplete = 600;
    public final static int dsaCoursePointsToComplete = 400;
    public final static int databasesCoursePointsToComplete = 480;
    public final static int springCoursePointsToComplete = 550;

    public static List<String> statisticsForCourse(List<Student> students, String courseName) {
        List<String> studentsForThisCourse = new ArrayList<>();

        for (var student : students) {
            int coursePoints = student.getCoursePoints(courseName);
            String completionPercentage = Statistics.completionPercentage(coursePoints, courseName);

            String studentInfo = student.getUniqueId() +
                    ":" +
                    coursePoints +
                    ":" +
                    completionPercentage;
            studentsForThisCourse.add(studentInfo);
        }

        studentsForThisCourse.sort(new PointsComparator());
        return studentsForThisCourse;
    }

    private static String completionPercentage(int coursePoints, String courseName) {
        int totalCompletionTotal;
        switch (courseName.toLowerCase()) {
            case "java":
                totalCompletionTotal = Statistics.javaCoursePointsToComplete;
                break;
            case "dsa":
                totalCompletionTotal = Statistics.dsaCoursePointsToComplete;
                break;
            case "databases":
                totalCompletionTotal = Statistics.databasesCoursePointsToComplete;
                break;
            case "spring":
                totalCompletionTotal = Statistics.springCoursePointsToComplete;
                break;
            default:
                totalCompletionTotal = 100;
                break;
        }

        double percentage = (coursePoints * 100.0) / totalCompletionTotal;
        return String.format("%.1f", percentage) + "%";
    }

    public static Map<String, String> getCoursesStatistics(List<Student> students) {
        Map<String, String> coursesStatistics = new HashMap<>();
        coursesStatistics.put("mostPopular", "n/a");
        coursesStatistics.put("leastPopular", "n/a");
        coursesStatistics.put("highestActivity", "n/a");
        coursesStatistics.put("lowestActivity", "n/a");
        coursesStatistics.put("easiestCourse", "n/a");
        coursesStatistics.put("hardestCourse", "n/a");

        if (students.isEmpty()) {
            return coursesStatistics;
        }

        String mostPopular = Statistics.getTop(Statistics.getCoursesPopularityData(students));
        String leastPopular = Statistics.getBot(Statistics.getCoursesPopularityData(students), mostPopular);
        String highestActivity = Statistics.getTop(Statistics.getCoursesActivityData(students));
        String lowestActivity = Statistics.getBot(Statistics.getCoursesActivityData(students), highestActivity);
        String easiestCourse = Statistics.getTop(Statistics.getCoursesDifficultyData(students));
        String hardestCourse = Statistics.getBot(Statistics.getCoursesDifficultyData(students), easiestCourse);

        coursesStatistics.put("mostPopular", mostPopular);
        coursesStatistics.put("leastPopular", leastPopular);
        coursesStatistics.put("highestActivity", highestActivity);
        coursesStatistics.put("lowestActivity", lowestActivity);
        coursesStatistics.put("easiestCourse", easiestCourse);
        coursesStatistics.put("hardestCourse", hardestCourse);

        return coursesStatistics;
    }


    private static String getTop(Map<String, Integer> data) {
        List<String> courses = new ArrayList<>();
        int maxValue = Integer.MIN_VALUE;

        for (var entry : data.entrySet()) {
            int value = entry.getValue();
            if (value > maxValue) {
                maxValue = value;
                courses.clear(); // Clear previous courses
            }
            if (value == maxValue) {
                courses.add(entry.getKey());
            }
        }

        if (courses.isEmpty()) {
            return "n/a";
        }

        return String.join(", ", courses);
    }

    private static String getBot(Map<String, Integer> data, String mostPopular) {
        List<String> courses = new ArrayList<>();
        int minValue = Integer.MAX_VALUE;

        for (var entry : data.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            if (value < minValue) {
                minValue = value;
                courses.clear(); // Clear previous courses
            }
            if (value == minValue && !mostPopular.contains(key)) {
                courses.add(key);
            }
        }

        if (courses.isEmpty()) {
            return "n/a";
        }

        return String.join(", ", courses);
    }

    private static Map<String, Integer> getCoursesPopularityData(List<Student> students) {
        Map<String, Integer> coursesData = new HashMap<>();

        int studentsEnrolledJava = 0;
        int studentsEnrolledDSA = 0;
        int studentsEnrolledDatabases = 0;
        int studentsEnrolledSpring = 0;

        for (var student : students) {
            if (student.getCoursePoints("Java") > 0) {
                studentsEnrolledJava++;
            }

            if (student.getCoursePoints("DSA") > 0) {
                studentsEnrolledDSA++;
            }

            if (student.getCoursePoints("Databases") > 0) {
                studentsEnrolledDatabases++;
            }

            if (student.getCoursePoints("Spring") > 0) {
                studentsEnrolledSpring++;
            }
        }

        coursesData.put("Java", studentsEnrolledJava);
        coursesData.put("DSA", studentsEnrolledDSA);
        coursesData.put("Databases", studentsEnrolledDatabases);
        coursesData.put("Spring", studentsEnrolledSpring);

        return coursesData;
    }

    private static Map<String, Integer> getCoursesActivityData(List<Student> students) {
        Map<String, Integer> coursesData = new HashMap<>();

        int completedTasksJava = 0;
        int completedTasksDSA = 0;
        int completedTasksDatabases = 0;
        int completedTasksSpring = 0;

        for (var student : students) {
            if (student.getCoursePoints("Java") > 0) {
                completedTasksJava++;
            }

            if (student.getCoursePoints("DSA") > 0) {
                completedTasksDSA++;
            }

            if (student.getCoursePoints("Databases") > 0) {
                completedTasksDatabases++;
            }

            if (student.getCoursePoints("Spring") > 0) {
                completedTasksSpring++;
            }
        }

        coursesData.put("Java", completedTasksJava);
        coursesData.put("DSA", completedTasksDSA);
        coursesData.put("Databases", completedTasksDatabases);
        coursesData.put("Spring", completedTasksSpring);

        return coursesData;
    }

    private static Map<String, Integer> getCoursesDifficultyData(List<Student> students) {
        Map<String, Integer> coursesData = new HashMap<>();

        int studentsPointsJava = 0;
        int studentsPointsDSA = 0;
        int studentsPointsDatabases = 0;
        int studentsPointsSpring = 0;

        int studentsEnrolledJava = 0;
        int studentsEnrolledDSA = 0;
        int studentsEnrolledDatabases = 0;
        int studentsEnrolledSpring = 0;

        for (var student : students) {
            if (student.getCoursePoints("Java") > 0) {
                studentsPointsJava += student.getCoursePoints("Java");
                studentsEnrolledJava++;
            }

            if (student.getCoursePoints("DSA") > 0) {
                studentsPointsDSA += student.getCoursePoints("DSA");
                studentsEnrolledDSA++;
            }

            if (student.getCoursePoints("Databases") > 0) {
                studentsPointsDatabases += student.getCoursePoints("Databases");
                studentsEnrolledDatabases++;
            }

            if (student.getCoursePoints("Spring") > 0) {
                studentsPointsSpring += student.getCoursePoints("Spring");
                studentsEnrolledSpring++;
            }
        }

        double javaAverage = (double) studentsPointsJava / studentsEnrolledJava;
        double dsaAverage = (double) studentsPointsDSA / studentsEnrolledDSA;
        double databasesAverage = (double) studentsPointsDatabases / studentsEnrolledDatabases;
        double springAverage = (double) studentsPointsSpring / studentsEnrolledSpring;

        coursesData.put("Java", (int) javaAverage);
        coursesData.put("DSA", (int) dsaAverage);
        coursesData.put("Databases", (int) databasesAverage);
        coursesData.put("Spring", (int) springAverage);

        return coursesData;
    }
}
