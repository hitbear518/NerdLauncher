package me.senwang.nerdlancher;

import android.app.ListFragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class NerdLauncherFragment extends ListFragment {
	public static final String TAG = NerdLauncherFragment.class.getSimpleName();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent startUpIntent = new Intent(Intent.ACTION_MAIN);
		startUpIntent.addCategory(Intent.CATEGORY_LAUNCHER);

		final PackageManager pm = getActivity().getPackageManager();
		List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(startUpIntent, 0);

		Collections.sort(resolveInfoList, new Comparator<ResolveInfo>() {
			@Override
			public int compare(ResolveInfo lhs, ResolveInfo rhs) {
				String leftString = lhs.loadLabel(pm).toString();
				String rightString = rhs.loadLabel(pm).toString();
				return leftString.compareToIgnoreCase(rightString);
			}
		});

		ArrayAdapter<ResolveInfo> adapter = new ArrayAdapter<ResolveInfo>(getActivity(),
				android.R.layout.simple_list_item_1, resolveInfoList) {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView textView;
				if (convertView == null) {
					textView = (TextView) super.getView(position, convertView, parent);
				} else {
					textView = (TextView) convertView;
				}
				ResolveInfo appInfo = getItem(position);
				textView.setText(appInfo.loadLabel(pm));
				return textView;
			}
		};

		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ResolveInfo resolveInfo = (ResolveInfo) getListAdapter().getItem(position);
		ActivityInfo activityInfo = resolveInfo.activityInfo;

		if (activityInfo == null) return;
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setClassName(activityInfo.packageName, activityInfo.name);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
