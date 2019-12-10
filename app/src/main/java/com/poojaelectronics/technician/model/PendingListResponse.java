package com.poojaelectronics.technician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendingListResponse
{
    @SerializedName( "Output" )
    @Expose
    private List<Output> output = null;

    public List<Output> getOutput()
    {
        return output;
    }

    public void setOutput( List<Output> output )
    {
        this.output = output;
    }

    public class Output
    {
        @SerializedName( "status" )
        @Expose
        private String status;
        @SerializedName( "message" )
        @Expose
        private String message;
        @SerializedName( "bookinglist" )
        @Expose
        private List<Bookinglist> bookinglist = null;

        public String getStatus()
        {
            return status;
        }

        public void setStatus( String status )
        {
            this.status = status;
        }

        public String getMessage()
        {
            return message;
        }

        public void setMessage( String message )
        {
            this.message = message;
        }

        public List<Bookinglist> getBookinglist()
        {
            return bookinglist;
        }

        public void setBookinglist( List<Bookinglist> bookinglist )
        {
            this.bookinglist = bookinglist;
        }

        public class Bookinglist
        {
            @SerializedName( "service_id" )
            @Expose
            private String service_id;
            @SerializedName( "service_type" )
            @Expose
            private String serviceType;
            @SerializedName( "brand" )
            @Expose
            private String brand;
            @SerializedName( "status" )
            @Expose
            private String status;
            @SerializedName( "date" )
            @Expose
            private String date;
            @SerializedName( "phone_number" )
            @Expose
            private String phoneNumber;
            @SerializedName( "time" )
            @Expose
            private String time;
            @SerializedName( "cancel_remarks" )
            @Expose
            private String cancelRemarks;
            @SerializedName( "re_schedule" )
            @Expose
            private String reSchedule;
            @SerializedName( "re_date" )
            @Expose
            private String reDate;
            @SerializedName( "re_time" )
            @Expose
            private String reTime;
            @SerializedName( "address" )
            @Expose
            private String address;
            @SerializedName( "technician" )
            @Expose
            private String technician;
            @SerializedName( "created_at" )
            @Expose
            private String createdAt;
            @SerializedName( "customer_name" )
            @Expose
            private String customerName;
            @SerializedName( "imageurl" )
            @Expose
            private String imageUrl;

            public String getImageUrl()
            {
                return imageUrl;
            }

            public void setImageUrl( String imageUrl )
            {
                this.imageUrl = imageUrl;
            }

            public String getCustomerName()
            {
                return customerName;
            }

            public void setCustomerName( String customerName )
            {
                this.customerName = customerName;
            }

            public String getServiceId()
            {
                return service_id;
            }

            public void setServiceId( String service_id )
            {
                this.service_id = service_id;
            }

            public String getServiceType()
            {
                return serviceType;
            }

            public void setServiceType( String serviceType )
            {
                this.serviceType = serviceType;
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

            public String getPhoneNumber()
            {
                return phoneNumber;
            }

            public void setPhoneNumber( String phoneNumber )
            {
                this.phoneNumber = phoneNumber;
            }

            public String getTime()
            {
                return time;
            }

            public void setTime( String time )
            {
                this.time = time;
            }

            public String getCancelRemarks()
            {
                return cancelRemarks;
            }

            public void setCancelRemarks( String cancelRemarks )
            {
                this.cancelRemarks = cancelRemarks;
            }

            public String getReSchedule()
            {
                return reSchedule;
            }

            public void setReSchedule( String reSchedule )
            {
                this.reSchedule = reSchedule;
            }

            public String getReDate()
            {
                return reDate;
            }

            public void setReDate( String reDate )
            {
                this.reDate = reDate;
            }

            public String getReTime()
            {
                return reTime;
            }

            public void setReTime( String reTime )
            {
                this.reTime = reTime;
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

            public String getCreatedAt()
            {
                return createdAt;
            }

            public void setCreatedAt( String createdAt )
            {
                this.createdAt = createdAt;
            }
        }
    }
}
