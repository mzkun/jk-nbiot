package com.goldcard.nbiot.common.dal.dataobject.sys;

import java.io.Serializable;

public class RoleResoucesDO implements Serializable{
	

    private static final long serialVersionUID = -5694210978320357838L;

    private int roleId;

    private int resId;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}