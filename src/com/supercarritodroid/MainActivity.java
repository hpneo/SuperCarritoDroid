package com.supercarritodroid;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.supercarritodroid.adapters.SupermarketListAdapter;
import com.supercarritodroid.models.Supermarket;
import com.supercarritodroid.rest.SupermarketTask;
import com.supercarritodroid.rest.TaskListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends SherlockActivity implements TaskListener {
	private ActionBar actionBar = null;
	private DrawerLayout drawer = null;
	private ListView drawerList = null;
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
        
        final Context context = this;
    	
    	this.actionBar = this.getSupportActionBar();
        this.actionBar.setTitle(R.string.title_activity_main);
        this.actionBar.setHomeButtonEnabled(true);
        this.actionBar.setDisplayShowHomeEnabled(true);
        
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerList = (ListView) findViewById(R.id.left_drawer);
        this.drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, R.id.listTextView, getResources().getStringArray(R.array.menu)));
        
        supermarkets = (ListView) findViewById(R.id.listViewSupermarkets);
        
        supermarketsAdapter = new SupermarketListAdapter(this, R.layout.item_supermarket, supermarketsCollection);
        supermarketsAdapter.setNotifyOnChange(true);
        
        supermarkets.setAdapter(supermarketsAdapter);
        
        supermarkets.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Supermarket supermarket = (Supermarket) parent.getItemAtPosition(position);
				
				Intent intent = new Intent(context, SearchActivity.class);
				intent.putExtra("supermarket", supermarket);
				
				context.startActivity(intent);
			}
		});
        
        fromDbOrService();
        
        
    }
    
    private void fromDbOrService() {
        if (Supermarket.isEmpty(Supermarket.class)) {
            SupermarketTask task = new SupermarketTask(this);
            task.execute(Supermarket.URLS.get("index"));
        }
        else {
            ArrayList<Supermarket> list = (ArrayList<Supermarket>) Supermarket.all(Supermarket.class);
            supermarketsCollection.clear();
            supermarketsCollection.addAll(list);
            
            ((SupermarketListAdapter) supermarkets.getAdapter()).notifyDataSetChanged();
        }
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuItem menuAction = menu.add("Options");
		menuAction.setIcon(R.drawable.ic_drawer);
		menuAction.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		menuAction.setVisible(true);
		menuAction.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

			@Override
			public boolean onMenuItemClick(MenuItem item) {
				if (drawer.isDrawerOpen(drawerList)) {
					drawer.closeDrawer(drawerList);
				}
				else {
					drawer.openDrawer(drawerList);
				}
				return false;
			}
		});
		
		return super.onCreateOptionsMenu(menu);
	}
}
