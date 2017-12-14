package com.goldcard.nbiot.common.util;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * ----------------------------------------------------------------
 * Copyright (C) 2010 浙江金卡股份有限公司
 * 版权所有。
 * 
 * 文件名：PageBase.java
 * 文件功能描述：分页公共类
 *
 * @autor 1925
 * @Date 2017/05/12 11:05
 * 
 * 修改标识：
 * 修改描述：
 * ----------------------------------------------------------------
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public class PageBase<T> extends PageInfo {
	
    
	private static final long serialVersionUID = -1897423842256864834L;
	
	private List<T> rows;

    public List<T> getRows() {
        rows = super.getList();
        return rows;
    }

    public PageBase(List<T> list) {
        super(list, 8);
    }
    
    public PageBase() {
        super();
    }
}
