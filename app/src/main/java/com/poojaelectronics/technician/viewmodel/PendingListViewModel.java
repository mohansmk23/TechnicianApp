package com.poojaelectronics.technician.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poojaelectronics.technician.Repository.PendingListRepository;
import com.poojaelectronics.technician.model.PendingListResponse;

import java.util.HashMap;

public class PendingListViewModel extends ViewModel
{
   private MutableLiveData mvdPendingListResponse = new MutableLiveData<>();
   private PendingListResponse pendingListResponse;

   public MutableLiveData getMvdPendingListResponse()
   {
      return mvdPendingListResponse;
   }

   public void init()
   {
      PendingListRepository pendingListRepository = new PendingListRepository();
      pendingListResponse = new PendingListResponse();
      HashMap<String, Object> pendingObject = new HashMap<>();
      pendingObject.put( "apimethod", "technician_list" );
      pendingObject.put( "tech_id", "2" );
      mvdPendingListResponse = pendingListRepository.getPendingList( pendingObject );
   }

}
