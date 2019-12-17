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
    private static TechnicianServiceStatusRepository technicianServiceStatusRepository;
    private Api api;
    private MutableLiveData<StartTaskResponse> loginResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<StartTaskResponse> getLoginResponse()
    {
        return loginResponse;
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public TechnicianServiceStatusRepository()
    {
        api = RetrofitService.createService( Api.class );
    }

    public MutableLiveData getCustomerDetails( HashMap<String, Object> body )
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
                    loginResponse.postValue( response.body() );
                }
            }

            @Override
            public void onFailure( @NonNull Call<StartTaskResponse> call, @NonNull Throwable t )
            {
                t.printStackTrace();
                isLoading.postValue( false );
                loginResponse.postValue( null );
            }
        } );
        return loginResponse;
    }
}
