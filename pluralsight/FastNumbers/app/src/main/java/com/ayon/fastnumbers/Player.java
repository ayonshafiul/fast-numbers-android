package com.ayon.fastnumbers;

public class Player {
    private int points;
    private ListQueue ls;
    private int leftButtonID;
    private int rightButtonID;
    private int leftNumber;
    private int rightNumber;
    private int currentChoice;
    public static final int CHOICE_LEFT = 1;
    public static final int CHOICE_RIGHT = 2;

    public Player(int leftButtonID, int rightButtonID) {
        this.leftButtonID = leftButtonID;
        this.rightButtonID = rightButtonID;
        this.points = 0;
        this.ls = new ListQueue();
    }

    public int getCurrentChoice() {
        return currentChoice;
    }

    public void setCurrentChoice(int currentChoice) {
        this.currentChoice = currentChoice;
    }

    public int getLeftNumber() {
        return leftNumber;
    }

    public void setLeftNumber(int leftNumber) {
        this.leftNumber = leftNumber;
    }

    public int getRightNumber() {
        return rightNumber;
    }

    public void setRightNumber(int rightNumber) {
        this.rightNumber = rightNumber;
    }

    public int getPoints() {
        return points;
    }

    public int getLeftButtonID() {
        return leftButtonID;
    }

    public int getRightButtonID() {
        return rightButtonID;
    }

    public ListQueue getLs() {
        return ls;
    }

    public void setLs(ListQueue ls) {
        this.ls = ls;
    }

    public void pullNextNumbers() {
        try {
            leftNumber = ls.dequeue();
            rightNumber = ls.dequeue();
        } catch ( Exception e) {
            System.out.println(e.getMessage());
        }

    }
    public void increasePoints() {
        this.points++;
    }

    public void decreasePoints() {
        this.points -= 3;
    }
}
