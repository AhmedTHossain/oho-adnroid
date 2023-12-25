package com.oho.oho.responses.qrcode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QRCodeData {
    @SerializedName("qr_code")
    @Expose
    private String qrCode;

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }
}
