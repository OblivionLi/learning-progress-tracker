package tracker;

import java.util.Comparator;

class PointsComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        int points1 = this.getPoints(o1);
        int points2 = this.getPoints(o2);

        if (points1 != points2) {
            return Integer.compare(points2, points1);
        } else {
            int uniqueId1 = this.getUniqueId(o1);
            int uniqueId2 = this.getUniqueId(o2);
            return Integer.compare(uniqueId1, uniqueId2);
        }
    }

    private int getPoints(String student) {
        String[] parts = student.split(":");
        return Integer.parseInt(parts[1]);
    }

    private int getUniqueId(String student) {
        String[] parts = student.split(":");
        return Integer.parseInt(parts[0]);
    }
}
