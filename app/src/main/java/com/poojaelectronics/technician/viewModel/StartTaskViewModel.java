package com.poojaelectronics.technician.viewModel;

import androidx.lifecycle.ViewModel;

import com.poojaelectronics.technician.Repository.StartTaskRepository;
import com.poojaelectronics.technician.Repository.TechnicianServiceStatusRepository;

import java.util.HashMap;

public class StartTaskViewModel extends ViewModel
{
    public StartTaskRepository startTaskRepository = new StartTaskRepository();
    public TechnicianServiceStatusRepository technicianServiceStatusRepository = new TechnicianServiceStatusRepository();

    public void init( String serviceId )
    {
        startTaskRepository = new StartTaskRepository();
        HashMap<String, Object> customerObject = new HashMap<>();
        customerObject.put( "apimethod", "customerdetails" );
        customerObject.put( "cust_id", serviceId );
        startTaskRepository.getCustomerDetails( customerObject );
    }

    public void picked( String serviceId, String status )
    {
        HashMap<String, Object> customerObject = new HashMap<>();
        customerObject.put( "apimethod", "technicianpickup" );
        customerObject.put( "service_id", serviceId );
        customerObject.put( "status", status );
        technicianServiceStatusRepository.getCustomerDetails( customerObject );
    }
}
