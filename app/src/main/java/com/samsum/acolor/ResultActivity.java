package com.samsum.acolor;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.samsum.R;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class ResultActivity extends Activity implements OnClickListener {
    private TextView txtViewScore;
    private TextView txtViewComments;
    private TextView txtViewCommentsDetails;
    private Button btnFacebook;
    private TextView btnReload;
    private int gameScore = 0;
    private RelativeLayout relativeMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_result);
        initViews();
        setListener();
        loadData();


    }


    private void initViews() {
        // TODO Auto-generated method stub
        txtViewScore = (TextView) findViewById(R.id.txt_view_score);
        txtViewComments = (TextView) findViewById(R.id.txt_view_comments);
        txtViewCommentsDetails = (TextView) findViewById(R.id.txt_view_comments_details);
        btnFacebook = (Button) findViewById(R.id.btn_facebook);
        btnReload = (Button) findViewById(R.id.btn_reload);
        relativeMain = (RelativeLayout) findViewById(R.id.relative_main);

    }

    private void setListener() {
        // TODO Auto-generated method stub
        btnFacebook.setOnClickListener(this);
        btnReload.setOnClickListener(this);
    }

    private void loadData() {
        // TODO Auto-generated method stub
        if (getIntent().getExtras() != null) {
            gameScore = getIntent().getExtras().getInt("score");
        }

        txtViewScore.setText("" + gameScore);

        setComments();
    }


    private void setComments() {
        if (gameScore >= 0 && gameScore <= 4) {
            txtViewComments.setText("BAT");
            txtViewCommentsDetails.setText("Your colour vision is not something to write home about. Bats live in the dark and rely on other senses than sight, and that's what you should do too.");
        } else if (gameScore >= 5 && gameScore <= 9) {
            txtViewComments.setText("MOLE");
            txtViewCommentsDetails.setText("You have moderate colour vision. You see your closest perimeter but don't go on any big adventures as you will probably get lost.");
        } else if (gameScore >= 10 && gameScore <= 14) {
            txtViewComments.setText("DOG");
            txtViewCommentsDetails.setText("You have decent colour vision. You see most of the sticks that are thrown to you but sometimes you're just lost.");
        } else if (gameScore >= 15 && gameScore <= 19) {
            txtViewComments.setText("CAT");
            txtViewCommentsDetails.setText("You have good colour vision. The mice should hide when you're on the move.");
        } else if (gameScore >= 20 && gameScore <= 24) {
            txtViewComments.setText("TIGER");
            txtViewCommentsDetails.setText("Your colour vision is superb. You wouldn't have any problems surviving in the jungle. You can see when the neighbouring tiger visits the bathroom faaaar away.");
        } else if (gameScore >= 25 && gameScore <= 29) {
            txtViewComments.setText("HAWK");
            txtViewCommentsDetails.setText("Wow, you have excellent colour vision. You can see a worm from the top of a tree.");
        } else if (gameScore >= 30) {
            txtViewComments.setText("ROBOT");
            txtViewCommentsDetails.setText("Your colour vision and survival skills are incredible. You can recognize a mosquito from miles away.");
        } else {
            txtViewComments.setText("UNDEFINED");
            txtViewCommentsDetails.setText("");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_reload:
                startActivity(new Intent(ResultActivity.this, MainActivity.class));
                finish();
                break;

            case R.id.btn_facebook:
                if (!checkStoragePermission(ResultActivity.this)) {
                    ActivityCompat.requestPermissions(ResultActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 11);
                } else {
                    new LongOperation((FrameLayout) findViewById(R.id.frame_layout)).execute();
                }

                break;

            default:
                break;
        }
    }

    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }


    public static void saveBitmap(Bitmap bitmap, String pathToStorage) {

        try {
            FileOutputStream output = new FileOutputStream(pathToStorage);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean checkStoragePermission(Activity activity) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            return true;
        }
        try {
            int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
            if (result == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException exceptionIgnored) {
            return false;
        }
    }


    private class LongOperation extends AsyncTask<String, Void, String> {
        private View frame_layout;


        private LongOperation(View frame_layout) {
            this.frame_layout = frame_layout;

        }

        @Override
        protected String doInBackground(String... params) {
            Bitmap bitmap = viewToBitmap(frame_layout);
            String path = MediaStore.Images.Media.insertImage(ResultActivity.this.getContentResolver(), bitmap, "" + System.currentTimeMillis(), null);

            return path;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                try {
                    Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    // create bitmap screen capture

                    sharingIntent.setType("image/*");
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(result));

                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Result : " + gameScore);
                    startActivity(Intent.createChooser(sharingIntent, "Share Score Using"));
                } catch (Exception e) {
                }
            }

        }
    }

}
