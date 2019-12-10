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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.poojaelectronics.technician.activity.EventHandlers;
import com.poojaelectronics.technician.view.StartTask;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.databinding.ItemPendingListBinding;
import com.poojaelectronics.technician.model.PendingListModel;
import com.poojaelectronics.technician.model.PendingListResponse;
import com.poojaelectronics.technician.viewmodel.PendingListViewModel;

import java.util.ArrayList;
import java.util.List;

public class PendingFragment extends Fragment
{
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
    {
        View pendingFragment = inflater.inflate( R.layout.fragment_recycler_view, container, false );
        recyclerView = pendingFragment.findViewById( R.id.recyclerView );
        PendingListViewModel pendingListViewModel = ViewModelProviders.of( this ).get( PendingListViewModel.class );
        pendingListViewModel.init();
        pendingListViewModel.getMvdPendingListResponse().observe( this, new Observer<PendingListResponse>()
        {
            @Override
            public void onChanged( PendingListResponse loginResponse )
            {
                if( loginResponse != null )
                {
                    if( loginResponse.getOutput().get( 0 ).getStatus().equalsIgnoreCase( "success" ) )
                    {
                        recyclerView.setVisibility( View.VISIBLE );
                        setUpRecyclerView( prepareData( loginResponse.getOutput().get( 0 ).getBookinglist() ) );
                    }
                }
            }
        } );
        return pendingFragment;
    }

    private List<PendingListModel> prepareData( List<PendingListResponse.Output.Bookinglist> pendingList )
    {
        List<PendingListModel> onGoingModel = new ArrayList<>();
        for( int i = 0; i < pendingList.size(); i++ )
        {
            PendingListModel pendingListModel = new PendingListModel();
            pendingListModel.setServiceId( pendingList.get( i ).getServiceId() );
            pendingListModel.setService_type( pendingList.get( i ).getServiceType() );
            pendingListModel.setBrand( pendingList.get( i ).getBrand() );
            pendingListModel.setStatus( pendingList.get( i ).getStatus() );
            pendingListModel.setDate( pendingList.get( i ).getDate() );
            pendingListModel.setPhone_number( pendingList.get( i ).getPhoneNumber() );
            pendingListModel.setCustomer_name( pendingList.get( i ).getCustomerName() );
            pendingListModel.setImage_url( pendingList.get( i ).getImageUrl() );
            pendingListModel.setTime( pendingList.get( i ).getTime() );
            pendingListModel.setCancel_remarks( pendingList.get( i ).getCancelRemarks() );
            pendingListModel.setRe_schedule( pendingList.get( i ).getReSchedule() );
            pendingListModel.setRe_date( pendingList.get( i ).getReDate() );
            pendingListModel.setAddress( pendingList.get( i ).getAddress() );
            pendingListModel.setTechnician( pendingList.get( i ).getTechnician() );
            pendingListModel.setCreated_at( pendingList.get( i ).getCreatedAt() );
            onGoingModel.add( pendingListModel );
        }
        return onGoingModel;
    }

    private void setUpRecyclerView( List<PendingListModel> appList )
    {
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        recyclerView.scheduleLayoutAnimation();
        PendingListAdapter adapter = new PendingListAdapter( appList );
        recyclerView.setAdapter( adapter );
    }

    public class PendingListAdapter extends RecyclerView.Adapter<PendingListAdapter.MyViewHolder>
    {
        private List<PendingListModel> serviceList;

        private class MyViewHolder extends RecyclerView.ViewHolder
        {
            private ItemPendingListBinding itemBinding;

            private MyViewHolder( ItemPendingListBinding binding )
            {
                super( binding.getRoot() );
                itemBinding = binding;
            }
        }

        private PendingListAdapter(  List<PendingListModel> serviceList )
        {
            this.serviceList = serviceList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType )
        {
            ItemPendingListBinding itemPendingListBinding = DataBindingUtil.inflate( LayoutInflater.from( parent.getContext() ), R.layout.item_pending_list, parent, false );
            return new MyViewHolder( itemPendingListBinding );
        }

        @Override
        public void onBindViewHolder( final MyViewHolder holder, int position )
        {
            PendingListModel model = serviceList.get( position );
            holder.itemBinding.setItemPendingList( model );
            holder.itemBinding.setClickHandler( new EventHandlers() {
                @Override
                public void OnItemClick( View view )
                {
                    startActivity( new Intent( getActivity(),StartTask.class ) );
                }
            } );
            /*holder.itemBinding.setC( new EventHandlers() {
                @Override
                public void OnItemClick()
                {
                    startActivity( new Intent( getActivity(),StartTask.class ) );
                }
            } );*/
            /*holder.itemBinding.setClickHandler( new EventHandlers() {
                @Override
                public void OngoingMenu( PendingListModel model, View v )
                {
                }

                @Override
                public void OnitemClick( PendingListModel model )
                {
                    startActivity( new Intent( getActivity(), StartTask.class ) );
                }
            } );*/

        }

        @Override
        public int getItemCount()
        {
            return serviceList.size();
        }
    }
}
