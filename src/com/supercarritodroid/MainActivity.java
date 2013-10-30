package com.supercarritodroid;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.supercarritodroid.adapters.SupermarketListAdapter;
import com.supercarritodroid.models.Supermarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends SherlockActivity {
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
        
        try {
        	supermarketsCollection.clear();
        	supermarketsCollection.addAll(Supermarket.fromJSON(new JSONArray("[{\"Codigo\":\"1\",\"Nombre\":\"Plaza Vea\",\"Paginaweb\":\"www.plazavea.com.pe\",\"Ruc\":\"20100070970 \",\"Rutaimg\":\"http://1.bp.blogspot.com/_a9mNTogA-OA/TGk9neww4tI/AAAAAAAAAAU/BR3kfdidVCA/s1600/PLAZA+VEA.jpg\",\"Telefono\":\"7876756\"},{\"Codigo\":\"2\",\"Nombre\":\"Metro\",\"Paginaweb\":\"www.metro.com.pe\",\"Ruc\":\"80100070970 \",\"Rutaimg\":\"http://www.peru-retail.com/img/blog/img-1271298069.jpg\",\"Telefono\":\"5645456\"},{\"Codigo\":\"3\",\"Nombre\":\"Totus\",\"Paginaweb\":\"www.tottus.com.pe\",\"Ruc\":\"10444770970 \",\"Rutaimg\":\"http://4.bp.blogspot.com/-4a96UV-Rd4g/Teb_dGZ3kkI/AAAAAAAACXI/NmgC-1baIxE/s1600/TOTTUS.jpg\",\"Telefono\":\"4563467\"}]")));
			
        	((SupermarketListAdapter) supermarkets.getAdapter()).notifyDataSetChanged();
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
}
