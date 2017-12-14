package com.goldcard.nbiot.common.dal.daointerface;

import java.util.Map;

public interface UserDao {
    
	public Integer checkUserFromGoldcard(Map<String, Object> map);
	
	public Integer checkUserFromUsmart(Map<String, Object> map);
	
}
