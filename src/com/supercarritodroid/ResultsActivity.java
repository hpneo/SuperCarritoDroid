package com.supercarritodroid;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.supercarritodroid.adapters.ProductListAdapter;
import com.supercarritodroid.models.Product;
import com.supercarritodroid.rest.ProductFilterTask;
import com.supercarritodroid.rest.TaskListener;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ResultsActivity extends SherlockActivity implements TaskListener {
	private ActionBar actionBar = null;
	private DrawerLayout drawer = null;
	private ListView drawerList = null;
	private ArrayList<Product> resultsCollection = new ArrayList<Product>();
	private ListView results;
	private ProductListAdapter resultsAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
        
        this.actionBar = this.getSupportActionBar();
        this.actionBar.setTitle(R.string.title_activity_search);
        this.actionBar.setHomeButtonEnabled(true);
        this.actionBar.setDisplayShowHomeEnabled(true);
        this.actionBar.setDisplayHomeAsUpEnabled(true);
        
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerList = (ListView) findViewById(R.id.left_drawer);
        this.drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, R.id.listTextView, getResources().getStringArray(R.array.menu)));
        
        results = (ListView) findViewById(R.id.listviewResults);
        
        resultsAdapter = new ProductListAdapter(this, R.layout.item_product, resultsCollection);
        resultsAdapter.setNotifyOnChange(true);
        
        results.setAdapter(resultsAdapter);
        
        String codsupermercado = getIntent().getExtras().getString("codsupermercado");
		String marca = getIntent().getExtras().getString("marca").toLowerCase();
		String categoria = getIntent().getExtras().getString("categoria").toLowerCase();
		String oferta = getIntent().getExtras().getString("oferta").toLowerCase();
		
		ProductFilterTask task = new ProductFilterTask(this);
		task.addParams("codsupermercado", codsupermercado);
		task.addParams("marca", marca);
		task.addParams("categoria", categoria);
		task.addParams("oferta", oferta);
		task.execute(Product.URLS.get("search"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public void onTaskCompleted(Object result) {
		ArrayList<Product> list = (ArrayList<Product>) result;
		
		Toast.makeText(this, list.size() + " products found", Toast.LENGTH_LONG).show();

		 resultsCollection.clear();
		 resultsCollection.addAll(list);

		 ((ProductListAdapter) results.getAdapter()).notifyDataSetChanged();
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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch (item.getItemId()) {
	    case android.R.id.home:
	    	super.onBackPressed();
	    	break;
	    }
	    
	    return super.onOptionsItemSelected(item);
	}
}