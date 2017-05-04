package com.deppon.foss.module.transfer.load.dubbo.api.service;

import com.deppon.foss.module.transfer.load.dubbo.api.define.LoadGoodsDetailDto;
import com.deppon.foss.module.transfer.load.dubbo.api.exception.TfrLoadException;

import java.util.List;

/**
 * Created by 335284 on 2017/3/30.
 */
public interface IPDALoadCarRefreshDetailService {
    List<LoadGoodsDetailDto> refrushLoadTaskDetail(String taskNo) throws TfrLoadException;
}
