package com.example.arlin_huang.sgsleakmanager.liteclass;

import android.os.Parcel;
import android.os.Parcelable;

import org.litepal.crud.LitePalSupport;


/**
 * Created by Arlin_Huang on 2018/3/26.
 */

public class Factories extends LitePalSupport implements Parcelable {

    private int id;
    private String name;
    private String attr;
    private String tenantName;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(attr);
        dest.writeString(tenantName);
    }

    public static final Parcelable.Creator<Factories> CREATOR = new Parcelable.Creator<Factories>() {
        @Override
        public Factories createFromParcel(Parcel source) {
            Factories factory =new Factories();
            factory.id=source.readInt();
            factory.name=source.readString();
            factory.attr=source.readString();
            factory.tenantName=source.readString();
            return factory;
        }

        @Override
        public Factories[] newArray(int size) {
            return new Factories[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getTenantName() {
        return tenantName;
    }

    public void setTenantName(String tenantName) {
        this.tenantName = tenantName;
    }
}
