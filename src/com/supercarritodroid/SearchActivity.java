package com.supercarritodroid;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.supercarritodroid.adapters.SupermarketListAdapter;
import com.supercarritodroid.models.Supermarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;

public class SearchActivity extends SherlockActivity {
	private ActionBar actionBar = null;
	private DrawerLayout drawer = null;
	private ListView drawerList = null;
	private Supermarket supermarket = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
        
        this.actionBar = this.getSupportActionBar();
        this.actionBar.setTitle(R.string.title_activity_search);
        this.actionBar.setHomeButtonEnabled(true);
        this.actionBar.setDisplayShowHomeEnabled(true);
        this.actionBar.setDisplayHomeAsUpEnabled(true);
        
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerList = (ListView) findViewById(R.id.left_drawer);
        this.drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, R.id.listTextView, getResources().getStringArray(R.array.menu)));
        
        this.supermarket = (Supermarket) getIntent().getExtras().get("supermarket");
        
        SupermarketListAdapter.populateView(this.supermarket, findViewById(R.id.layoutSupermarket));
        
        final Context context = this;
        
        final Spinner spinnerBrands = (Spinner) findViewById(R.id.spinnerBrands);
        final Spinner spinnerCategories = (Spinner) findViewById(R.id.spinnerCategories);
        final CheckBox checkBoxInOffer = (CheckBox) findViewById(R.id.checkBoxInOffer);
        
        Button buttonSearch = (Button) findViewById(R.id.buttonSearch);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, ResultsActivity.class);
				intent.putExtra("codsupermercado", supermarket.getCodigo());
				intent.putExtra("marca", spinnerBrands.getSelectedItem().toString());
				intent.putExtra("categoria", spinnerCategories.getSelectedItem().toString());
				intent.putExtra("oferta", checkBoxInOffer.isChecked() ? "si" : "no");
				
				context.startActivity(intent);
			}
		});
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
