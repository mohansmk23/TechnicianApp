package com.poojaelectronics.technician.Retrofit;

import com.poojaelectronics.technician.model.CompleteResponse;
import com.poojaelectronics.technician.model.HistoryListResponse;
import com.poojaelectronics.technician.model.LoginResponse;
import com.poojaelectronics.technician.model.PendingListResponse;
import com.poojaelectronics.technician.model.StartTaskResponse;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @POST( "technicianpickup" )
    Call<StartTaskResponse> technician_picup( @Body HashMap<String, Object> body );

    @Multipart
    @POST( "completestatus" )
    Call<CompleteResponse> complete_status( @Part( "apimethod" ) RequestBody apimethod, @Part( "service_id" ) RequestBody service_id, @Part( "amount" ) RequestBody amount, @Part( "reason" ) RequestBody remarks, @Part( "status" ) RequestBody status,@Part( "technician_rating" ) RequestBody rating,@Part MultipartBody.Part file );
}
