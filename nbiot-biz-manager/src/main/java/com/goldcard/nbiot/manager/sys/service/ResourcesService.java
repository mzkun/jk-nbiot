package com.goldcard.nbiot.manager.sys.service;

import com.goldcard.nbiot.common.dal.dataobject.sys.Tree;

import java.util.List;

public interface ResourcesService {

    List<Tree> getResourcesList(String userId);
    
}
