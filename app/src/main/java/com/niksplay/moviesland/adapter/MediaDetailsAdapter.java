package com.niksplay.moviesland.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.niksplay.moviesland.R;
import com.niksplay.moviesland.adapter.holder.AbsViewHolder;
import com.niksplay.moviesland.adapter.holder.LabelHolder;
import com.niksplay.moviesland.adapter.holder.MediaDetailHeaderHolder;
import com.niksplay.moviesland.adapter.holder.MediaImagesHolder;
import com.niksplay.moviesland.adapter.holder.PersonsPagerHolder;
import com.niksplay.moviesland.adapter.holder.MediaReviewHolder;
import com.niksplay.moviesland.adapter.holder.MediasPagerHolder;
import com.niksplay.moviesland.adapter.item.ItemType;

/**
 * Created by nikita on 21.11.15.
 */
public class MediaDetailsAdapter extends AbsAdapter {

    @Override
    public AbsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ItemType.TYPE_MEDIA_HEADER:
                return new MediaDetailHeaderHolder(inflater.inflate(R.layout.list_item_media_header, parent, false));
            case ItemType.TYPE_MEDIA_IMAGES:
                return new MediaImagesHolder(inflater.inflate(R.layout.list_item_media_images, parent, false));
            case ItemType.TYPE_LABEL:
                return new LabelHolder(inflater.inflate(R.layout.list_item_label, parent, false));
            case ItemType.TYPE_PERSONS:
                return new PersonsPagerHolder(inflater.inflate(R.layout.list_item_media_persons, parent, false));
            case ItemType.TYPE_MEDIAS:
                return new MediasPagerHolder(inflater.inflate(R.layout.list_item_medias, parent, false));
            case ItemType.TYPE_REVIEW:
                return new MediaReviewHolder(inflater.inflate(R.layout.list_item_review, parent, false));
            default:
                throw new IllegalArgumentException("Unsupported view type " + viewType);
        }
    }


}
