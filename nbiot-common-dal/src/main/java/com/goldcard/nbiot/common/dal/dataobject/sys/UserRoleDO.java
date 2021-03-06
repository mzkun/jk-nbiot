package com.goldcard.nbiot.common.dal.dataobject.sys;

import java.io.Serializable;

public class UserRoleDO implements Serializable{

    private static final long serialVersionUID = -2858420661093129256L;

    private int userId;
    
    private int roleId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
