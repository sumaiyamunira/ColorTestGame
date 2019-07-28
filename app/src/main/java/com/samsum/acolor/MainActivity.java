package com.samsum.acolor;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;

import com.samsum.R;
import com.samsum.adatper.GridViewAdapter;


public class MainActivity extends Activity implements OnItemClickListener {

	private GridView gridView;
	private TextView txtViewScore;
	private TextView txtViewTimer;
	private TextView txtViewError;
	private ArrayList<Integer> colorList;
	private CountDownTimer countDownTimer;
	
	private int gameLabel = 0;
	private int LabelTimes = 0;
	private int correctGridIndex = 0;
	private int gameScore = 0;
	
	private final long startTime = 14 * 1000;
	private final long interval = 1 * 1000;
	private int i;
	private int errorCount = 0;
	
	private boolean isFirstTime = false;
	private static final int HOW_TO_PLAY = Menu.FIRST;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initViews();
		setListener();
		addColors();
		setColums(gameLabel);
		loadData();
	}

	private void addColors() {
		// TODO Auto-generated method stub
		colorList = new ArrayList<Integer>();
		for(int i =0; i < 7; i++) {
			Random rnd = new Random(); 
			int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
			colorList.add(color);
		}
	}

	private void initViews() {
		// TODO Auto-generated method stub
		gameLabel = 1;
		LabelTimes = 1;
		txtViewTimer = (TextView) findViewById(R.id.txt_view_timer);
		gridView = (GridView) findViewById(R.id.grid_view);
		txtViewScore = (TextView) findViewById(R.id.txt_view_score);
		txtViewError = (TextView) findViewById(R.id.txt_view_timer_error);
		txtViewError.setText(""+errorCount);
	
	}

	private void setListener() {
		// TODO Auto-generated method stub
		gridView.setOnItemClickListener(this);
	}

	private void loadData() {
		if(countDownTimer != null) {
			countDownTimer.cancel();
		}
		
		i = 14;
		if(isFirstTime) {
			countDownTimer = new MyCountDownTimer(startTime, interval);
			countDownTimer.start();
		}
		correctGridIndex = getCorrectIndex(0, ((gameLabel + 1) * (gameLabel + 1)) - 1);
		gridView.setAdapter(new GridViewAdapter(getApplicationContext(), (gameLabel + 1), colorList.get(getCorrectIndex(0, colorList.size() - 1)), correctGridIndex, LabelTimes));
	}
	
	private void setColums(int gameLable) {
		switch (gameLable) {
		case 1:
			gridView.setNumColumns(2);
			break;
		case 2:
			gridView.setNumColumns(3);		
			break;
		case 3:
			gridView.setNumColumns(4);
			break;
		case 4:
			gridView.setNumColumns(5);
			break;
		case 5:
			gridView.setNumColumns(6);
			break;

		default:
			break;
		}
	}
	
	private int getCorrectIndex(int min, int max) {
		Random rand = new Random();
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(position == correctGridIndex) {
			isFirstTime = true;
			gameScore++;
			txtViewScore.setText(""+gameScore);
			switch (gameLabel) {
			case 1:
				gameLabel++;
				setColums(gameLabel);
				loadData();
				break;
			case 2:
				LabelTimes++;
				if(LabelTimes == 4) {
					gameLabel++;
					LabelTimes = 0;
				}
				setColums(gameLabel);
				loadData();
				break;
			case 3:
				LabelTimes++;
				if(LabelTimes == 7) {
					gameLabel++;
					LabelTimes = 0;
				}
				setColums(gameLabel);
				loadData();
				break;
			case 4:
				LabelTimes++;
				if(LabelTimes == 10) {
					gameLabel++;
					LabelTimes = 0;
				}
				setColums(gameLabel);
				loadData();
				break;
				
			case 5:
				LabelTimes++;
				setColums(gameLabel);
				loadData();
				break;

			default:
				break;
			}
		} else {
			if(isFirstTime) {
				errorCount++;
				txtViewError.setText(""+errorCount);
				if(i > 2) {
					i = i - 2;
					if(countDownTimer != null) {
						countDownTimer.cancel();
						countDownTimer = new MyCountDownTimer(i * 1000, interval);
						countDownTimer.start();
					}
				}
			}
		}
	}

	 public class MyCountDownTimer extends CountDownTimer {
		  public MyCountDownTimer(long startTime, long interval) {
		   super(startTime, interval);
		  }

		  @Override
		  public void onFinish() {
			  txtViewTimer.setText("00:00");
			  gridView.setEnabled(false);
			  startActivity(new Intent(MainActivity.this, ResultActivity.class).putExtra("score", gameScore));
			  finish();
			  //Toast.makeText(getApplicationContext(), "Game Finished", Toast.LENGTH_LONG).show();
		  }

		  @Override
		  public void onTick(long millisUntilFinished) {
			if (i > 0) {
				i--;
				if (i > 9) {
					txtViewTimer.setText("00:" + i);
				} else {
					txtViewTimer.setText("00:0" + i);
				}
				
				if (i < 5) {
					txtViewTimer.setTextColor(Color.RED);
				} else {
					txtViewTimer.setTextColor(Color.BLACK);
				}
				
			  }
			  //txtViewTimer.setText("" + millisUntilFinished / 1000);
		  }
	 }
	 
	 @Override
	protected void onDestroy() {
		super.onDestroy();
		if(countDownTimer != null) {
			countDownTimer.cancel();
		}
	}
	 
	 
	 @Override
		public boolean onCreateOptionsMenu(Menu menu) {
			boolean result = super.onCreateOptionsMenu(menu);
			menu.add(0, HOW_TO_PLAY, 0, "How to Play");
			
			return result;
		}

		@Override
		public boolean onMenuItemSelected(int featureId, MenuItem item) {

			switch (item.getItemId()) {
				case HOW_TO_PLAY: {
					Intent i = new Intent(MainActivity.this, SettingsActivity.class);
					startActivity(i);
					return true;
				}
				
			}

			return super.onMenuItemSelected(featureId, item);
		}
	 
	 
}
