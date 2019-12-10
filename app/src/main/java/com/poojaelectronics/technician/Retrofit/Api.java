package com.poojaelectronics.technician.Retrofit;

import com.poojaelectronics.technician.model.HistoryListResponse;
import com.poojaelectronics.technician.model.LoginResponse;
import com.poojaelectronics.technician.model.PendingListResponse;
import com.poojaelectronics.technician.model.StartTaskResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Api
{
    @POST( "technicianlogin" )
    Call<LoginResponse> login( @Body HashMap<String, Object> body );

    @POST( "technicianloginlist" )
    Call<PendingListResponse> pending_list( @Body HashMap<String, Object> body );

    @POST( "techhistory" )
    Call<HistoryListResponse> history_list( @Body HashMap<String, Object> body );

    @POST( "customerdetails" )
    Call<StartTaskResponse> customer_details( @Body HashMap<String, Object> body );
}
