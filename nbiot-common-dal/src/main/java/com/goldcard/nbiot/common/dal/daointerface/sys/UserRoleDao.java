package com.goldcard.nbiot.common.dal.daointerface.sys;

import com.goldcard.nbiot.common.dal.dataobject.sys.UserRoleDO;

public interface UserRoleDao {

    /**
     * 添加用户权限
     * @param userRoleDO
     */
    void save(UserRoleDO userRoleDO);

    /**
     * 删除用户权限
     * @param  userRoleDO
     */
    void delete(UserRoleDO userRoleDO);
}
