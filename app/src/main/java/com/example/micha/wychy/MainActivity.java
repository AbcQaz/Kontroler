package com.example.micha.wychy;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;


public class MainActivity extends ActionBarActivity implements View.OnTouchListener
{
    static int bitsPerSecond = 2400;
    ImageView ball;
    ImageView grid;

    boolean moving = false;
    float x, y = 0.0f;

    TextView longitude;
    TextView latitude;

    float leftBound = 0;
    float rightBound = 0;
    float topBound = 0;
    float bottomBound = 0;

    float rangeX;
    float rangeY;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ball = (ImageView) findViewById(R.id.ball);
        ball.setOnTouchListener(this);
        grid = (ImageView) findViewById(R.id.grid);
        longitude = (TextView) findViewById(R.id.longitude);
        latitude = (TextView) findViewById(R.id.latitude);
    }

    protected void setGridBounds()
    {
        leftBound = grid.getX() + ball.getWidth() / 2;
        rightBound = grid.getX() + grid.getWidth() - ball.getWidth() / 2;
        bottomBound = grid.getY() + grid.getHeight() + ball.getHeight();
        topBound = grid.getY() + ball.getWidth() * 2;
    }

    protected boolean isOnAxisX(float f)
    {
        if(f < rightBound && f > leftBound)
            return true;
        return false;
    }

    protected boolean isOnAxisY(float f)
    {
        if(f < bottomBound && f > topBound)
            return true;
        return false;
    }

    protected void dragToSpecificPoints()
    {
        float sticker = 0.12f;
        float x = (ball.getX() + ball.getWidth() / 2 - leftBound - rangeX / 2) / (rangeX / 2);
        float y = -(ball.getY() + ball.getHeight() * 2 - topBound - rangeY / 2) / (rangeY / 2);
        if(x + sticker > 1)
            ball.setX(rightBound - ball.getWidth() / 2);
        if(x - sticker < -1)
            ball.setX(leftBound - ball.getWidth() / 2);
        if(y + sticker > 1)
            ball.setY(topBound - ball.getHeight() * 2);
        if(y - sticker < -1)
            ball.setY(bottomBound - ball.getHeight() * 2);
        if(Math.abs(y) < sticker && Math.abs(x) < sticker)
        {
            ball.setX(grid.getX() + (grid.getWidth() - ball.getWidth()) / 2);
            ball.setY(grid.getY() + (grid.getHeight() - ball.getHeight()) / 2);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void optionsOpener(View v)
    {
        startActivity(new Intent(getApplicationContext(), Options.class));
    }

    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        if(rightBound == 0)
        {
            setGridBounds();
            rangeX = rightBound - leftBound;
            rangeY = bottomBound - topBound;
        }

        switch(motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
            {
                moving = true;
                break;
            }

            case MotionEvent.ACTION_UP:
            {
                moving = false;
                break;
            }

            case MotionEvent.ACTION_MOVE:
            {
                if(moving && isOnAxisX(motionEvent.getRawX()))
                {
                    x = motionEvent.getRawX() - ball.getWidth() / 2;
                    ball.setX(x);
                }

                if(moving && isOnAxisY(motionEvent.getRawY()))
                {
                    y = motionEvent.getRawY() - ball.getHeight() * 2;
                    ball.setY(y);
                }

                TextView warning = (TextView) findViewById(R.id.warning);
                if(!isOnAxisX(motionEvent.getRawX()) && !isOnAxisY(motionEvent.getRawY()))
                {
                    warning.setText("Kaj leziesz cholero?");
                }
                else
                    warning.setText("");

                dragToSpecificPoints();
                float valueX = (ball.getX() + ball.getWidth() / 2 - leftBound - rangeX / 2) / (rangeX / 2);
                float valueY = -(ball.getY() + ball.getHeight() * 2 - topBound - rangeY / 2) / (rangeY / 2);
                DecimalFormat d = new DecimalFormat("#.##");
                longitude.setText("X = " + String.valueOf(d.format(valueX)));
                latitude.setText("Y = " + String.valueOf(d.format(valueY)));
                break;
            }
        }
        return true;
    }

    /*private class DragListener implements View.OnDragListener
    {
        @Override
        public boolean onDrag(View v, DragEvent event)
        {
            switch(event.getAction())
            {
                case DragEvent.ACTION_DRAG_STARTED:
                {

                }
            }
            return false;
        }
    }*/
}

































