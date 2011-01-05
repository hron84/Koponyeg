package me.hron.koponyeg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class Preferences extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Preference p = findPreference("town");
        p.setOnPreferenceChangeListener(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String town = prefs.getString("town", "");
        if (town.length() != 0) {
            p.setSummary(town);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast t = Toast.makeText(getApplicationContext(), "Settings saved", Toast.LENGTH_SHORT);
        t.show();
    }

    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().contentEquals("town")) {
            String value = (String) newValue;
            preference.setSummary(value);
        }
        return true;
    }
}
