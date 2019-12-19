package com.poojaelectronics.technician.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.poojaelectronics.technician.Retrofit.Api;
import com.poojaelectronics.technician.Retrofit.RetrofitService;
import com.poojaelectronics.technician.model.StartTaskResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TechnicianServiceStatusRepository
{
    private Api api;
    public MutableLiveData<StartTaskResponse> technicianServiceResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorResponse = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public TechnicianServiceStatusRepository()
    {
        api = RetrofitService.createService( Api.class );
    }

    public void getCustomerDetails( HashMap<String, Object> body )
    {
        isLoading.setValue( true );
        api.technician_picup( body ).enqueue( new Callback<StartTaskResponse>()
        {
            @Override
            public void onResponse( @NonNull Call<StartTaskResponse> call, @NonNull Response<StartTaskResponse> response )
            {
                isLoading.postValue( false );
                if( response.isSuccessful() )
                {
                    technicianServiceResponse.postValue( response.body() );
                }
                else
                {
                    errorResponse.setValue( response.raw().code() + " " + response.raw().message() );
                }
            }

            @Override
            public void onFailure( @NonNull Call<StartTaskResponse> call, @NonNull Throwable t )
            {
                t.printStackTrace();
                isLoading.postValue( false );
                technicianServiceResponse.postValue( null );
            }
        } );
    }
}
