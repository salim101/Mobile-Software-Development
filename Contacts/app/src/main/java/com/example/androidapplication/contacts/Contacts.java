package com.example.androidapplication.contacts;


public class Contacts {
    //declare variables
    private int contact_id;
    private String contact_name,contact_address1,contact_address2,contact_county,contact_contactno;

    public  Contacts(){//constructor
        contact_id =0;
        contact_name="";
        contact_address1="";
        contact_address2="";
        contact_county="";
        contact_contactno="";
    }

    public Contacts(int contact_id, String contact_name, String contact_address1, String contact_address2,String contact_county,String contact_contactno){
        this.contact_id=contact_id;
        this.contact_name=contact_name;
        this.contact_address1=contact_address1;
        this.contact_address2=contact_address2;
        this.contact_county=contact_county;
        this.contact_contactno=contact_contactno;
    }

    //getters and setters for all the attributes
    public Contacts(String contact_name){
        this.contact_name=contact_name;
    }

    public int getContact_id() {
        return contact_id;
    }

    public String getContact_contactno() {
        return contact_contactno;
    }

    public String getContact_county() {
        return contact_county;
    }

    public String getContact_address2() {
        return contact_address2;
    }

    public String getContact_address1() {
        return contact_address1;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }

    public void setContact_contactno(String contact_contactno) {
        this.contact_contactno = contact_contactno;
    }

    public void setContact_county(String contact_county) {
        this.contact_county = contact_county;
    }

    public void setContact_address2(String contact_address2) {
        this.contact_address2 = contact_address2;
    }

    public void setContact_address1(String contact_address1) {
        this.contact_address1 = contact_address1;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }



}//end class
