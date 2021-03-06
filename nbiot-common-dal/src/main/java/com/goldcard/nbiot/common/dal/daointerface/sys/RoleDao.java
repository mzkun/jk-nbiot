package com.goldcard.nbiot.common.dal.daointerface.sys;

import com.goldcard.nbiot.common.dal.dataobject.sys.RoleDO;

import java.util.List;
import java.util.Map;

public interface RoleDao {

	/**
	 * 获取所有角色
	 * @param map
	 * @return
	 */
    List<RoleDO> getRoleList(Map<String, Object> map);

    Integer addRole(RoleDO roleDO);
}
