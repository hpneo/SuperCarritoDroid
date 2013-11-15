package com.supercarritodroid;

import java.util.ArrayList;
import java.util.Arrays;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.supercarritodroid.models.Product;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class NewProductActivity extends SherlockActivity {
	private ActionBar actionBar = null;
	private DrawerLayout drawer = null;
	private ListView drawerList = null;
	private Product product = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_product);
		
		this.actionBar = this.getSupportActionBar();
        this.actionBar.setTitle(R.string.title_activity_search);
        this.actionBar.setHomeButtonEnabled(true);
        this.actionBar.setDisplayShowHomeEnabled(true);
        this.actionBar.setDisplayHomeAsUpEnabled(true);
        
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.drawerList = (ListView) findViewById(R.id.left_drawer);
        this.drawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, R.id.listTextView, getResources().getStringArray(R.array.menu)));
        
        this.product = (Product) getIntent().getExtras().get("product");
        
        EditText editTextSelectedProductName = (EditText) findViewById(R.id.editTextSelectedProductName);
        EditText editTextSelectedProductUnitPrice = (EditText) findViewById(R.id.editTextSelectedProductUnitPrice);
        Spinner spinnerSelectedProductCategory = (Spinner) findViewById(R.id.spinnerSelectedProductCategory);
        Spinner spinnerSelectedProductBrand = (Spinner) findViewById(R.id.spinnerSelectedProductBrand);
        EditText editTextSelectedProductQuantity = (EditText) findViewById(R.id.editTextSelectedProductQuantity);
        
        String[] categories_array = getResources().getStringArray(R.array.categories);
        String[] brands_array = getResources().getStringArray(R.array.brands);
        
        for(int i = 0; i < categories_array.length; i++) {
        	categories_array[i] = categories_array[i].toLowerCase();
        }
        
        for(int i = 0; i < brands_array.length; i++) {
        	brands_array[i] = brands_array[i].toLowerCase();
        }
        
        ArrayList<String> categories = new ArrayList<String>(Arrays.asList(categories_array));
        ArrayList<String> brands = new ArrayList<String>(Arrays.asList(brands_array));
        
        int categories_index = categories.indexOf(this.product.getCategoria());
        int brands_index = brands.indexOf(this.product.getMarca());
        
        editTextSelectedProductName.setText(this.product.getNombre());
        editTextSelectedProductUnitPrice.setText(this.product.getPrecio().toString());
        editTextSelectedProductQuantity.setText("1");
        
        if (categories_index > -1) {
        	spinnerSelectedProductCategory.setSelection(categories_index);
        }
        
        if (brands_index > -1) {
        	spinnerSelectedProductBrand.setSelection(brands_index);
        }
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
