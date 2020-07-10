package ir.mohamadhoseini.movieapp.models;
import androidx.room.ColumnInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Rating implements Serializable {

    @ColumnInfo(name = "Source")
    @SerializedName("Source")
    @Expose
    private String source;

    @ColumnInfo(name = "Value")
    @SerializedName("Value")
    @Expose
    private String value;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
