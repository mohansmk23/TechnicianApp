package com.poojaelectronics.technician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StartTaskResponse
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
        @SerializedName( "profile" )
        @Expose
        private List<Profile> profile = null;

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

        public List<Profile> getProfile()
        {
            return profile;
        }

        public void setProfile( List<Profile> profile )
        {
            this.profile = profile;
        }

        public class Profile
        {
            @SerializedName( "customer_name" )
            @Expose
            private String customerName;
            @SerializedName( "address" )
            @Expose
            private String address;
            @SerializedName( "phone" )
            @Expose
            private String phone;
            @SerializedName( "email" )
            @Expose
            private String email;
            @SerializedName( "category_name" )
            @Expose
            private String categoryName;
            @SerializedName( "service_type" )
            @Expose
            private String serviceType;
            @SerializedName( "brand_name" )
            @Expose
            private String brandName;
            @SerializedName( "technician_name" )
            @Expose
            private String technicianName;
            @SerializedName( "date" )
            @Expose
            private String date;
            @SerializedName( "time" )
            @Expose
            private String time;
            @SerializedName( "remarks" )
            @Expose
            private String remarks;
            @SerializedName( "status" )
            @Expose
            private String status;
            @SerializedName( "cancel_remarks" )
            @Expose
            private String cancelRemarks;
            @SerializedName( "created_at" )
            @Expose
            private String createdAt;
            @SerializedName( "re_schedule" )
            @Expose
            private String reSchedule;
            @SerializedName( "re_date" )
            @Expose
            private String reDate;
            @SerializedName( "re_time" )
            @Expose
            private String reTime;
            @SerializedName( "lat" )
            @Expose
            private String lat;
            @SerializedName( "lng" )
            @Expose
            private String lng;

            public String getCustomerName()
            {
                return customerName;
            }

            public void setCustomerName( String customerName )
            {
                this.customerName = customerName;
            }

            public String getAddress()
            {
                return address;
            }

            public void setAddress( String address )
            {
                this.address = address;
            }

            public String getPhone()
            {
                return phone;
            }

            public void setPhone( String phone )
            {
                this.phone = phone;
            }

            public String getEmail()
            {
                return email;
            }

            public void setEmail( String email )
            {
                this.email = email;
            }

            public String getCategoryName()
            {
                return categoryName;
            }

            public void setCategoryName( String categoryName )
            {
                this.categoryName = categoryName;
            }

            public String getServiceType()
            {
                return serviceType;
            }

            public void setServiceType( String serviceType )
            {
                this.serviceType = serviceType;
            }

            public String getBrandName()
            {
                return brandName;
            }

            public void setBrandName( String brandName )
            {
                this.brandName = brandName;
            }

            public String getTechnicianName()
            {
                return technicianName;
            }

            public void setTechnicianName( String technicianName )
            {
                this.technicianName = technicianName;
            }

            public String getDate()
            {
                return date;
            }

            public void setDate( String date )
            {
                this.date = date;
            }

            public String getTime()
            {
                return time;
            }

            public void setTime( String time )
            {
                this.time = time;
            }

            public String getRemarks()
            {
                return remarks;
            }

            public void setRemarks( String remarks )
            {
                this.remarks = remarks;
            }

            public String getStatus()
            {
                return status;
            }

            public void setStatus( String status )
            {
                this.status = status;
            }

            public String getCancelRemarks()
            {
                return cancelRemarks;
            }

            public void setCancelRemarks( String cancelRemarks )
            {
                this.cancelRemarks = cancelRemarks;
            }

            public String getCreatedAt()
            {
                return createdAt;
            }

            public void setCreatedAt( String createdAt )
            {
                this.createdAt = createdAt;
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

            public String getLat()
            {
                return lat;
            }

            public void setLat( String lat )
            {
                this.lat = lat;
            }

            public String getLng()
            {
                return lng;
            }

            public void setLng( String lng )
            {
                this.lng = lng;
            }
        }
    }
}
