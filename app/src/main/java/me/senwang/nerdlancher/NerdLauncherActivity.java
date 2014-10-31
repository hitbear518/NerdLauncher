package me.senwang.nerdlancher;

import android.app.Fragment;


public class NerdLauncherActivity extends SingleFragmentActivity {

	@Override
	protected Fragment createFragment() {
		return new NerdLauncherFragment();
	}
}
