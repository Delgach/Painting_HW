package com.example.vladimir.painting_hw;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button buttonClear;
    private Button buttonPen;
    private Button buttonRect;
    private PaintingView paintingView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonClear = (Button) findViewById(R.id.button_clear);
        buttonPen = (Button) findViewById(R.id.pen);
        buttonRect = (Button) findViewById(R.id.rect);
        paintingView = (PaintingView) findViewById(R.id.painting_view);
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintingView.clear();
            }
        });
        buttonPen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintingView.bePen();
            }
        });
        buttonRect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paintingView.beRect();
            }
        });

    }
}
