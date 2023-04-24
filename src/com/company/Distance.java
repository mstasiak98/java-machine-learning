package com.company;

public class Distance implements Comparable<Distance>{

    private int distance;
    private int idxOfTeachingObject;

    public Distance(int distance, int idxOfTeachingObject) {
        this.distance = distance;
        this.idxOfTeachingObject = idxOfTeachingObject;
    }

    public int getDistance() {
        return distance;
    }

    public int getIdxOfTeachingObject() {
        return idxOfTeachingObject;
    }

    @Override
    public String toString() {
        return "odl = " + distance + ", idx = " + idxOfTeachingObject;
    }

    @Override
    public int compareTo(Distance o) {
        return Integer.compare(getDistance(), o.getDistance());
    }
}
