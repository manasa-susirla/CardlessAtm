package com.example.lenovo.cardlessatm;

/**
 * Created by HP on 08-05-2018.
 */

public class Userdetails {
    private String IFSC;
    private Long acc_no;
    private String accesscode;
    private Long balance;
    private String currentTransactionID;
    private String isLinked;
    private Long mobile;
    private Long pin;
    private String transactionIDList;

    public Userdetails() {
    }

    public Userdetails(String IFSC, Long acc_no, String accesscode, Long balance, String currentTransactionID, String isLinked, Long mobile, Long pin, String transactionIDList) {
        this.IFSC = IFSC;
        this.acc_no = acc_no;
        this.accesscode = accesscode;
        this.balance = balance;
        this.currentTransactionID = currentTransactionID;
        this.isLinked = isLinked;
        this.mobile = mobile;
        this.pin = pin;
        this.transactionIDList = transactionIDList;
    }

    public String getIFSC() {
        return IFSC;
    }

    public void setIFSC(String IFSC) {
        this.IFSC = IFSC;
    }

    public Long getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(Long acc_no) {
        this.acc_no = acc_no;
    }

    public String getAccesscode() {
        return accesscode;
    }

    public void setAccesscode(String accesscode) {
        this.accesscode = accesscode;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getCurrentTransactionID() {
        return currentTransactionID;
    }

    public void setCurrentTransactionID(String currentTransactionID) {
        this.currentTransactionID = currentTransactionID;
    }

    public String getIsLinked() {
        return isLinked;
    }

    public void setIsLinked(String isLinked) {
        this.isLinked = isLinked;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

    public Long getPin() {
        return pin;
    }

    public void setPin(Long pin) {
        this.pin = pin;
    }

    public String getTransactionIDList() {
        return transactionIDList;
    }

    public void setTransactionIDList(String transactionIDList) {
        this.transactionIDList = transactionIDList;
    }
}