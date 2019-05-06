/**
 * Pada bagian ini akan membuat sebuah penampilan database dengan menggunakan listview,
 * listview didefinisikan pada XML.
 *
 * Tampilan akan berada : DailyActivity.jawa
 * Menggunakan XML      : activity_daily.xml
 */

package com.timdakwah.letshijrah.adapter;

import android.content.Context;
import android.media.Image;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.timdakwah.letshijrah.R;
import com.timdakwah.letshijrah.model.Product_Daily;

import java.util.List;

public class Adapter_Daily extends BaseAdapter {
    /**
     * Mendefinisikan variabel Context dan List yang digunakan.
     * Di sini menggunakan List dari Product_Challenge.java
     */
    private Context Adapter_Context_Daily;
    private List<Product_Daily> Adapter_ProductList_Daily;

    public Adapter_Daily(Context adapter_Context_Daily, List<Product_Daily> adapter_ProductList_Daily) {
        this.Adapter_Context_Daily = adapter_Context_Daily;
        this.Adapter_ProductList_Daily = adapter_ProductList_Daily;
    }


    @Override
    public int getCount() {
        return Adapter_ProductList_Daily.size();
    }

    @Override
    public Object getItem(int position) {
        return Adapter_ProductList_Daily.get(position);
    }

    @Override
    public long getItemId(int position) {
        return Adapter_ProductList_Daily.get(position).getId();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(Adapter_Context_Daily, R.layout.listview_daily_challenge, null);
        TextView TV_title = (TextView)v.findViewById(R.id.listview_daily_title);
        TV_title.setText(Adapter_ProductList_Daily.get(position).getJudul());
        ImageView IV_icon = (ImageView)v.findViewById(R.id.listview_daily_status);
        Integer IV = Adapter_ProductList_Daily.get(position).getSelesai();
        if (IV == 1) {
            IV_icon.setImageResource(R.drawable.ic_action_check_blue);
        } else {
            IV_icon.setImageResource(R.drawable.ic_action_check_grey);
        }
        return v;
    }

}
