
package com.delectable.model;

import com.google.gson.annotations.Expose;

public class Child {

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

}
