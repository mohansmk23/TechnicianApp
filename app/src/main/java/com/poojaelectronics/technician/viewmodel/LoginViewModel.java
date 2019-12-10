package com.poojaelectronics.technician.viewmodel;

import android.widget.EditText;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.Repository.LoginRepository;
import com.poojaelectronics.technician.model.LoginModel;
import com.poojaelectronics.technician.model.LoginResponse;

import java.util.HashMap;

public class LoginViewModel extends ViewModel
{
    private LoginModel loginModel ;
    private LoginRepository loginRepository ;
    public ObservableField<Integer> emailError = new ObservableField<>();
    public ObservableField<Integer> passwordError = new ObservableField<>();
    private MutableLiveData loginresponse = new MutableLiveData<>();

    public void init()
    {
        loginModel = new LoginModel();
        loginRepository = new LoginRepository();
    }


    public LoginModel getLoginModel()
    {
        return loginModel;
    }

    private boolean isUserValid()
    {
        if( loginModel.getUserName() != null && loginModel.getUserName().length() >= 2 )
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

    public boolean userEditorAction()
    {
        return !isUserValid();
    }

    public boolean passwordEditorAction()
    {
        if( isValid() )
        {
            HashMap<String, Object> loginObject = new HashMap<>();
            loginObject.put( "apimethod", "poojaapi" );
            loginObject.put( "username", loginModel.getUserName() );
            loginObject.put( "password", loginModel.getPassword() );
            loginresponse = loginRepository.login( loginObject );
        }
        return false;
    }

    private boolean isPasswordValid()
    {
        if( loginModel.getPassword() != null && loginModel.getPassword().length() >= 2 )
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

    private boolean isValid()
    {
        return isUserValid() && isPasswordValid();
    }

    public MutableLiveData<LoginResponse> getLoginResponse() {
        return loginRepository.getLoginResponse();
    }

    public MutableLiveData<Boolean> getIsLoading(){
        return  loginRepository.getIsLoading();
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
