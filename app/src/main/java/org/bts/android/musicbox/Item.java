package org.bts.android.musicbox;

/**
 * Created by pedrodelmonte on 20/03/17.
 */

public class Item {

    private String mPath;
    private String mTitle;
    private int mTrackId;


    public Item(String mPath, String mTitle, int mTrackid) {
        this.mPath = mPath;
        this.mTitle = mTitle;
        this.mTrackId = mTrackid;
    }

    public String getPath() {
        return mPath;
    }

    public void setPath(String ImagePath) {
        this.mPath = ImagePath;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        this.mTitle = Title;
    }

    public int getmTrackId() {
        return mTrackId;
    }

    public void setmTrackId(int mTrackId) {
        this.mTrackId = mTrackId;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Current Song Playing: ").append(mTitle);

        return sb.toString();
    }

}
