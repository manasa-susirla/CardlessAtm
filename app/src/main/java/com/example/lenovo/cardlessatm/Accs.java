package com.example.lenovo.cardlessatm;

public class Accs {

    private int acc_no;
    private float balance;

    private int pin;
    private String IFSC;

    private String accesscode;

    private String currentTransactionID;
    private String isLinked;
    private Long mobile;

    private String transactionIDList;


    Accs(){

    }

    public Accs(int acc_no, float balance, int pin, String IFSC, String accesscode, String currentTransactionID, String isLinked, Long mobile, String transactionIDList) {
        this.acc_no = acc_no;
        this.balance = balance;
        this.pin = pin;
        this.IFSC = IFSC;
        this.accesscode = accesscode;
        this.currentTransactionID = currentTransactionID;
        this.isLinked = isLinked;
        this.mobile = mobile;
        this.transactionIDList = transactionIDList;
    }

    public String getIFSC() {
        return IFSC;
    }

    public void setIFSC(String IFSC) {
        this.IFSC = IFSC;
    }

    public String getAccesscode() {
        return accesscode;
    }

    public void setAccesscode(String accesscode) {
        this.accesscode = accesscode;
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

    public String getTransactionIDList() {
        return transactionIDList;
    }

    public void setTransactionIDList(String transactionIDList) {
        this.transactionIDList = transactionIDList;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public int getAcc_no() {
        return acc_no;
    }

    public void setAcc_no(int acc_no) {
        this.acc_no = acc_no;
    }



    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }
}