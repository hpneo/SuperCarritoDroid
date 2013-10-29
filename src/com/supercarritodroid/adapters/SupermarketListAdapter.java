package com.supercarritodroid.adapters;

import java.util.ArrayList;

import com.loopj.android.image.SmartImageView;
import com.supercarritodroid.R;
import com.supercarritodroid.models.Supermarket;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SupermarketListAdapter extends ArrayAdapter<Supermarket> {
	private ArrayList<Supermarket> collection;
	private int layoutResourceId;

	public SupermarketListAdapter(Context context, int layoutResourceId, ArrayList<Supermarket> baseCollection) {
		super(context, layoutResourceId, baseCollection);
		this.layoutResourceId = layoutResourceId;
		this.collection = baseCollection;
	}
	
	 @Override
	public View getView(int position, View view, ViewGroup parent) {
		Supermarket supermarket = collection.get(position);
		
		if (view == null) {
			LayoutInflater view_inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = view_inflater.inflate(layoutResourceId, null);
		}
		
		view = populateView(supermarket, view);
		
		return view;
	}
	 
	public static View populateView(Supermarket supermarket, View view) {
		TextView supermarketName = (TextView) view.findViewById(R.id.item_supermarket_name);
		SmartImageView supermarketImage = (SmartImageView) view.findViewById(R.id.item_supermarket_image);
		
		if (supermarket != null) {
			supermarketName.setText(supermarket.getNombre());
			supermarketImage.setImageUrl(supermarket.getRutaImagen());
		}
		
		return view;
	}

}
