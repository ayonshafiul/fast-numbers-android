package com.ayon.fastnumbers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {




    private Button start;
    private Button reset;
    private Button playerOneLeft;
    private Button playerOneRight;
    private Button playerTwoLeft;
    private Button playerTwoRight;

    private TextView timer;
    private TextView playerOnePoints;
    private TextView playerTwoPoints;

    private Game g;

    private int gDuration;
    private int gType;
    private boolean interrupted;

    public static final String SHARED_PREFS = "SHARED_PREF";
    public static final String SINGLE_HIGH_SCORE = "SINGLE_HIGH_SCORE";
    public static final String DOUBLE_HIGH_SCORE = "DOUBLE_HIGH_SCORE";
    public static final String DURATION = "SECONDS";
    public static final String GAME_TYPE = "GAME_TYPE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFields();
        createNewGame();

        View.OnClickListener startButtonListener  = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                g.setIsGameRunning(true);
                showOnlyTimer();
                startTimer();
            }
        };
        start.setOnClickListener(startButtonListener);

        View.OnClickListener resetButtonListener  = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewGame();
            }
        };
        reset.setOnClickListener(resetButtonListener);

        View.OnClickListener gameButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (g.getIsGameRunning()) {
                    int id = view.getId();
                    if (id == R.id.player1_left || id == R.id.player1_right ) {
                        if ( id == R.id.player1_left ) {
                            g.getP1().setCurrentChoice(Player.CHOICE_LEFT);
                        } else {
                            g.getP1().setCurrentChoice(Player.CHOICE_RIGHT);
                        }
                        g.handleCurrentMove(g.getP1());
                        g.prepareNextMove(g.getP1());
                        displayCurrentState(g.getP1());
                    } else if (id == R.id.player2_left || id == R.id.player2_right ) {
                        if ( id == R.id.player2_left ) {
                            g.getP2().setCurrentChoice(Player.CHOICE_LEFT);
                        } else {
                            g.getP2().setCurrentChoice(Player.CHOICE_RIGHT);
                        }
                        g.handleCurrentMove(g.getP2());
                        g.prepareNextMove(g.getP2());
                        displayCurrentState(g.getP2());
                    }
                }
            }
        };
        playerOneLeft.setOnClickListener(gameButtonClickListener);
        playerOneRight.setOnClickListener(gameButtonClickListener);
        playerTwoLeft.setOnClickListener(gameButtonClickListener);
        playerTwoRight.setOnClickListener(gameButtonClickListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        createNewGame();
    }

    @Override
    protected void onPause() {
        super.onPause();
        interrupted = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.settings:
                Intent i = new Intent(this, SettingsActivity.class);
                startActivity(i);
                return true;
            case R.id.help:
                Intent i1 = new Intent(this, HelpActivity.class);
                startActivity(i1);
                return true;
            case R.id.high_score:
                Intent i2 = new Intent(this, HighScoreActivity.class);
                startActivity(i2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void displayCurrentGameState() {
        displayCurrentState(g.getP1());
        displayCurrentState(g.getP2());
    }

    private void startTimer() {
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if ( gDuration > 0 && !interrupted) {
                    int minutes = gDuration / 60;
                    int secs = gDuration % 60;
                    String timerStr = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
                    timer.setText(timerStr);
                    gDuration--;
                    handler.postDelayed(this,1000);
                } else {
                    g.setIsGameRunning(false);
                    showOnlyReset();
                    if (!interrupted) Toast.makeText(MainActivity.this,g.decideWinner(),Toast.LENGTH_SHORT).show();
                    updateHighScore();
                }
            }
        });
    }

    private void initFields() {
        start = (Button) findViewById(R.id.button_start);
        reset = (Button) findViewById(R.id.button_reset);
        playerOneLeft = (Button) findViewById(R.id.player1_left);
        playerOneRight = (Button) findViewById(R.id.player1_right);
        playerTwoLeft = (Button) findViewById(R.id.player2_left);
        playerTwoRight = (Button) findViewById(R.id.player2_right);
        timer = (TextView) findViewById(R.id.text_timer);
        playerOnePoints = (TextView) findViewById(R.id.player1_points);
        playerTwoPoints = (TextView) findViewById(R.id.player2_points);
    }

    private void displayCurrentState(Player p) {
        if ( p == g.getP1() ) {
            playerOneLeft.setText(String.valueOf(g.getP1().getLeftNumber()));
            playerOneRight.setText(String.valueOf(g.getP1().getRightNumber()));
            playerOnePoints.setText(String.valueOf(g.getP1().getPoints()));
        } else {
            playerTwoLeft.setText(String.valueOf(g.getP2().getLeftNumber()));
            playerTwoRight.setText(String.valueOf(g.getP2().getRightNumber()));
            playerTwoPoints.setText(String.valueOf(g.getP2().getPoints()));
        }

    }

    private void showOnlyStart() {
        start.setVisibility(View.VISIBLE);
        timer.setVisibility(View.GONE);
        reset.setVisibility(View.GONE);
    }
    private void showOnlyReset() {
        start.setVisibility(View.GONE);
        timer.setVisibility(View.GONE);
        reset.setVisibility(View.VISIBLE);
    }
    private void showOnlyTimer() {
        start.setVisibility(View.GONE);
        timer.setVisibility(View.VISIBLE);
        reset.setVisibility(View.GONE);
    }

    private void loadSettings() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        gType = sharedPreferences.getInt(GAME_TYPE,Game.GREATER_THAN);
        gDuration = sharedPreferences.getInt(DURATION, 20);
    }

    private void updateHighScore() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (g.getP1().getPoints() + g.getP2().getPoints() > sharedPreferences.getInt(DOUBLE_HIGH_SCORE, 0)) {
            editor.putInt(DOUBLE_HIGH_SCORE, g.getP1().getPoints() + g.getP2().getPoints());
            Toast.makeText(MainActivity.this, "DOUBLE HIGH SCORE ACHIEVED!",Toast.LENGTH_SHORT).show();
        }
        if (g.getP1().getPoints() > sharedPreferences.getInt(SINGLE_HIGH_SCORE, 0) ) {
            editor.putInt(SINGLE_HIGH_SCORE,g.getP1().getPoints());
            Toast.makeText(MainActivity.this, "SINGLE HIGH SCORE ACHIEVED BY PLAYER 1!",Toast.LENGTH_SHORT).show();
        }
        if (g.getP2().getPoints() > sharedPreferences.getInt(SINGLE_HIGH_SCORE, 0) ) {
            editor.putInt(SINGLE_HIGH_SCORE, g.getP2().getPoints());
            Toast.makeText(MainActivity.this, "SINGLE HIGH SCORE ACHIEVED BY PLAYER 2!",Toast.LENGTH_SHORT).show();
        }
        editor.apply();
    }

    private void createNewGame() {
        showOnlyStart();
        loadSettings();
        g = new Game(gType);
        displayCurrentGameState();
        interrupted = false;
    }
}
