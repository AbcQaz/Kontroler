package com.example.micha.wychy;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.TextView;


public class Options extends ActionBarActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);
        writeBitsPerSecond();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_options, menu);
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

    public void thatLittleFuckerWhoBringsYouBack(View v)
    {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }

    public void radioEventHandler(View v)
    {
        try
        {
            switch (v.getId())
            {
                case R.id.low:
                {
                    MainActivity.bitsPerSecond = 2400;
                    break;
                }

                case R.id.mid:
                {
                    MainActivity.bitsPerSecond = 4800;
                    break;
                }

                case R.id.hi:
                {
                    MainActivity.bitsPerSecond = 7200;
                    break;
                }
            }
            writeBitsPerSecond();
        }
        catch(Exception e)
        {
            e.printStackTrace();//Log.d("GRZYBI TUTAJ",);
        }
    }
    public void writeBitsPerSecond()
    {
        TextView textView = (TextView) findViewById(R.id.textViewBitRate);
        textView.setText(Integer.toString(MainActivity.bitsPerSecond));
    }
}
