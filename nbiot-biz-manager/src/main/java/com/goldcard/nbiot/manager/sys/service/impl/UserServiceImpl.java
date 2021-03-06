package com.goldcard.nbiot.manager.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.goldcard.nbiot.common.dal.daointerface.sys.UserDao;
import com.goldcard.nbiot.common.dal.dataobject.sys.UserDO;
import com.goldcard.nbiot.common.model.User;
import com.goldcard.nbiot.common.util.PageBase;
import com.goldcard.nbiot.manager.sys.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public PageBase<User> getUserList(Map<String, Object> map, int pageIndex, int pageSize) {

        PageHelper.startPage(pageIndex, pageSize);
        List<UserDO> userDOS = userDao.getUserList(map);
        PageBase<UserDO> pageBases =new PageBase<UserDO>(userDOS);

        PageBase<User> deviceInfos = new PageBase<User>();
        deviceInfos.setTotal(pageBases.getTotal());
        deviceInfos.setList(transferDOList2InfoList(userDOS));
        return deviceInfos;
    }

    @Override
    public Integer addUser(User user) {
        user.setStatus("0");
        user.setPassword("123456");
        return userDao.addUser(transferInfo2DO(user));
    }

    @Override
    public int userEnable(User user) {
        return userDao.userEnable(transferInfo2DO(user));
    }

    @Override
    public int userUnable(User user) {
        return userDao.userUnable(transferInfo2DO(user));
    }

    @Override
    public List<User> selectListByUsername(String userName) {
        return null ;
    }

    /**
     * 将底层DO List转换成上层Info list
     * @param UserDOs
     * @return
     */
    private List<User> transferDOList2InfoList(List<UserDO> UserDOs) {
        List<User> users = new ArrayList<User>();
        if (UserDOs != null && !UserDOs.isEmpty()) {
            for (UserDO userDO : UserDOs) {
                User user = new User();
                user.setId(String.valueOf(userDO.getId()));
                user.setUserNo(userDO.getUserNo());
                user.setPassword(userDO.getPassword());
                user.setUserName(userDO.getUserName());
                user.setStatus(userDO.getStatus());
                user.setRemark(userDO.getRemark());
                user.setCreateId(userDO.getCreateId());
                user.setCreateTime(userDO.getCreateTime());
                users.add(user);
            }
        }
        return users;
    }

    /**
     * 将底层 Info 转换成上层DO
     * @param user
     * @return
     */
    private UserDO transferInfo2DO(User user) {
        UserDO userDO = null;
        if (null != user) {
            userDO = new UserDO();
            userDO.setId(Integer.valueOf(user.getId()));
            userDO.setUserNo(user.getUserNo());
            userDO.setPassword(user.getPassword());
            userDO.setUserName(user.getUserName());
            userDO.setStatus(user.getStatus());
            userDO.setRemark(user.getRemark());
            userDO.setCreateId(user.getCreateId());
            userDO.setCreateTime(user.getCreateTime());
        }
        return userDO;
    }

    /**
     * 将底层 DO 转换成上层Info
     * @param userDO
     * @return
     */
    private User transferDO2Info(UserDO userDO){
        User user = null;
        if(null != userDO){
            user = new User();
            user.setId(String.valueOf(userDO.getId()));
            user.setUserNo(userDO.getUserNo());
            user.setPassword(userDO.getPassword());
            user.setUserName(userDO.getUserName());
            user.setStatus(userDO.getStatus());
            user.setRemark(userDO.getRemark());
            user.setCreateId(userDO.getCreateId());
            user.setCreateTime(userDO.getCreateTime());
        }
        return user;
    }
}
