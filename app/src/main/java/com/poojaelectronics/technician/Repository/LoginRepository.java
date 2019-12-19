package com.poojaelectronics.technician.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.poojaelectronics.technician.Retrofit.Api;
import com.poojaelectronics.technician.Retrofit.RetrofitService;
import com.poojaelectronics.technician.model.LoginResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository
{
    private Api api;
    private MutableLiveData<LoginResponse> loginResponse = new MutableLiveData<>();
    public MutableLiveData<String> errorResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<LoginResponse> getLoginResponse()
    {
        return loginResponse;
    }

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public LoginRepository()
    {
        api = RetrofitService.createService( Api.class );
    }

    public MutableLiveData login( HashMap<String, Object> body )
    {
        isLoading.setValue( true );
        api.login( body ).enqueue( new Callback<LoginResponse>()
        {
            @Override
            public void onResponse( @NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response )
            {
                isLoading.postValue( false );
                if( response.isSuccessful() )
                {
                    loginResponse.postValue( response.body() );
                }
                else
                {
                    errorResponse.postValue( response.raw().code() + " " + response.raw().code() );
                }

            }

            @Override
            public void onFailure( @NonNull Call<LoginResponse> call, @NonNull Throwable t )
            {
                t.printStackTrace();
                isLoading.postValue( false );
                loginResponse.postValue( null );
            }
        } );
        return loginResponse;
    }
}
