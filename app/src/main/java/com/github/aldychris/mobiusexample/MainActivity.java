package com.github.aldychris.mobiusexample;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.spotify.mobius.Mobius;
import com.spotify.mobius.MobiusLoop;

public class MainActivity extends AppCompatActivity {

    private TextView tvNumber;

    private MobiusLoop<Integer, CounterEvent, CounterEffect> loop;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNumber = findViewById(R.id.tvCounter);

        loop = Mobius.loop(CounterLogic::updateWithEffect, CounterEffectHandler::effectHandler)
                .startFrom(2);
        loop.observe(counter -> {
            tvNumber.setText(counter.toString());
        });

    }

    public void btnPlusClicked(View view){
        loop.dispatchEvent(CounterEvent.UP);
    }

    public void btnMinClicked(View view){
        loop.dispatchEvent(CounterEvent.DOWN);
    }

    @Override
    protected void onStop() {
        loop.dispose();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        loop.dispose();
        super.onDestroy();
    }
}
