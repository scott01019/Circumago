package com.apps.scott.circumago.common.twisty.board.move;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Scott on 10/21/2016.
 */
public class ParcelableMove implements Parcelable {
    private Integer mCenter;
    private Move.Rotation mRotation;

    public ParcelableMove(int center, Move.Rotation rotation) {
        mCenter = center;
        mRotation = rotation;
    }

    public ParcelableMove(Parcel in) {
        mCenter = in.readInt();
        mRotation = (Move.Rotation) in.readSerializable();
    }

    public Integer getCenter() { return mCenter; }
    public Move.Rotation getRotation() { return mRotation; }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public ParcelableMove createFromParcel(Parcel in) {
            return new ParcelableMove(in);
        }

        public ParcelableMove[] newArray(int size) {
            return new ParcelableMove[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mCenter);
        dest.writeSerializable(mRotation);
    }
}
