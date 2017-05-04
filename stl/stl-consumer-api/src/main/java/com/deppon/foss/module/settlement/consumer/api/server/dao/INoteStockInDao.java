package com.deppon.foss.module.settlement.consumer.api.server.dao;

import java.util.List;

import com.deppon.foss.module.settlement.consumer.api.shared.domain.NoteStockInEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.NoteStockInDto;


/**
 * 小票单据入库Dao
 * @author 101911-foss-zhouChunlai
 * @date 2012-11-14 下午7:46:53
 */
public interface INoteStockInDao {
	
    /**
     * 票据下发
     * @author 101911-foss-zhouChunlai
     * @date 2012-10-19 上午11:32:49
     */
    int addStockIn(List<NoteStockInEntity> stockInLst);
    
    /**
     * 校验是否重复下发
     * @author 101911-foss-zhouChunlai
     * @date 2012-10-22 下午2:21:40
     */
    List<NoteStockInEntity> checkStockInEntity(NoteStockInEntity noteStockInEntity);
    
    /**
	 * 根据申请Id 查询入库数据
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-29 下午2:55:43
	 */
	 List<NoteStockInEntity> queryStockInById(NoteStockInDto noteStockInDto, int start, int limit);

	/**
	 * 根据申请Id 查询入库数据总条数
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-29 下午2:56:11
	 */
	 Long countQueryStockInById(NoteStockInDto noteStockInDto);
}
