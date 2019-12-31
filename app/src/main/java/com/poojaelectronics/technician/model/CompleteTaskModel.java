package com.poojaelectronics.technician.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class CompleteTaskModel extends BaseObservable
{
    private String amount, remarks="";
    private boolean signed;

    @Bindable
    public String getAmount()
    {
        return amount;
    }

    public void setAmount( String amount )
    {
        this.amount = amount;
        notifyPropertyChanged( BR.amount );
    }

    @Bindable
    public String getRemarks()
    {
        return remarks;
    }

    public void setRemarks( String remarks )
    {
        this.remarks = remarks;
        notifyPropertyChanged( BR.remarks );
    }

    /*@Bindable
    public boolean getSigned()
    {
        return signed;
    }

    public void setSigned( boolean signed )
    {
        this.signed = signed;
        notifyPropertyChanged( BR.signed );
    }*/
}
