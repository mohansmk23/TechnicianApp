package com.poojaelectronics.technician.Retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService
{
        private static final String Api_MainLogin_Url = "http://192.168.1.55/2019/pooja_elctrical/index.php/";
//    private static final String Api_MainLogin_Url = "http://hirephpcoder.com/dev/poojaelectricals/index.php/";
    private static Retrofit retrofit = new Retrofit.Builder().client( getClient() ).baseUrl( Api_MainLogin_Url ).addConverterFactory( GsonConverterFactory.create() ).build();

    public static <S> S createService( Class<S> serviceClass )
    {
        return retrofit.create( serviceClass );
    }

    private static OkHttpClient getClient()
    {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel( HttpLoggingInterceptor.Level.BODY );
        return new OkHttpClient.Builder().addInterceptor( interceptor ).build();
    }
}
