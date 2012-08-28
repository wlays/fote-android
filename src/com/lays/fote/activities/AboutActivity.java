package com.lays.fote.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.lays.fote.FoteApplication;
import com.lays.fote.R;

public class AboutActivity extends SherlockFragmentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	FoteApplication.tracker.trackPageView("/About");
	setContentView(R.layout.activity_about);
    }

    public void composeEmail(View v) {
	try {
	    final Intent emailIntent = new Intent(Intent.ACTION_SEND);
	    emailIntent.setType("plain/text");
	    String recipient = this.getResources().getString(R.string.about_email);
	    emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { recipient });
	    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Fote Feedback");
	    startActivity(Intent.createChooser(emailIntent, "Send mail..."));
	} catch (ActivityNotFoundException e) {
	    // cannot send email for some reason
	    e.printStackTrace();
	}
    }
}
