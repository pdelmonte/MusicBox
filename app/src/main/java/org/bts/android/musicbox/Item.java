package org.bts.android.musicbox;

/**
 * Created by pedrodelmonte on 20/03/17.
 */

public class Item {

    private String mImagePath;
    private String mTitle;


    public Item(String mImagePath, String mTitle) {
        this.mImagePath = mImagePath;
        this.mTitle = mTitle;

    }

    public String getImagePath() {
        return mImagePath;
    }

    public void setImagePath(String ImagePath) {
        this.mImagePath = ImagePath;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String Title) {
        this.mTitle = Title;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Item{");
        sb.append("mImagePath='").append(mImagePath).append('\'');
        sb.append(", mTitle='").append(mTitle).append('\'');
        sb.append(", mBody='");
        sb.append('}');

        return sb.toString();
    }

}
