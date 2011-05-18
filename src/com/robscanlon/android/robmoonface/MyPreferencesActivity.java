package com.robscanlon.android.robmoonface;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.Preference.OnPreferenceChangeListener;
import android.widget.Toast;


public class MyPreferencesActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.prefs);

	}

	/**
	 * Checks that a preference is a valid numerical value
	 */
	Preference.OnPreferenceChangeListener numberCheckListener = new OnPreferenceChangeListener() {

		@Override
		public boolean onPreferenceChange(Preference preference, Object newValue) {
			return true;
		};

	};

}
