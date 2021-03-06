package com.ashomok.eNumbers.activities.ocr_task;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.ashomok.eNumbers.R;

/**
 * Created by Iuliia on 13.12.2015.
 */
public class OCRAnimationActivity extends AppCompatActivity {

    private String imageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ocr_animation_layout);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            imageUri = bundle.getString("image");
        }

        Button cancel = findViewById(R.id.cancel_btn);
        cancel.setOnClickListener(v -> {

            setResult(RESULT_CANCELED);
            finish();
        });

        OCRAnimationView ocrAnimationView = findViewById(R.id.ocr_animation_view);
        ocrAnimationView.setImageUri(imageUri);

    }
}
