package com.goldcard.nbiot.common.dal.dataobject.sys;

import java.io.Serializable;

public class RoleDO implements Serializable {
	
    private static final long serialVersionUID = -127592867338245552L;

    //ID
    private int id;
    //角色名
    private String name;
    //启用状态（0：否，1：是）
    private String status;
    //排序
    private int priority;
    //备注
    private String remark;
    //创建人
    private String createId;
    //创建时间
    private String createDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
