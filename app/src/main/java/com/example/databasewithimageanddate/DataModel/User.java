package com.example.databasewithimageanddate.DataModel;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Users")
public class User {
    @PrimaryKey(autoGenerate = true) // This no need user to input so put autoGenerate
    int uid;
    @ColumnInfo(name = "Name")
    String fullName;
    @ColumnInfo(name = "User_Name")
    String userName;
    String password;
    Date dob;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB) // Byte will become a BLOB type of the field inside our database
    byte [] image;

    public int getUid() {
        return uid;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public Date getDob() {
        return dob;
    }

    public byte[] getImage() {
        return image;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
