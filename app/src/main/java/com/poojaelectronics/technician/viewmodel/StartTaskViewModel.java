package com.poojaelectronics.technician.viewmodel;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poojaelectronics.technician.Repository.StartTaskRepository;
import com.poojaelectronics.technician.Repository.TechnicianServiceStatusRepository;

import java.util.HashMap;

public class StartTaskViewModel extends ViewModel
{
    StartTaskRepository startTaskRepository = new StartTaskRepository();
    public TechnicianServiceStatusRepository technicianServiceStatusRepository = new TechnicianServiceStatusRepository();
    private MutableLiveData startTaskResponse = new MutableLiveData<>();

    public TechnicianServiceStatusRepository getTechnicianServiceStatusRepository()
    {
        return technicianServiceStatusRepository;
    }

    private MutableLiveData technicianResponse = new MutableLiveData<>();

    public MutableLiveData getTechnicianResponse()
    {
        return technicianResponse;
    }

    public MutableLiveData getStartTaskResponse()
    {
        return startTaskResponse;
    }

    public void init( String serviceId )
    {
        startTaskRepository = new StartTaskRepository();
        HashMap<String, Object> customerObject = new HashMap<>();
        customerObject.put( "apimethod", "customerdetails" );
        customerObject.put( "cust_id", serviceId );
        startTaskResponse = startTaskRepository.getCustomerDetails( customerObject );
    }

    public void picked( String serviceId, String status )
    {
        HashMap<String, Object> customerObject = new HashMap<>();
        customerObject.put( "apimethod", "technicianpickup" );
        customerObject.put( "service_id", serviceId );
        customerObject.put( "status", status );
        technicianResponse = technicianServiceStatusRepository.getCustomerDetails( customerObject );
    }
}
