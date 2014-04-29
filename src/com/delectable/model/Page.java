
package com.delectable.model;

import com.google.gson.annotations.Expose;

public class Page {

    @Expose
    private String kind;
    @Expose
    private Data data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
