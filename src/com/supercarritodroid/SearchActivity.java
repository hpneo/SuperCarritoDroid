package com.supercarritodroid;

import org.json.JSONArray;
import org.json.JSONException;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.supercarritodroid.adapters.SupermarketListAdapter;
import com.supercarritodroid.models.Supermarket;

import android.os.Bundle;

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
        
        try {
			this.supermarket = Supermarket.fromJSON(new JSONArray("[{\"Codigo\":\"1\",\"Nombre\":\"Plaza Vea\",\"Paginaweb\":\"www.plazavea.com.pe\",\"Ruc\":\"20100070970 \",\"Rutaimg\":\"http://1.bp.blogspot.com/_a9mNTogA-OA/TGk9neww4tI/AAAAAAAAAAU/BR3kfdidVCA/s1600/PLAZA+VEA.jpg\",\"Telefono\":\"7876756\"},{\"Codigo\":\"2\",\"Nombre\":\"Metro\",\"Paginaweb\":\"www.metro.com.pe\",\"Ruc\":\"80100070970 \",\"Rutaimg\":\"http://www.peru-retail.com/img/blog/img-1271298069.jpg\",\"Telefono\":\"5645456\"},{\"Codigo\":\"3\",\"Nombre\":\"Totus\",\"Paginaweb\":\"www.tottus.com.pe\",\"Ruc\":\"10444770970 \",\"Rutaimg\":\"http://4.bp.blogspot.com/-4a96UV-Rd4g/Teb_dGZ3kkI/AAAAAAAACXI/NmgC-1baIxE/s1600/TOTTUS.jpg\",\"Telefono\":\"4563467\"}]")).get(0);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        SupermarketListAdapter.populateView(this.supermarket, findViewById(R.id.layoutSupermarket));
	}

}
