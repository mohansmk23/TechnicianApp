package com.poojaelectronics.technician.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poojaelectronics.technician.Repository.StartTaskRepository;

import java.util.HashMap;

public class StartTaskViewModel extends ViewModel
{

    StartTaskRepository startTaskRepository ;

    public MutableLiveData getStartTaskResponse()
    {
        return startTaskResponse;
    }

    private MutableLiveData startTaskResponse = new MutableLiveData<>();
    public void init()
    {
        startTaskRepository = new StartTaskRepository();
        HashMap<String, Object> customerObject = new HashMap<>();
        customerObject.put( "apimethod", "customerdetails" );
        customerObject.put( "cust_id", "1" );
        startTaskResponse = startTaskRepository.getCustomerDetails(customerObject);

    }
}
