package com.poojaelectronics.technician.viewmodel;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.poojaelectronics.technician.Repository.CompleteTaskRepository;
import com.poojaelectronics.technician.model.CompleteTaskModel;

import java.io.File;

public class CompleteTaskViewModel extends ViewModel
{
    public CompleteTaskModel completeTaskModel = new CompleteTaskModel();
    public CompleteTaskRepository completeTaskRepository = new CompleteTaskRepository();
    public ObservableField<Integer> amountError = new ObservableField<>();
    public ObservableField<Integer> signError = new ObservableField<>();

    public void onComplete( String amount, String remarks, String serviceID,Float rating, File customerSign)
    {
        /*if( completeTaskModel.getAmount().isEmpty() )
        {
            amountError.set( R.string.error_empty_username );
        }
        *//*else if( !completeTaskModel.getSigned() )
        {
            signError.set( R.string.error_empty_username );
        }*//*
        else
        {
        }*/
        completeTaskRepository.setCompleteStatus( serviceID, amount, remarks,String.valueOf( rating ),customerSign );
    }
}
