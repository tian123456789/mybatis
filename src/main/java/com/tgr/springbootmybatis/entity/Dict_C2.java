package com.tgr.springbootmybatis.entity;

public class Dict_C2 {
    private Integer id;

    private String name;

    private String comm;

    private Integer c1;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getComm() {
        return comm;
    }

    public void setComm(String comm) {
        this.comm = comm == null ? null : comm.trim();
    }

    public Integer getC1() {
        return c1;
    }

    public void setC1(Integer c1) {
        this.c1 = c1;
    }
}