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
    private Api api;
    public MutableLiveData<PendingListResponse> pendingListResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorResponse = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public PendingListRepository()
    {
        api = RetrofitService.createService( Api.class );
    }

    public void getPendingList( HashMap<String, Object> body )
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
                else
                {
                    errorResponse.postValue( response.raw().code() + " " + response.raw().message() );
                }
            }

            @Override
            public void onFailure( @NonNull Call<PendingListResponse> call, @NonNull Throwable t )
            {
                isLoading.postValue( false );
                pendingListResponse.postValue( null );
            }
        } );
    }
}
