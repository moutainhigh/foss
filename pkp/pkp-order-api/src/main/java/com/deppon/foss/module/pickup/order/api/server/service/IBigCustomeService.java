package com.deppon.foss.module.pickup.order.api.server.service;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.order.api.shared.domain.CombinateBillEntity;
import com.deppon.foss.module.pickup.order.api.shared.dto.BigCustomeDto;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * caohuibin
 * Created by 268217 on 2015/9/11.
 */
public interface IBigCustomeService extends IService {

    /**
     * 查询大客户预打标签页面显示的数据
     * @return
     */
    BigCustomeDto queryBigCustomeDto(Date curDate, Date preDate);

    /**
     * 页面显示
     * 按时间查询记录
     */
    public List<BigCustomeDto> queryBigCustomeList(Date curDate, Date preDate,int start, int limit);

    /**
     * 导出
     * 按时间查询记录
     */
    public List<CombinateBillEntity> queryBigCustomeSummaryList(Date billTimeFrom, Date billTimeTo);

    /**
     * 页面显示明细总数
     * 按时间查询记录
     */
    Long queryBigCustomeTotalCount(Date billTimeFrom, Date billTimeTo);

    /**
     *
     * 导出
     *
     * @author CAOHUIBIN
     * @date 2015-09-18上午9:42:02
     */

    InputStream queryExport(Date curDate, Date preDate);

}
