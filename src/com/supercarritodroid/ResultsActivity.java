package com.supercarritodroid;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jocasta.callbacks.AsyncFailCallback;
import com.jocasta.callbacks.AsyncSuccessCallback;
import com.supercarritodroid.adapters.ProductListAdapter;
import com.supercarritodroid.models.Product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ResultsActivity extends SherlockActivity {
	private ActionBar actionBar = null;
	private DrawerLayout drawer = null;
	private ListView drawerList = null;
	private ArrayList<Product> resultsCollection = new ArrayList<Product>();
	private ListView results;
	private ProductListAdapter resultsAdapter;
	private String codsupermercado = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
		
		final Context context = this;
		final Activity activity = this;
        
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
        
        results.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Product product = (Product) parent.getItemAtPosition(position);
				
				Intent intent = new Intent(context, NewProductActivity.class);
				intent.putExtra("product", product);
				intent.putExtra("supermarket_id", codsupermercado);
				
				context.startActivity(intent);
			}
		});

        this.codsupermercado = getIntent().getExtras().getString("supermarket_id");
        String marca = getIntent().getExtras().getString("marca").toLowerCase();
        String categoria = getIntent().getExtras().getString("categoria").toLowerCase();
        String oferta = getIntent().getExtras().getString("oferta").toLowerCase();
        
        ArrayList<NameValuePair> filterParams = new ArrayList<NameValuePair>();
        
        filterParams.add(new BasicNameValuePair("codsupermercado", this.codsupermercado));
        filterParams.add(new BasicNameValuePair("marca", marca));
        filterParams.add(new BasicNameValuePair("categoria", categoria));
        filterParams.add(new BasicNameValuePair("oferta", oferta));
        
        Product.get(Product.BASE_URL + Product.URLS.get("search"), filterParams, new AsyncSuccessCallback() {
            
            @Override
            public void run(String data) {
                if (!data.equals(null)) {
                    try {
                        ArrayList<Product> list = Product.fromJSON(new JSONArray(data));
                        
                        Toast.makeText(context, list.size() + " products found", Toast.LENGTH_LONG).show();

                        resultsCollection.clear();
                        resultsCollection.addAll(list);

                        ((ProductListAdapter) results.getAdapter()).notifyDataSetChanged();
                    } catch (JSONException e) {
                    }
                }
            }
        }, new AsyncFailCallback() {
            
            @Override
            public void run(final Exception error) {
                Log.i("ResultsActivity", error.getMessage());
                activity.runOnUiThread(new Runnable() {
                    
                    @Override
                    public void run() {
                        Toast.makeText(context, "Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
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