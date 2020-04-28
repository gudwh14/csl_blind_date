package com.csl.csl_blinddate.Data;

import android.os.Parcel;
import android.os.Parcelable;

import com.kakao.usermgmt.response.model.User;

public class UserData implements Parcelable {
    private String userID;
    private int age;
    private String gender;
    private String school;
    private String mail;
    private boolean certification;

    public UserData (String userID, int age, String gender, String school, String mail, boolean certification ) {
        this.userID = userID;
        this.age = age;
        this.gender = gender;
        this.school = school;
        this.mail = mail;
        this.certification = certification;
    }

    public String getSchool() {
        return school;
    }

    public int getAge() {
        return age;
    }

    public String getGender() {
        return gender;
    }

    public String getMail() {
        return mail;
    }

    public String getUserID() {
        return userID;
    }

    public boolean isCertification() {
        return certification;
    }

    protected UserData(Parcel in) {
        userID = in.readString();
        age = in.readInt();
        gender = in.readString();
        school = in.readString();
        mail = in.readString();
        certification = in.readInt() == 1;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userID);
        parcel.writeInt(age);
        parcel.writeString(gender);
        parcel.writeString(school);
        parcel.writeString(mail);
        parcel.writeInt(certification ? 1 : 0);
    }

    public static final Creator<UserData> CREATOR = new Creator<UserData>() {
        @Override
        public UserData createFromParcel(Parcel parcel) {
            return new UserData(parcel);
        }

        @Override
        public UserData[] newArray(int i) {
            return new UserData[i];
        }
    };
}
