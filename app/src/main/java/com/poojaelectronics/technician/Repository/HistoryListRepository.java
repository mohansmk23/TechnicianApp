package com.poojaelectronics.technician.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.poojaelectronics.technician.Retrofit.Api;
import com.poojaelectronics.technician.Retrofit.RetrofitService;
import com.poojaelectronics.technician.model.HistoryListResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryListRepository
{
    private static PendingListRepository pendingListRepository;
    private Api api;
    private MutableLiveData<HistoryListResponse> pendingListResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public HistoryListRepository()
    {
        api = RetrofitService.createService( Api.class );
    }

    public MutableLiveData<HistoryListResponse> getPendingListResponse()
    {
        return pendingListResponse;
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public MutableLiveData getPendingList( HashMap<String, Object> body )
    {
        isLoading.setValue( true );
        api.history_list( body ).enqueue( new Callback<HistoryListResponse>()
        {
            @Override
            public void onResponse( @NonNull Call<HistoryListResponse> call, @NonNull Response<HistoryListResponse> response )
            {
                isLoading.postValue( false );
                if( response.isSuccessful() )
                {
                    pendingListResponse.postValue( response.body() );
                }
            }

            @Override
            public void onFailure( @NonNull Call<HistoryListResponse> call, @NonNull Throwable t )
            {
                t.printStackTrace();
                isLoading.postValue( false );
                pendingListResponse.postValue( null );
            }
        } );
        return pendingListResponse;
    }
}
