package com.goldcard.nbiot.common.dal.daointerface.sys;

import com.goldcard.nbiot.common.dal.dataobject.sys.UserDO;

import java.util.List;
import java.util.Map;

public interface UserDao {

	/**
	 * 查询所有用户
	 * @param map
	 * @return
	 */
	List<UserDO> getUserList(Map<String, Object> map);

	/**
	 * 新增用户
	 * @param userDO
	 * @return
	 */
	Integer addUser(UserDO userDO);

	/**
	 * 启用用户
	 * @param userDO
	 * @return
	 */
	int userEnable(UserDO userDO);

	/**
	 * 禁用用户
	 * @param userDO
	 * @return
	 */
	int userUnable(UserDO userDO);

	/**
	 * 根据用户姓名查询用户列表
	 * @param  userName
	 * @return
	 */
	List<UserDO> selectListByUsername(String userName);
}
