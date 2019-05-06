/**
 * Pada bagian ini akan membuat sebuah penampilan database dengan menggunakan listview,
 * listview didefinisikan pada XML.
 *
 * Tampilan akan berada : DailyActivity.jawa
 * Menggunakan XML      : activity_daily.xml
 */

package com.timdakwah.letshijrah.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.timdakwah.letshijrah.R;
import com.timdakwah.letshijrah.model.Product_References;

import java.util.List;

public class Adapter_References extends BaseAdapter {
    /**
     * Mendefinisikan variabel Context dan List yang digunakan.
     * Di sini menggunakan List dari Product_Challenge.java
     */
    private Context Adapter_Context_References;
    private List<Product_References> Adapter_ProductList_References;

    public Adapter_References(Context adapter_Context_References, List<Product_References> adapter_ProductList_References) {
        this.Adapter_Context_References = adapter_Context_References;
        this.Adapter_ProductList_References = adapter_ProductList_References;
    }

    public int getCount() {
        return Adapter_ProductList_References.size();
    }


    public Object getItem(int position) {
        return Adapter_ProductList_References.get(position);
    }


    public long getItemId(int position) {
        return Adapter_ProductList_References.get(position).getId();
    }

    /**
     * Bagian di bawah ini akan membuat tampilan pada listview yang digunakan dengan:
     * @param position      : posisi dari tiap-tiap list pada listview
     * @param convertView   : ...
     * @param parent        : ...
     *
     * Definisikan setiap TextView pada listview yang ada.
     * Import data untuk TextView berdasarkan ProductList yang mengambil dari List<ProductList>
     * Data ditampilkan pada setText.
     *
     * @return : nilai dari hasil TextView masing-masing
     */


    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(Adapter_Context_References, R.layout.listview_reference, null);
        TextView TV_Judul = (TextView)v.findViewById(R.id.listview_refrence_title);
        TV_Judul.setText(Adapter_ProductList_References.get(position).getJudul());
        return v;
    }
}
