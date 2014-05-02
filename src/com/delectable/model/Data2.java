package com.delectable.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Data2 implements Parcelable{

	private String title;
	private int score;
	private int num_comments;
	private String thumbnail;

	private boolean is_self;
	private String selftext;
	private String url;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getNum_comments() {
		return num_comments;
	}
	public void setNum_comments(int num_comments) {
		this.num_comments = num_comments;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}


	public boolean isSelf() {
		return is_self;
	}
	public void setIs_self(boolean is_self) {
		this.is_self = is_self;
	}
	public String getSelftext() {
		return selftext;
	}
	public void setSelftext(String selftext) {
		this.selftext = selftext;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(title);
		out.writeInt(score);
		out.writeInt(num_comments);
		out.writeString(thumbnail);
		
		out.writeByte((byte) (is_self ? 1 : 0)); 
		out.writeString(selftext);
		out.writeString(url);
	}
	
	public static final Parcelable.Creator<Data2> CREATOR = new Parcelable.Creator<Data2>() {
		public Data2 createFromParcel(Parcel in) {
			return new Data2(in);
		}

		public Data2[] newArray(int size) {
			return new Data2[size];
		}
	};

	/**
	 * Parcelable Constructor
	 * @param in
	 */
	private Data2(Parcel in) {
		title = in.readString();
		score = in.readInt();
		num_comments = in.readInt();
		thumbnail = in.readString();
		
		is_self = in.readByte() != 0;
		selftext = in.readString();
		url = in.readString(); 
	}


}
