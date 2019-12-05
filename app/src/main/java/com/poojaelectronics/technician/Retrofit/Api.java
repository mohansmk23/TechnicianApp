package com.poojaelectronics.technician.Retrofit;

import com.poojaelectronics.technician.model.LoginResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api
{
    @POST( "technicianlogin")
    Call<LoginResponse> login( @Body HashMap<String, Object> body);
}
