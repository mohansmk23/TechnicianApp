/*
 * *
 *  * Developed by Saravana  on 8/14/19 10:20 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Company Istrides Technology
 *  * Last modified 8/14/19 10:20 AM
 *  *
 */

package com.poojaelectronics.technician.ServiceListFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.poojaelectronics.technician.R;

public class HistoryFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
    {
        View historyFragment = inflater.inflate( R.layout.fragment_history , container, false );
        return historyFragment  ;
    }
}
