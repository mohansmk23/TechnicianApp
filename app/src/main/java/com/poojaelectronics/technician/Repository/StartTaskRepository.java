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

public class StartTaskRepository
{
    private Api api;
    public MutableLiveData<StartTaskResponse> customerDetailsResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorResponse = new MutableLiveData<>();
    public MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public StartTaskRepository()
    {
        api = RetrofitService.createService( Api.class );
    }

    public void getCustomerDetails( HashMap<String, Object> body )
    {
        isLoading.setValue( true );
        api.customer_details( body ).enqueue( new Callback<StartTaskResponse>()
        {
            @Override
            public void onResponse( @NonNull Call<StartTaskResponse> call, @NonNull Response<StartTaskResponse> response )
            {
                isLoading.setValue( false );
                if( response.isSuccessful() )
                {
                    customerDetailsResponse.postValue( response.body() );
                }
                else
                {
                    errorResponse.postValue( response.raw().code() + " " + response.raw().message() );
                }
            }

            @Override
            public void onFailure( @NonNull Call<StartTaskResponse> call, @NonNull Throwable t )
            {
                errorResponse.postValue( t.getCause() + " " + t.getMessage() );
                isLoading.setValue( false );
                t.printStackTrace();
                customerDetailsResponse.postValue( null );
            }

        } );
    }
}
