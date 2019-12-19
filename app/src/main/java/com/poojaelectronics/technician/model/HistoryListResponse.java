package com.poojaelectronics.technician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryListResponse
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
        @SerializedName( "completed_list" )
        @Expose
        private List<CompletedList> completedList = null;

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

        public List<CompletedList> getCompletedList()
        {
            return completedList;
        }

        public void setCompletedList( List<CompletedList> completedList )
        {
            this.completedList = completedList;
        }

        public class CompletedList
        {
            @SerializedName( "service_id" )
            @Expose
            private String serviceId;
            @SerializedName( "customer_name" )
            @Expose
            private String customerName;
            @SerializedName( "image_url" )
            @Expose
            private String imageUrl;
            @SerializedName( "email" )
            @Expose
            private String email;
            @SerializedName( "phone" )
            @Expose
            private String phone;
            @SerializedName( "address" )
            @Expose
            private String address;
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
            @SerializedName( "unique_id" )
            @Expose
            private String uniqueId;
            @SerializedName( "status" )
            @Expose
            private String status;
            @SerializedName( "amount" )
            @Expose
            private String amount;
            @SerializedName( "complete_remarks" )
            @Expose
            private String completeRemarks;
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

            public String getServiceId()
            {
                return serviceId;
            }

            public void setServiceId( String service_id )
            {
                this.serviceId = service_id;
            }

            public String getCustomerName()
            {
                return customerName;
            }

            public void setCustomerName( String customerName )
            {
                this.customerName = customerName;
            }

            public String getImageUrl()
            {
                return imageUrl;
            }

            public void setImageUrl( String imageUrl )
            {
                this.imageUrl = imageUrl;
            }

            public String getEmail()
            {
                return email;
            }

            public void setEmail( String email )
            {
                this.email = email;
            }

            public String getPhone()
            {
                return phone;
            }

            public void setPhone( String phone )
            {
                this.phone = phone;
            }

            public String getAddress()
            {
                return address;
            }

            public void setAddress( String address )
            {
                this.address = address;
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

            public String getUniqueId()
            {
                return uniqueId;
            }

            public void setUniqueId( String uniqueId )
            {
                this.uniqueId = uniqueId;
            }

            public String getStatus()
            {
                return status;
            }

            public void setStatus( String status )
            {
                this.status = status;
            }

            public String getAmount()
            {
                return amount;
            }

            public void setAmount( String amount )
            {
                this.amount = amount;
            }

            public String getCompleteRemarks()
            {
                return completeRemarks;
            }

            public void setCompleteRemarks( String completeRemarks )
            {
                this.completeRemarks = completeRemarks;
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
        }
    }
}
