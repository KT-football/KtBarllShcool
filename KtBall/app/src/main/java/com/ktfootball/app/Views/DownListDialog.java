package com.ktfootball.app.Views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.frame.app.view.NumberProgressBar.NumberProgressBar;
import com.ktfootball.app.Adapter.DownListAdapter;
import com.ktfootball.app.Entity.LoadFile;

import java.util.List;

/**
 * Created by jy on 16/6/21.
 */
public class DownListDialog extends Dialog {

    private RecyclerView rc;
    private TextView cancel;
    private List<LoadFile> mFileList;
    private DownListAdapter downListAdapter;

    public DownListDialog(Context context, int themeResId,List<LoadFile> mFileList) {
        super(context, themeResId);
        this.mFileList = mFileList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.chongwuzhiwu.frame.R.layout.layout_downlist);
        init();
    }

    private void init() {
        rc = (RecyclerView) findViewById(com.chongwuzhiwu.frame.R.id.layout_downlist_rc);
        LinearLayoutManager ll = new LinearLayoutManager(getContext());
        rc.setLayoutManager(ll);
        downListAdapter = new DownListAdapter(rc,mFileList);
        rc.setAdapter(downListAdapter);
        cancel = (TextView) findViewById(com.chongwuzhiwu.frame.R.id.layout_downlist_cancle);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDismiss();
            }
        });
    }

    public void notifyItemChanged(int index){
        if(downListAdapter != null){
            downListAdapter.notifyItemChanged(index);
        }
    }

    public void dialogDismiss() {
        dismiss();
    }

}
