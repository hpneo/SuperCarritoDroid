package com.supercarritodroid;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.supercarritodroid.adapters.SupermarketListAdapter;
import com.supercarritodroid.models.Supermarket;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;

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

}
