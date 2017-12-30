package com.goldcard.nbiot.common.dal.dataobject.sys;

import java.io.Serializable;

public class UserDO implements Serializable{
	

    private static final long serialVersionUID = 7165906136491943522L;

    //ID
    private int id;
    //登录号
    private String userNo;
    //密码
    private String password;
    //用户名
    private String userName;
    //启用状态（0：否，1：是）
    private String status;
    //备注
    private String remark;
    //创建人
    private String createId;
    //创建时间
    private String createTime;
    //更新时间
    private String updateDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }
}
