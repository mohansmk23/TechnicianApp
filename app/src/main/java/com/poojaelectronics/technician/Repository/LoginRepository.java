package com.poojaelectronics.technician.Repository;

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
    private static LoginRepository loginRepository;
    private Api api;
    MutableLiveData<LoginResponse> loginResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public static LoginRepository getInstance()
    {
        if( loginRepository == null )
        {
            loginRepository = new LoginRepository();
        }
        return loginRepository;
    }

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
            public void onResponse( Call<LoginResponse> call, Response<LoginResponse> response )
            {
                isLoading.postValue( false );
                if( response.isSuccessful() )
                {
                    loginResponse.postValue( response.body() );
                }
            }

            @Override
            public void onFailure( Call<LoginResponse> call, Throwable t )
            {
                isLoading.postValue( false );
                loginResponse.postValue( null );
            }
        } );
        return loginResponse;
    }
}
