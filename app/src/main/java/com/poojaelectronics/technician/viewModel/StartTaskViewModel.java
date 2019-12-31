package com.poojaelectronics.technician.viewModel;

import androidx.lifecycle.ViewModel;

import com.poojaelectronics.technician.Repository.CompleteTaskRepository;
import com.poojaelectronics.technician.Repository.StartTaskRepository;
import com.poojaelectronics.technician.Repository.TechnicianServiceStatusRepository;
import com.poojaelectronics.technician.model.CompleteTaskModel;

import java.io.File;
import java.util.HashMap;

public class StartTaskViewModel extends ViewModel
{
    public StartTaskRepository startTaskRepository = new StartTaskRepository();
    public CompleteTaskModel completeTaskModel = new CompleteTaskModel();
    public CompleteTaskRepository completeTaskRepository = new CompleteTaskRepository();

    public void onComplete( String amount, String remarks, String serviceID, Float rating, File customerSign )
    {
        completeTaskRepository.setCompleteStatus( serviceID, amount, remarks, String.valueOf( rating ), customerSign );
    }

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
