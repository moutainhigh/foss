package com.deppon.foss.module.settlement.pay.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.settlement.pay.api.shared.domain.DebitWillOverEntity;
import com.deppon.foss.module.settlement.pay.api.shared.dto.DebitWillOverDto;

import java.io.InputStream;
import java.util.List;

/**
 * Created by 073615 on 2014/12/23.
 */
public interface  IDebitWillOverService extends IService {
    /**
     * 根据总条数查询
     * @param dto
     * @return
     */
    List<DebitWillOverEntity> queryByDebitInfobyCondition(DebitWillOverDto dto);

    /**
     * 分页查询
     * @param dto
     * @param start
     * @param limit
     * @return
     */
    List<DebitWillOverEntity> queryByDebitInfobyPages(DebitWillOverDto dto,int start,int limit);

    /**
     * 导出到Excel
     * @param dto
     * @return
     */
    InputStream  exportDebitInfo(DebitWillOverDto dto);

    /**
     * 查询总页数
     * @param dto
     * @return
     */
    long queryDebitCount(DebitWillOverDto dto);
}
