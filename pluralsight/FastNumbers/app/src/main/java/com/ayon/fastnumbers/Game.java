package com.ayon.fastnumbers;

import java.util.Random;

public class Game {
    private Player p1;
    private Player p2;
    private int gameType;
    private boolean isGameRunning;
    public static final int GREATER_THAN = 0;
    public static final int LESS_THAN = 1;

    public Game(int gameType) {
        p1 = new Player(R.id.player1_left, R.id.player1_right);
        p2 = new Player(R.id.player2_left, R.id.player2_right);
        if ( gameType == Game.GREATER_THAN || gameType == Game.LESS_THAN ) {
            this.gameType = gameType;
        }
        populateQueue(5);
        prepareNextMove(p1);
        prepareNextMove(p2);
    }


    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public void handleCurrentMove(Player p) {
        if (isCurrentChoiceCorrect(p) ) {
            p.increasePoints();
        } else {
            p.decreasePoints();
        }
    }
    public void prepareNextMove(Player p) {
        if (p.getLs().isAlmostEmpty()) {
            populateQueue(5);
        }
        p.pullNextNumbers();
    }
    public String decideWinner() {
        String str = "";
        if ( p1.getPoints() > p2.getPoints() ) {
            str = "The Winner is Player 1";
        } else if ( p1.getPoints() < p2.getPoints() ) {
            str = "The Winner is Player 2";
        } else {
            str = "Tie";
        }
        return str;
    }
    private boolean isCurrentChoiceCorrect(Player p) {
        boolean isCorrect = false;

        if (gameType == Game.GREATER_THAN ) {
            if ( p.getCurrentChoice() == Player.CHOICE_LEFT ) {
                if ( p.getLeftNumber() > p.getRightNumber() ) {
                    isCorrect = true;
                }
            } else if (p.getCurrentChoice() == Player.CHOICE_RIGHT )  {
                if ( p.getRightNumber() > p.getLeftNumber() ) {
                    isCorrect = true;
                }
            }
        } else if (gameType == Game.LESS_THAN) {
            if ( p.getCurrentChoice() == Player.CHOICE_LEFT ) {
                if ( p.getLeftNumber() < p.getRightNumber() ) {
                    isCorrect = true;
                }
            } else if (p.getCurrentChoice() == Player.CHOICE_RIGHT )  {
                if ( p.getRightNumber() < p.getLeftNumber() ) {
                    isCorrect = true;
                }
            }
        }

        return isCorrect;
    }

    private void populateQueue(int count) {
        ListQueue ls1 = p1.getLs();
        ListQueue ls2 = p2.getLs();
        for ( int i = 0; i < count; i++ ) {
            int left = generateRandomNumber();
            int right = generateRandomNumber();
            if ( left == right ) {
                ++left;
            }
            ls1.enqueue(left);
            ls1.enqueue(right);
            ls2.enqueue(left);
            ls2.enqueue(right);
        }
    }

    private int generateRandomNumber() {
        return new Random().nextInt(19) - 9;
    }

    public boolean getIsGameRunning() {
        return isGameRunning;
    }

    public void setIsGameRunning(boolean isGameRunning) {
        this.isGameRunning = isGameRunning;
    }
}
