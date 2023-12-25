package com.oho.oho.responses.qrcode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetQrCodeResponse {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("data")
    @Expose
    private QRCodeData data;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public QRCodeData getData() {
        return data;
    }

    public void setData(QRCodeData data) {
        this.data = data;
    }
}
