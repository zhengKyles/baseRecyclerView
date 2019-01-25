package com.kyle.baserecyclerviewdemo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.kyle.baserecyclerview.BaseAdapter;
import com.kyle.baserecyclerview.LRecyclerView;
import com.kyle.baserecyclerviewdemo.databinding.ItemBinding;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private LRecyclerView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list=findViewById(R.id.list);
        Adapter adapter=new Adapter();
        list.setAdapter(adapter);
        adapter.setNewData(Arrays.asList(new String[20]));
    }
    private class Adapter extends BaseAdapter<String,ItemBinding>{

        public Adapter() {
            super(R.layout.item);
        }

        @Override
        protected void convert(ItemBinding binding, int position, String item) {

        }
    }
}
