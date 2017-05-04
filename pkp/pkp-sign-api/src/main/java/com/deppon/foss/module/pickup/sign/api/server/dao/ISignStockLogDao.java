package com.deppon.foss.module.pickup.sign.api.server.dao;

import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockLogEntity;
/**
 * 
 *签收反签收同步改异步库存日志接口
 * @author 
 *		foss-meiying
 * @date 
 *      2013-3-22 下午4:13:29
 * @since
 * @version
 */
public interface ISignStockLogDao {
	/**
     * 添加一条记录
     * @author foss-meiying
     * @date 2013-3-22 下午3:46:55
     * @param record
     * @return
     * @see
     */
    int insert(SignStockLogEntity record);
    /**
     * 有选择性的添加数据
     * @author foss-meiying
     * @date 2013-3-22 下午3:53:11
     * @param record
     * @return
     * @see
     */
    int insertSelective(SignStockLogEntity record);
}
