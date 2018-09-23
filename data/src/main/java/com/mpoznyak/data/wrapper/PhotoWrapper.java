package com.mpoznyak.data.wrapper;

import android.os.Parcel;
import android.os.Parcelable;

import com.mpoznyak.domain.model.Entry;


public class PhotoWrapper extends Entry implements Parcelable {

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new PhotoWrapper(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new PhotoWrapper[size];
        }
    };

    private PhotoWrapper(Parcel source) {

        mId = source.readInt();
        mCase_Id = source.readInt();
        mName = source.readString();
        mLastModified = source.readString();
        mSize = source.readLong();

    }

    public PhotoWrapper() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeInt(this.mCase_Id);
        dest.writeString(this.mName);
        dest.writeString(this.mLastModified);
        dest.writeLong(this.mSize);

    }
}
