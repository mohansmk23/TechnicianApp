package com.poojaelectronics.technician.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;

public class LoginModel extends BaseObservable
{
    private String userName;
    private String password;

    @Bindable
    public String getUserName()
    {
        return userName;
    }

    public void setUserName( String userName )
    {
        this.userName = userName;
        notifyPropertyChanged( BR.userName );
    }

    @Bindable
    public String getPassword()
    {
        return password;
    }

    public void setPassword( String password )
    {
        this.password = password;
        notifyPropertyChanged( BR.password );
    }

}
