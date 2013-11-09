package com.supercarritodroid;

import java.util.ArrayList;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.supercarritodroid.models.Product;
import com.supercarritodroid.rest.ProductFilterTask;
import com.supercarritodroid.rest.TaskListener;

import android.os.Bundle;
import android.widget.Toast;

public class ResultsActivity extends SherlockActivity implements TaskListener {
	private ActionBar actionBar = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_results);
        
        this.actionBar = this.getSupportActionBar();
        this.actionBar.setTitle(R.string.title_activity_search);
        this.actionBar.setHomeButtonEnabled(true);
        this.actionBar.setDisplayShowHomeEnabled(true);
        this.actionBar.setDisplayHomeAsUpEnabled(true);
		
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