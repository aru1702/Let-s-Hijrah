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
import android.os.Handler;

import com.timdakwah.letshijrah.R;
import com.timdakwah.letshijrah.model.Product_ChallengesList;

import java.util.List;
import java.util.TimerTask;

public class Adapter_ChallengesList extends BaseAdapter {
    /**
     * Mendefinisikan variabel Context dan List yang digunakan.
     * Di sini menggunakan List dari Product_Challenge.java
     */
    private Context Adapter_Context_ChallengesList;
    private List<Product_ChallengesList> Adapter_ProductList_ChallengesList;

    public Adapter_ChallengesList(Context adapter_Context_ChallengesList, List<Product_ChallengesList> adapter_ProductList_ChallengesList) {
        this.Adapter_Context_ChallengesList = adapter_Context_ChallengesList;
        this.Adapter_ProductList_ChallengesList = adapter_ProductList_ChallengesList;
    }

    public int getCount() {
        return Adapter_ProductList_ChallengesList.size();
    }


    public Object getItem(int position) {
        return Adapter_ProductList_ChallengesList.get(position);
    }


    public long getItemId(int position) {
        return Adapter_ProductList_ChallengesList.get(position).getId();
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
        View v = View.inflate(Adapter_Context_ChallengesList, R.layout.listview_challenges_list, null);
        TextView TV_Judul = (TextView)v.findViewById(R.id.listview_challenges_list_title);
        TextView TV_SubJudul = (TextView)v.findViewById(R.id.listview_challenges_list_subtitle);
        Integer getSelesai = Adapter_ProductList_ChallengesList.get(position).getSelesai();



        TV_Judul.setText(Adapter_ProductList_ChallengesList.get(position).getJudul());
        if (getSelesai == 0) {
            TV_SubJudul.setText("Belum selesai!");
        } else if (getSelesai == 1){
            TV_SubJudul.setText("Sudah selesai!");
        } else {
            TV_SubJudul.setText("Error data received!");
        }
        return v;
    }
}
