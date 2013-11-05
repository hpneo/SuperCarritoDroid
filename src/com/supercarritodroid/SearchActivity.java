package com.supercarritodroid;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.supercarritodroid.adapters.SupermarketListAdapter;
import com.supercarritodroid.models.Supermarket;

import android.os.Bundle;

public class SearchActivity extends SherlockActivity {
	private ActionBar actionBar = null;
	private Supermarket supermarket = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
        
        this.actionBar = this.getSupportActionBar();
        this.actionBar.setTitle(R.string.title_activity_search);
        this.actionBar.setHomeButtonEnabled(true);
        this.actionBar.setDisplayShowHomeEnabled(true);
        
        this.supermarket = (Supermarket) getIntent().getExtras().get("supermarket");
        
        SupermarketListAdapter.populateView(this.supermarket, findViewById(R.id.layoutSupermarket));
	}

}
