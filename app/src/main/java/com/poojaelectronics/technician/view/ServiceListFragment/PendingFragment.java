/*
 * *
 *  * Developed by Saravana  on 8/13/19 4:32 PM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Company Istrides Technology
 *  * Last modified 8/13/19 4:32 PM
 *  *
 */

package com.poojaelectronics.technician.view.ServiceListFragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.poojaelectronics.technician.R;
import com.poojaelectronics.technician.common.EventHandlers;
import com.poojaelectronics.technician.common.LoadingDialog;
import com.poojaelectronics.technician.common.Session;
import com.poojaelectronics.technician.common.UpdateBadgeCount;
import com.poojaelectronics.technician.databinding.ItemPendingListBinding;
import com.poojaelectronics.technician.model.PendingListModel;
import com.poojaelectronics.technician.model.PendingListResponse;
import com.poojaelectronics.technician.view.StartTask;
import com.poojaelectronics.technician.viewModel.PendingListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PendingFragment extends Fragment
{
    private RecyclerView recyclerView;
    private TextView noData;
    private Session session;
    private SwipeRefreshLayout pullRefresh;
    private PendingListViewModel pendingListViewModel;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
    {
        View pendingFragment = inflater.inflate( R.layout.fragment_recycler_view, container, false );
        recyclerView = pendingFragment.findViewById( R.id.recyclerView );
        noData = pendingFragment.findViewById( R.id.noData );
        pullRefresh = pendingFragment.findViewById( R.id.refresh );
        pendingListViewModel = ViewModelProviders.of( this ).get( PendingListViewModel.class );
        session = new Session( getActivity() );
        pendingListViewModel.init( session.getTechId() );
        setupObservers();
        pullRefresh.setColorSchemeResources( R.color.themeColor1, R.color.themeColor2, R.color.colorPrimary );
        pullRefresh.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                pendingListViewModel.init( session.getTechId() );
            }
        } );
        return pendingFragment;
    }

    private void setupObservers()
    {
        pendingListViewModel.pendingListRepository.pendingListResponse.observe( this, new Observer<PendingListResponse>()
        {
            @Override
            public void onChanged( PendingListResponse pendingListResponse )
            {
                pullRefresh.setRefreshing( false );
                if( pendingListResponse != null )
                {
                    if( pendingListResponse.getOutput().get( 0 ).getStatus().equalsIgnoreCase( "success" ) )
                    {
                        if( pendingListResponse.getOutput().get( 0 ).getBookinglist().size() > 0 )
                        {
                            recyclerView.setVisibility( View.VISIBLE );
                            noData.setVisibility( View.GONE );
                            setUpRecyclerView( prepareData( pendingListResponse.getOutput().get( 0 ).getBookinglist() ) );
                            ( ( UpdateBadgeCount ) Objects.requireNonNull( getActivity() ) ).updatePendingBadgeCount( String.valueOf( pendingListResponse.getOutput().get( 0 ).getBookinglist().size() ) );
                        }
                        else
                        {
                            recyclerView.setVisibility( View.GONE );
                            noData.setVisibility( View.VISIBLE );
                        }
                    }
                    else
                    {
                        recyclerView.setVisibility( View.GONE );
                        noData.setVisibility( View.VISIBLE );
                    }
                }
                else
                {
                    pendingListViewModel.pendingListRepository.errorResponse.observe( Objects.requireNonNull( getActivity() ), new Observer<String>()
                    {
                        @Override
                        public void onChanged( String s )
                        {
                            Snackbar.make( Objects.requireNonNull( getView() ), s, Snackbar.LENGTH_LONG ).show();
                        }
                    } );
                }
            }
        } );
        pendingListViewModel.pendingListRepository.isLoading.observe( this, new Observer<Boolean>()
        {
            @Override
            public void onChanged( Boolean aBoolean )
            {
                if( aBoolean )
                {
                    LoadingDialog.showDialog( getActivity() );
                }
                else
                {
                    LoadingDialog.dismiss();
                }
            }
        } );
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

        private PendingListAdapter( List<PendingListModel> serviceList )
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
            final PendingListModel model = serviceList.get( position );
            holder.itemBinding.setItemPendingList( model );
            holder.itemBinding.setClickHandler( new EventHandlers()
            {
                @Override
                public void OnItemClick( View view )
                {
                    Intent intent = new Intent( getActivity(), StartTask.class );
                    intent.putExtra( "service_id", model.getServiceId() );
                    intent.putExtra( "status", model.getStatus() );
                    startActivity( intent );
                }
            } );
        }

        @Override
        public int getItemCount()
        {
            return serviceList.size();
        }
    }

    public BroadcastReceiver myReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive( Context context, Intent intent )
        {
            pendingListViewModel.init( session.getTechId() );
        }
    };
}
