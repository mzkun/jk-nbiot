package com.goldcard.nbiot.manager.sys.service;

import com.goldcard.nbiot.common.model.User;
import com.goldcard.nbiot.common.util.PageBase;

import java.util.List;
import java.util.Map;

public interface UserService {

    /**
     * 查询用户列表
     * @param map
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public PageBase<User> getUserList(Map<String, Object> map, int pageIndex, int pageSize);

    /**
     * 新增用户
     * @param user
     * @return
     */
    public Integer addUser(User user);

    /**
     * 启用用户
     * @param user
     * @return
     */
    public int userEnable(User user);

    /**
     * 禁用用户
     * @param user
     * @return
     */
    public int userUnable(User user);

    /**
     * 根据用户名查询用户列表 
     * @param userName
     * @return
     */
    public List<User> selectListByUsername(String userName);
}
