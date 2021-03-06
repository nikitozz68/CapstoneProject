package com.niksplay.moviesland.adapter.item;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.niksplay.moviesland.R;
import com.niksplay.moviesland.adapter.holder.AbsViewHolder;
import com.niksplay.moviesland.adapter.holder.CreditPagerHolder;
import com.niksplay.moviesland.model.Credit;

import java.util.List;

/**
 * Created by nikita on 21.11.15.
 */
public class ItemPagerCredits extends SimpleItem<List<Credit>> {

    CreditPagerHolder.OnItemSelectedListener mOnItemSelectedListener;

    private int mCount;

    public ItemPagerCredits(List<Credit> data, int count, CreditPagerHolder.OnItemSelectedListener listener) {
        super(data, ItemType.TYPE_CREDITS * count);
        mOnItemSelectedListener = listener;
        mCount = count;
    }

    @Override
    public AbsViewHolder onCreateViewHolder(LayoutInflater inflater, ViewGroup parent) {
        return new CreditPagerHolder(inflater.inflate(R.layout.list_item_pager_credits, parent, false),  mCount,  mOnItemSelectedListener);
    }
}
