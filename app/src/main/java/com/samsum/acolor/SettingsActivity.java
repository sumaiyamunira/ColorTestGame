package com.samsum.acolor;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

import com.samsum.R;

public class SettingsActivity extends Activity {
	private TextView txtViewRules;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		txtViewRules = (TextView) findViewById(R.id.txt_view_rules);
		txtViewRules.setText(Html.fromHtml("<h2>Test your colour vision</h2><p>1. Touch on the box that has an irregular colour compared to the rest of the boxes.</p><p>2. The test starts when you click on the first box.</p><p>3. You have 14 seconds to decide on each grid.</p><p>4. When you click the wrong box you will lose 2 seconds.</p>"));
	}

}
