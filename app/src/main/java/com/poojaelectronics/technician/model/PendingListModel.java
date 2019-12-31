package com.poojaelectronics.technician.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.recyclerview.widget.DiffUtil;

public class PendingListModel implements Parcelable
{
    private String service_id, service_type, brand, status, date, phone_number, time, cancel_remarks, re_schedule, re_date, re_time, address, technician, created_at, customer_name, image_url;

    public PendingListModel()
    {}

    public PendingListModel( Parcel in )
    {
        service_id = in.readString();
        service_type = in.readString();
        brand = in.readString();
        status = in.readString();
        date = in.readString();
        phone_number = in.readString();
        time = in.readString();
        cancel_remarks = in.readString();
        re_schedule = in.readString();
        re_date = in.readString();
        re_time = in.readString();
        address = in.readString();
        technician = in.readString();
        created_at = in.readString();
        customer_name = in.readString();
        image_url = in.readString();
    }

    public static final Parcelable.Creator<PendingListModel> CREATOR = new Parcelable.Creator<PendingListModel>()
    {
        @Override
        public PendingListModel createFromParcel( Parcel in )
        {
            return new PendingListModel( in );
        }

        @Override
        public PendingListModel[] newArray( int size )
        {
            return new PendingListModel[ size ];
        }
    };

    public String getServiceId()
    {
        return service_id;
    }

    public void setServiceId( String service_id )
    {
        this.service_id = service_id;
    }

    public String getCustomer_name()
    {
        return customer_name;
    }

    public String getImage_url()
    {
        return image_url;
    }

    public void setImage_url( String image_url )
    {
        this.image_url = image_url;
    }

    public void setCustomer_name( String customer_name )
    {
        this.customer_name = customer_name;
    }

    public String getService_type()
    {
        return service_type;
    }

    public void setService_type( String service_type )
    {
        this.service_type = service_type;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setBrand( String brand )
    {
        this.brand = brand;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus( String status )
    {
        this.status = status;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate( String date )
    {
        this.date = date;
    }

    public String getPhone_number()
    {
        return phone_number;
    }

    public void setPhone_number( String phone_number )
    {
        this.phone_number = phone_number;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime( String time )
    {
        this.time = time;
    }

    public String getCancel_remarks()
    {
        return cancel_remarks;
    }

    public void setCancel_remarks( String cancel_remarks )
    {
        this.cancel_remarks = cancel_remarks;
    }

    public String getRe_schedule()
    {
        return re_schedule;
    }

    public void setRe_schedule( String re_schedule )
    {
        this.re_schedule = re_schedule;
    }

    public String getRe_date()
    {
        return re_date;
    }

    public void setRe_date( String re_date )
    {
        this.re_date = re_date;
    }

    public String getRe_time()
    {
        return re_time;
    }

    public void setRe_time( String re_time )
    {
        this.re_time = re_time;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress( String address )
    {
        this.address = address;
    }

    public String getTechnician()
    {
        return technician;
    }

    public void setTechnician( String technician )
    {
        this.technician = technician;
    }

    public String getCreated_at()
    {
        return created_at;
    }

    public void setCreated_at( String created_at )
    {
        this.created_at = created_at;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel( Parcel dest, int flags )
    {
        dest.writeString( service_id );
        dest.writeString( service_type );
        dest.writeString( brand );
        dest.writeString( status );
        dest.writeString( date );
        dest.writeString( phone_number );
        dest.writeString( time );
        dest.writeString( cancel_remarks );
        dest.writeString( re_schedule );
        dest.writeString( re_date );
        dest.writeString( re_time );
        dest.writeString( address );
        dest.writeString( technician );
        dest.writeString( created_at );
        dest.writeString( customer_name );
        dest.writeString( image_url );
    }

    public static final DiffUtil.ItemCallback<PendingListModel> CALLBACK = new DiffUtil.ItemCallback<PendingListModel>()
    {
        @Override
        public boolean areItemsTheSame( PendingListModel oldItem, PendingListModel newItem )
        {
            return oldItem.service_id.equals( newItem.service_id );
        }

        @Override
        public boolean areContentsTheSame( PendingListModel oldItem, PendingListModel newItem )
        {
            return true;
        }
    };
}
