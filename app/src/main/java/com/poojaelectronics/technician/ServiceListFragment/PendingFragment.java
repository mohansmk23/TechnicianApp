/*
 * *
 *  * Developed by Saravana  on 8/13/19 4:32 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Company Istrides Technology
 *  * Last modified 8/13/19 4:32 PM
 *  *
 */

package com.poojaelectronics.technician.ServiceListFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.poojaelectronics.technician.Activity.StartTask;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.databinding.ItemPendingListBinding;
import com.poojaelectronics.technician.viewmodel.PendingListViewModel;

import java.util.Objects;

public class PendingFragment extends Fragment
{
    private PendingListViewModel pendingListViewModel;
    private ItemPendingListBinding itemPendingListBinding;
    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
    {
        View pendingFragment = inflater.inflate( R.layout.fragment_recycler_view, container, false );
        pendingListViewModel = new PendingListViewModel();
        pendingListViewModel = ViewModelProviders.of( this ).get( PendingListViewModel.class );
        itemPendingListBinding = DataBindingUtil.setContentView( Objects.requireNonNull( getActivity() ), R.layout.item_pending_list );
        LinearLayout nextButton = pendingFragment.findViewById( R.id.button_next );
        nextButton.setOnClickListener( nextIntent );
        return pendingFragment;
    }

    private View.OnClickListener nextIntent = new View.OnClickListener()
    {
        @Override
        public void onClick( View view )
        {
            Intent startTask = new Intent( getActivity(), StartTask.class );
            startActivity( startTask );
        }
    };
}
