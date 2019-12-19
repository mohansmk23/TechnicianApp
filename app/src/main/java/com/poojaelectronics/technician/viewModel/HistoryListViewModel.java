package com.poojaelectronics.technician.viewModel;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.poojaelectronics.technician.Repository.HistoryListRepository;

import java.util.HashMap;

public class HistoryListViewModel extends ViewModel
{
    public HistoryListRepository historyListRepository = new HistoryListRepository();


    public void init(String tech_id)
    {
        HashMap<String, Object> pendingObject = new HashMap<>();
        pendingObject.put( "apimethod", "techhistory" );
        pendingObject.put( "tech_id",tech_id );
        historyListRepository.getPendingList( pendingObject );
    }

    @BindingAdapter( "list_history_image")
    public static void loadImage( ImageView view, String imageUrl) {
        Glide.with(view.getContext())
             .load(imageUrl)
             .into(view);
    }
}
