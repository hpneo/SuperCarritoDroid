package com.supercarritodroid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.supercarritodroid.models.Product;
import com.supercarritodroid.rest.PurchaseTask;
import com.supercarritodroid.rest.TaskListener;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class NewProductActivity extends SherlockActivity {
	private ActionBar actionBar = null;
	private DrawerLayout drawer = null;
	private ListView drawerList = null;
	private Product product = null;
	private String supermarket_id = null;

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
        this.supermarket_id = (String) getIntent().getExtras().get("supermarket_id");
        
        EditText editTextSelectedProductName = (EditText) findViewById(R.id.editTextSelectedProductName);
        final EditText editTextSelectedProductUnitPrice = (EditText) findViewById(R.id.editTextSelectedProductUnitPrice);
        Spinner spinnerSelectedProductCategory = (Spinner) findViewById(R.id.spinnerSelectedProductCategory);
        Spinner spinnerSelectedProductBrand = (Spinner) findViewById(R.id.spinnerSelectedProductBrand);
        final EditText editTextSelectedProductQuantity = (EditText) findViewById(R.id.editTextSelectedProductQuantity);
        
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
        
        Button buttonAdd = (Button) findViewById(R.id.buttonAdd);
        
        final Context context = this;
        final SharedPreferences preferences = getSharedPreferences("SuperCarritoDroidPreferences", MODE_PRIVATE);
        final String codcliente = String.valueOf(Integer.valueOf(preferences.getString("user_id", "0").replaceAll("\\D*", "")));
        
        buttonAdd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			    PurchaseTask purchaseTask = new PurchaseTask();
			    purchaseTask.addParams("codcompra", (Calendar.getInstance().getTimeInMillis() / 10000) + "");
                purchaseTask.addParams("codcliente", codcliente);
                purchaseTask.addParams("fechacompra", "25/11/2013");
                purchaseTask.addParams("total", "0");
			    
			    purchaseTask.setListener(new TaskListener() {
					
					@Override
					public void onTaskError(Object result) {
						Error error = (Error) result;
						
						Toast.makeText(context, "purchaseTask Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
					}
					
					@Override
					public void onTaskCancelled(Object result) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onTaskCompleted(Object result) {
						final String purchase_id = String.valueOf(Integer.valueOf((String) result));
						
						Toast.makeText(context, "New purchase: " + purchase_id, Toast.LENGTH_LONG).show();
						
						PurchaseTask purchaseDetailTask = new PurchaseTask();
						purchaseDetailTask.addParams("codcomp", purchase_id);
                        purchaseDetailTask.addParams("codCli", codcliente);
                        purchaseDetailTask.addParams("codpro", product.getCodigo());
                        purchaseDetailTask.addParams("codsuperm", supermarket_id);
                        purchaseDetailTask.addParams("canti", editTextSelectedProductQuantity.getText().toString());
                        purchaseDetailTask.addParams("subtot", "0");
                        
						purchaseDetailTask.setListener(new TaskListener() {
							
							@Override
							public void onTaskError(Object result) {
							    Error error = (Error) result;
		                        
		                        Toast.makeText(context, "purchaseDetailTask Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
							}
							
							@Override
							public void onTaskCancelled(Object result) {
								// TODO Auto-generated method stub
								
							}
							
							@Override
							public void onTaskCompleted(Object result) {
								Toast.makeText(context, "New purchase detail: " + (String) result, Toast.LENGTH_LONG).show();
								
								double subtot = Double.valueOf(editTextSelectedProductQuantity.getText().toString()) * Double.valueOf(editTextSelectedProductUnitPrice.getText().toString());
								
								PurchaseTask updatePurchaseTask = new PurchaseTask();
								updatePurchaseTask.addParams("codcomp", purchase_id);
								updatePurchaseTask.addParams("subtot", String.valueOf(subtot));
								
								updatePurchaseTask.setListener(new TaskListener() {
                                    
                                    @Override
                                    public void onTaskError(Object result) {
                                        Error error = (Error) result;
                                        
                                        Toast.makeText(context, "updatePurchaseTask Error: " + error.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    
                                    @Override
                                    public void onTaskCompleted(Object result) {
                                        // TODO Auto-generated method stub
                                        
                                    }
                                    
                                    @Override
                                    public void onTaskCancelled(Object result) {
                                        Toast.makeText(context, "New purchase detail updated: " + (String) result, Toast.LENGTH_LONG).show();
                                    }
                                });
								
								updatePurchaseTask.execute("GetActualizaCompra");
							}
						});
						
						purchaseDetailTask.execute("GetRegistraDetalleCompra");
					}
				});

                purchaseTask.execute("GetRegistraCompra");
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
