package com.goldcard.nbiot.common.model;

import java.io.Serializable;

public class RoleResources implements Serializable{

    private static final long serialVersionUID = -5694210978320357838L;

    /** 角色id **/
    private int roleId;

    /** 资源id  **/
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