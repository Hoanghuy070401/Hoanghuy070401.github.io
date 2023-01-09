package com.manager.quanlybanhang.model;

import java.util.List;

public class DonHangModel {
    boolean success;
    String messenger;
    List<DonHang> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessenger() {
        return messenger;
    }

    public void setMessenger(String messenger) {
        this.messenger = messenger;
    }

    public List<DonHang> getResult() {
        return result;
    }

    public void setResult(List<DonHang> result) {
        this.result = result;
    }
}
