package com.poojaelectronics.technician.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.poojaelectronics.technician.Retrofit.Api;
import com.poojaelectronics.technician.Retrofit.RetrofitService;
import com.poojaelectronics.technician.model.CompleteResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteTaskRepository
{
    private Api api;
    private MutableLiveData<CompleteResponse> completeTaskResponse = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsLoading()
    {
        return isLoading;
    }

    public MutableLiveData<CompleteResponse> getCompleteTaskResponse()
    {
        return completeTaskResponse;
    }

    public CompleteTaskRepository()
    {
        api = RetrofitService.createService( Api.class );
    }

    public void setCompleteStatus( String serviceID, String serviceAmount, String remarks, String technicianRating, File customerSign )
    {
        RequestBody apimethod = RequestBody.create( MediaType.parse( "multipart/form-data" ), "completestatus" );
        RequestBody serviceId = RequestBody.create( MediaType.parse( "multipart/form-data" ), serviceID );
        RequestBody amount = RequestBody.create( MediaType.parse( "multipart/form-data" ), serviceAmount );
        RequestBody reason = RequestBody.create( MediaType.parse( "multipart/form-data" ), remarks );
        RequestBody status = RequestBody.create( MediaType.parse( "multipart/form-data" ), "Completed" );
        RequestBody rating = RequestBody.create( MediaType.parse( "multipart/form-data" ), technicianRating );
        RequestBody file = RequestBody.create( MediaType.parse( "multipart/form-data" ), customerSign );
        MultipartBody.Part customerSignature = MultipartBody.Part.createFormData( "upload_file", customerSign.getName(), file );
        isLoading.setValue( true );
        api.complete_status( apimethod, serviceId, amount, reason, status, rating, customerSignature ).enqueue( new Callback<CompleteResponse>()
        {
            @Override
            public void onResponse( @NonNull Call<CompleteResponse> call, @NonNull Response<CompleteResponse> response )
            {
                isLoading.postValue( false );
                if( response.isSuccessful() )
                {
                    completeTaskResponse.postValue( response.body() );
                }
            }

            @Override
            public void onFailure( @NonNull Call<CompleteResponse> call, @NonNull Throwable t )
            {
                t.printStackTrace();
                isLoading.postValue( false );
                completeTaskResponse.postValue( null );
            }
        } );
    }
}
