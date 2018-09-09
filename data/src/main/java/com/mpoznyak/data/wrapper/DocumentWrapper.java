package com.mpoznyak.data.wrapper;

import android.os.Parcel;
import android.os.Parcelable;

import com.mpoznyak.domain.model.Entry;


public class DocumentWrapper extends Entry implements Parcelable {


    public static final Parcelable.Creator CREATOR = new Creator() {
        @Override
        public Object createFromParcel(Parcel source) {
            return new DocumentWrapper(source);
        }

        @Override
        public Object[] newArray(int size) {
            return new DocumentWrapper[size];
        }
    };

    private DocumentWrapper(Parcel source) {

        mId = source.readInt();
        mCase_Id = source.readInt();
        mName = source.readString();
        mLastModified = source.readString();
        mSize = source.readLong();

    }

    public DocumentWrapper() {
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
