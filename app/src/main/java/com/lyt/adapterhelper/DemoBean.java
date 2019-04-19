package com.lyt.adapterhelper;

public class DemoBean {
    private int id;
    private String name;

    public DemoBean(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public DemoBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name==null?"":name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
