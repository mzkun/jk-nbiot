package com.goldcard.nbiot.manager.sys.service.impl;

import com.goldcard.nbiot.common.dal.dataobject.sys.Tree;
import com.goldcard.nbiot.manager.sys.service.ResourcesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourcesServiceImpl implements ResourcesService {
	
    @Override
    public List<Tree> getResourcesList(String userId) {
        return null;
    }
}
