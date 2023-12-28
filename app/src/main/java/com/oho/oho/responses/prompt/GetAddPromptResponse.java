package com.oho.oho.responses.prompt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oho.oho.models.PromptAnswer;

public class GetAddPromptResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private PromptAnswer data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public PromptAnswer getData() {
        return data;
    }

    public void setData(PromptAnswer data) {
        this.data = data;
    }
}
