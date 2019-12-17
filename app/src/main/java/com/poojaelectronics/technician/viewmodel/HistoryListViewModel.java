package com.poojaelectronics.technician.viewmodel;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poojaelectronics.technician.Repository.HistoryListRepository;
import com.poojaelectronics.technician.model.HistoryListResponse;

import java.util.HashMap;

public class HistoryListViewModel extends ViewModel
{
    public static final String MyPREFERENCES = "MyPrefs" ;
    HistoryListResponse historyListResponse;
    private MutableLiveData mvdPendingListResponse = new MutableLiveData<>();

    public MutableLiveData getMvdPendingListResponse()
    {
        return mvdPendingListResponse;
    }

    public void init(String tech_id)
    {
        HistoryListRepository historyListRepository = new HistoryListRepository();
        historyListResponse = new HistoryListResponse();
        HashMap<String, Object> pendingObject = new HashMap<>();
        pendingObject.put( "apimethod", "techhistory" );
        pendingObject.put( "tech_id",tech_id );
        mvdPendingListResponse = historyListRepository.getPendingList( pendingObject );
    }
}
