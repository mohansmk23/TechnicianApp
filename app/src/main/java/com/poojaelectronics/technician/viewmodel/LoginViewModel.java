package com.poojaelectronics.technician.viewmodel;

import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.model.LoginModel;

public class LoginViewModel extends ViewModel
{
    private LoginModel loginModel = new LoginModel();
    public ObservableField<Integer> emailError = new ObservableField<>();
    public ObservableField<Integer> passwordError = new ObservableField<>();

    public LoginModel getLoginModel()
    {
        return loginModel;
    }

    public boolean isUserValid()
    {
        if( loginModel.getUserName() != null && loginModel.getUserName().length() > 2 )
        {
            emailError.set( null );
            return true;
        }
        else
        {
            if( loginModel.getUserName() == null || loginModel.getUserName().isEmpty() )
            {
                emailError.set( R.string.error_empty_username );
            }
            else if( loginModel.getUserName().length() < 2 )
            {
                emailError.set( R.string.error_length_username );
            }
        }
        return false;
    }

    public boolean isPasswordValid()
    {
        if( loginModel.getPassword() != null && loginModel.getPassword().length() > 2 )
        {
            passwordError.set( null );
            return true;
        }
        else
        {
            if( loginModel.getPassword() == null || loginModel.getPassword().isEmpty() )
            {
                passwordError.set( R.string.error_empty_password );
            }
            else if( loginModel.getPassword().length() < 2 )
            {
                passwordError.set( R.string.error_length_password );
            }
        }
        return false;
    }

    public boolean isValid()
    {
        return isUserValid() && isPasswordValid();
    }

    @BindingAdapter( "error" )
    public static void setError( EditText editText, Object strOrResId )
    {
        if( strOrResId instanceof Integer )
        {
            editText.setError( editText.getContext().getString( ( Integer ) strOrResId ) );
        }
        else
        {
            editText.setError( ( String ) strOrResId );
        }
    }

    @BindingAdapter( "android:onEditorAction" )
    public static void onEditorAction( TextView view, TextView.OnEditorActionListener onEditorActionListener )
    {
        view.setOnEditorActionListener( onEditorActionListener );
    }
}
