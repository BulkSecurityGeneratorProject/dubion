
package com.dubion.service.dto.NapsterAPI;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Artists {

    @SerializedName("ids")
    @Expose
    private List<String> ids = null;
    @SerializedName("href")
    @Expose
    private String href;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
