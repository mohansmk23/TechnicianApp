package com.poojaelectronics.technician.viewModel;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.poojaelectronics.technician.Repository.PendingListRepository;

import java.util.HashMap;

public class PendingListViewModel extends ViewModel
{
   public PendingListRepository pendingListRepository = new PendingListRepository();


   public void init(String techId)
   {
      HashMap<String, Object> pendingObject = new HashMap<>();
      pendingObject.put( "apimethod", "technician_list" );
      pendingObject.put( "tech_id", techId );
      pendingListRepository.getPendingList( pendingObject );
   }

   @BindingAdapter( "list_image")
   public static void loadImage( ImageView view, String imageUrl) {
      Glide.with(view.getContext())
           .load(imageUrl)
           .into(view);
   }

}
