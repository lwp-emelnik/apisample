package com.ichi2.apisample;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int AD_PERM_REQUEST = 0;
    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 1;

    private EditText inputFilename;
    private EditText inputStartNote;
    private EditText inputAscDesc;
    private EditText inputMelHar;
    private EditText inputInterval;

    private AnkiDroidHelper mAnkiDroid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputFilename = findViewById(R.id.inputFilename);
        inputStartNote = findViewById(R.id.inputStartNote);
        inputAscDesc = findViewById(R.id.inputAscDesc);
        inputMelHar = findViewById(R.id.inputMelHar);
        inputInterval = findViewById(R.id.inputInterval);

        final Button actionSelectFile = findViewById(R.id.actionSelectFile);
        actionSelectFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputFilename.setText("/path/to/file.m4a");
            }
        });

        final Button actionCheckExistence = findViewById(R.id.actionCheckExistence);
        actionCheckExistence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnkiDroid.shouldRequestPermission()) {
                    mAnkiDroid.requestPermission(MainActivity.this, AD_PERM_REQUEST);
                    return;
                }

                String message;

                if (inputStartNote.getText().toString().isEmpty() && inputAscDesc.getText().toString().isEmpty()) {
                    message = getResources().getString(R.string.nothing_to_check);
                } else  if (getMusInterval().existsInAnki()) {
                    message = getResources().getString(R.string.card_exists);
                } else {
                    message = getResources().getString(R.string.card_not_exists);
                }

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        final Button actionAddToAnki = findViewById(R.id.actionAddToAnki);
        actionAddToAnki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnkiDroid.shouldRequestPermission()) {
                    mAnkiDroid.requestPermission(MainActivity.this, AD_PERM_REQUEST);
                    return;
                }

                final MusInterval musInterval = getMusInterval();
                String message = getResources().getString(R.string.item_added);

                try {
                    musInterval.addToAnki();
                } catch (MusInterval.NoSuchModelException e) {
                    message = getResources().getString(R.string.model_not_found, musInterval.getModelName());
                } catch (MusInterval.CreateDeckException e) {
                    message = getResources().getString(R.string.create_deck_error, musInterval.getDeckName());
                } catch (MusInterval.AddToAnkiException e) {
                    message = getResources().getString(R.string.add_card_error);
                }

                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        mAnkiDroid = new AnkiDroidHelper(this);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case AD_PERM_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(MainActivity.this, R.string.anki_permission_denied, Toast.LENGTH_LONG).show();
                }
            }
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = getIntent();
                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                    am.set(AlarmManager.RTC, System.currentTimeMillis() + 100, pendingIntent);
                    System.exit(0);
                } else {
                    Toast.makeText(MainActivity.this, R.string.fs_permission_denied, Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    public MusInterval getMusInterval() {
        return new MusInterval(mAnkiDroid, inputFilename.getText().toString(), inputStartNote.getText().toString(),
                inputAscDesc.getText().toString(), inputMelHar.getText().toString(), inputInterval.getText().toString());
    }

}
