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
    private Api api;
    public MutableLiveData<HistoryListResponse> pendingListResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorResponse = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public HistoryListRepository()
    {
        api = RetrofitService.createService( Api.class );
    }


    public void getPendingList( HashMap<String, Object> body )
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
                else
                {
                    errorResponse.postValue( response.raw().code() + " " + response.raw().code() );
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
    }
}
