package com.oho.oho.responses.qrcode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QRCodeData {
    @SerializedName("qr_code")
    @Expose
    private String qrCode;
    @SerializedName("match_id")
    @Expose
    private Integer match_id;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Integer getMatch_id() {
        return match_id;
    }

    public void setMatch_id(Integer match_id) {
        this.match_id = match_id;
    }
}
