package com.deppon.foss.module.settlement.closing.server.service.impl;

import com.deppon.foss.module.settlement.closing.api.server.dao.IMvrHiEntityDao;
import com.deppon.foss.module.settlement.closing.api.server.service.IMvrHiService;
import com.deppon.foss.module.settlement.closing.api.shared.domain.MvrHiEntity;
import com.deppon.foss.module.settlement.closing.api.shared.dto.MvrHiDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author 302307
 * @date 2015/12/17
 */
public class MvrHiService implements IMvrHiService {

    /** 日志. */
    private static final Logger LOGGER = LogManager
            .getLogger(MvrHiService.class);

    /** 数据库接口. */
    private IMvrHiEntityDao mvrHiEntityDao;
    
	public void setMvrHiEntityDao(IMvrHiEntityDao mvrHiEntityDao) {
		this.mvrHiEntityDao = mvrHiEntityDao;
	}

	@Override
    public List<MvrHiEntity> queryMvrHi(MvrHiDto dto, int offset, int limit) {

        LOGGER.info("查询家装月报表列表,dto:" + dto);

        // 查询参数为空，抛出异常
        if (dto == null) {
            throw new SettlementException("查询参数为空");
        }

        // 查询期间为空，抛出异常
        if (StringUtils.isEmpty(dto.getPeriod())) {
            throw new SettlementException("查询报表期间为空");
        }

        // 查询结果
        List<MvrHiEntity> queryList = mvrHiEntityDao.queryMvrHi(dto, offset,
                limit);

        LOGGER.info("结束查询家装月报表列表");

        return queryList;
    }

    @Override
    public MvrHiDto queryMvrHiTotal(MvrHiDto dto) {
        LOGGER.info("查询家装月报表汇总,dto:" + dto);

        // 查询参数为空，抛出异常
        if (dto == null) {
            throw new SettlementException("查询参数为空");
        }

        // 查询期间为空，抛出异常
        if (StringUtils.isEmpty(dto.getPeriod())) {
            throw new SettlementException("查询报表期间为空");
        }
        // 查询结果
        MvrHiDto queryDto = mvrHiEntityDao.queryMvrHiTotal(dto);
        LOGGER.info("结束查询家装月报表汇总");

        return queryDto;
    }
}
