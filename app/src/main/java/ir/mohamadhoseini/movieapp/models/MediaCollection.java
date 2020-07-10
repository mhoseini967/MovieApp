package ir.mohamadhoseini.movieapp.models;

import java.io.Serializable;

public abstract class MediaCollection implements Serializable {
    // use this super class for base collection and detect type of collection
    private int type;
    public MediaCollection(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }

}
