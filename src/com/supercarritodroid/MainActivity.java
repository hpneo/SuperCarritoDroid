package com.supercarritodroid;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.supercarritodroid.adapters.SupermarketListAdapter;
import com.supercarritodroid.models.Supermarket;
import com.supercarritodroid.rest.SupermarketTask;
import com.supercarritodroid.rest.TaskListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends SherlockActivity implements TaskListener {
	private ActionBar actionBar = null;
	private ArrayList<Supermarket> supermarketsCollection = new ArrayList<Supermarket>();
	private ListView supermarkets;
	private SupermarketListAdapter supermarketsAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        SharedPreferences preferences = getSharedPreferences("SuperCarritoDroidPreferences", MODE_PRIVATE);
        
        if (preferences.contains("user_id")) {
        	showMain();
        }
        else {
        	Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            this.startActivity(intent);
            this.finish();
        }
    }
    
    private void showMain() {
    	setContentView(R.layout.activity_main);
    	this.actionBar = this.getSupportActionBar();
        this.actionBar.setTitle(R.string.title_activity_main);
        this.actionBar.setHomeButtonEnabled(true);
        this.actionBar.setDisplayShowHomeEnabled(true);
        
        supermarkets = (ListView) findViewById(R.id.listViewSupermarkets);
        
        supermarketsAdapter = new SupermarketListAdapter(this, R.layout.item_supermarket, supermarketsCollection);
        supermarketsAdapter.setNotifyOnChange(true);
        
        supermarkets.setAdapter(supermarketsAdapter);
        
        final Context context = this;
        
        supermarkets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Supermarket supermarket = (Supermarket) parent.getItemAtPosition(position);
				
				Intent intent = new Intent(context, SearchActivity.class);
				intent.putExtra("supermarket", supermarket);
				
				context.startActivity(intent);
			}
		});
        
        SupermarketTask task = new SupermarketTask(this);
    	task.execute(Supermarket.URLS.get("index"));
    }

	@Override
	@SuppressWarnings("unchecked")
	public void onTaskCompleted(Object result) {
		ArrayList<Supermarket> list = (ArrayList<Supermarket>) result;
		
		supermarketsCollection.clear();
		supermarketsCollection.addAll(list);
		
		((SupermarketListAdapter) supermarkets.getAdapter()).notifyDataSetChanged();
	}

	@Override
	public void onTaskError(Object result) {
		Error error = (Error) result;
		
		Toast.makeText(this, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
	}

	@Override
	public void onTaskCancelled(Object result) {
		String message = (String) result;
		
		Toast.makeText(this, "Cancelled: " + message, Toast.LENGTH_LONG).show();
	}
}
