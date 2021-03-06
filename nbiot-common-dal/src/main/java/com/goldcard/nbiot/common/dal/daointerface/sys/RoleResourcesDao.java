package com.goldcard.nbiot.common.dal.daointerface.sys;

import com.goldcard.nbiot.common.dal.dataobject.sys.RoleResoucesDO;

import java.util.List;

public interface RoleResourcesDao {
    
    /**
     *  根据roleId查询permission中res类别的perId
     */
    public List<String> selectPeridByRoleid(String perId);
    
    /**
     * 添加权限和资源
     */
    void save(RoleResoucesDO roleResoucesDO);
     
    /**
     * 删除权限和资源
     */
    void deleteById(String roleId);
}