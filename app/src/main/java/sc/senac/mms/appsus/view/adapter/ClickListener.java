package sc.senac.mms.appsus.view.adapter;

import android.view.View;

public interface ClickListener {
    void onItemClick(int position, View v);
    boolean onItemLongClick(int position, View v);
}
