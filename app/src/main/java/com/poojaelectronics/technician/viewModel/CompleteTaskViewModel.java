package com.poojaelectronics.technician.viewModel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.poojaelectronics.technician.Repository.CompleteTaskRepository;
import com.poojaelectronics.technician.model.CompleteTaskModel;

import java.io.File;

public class CompleteTaskViewModel extends ViewModel
{
    public CompleteTaskModel completeTaskModel = new CompleteTaskModel();
    public CompleteTaskRepository completeTaskRepository = new CompleteTaskRepository();

    public void onComplete( String amount, String remarks, String serviceID,Float rating, File customerSign)
    {
        completeTaskRepository.setCompleteStatus( serviceID, amount, remarks,String.valueOf( rating ),customerSign );
    }
}
