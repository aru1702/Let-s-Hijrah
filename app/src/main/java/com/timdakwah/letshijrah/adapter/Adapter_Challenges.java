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
import android.widget.ImageView;
import android.widget.TextView;

import com.timdakwah.letshijrah.R;
import com.timdakwah.letshijrah.model.Product_Challenges;

import java.util.List;

public class Adapter_Challenges extends BaseAdapter {
    /**
     * Mendefinisikan variabel Context dan List yang digunakan.
     * Di sini menggunakan List dari Product_Challenge.java
     */
    private Context Adapter_Context_Challenges;
    private List<Product_Challenges> Adapter_ProductList_Challenges;

    public Adapter_Challenges(Context adapter_Context_Challenges, List<Product_Challenges> adapter_ProductList_Challenges) {
        this.Adapter_Context_Challenges = adapter_Context_Challenges;
        this.Adapter_ProductList_Challenges = adapter_ProductList_Challenges;
    }

    public int getCount() {
        return Adapter_ProductList_Challenges.size();
    }


    public Object getItem(int position) {
        return Adapter_ProductList_Challenges.get(position);
    }


    public long getItemId(int position) {
        return Adapter_ProductList_Challenges.get(position).getId();
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
        View v = View.inflate(Adapter_Context_Challenges, R.layout.listview_challenge_level, null);

        TextView TV_Judul = (TextView)v.findViewById(R.id.listview_challenge_level_title);
        TV_Judul.setText(Adapter_ProductList_Challenges.get(position).getJudul());

        ImageView IV_Medal = (ImageView)v.findViewById(R.id.listview_challenge_icon);
        Integer myID = Adapter_ProductList_Challenges.get(position).getId();
        if (myID == 1) {
            IV_Medal.setImageResource(R.drawable.medal_bronze);
        } else if (myID == 2) {
            IV_Medal.setImageResource(R.drawable.medal_silver);
        } else if (myID == 3) {
            IV_Medal.setImageResource(R.drawable.medal_gold);
        }
        return v;
    }
}
