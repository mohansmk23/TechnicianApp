package com.poojaelectronics.technician.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.poojaelectronics.technician.Retrofit.Api;
import com.poojaelectronics.technician.Retrofit.RetrofitService;
import com.poojaelectronics.technician.model.PendingListResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingListRepository
{
    private static PendingListRepository pendingListRepository;
    private Api api;
    private MutableLiveData<PendingListResponse> pendingListResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public PendingListRepository()
    {
        api = RetrofitService.createService( Api.class );
    }

    public MutableLiveData<PendingListResponse> getPendingListResponse()
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
        api.pending_list( body ).enqueue( new Callback<PendingListResponse>()
        {
            @Override
            public void onResponse( @NonNull Call<PendingListResponse> call, @NonNull Response<PendingListResponse> response )
            {
                isLoading.postValue( false );
                if( response.isSuccessful() )
                {
                    pendingListResponse.postValue( response.body() );
                }
            }

            @Override
            public void onFailure( @NonNull Call<PendingListResponse> call, @NonNull Throwable t )
            {
                t.printStackTrace();
                isLoading.postValue( false );
                pendingListResponse.postValue( null );
            }
        } );
        return pendingListResponse;
    }
}
