
package com.delectable.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

public class Child implements Parcelable{

    @Expose
    private String kind;
    @Expose
    private Data2 data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Data2 getData() {
        return data;
    }

    public void setData(Data2 data) {
        this.data = data;
    }

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(kind);
		out.writeParcelable(data, flags);
	}
	
	public static final Parcelable.Creator<Child> CREATOR = new Parcelable.Creator<Child>() {
		public Child createFromParcel(Parcel in) {
			return new Child(in);
		}

		public Child[] newArray(int size) {
			return new Child[size];
		}
	};

	/**
	 * Parcelable Constructor
	 * @param in
	 */
	private Child(Parcel in) {
		kind = in.readString();
		data = (Data2)in.readParcelable(Data2.class.getClassLoader());
	}

}
