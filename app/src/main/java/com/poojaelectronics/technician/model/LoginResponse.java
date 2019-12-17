package com.poojaelectronics.technician.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse
{
    @SerializedName( "Output")
    @Expose
    private List<Output> output = null;

    public List<Output> getOutput() {
        return output;
    }

    public void setOutput(List<Output> output) {
        this.output = output;
    }
    public class Output {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("profile")
        @Expose
        private List<Profile> profile = null;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Profile> getProfile() {
            return profile;
        }

        public void setProfile(List<Profile> profile) {
            this.profile = profile;
        }

        public class Profile {

            @SerializedName("technician_name")
            @Expose
            private String technicianName;
            @SerializedName("address")
            @Expose
            private String address;
            @SerializedName("phone_no")
            @Expose
            private String phoneNo;
            @SerializedName("email_id")
            @Expose
            private String emailId;
            @SerializedName("technician_image")
            @Expose
            private String technicianImage;
            @SerializedName("auth_key")
            @Expose
            private String authKey;
            @SerializedName( "tech_id")
            @Expose
            private String techId;


            public String getTechId()
            {
                return techId;
            }

            public void setTechId( String techId )
            {
                this.techId = techId;
            }

            public String getTechnicianName() {
                return technicianName;
            }

            public void setTechnicianName(String technicianName) {
                this.technicianName = technicianName;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPhoneNo() {
                return phoneNo;
            }

            public void setPhoneNo(String phoneNo) {
                this.phoneNo = phoneNo;
            }

            public String getEmailId() {
                return emailId;
            }

            public void setEmailId(String emailId) {
                this.emailId = emailId;
            }

            public String getTechnicianImage() {
                return technicianImage;
            }

            public void setTechnicianImage(String technicianImage) {
                this.technicianImage = technicianImage;
            }

            public String getAuthKey() {
                return authKey;
            }

            public void setAuthKey(String authKey) {
                this.authKey = authKey;
            }

        }

    }
}
