/*
 * *
 *  * Developed by Saravana  on 8/14/19 10:20 AM
 *  * Copyright (c) 2019 . All rights reserved.
 *  * Company Istrides Technology
 *  * Last modified 8/14/19 10:20 AM
 *  *
 */

package com.poojaelectronics.technician.view.ServiceListFragment;

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
import com.poojaelectronics.technician.common.Session;
import com.poojaelectronics.technician.common.UpdateBadgeCount;
import com.poojaelectronics.technician.databinding.ItemHistoryBinding;
import com.poojaelectronics.technician.model.HistoryListModel;
import com.poojaelectronics.technician.model.HistoryListResponse;
import com.poojaelectronics.technician.viewModel.HistoryListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HistoryFragment extends Fragment
{
    private RecyclerView recyclerView;
    private TextView noData;
    private HistoryListViewModel historyListViewModel;
    private SwipeRefreshLayout pullRefresh;
    private Session session;

    @Nullable
    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState )
    {
        View historyFragment = inflater.inflate( R.layout.fragment_history, container, false );
        session = new Session( getActivity() );
        recyclerView = historyFragment.findViewById( R.id.recyclerView );
        noData = historyFragment.findViewById( R.id.noData );
        pullRefresh = historyFragment.findViewById( R.id.refresh );
        historyListViewModel = ViewModelProviders.of( Objects.requireNonNull( getActivity() ) ).get( HistoryListViewModel.class );
        historyListViewModel.init( session.getTechId() );
        setObserver();
        pullRefresh.setColorSchemeResources( R.color.themeColor1, R.color.themeColor2, R.color.colorPrimary );
        pullRefresh.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                historyListViewModel.init( session.getTechId() );
            }
        } );
        return historyFragment;
    }

    private void setObserver()
    {
        historyListViewModel.historyListRepository.pendingListResponse.observe( this, new Observer<HistoryListResponse>()
        {
            @Override
            public void onChanged( HistoryListResponse loginResponse )
            {
                pullRefresh.setRefreshing( false );
                if( loginResponse != null )
                {
                    if( loginResponse.getOutput().get( 0 ).getStatus().equalsIgnoreCase( "success" ) )
                    {
                        recyclerView.setVisibility( View.VISIBLE );
                        noData.setVisibility( View.GONE );
                        setUpRecyclerView( prepareData( loginResponse.getOutput().get( 0 ).getCompletedList() ) );
                        ( ( UpdateBadgeCount ) getActivity() ).updateHistoryBadgeCount( String.valueOf( loginResponse.getOutput().get( 0 ).getCompletedList().size() ) );
                    }
                    else
                    {
                        recyclerView.setVisibility( View.GONE );
                        noData.setVisibility( View.VISIBLE );
                    }
                }
                else
                {
                    historyListViewModel.historyListRepository.errorResponse.observe( getActivity(), new Observer<String>() {
                        @Override
                        public void onChanged( String s )
                        {
                            Snackbar.make( getView(), s, Snackbar.LENGTH_LONG ).show();
                        }
                    } );

                }
            }
        } );
        historyListViewModel.historyListRepository.isLoading.observe( this, new Observer<Boolean>()
        {
            @Override
            public void onChanged( Boolean aBoolean )
            {
            }
        } );
    }

    private List<HistoryListModel> prepareData( List<HistoryListResponse.Output.CompletedList> pendingList )
    {
        List<HistoryListModel> onGoingModel = new ArrayList<>();
        for( int i = 0; i < pendingList.size(); i++ )
        {
            HistoryListModel pendingListModel = new HistoryListModel();
            pendingListModel.setService_id( pendingList.get( i ).getServiceId() );
            pendingListModel.setService_type( pendingList.get( i ).getServiceType() );
            pendingListModel.setBrand_name( pendingList.get( i ).getBrandName() );
            pendingListModel.setStatus( pendingList.get( i ).getStatus() );
            pendingListModel.setDate( pendingList.get( i ).getDate() );
            pendingListModel.setPhone( pendingList.get( i ).getPhone() );
            pendingListModel.setCustomer_name( pendingList.get( i ).getCustomerName() );
            pendingListModel.setImage_url( pendingList.get( i ).getImageUrl() );
            pendingListModel.setTime( pendingList.get( i ).getTime() );
            pendingListModel.setAmount( pendingList.get( i ).getAmount() );
            pendingListModel.setCancel_remarks( pendingList.get( i ).getCancelRemarks() );
            pendingListModel.setRe_schedule( pendingList.get( i ).getReSchedule() );
            pendingListModel.setRe_date( pendingList.get( i ).getReDate() );
            pendingListModel.setAddress( pendingList.get( i ).getAddress() );
            pendingListModel.setTechnician_name( pendingList.get( i ).getTechnicianName() );
            pendingListModel.setCreated_at( pendingList.get( i ).getCreatedAt() );
            onGoingModel.add( pendingListModel );
        }
        return onGoingModel;
    }

    private void setUpRecyclerView( List<HistoryListModel> appList )
    {
        recyclerView.setHasFixedSize( true );
        recyclerView.setLayoutManager( new LinearLayoutManager( getActivity() ) );
        recyclerView.scheduleLayoutAnimation();
        HistoryListAdapter adapter = new HistoryListAdapter( appList );
        recyclerView.setAdapter( adapter );
    }

    public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.MyViewHolder>
    {
        private List<HistoryListModel> serviceList;

        private class MyViewHolder extends RecyclerView.ViewHolder
        {
            private ItemHistoryBinding itemBinding;

            private MyViewHolder( ItemHistoryBinding binding )
            {
                super( binding.getRoot() );
                itemBinding = binding;
            }
        }

        private HistoryListAdapter( List<HistoryListModel> serviceList )
        {
            this.serviceList = serviceList;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder( @NonNull ViewGroup parent, int viewType )
        {
            ItemHistoryBinding itemPendingListBinding = DataBindingUtil.inflate( LayoutInflater.from( parent.getContext() ), R.layout.item_history, parent, false );
            return new MyViewHolder( itemPendingListBinding );
        }

        @Override
        public void onBindViewHolder( final MyViewHolder holder, int position )
        {
            HistoryListModel model = serviceList.get( position );
            holder.itemBinding.setHistoryItemList( model );
        }

        @Override
        public int getItemCount()
        {
            return serviceList.size();
        }
    }
}
