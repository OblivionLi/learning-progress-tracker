# learning progress tracker

#### This application monitors students' progress in specific courses such as Java, DSA, Spring and others. It keeps track of individual students' points and notifies them when they achieve the required points for completing a course. Upon reaching the point milestone, students will receive a certificate as recognition of their accomplishment.
#### In addition, the application provides comprehensive statistics about the courses, including information on the most and least popular courses, the most challenging and easiest courses, and more. It also offers individual course statistics, displaying the number of students enrolled in each course, their respective points, and the percentage of course completion.
---
##### Example (`>` is user input):
```
Learning Progress Tracker
> add students
Enter student credentials or 'back' to return:
> John Doe johnd@email.net
The student has been added.
> Jane Spark jspark@yahoo.com
The student has been added.
> back
Total 2 students have been added.
> list
Students:
10000
10001
> add points
Enter an id and points or 'back' to return:
> 10000 600 400 0 0
Points updated.
> back
> notify
To: johnd@email.net
Re: Your Learning Progress
Hello, John Doe! You have accomplished our Java course!
To: johnd@email.net
Re: Your Learning Progress
Hello, John Doe! You have accomplished our DSA course!
Total 1 students have been notified.
> notify
Total 0 students have been notified.
> exit
Bye!
```
---
##### Example 2 (`>` is user input):
```
Learning Progress Tracker
> add students
Enter student credentials or 'back' to return:
> John Doe johnd@email.net
The student has been added.
> Jane Spark jspark@yahoo.com
The student has been added.
> back
Total 2 students have been added.
> list
Students:
10000
10001
> add points
Enter an id and points or 'back' to return:
> 10000 8 7 7 5
Points updated.
> 10000 7 6 9 7
Points updated.
> 10000 6 5 5 0
Points updated.
> 10001 8 0 8 6
Points updated.
> 10001 7 0 0 0
Points updated.
> 10001 9 0 0 5
Points updated.
> back
> statistics
Type the name of a course to see details or 'back' to quit:
Most popular: Java, Databases, Spring
Least popular: DSA
Highest activity: Java
Lowest activity: DSA
Easiest course: Java
Hardest course: Spring
> java
Java
id    points    completed
10001 24        4.0%
10000 21        3.5%
> dsa
DSA
id    points    completed
10000 18        4.5%
> databases
Databases
id    points    completed
10000 21        4.4%
10001 8	        1.7%
> spring
Spring
id    points    completed
10000 12        2.2%
10001 11        2.0%
> back
> exit
Bye!
```
