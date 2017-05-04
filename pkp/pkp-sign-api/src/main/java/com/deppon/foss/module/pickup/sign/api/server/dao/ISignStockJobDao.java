package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.domain.SignStockEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.SignStockDto;
/**
 * 
 *签收反签收同步改异步库存接口
 * @author 
 *		foss-meiying
 * @date 
 *      2013-3-22 下午3:10:54
 * @since
 * @version
 */
public interface ISignStockJobDao {
    /**
     * 添加一条记录
     * @author foss-meiying
     * @date 2013-3-22 下午3:46:55
     * @param record
     * @return
     * @see
     */
    int insert(SignStockEntity record);
    /**
     * 有选择性的添加数据
     * @author foss-meiying
     * @date 2013-3-22 下午3:53:11
     * @param record
     * @return
     * @see
     */
    int insertSelective(SignStockEntity record);
    /**
     * 修改根据相应的条件  
     * @author foss-meiying
     * @date 2013-3-22 下午4:06:26
     * @param record
     * @return
     * @see
     */
    int updateByCondition(SignStockDto record);
    /**
     * 查询根据相应的条件
     * @author foss-meiying
     * @date 2013-3-22 下午4:07:29
     * @param record
     * @return
     * @see
     */
    List<SignStockEntity> queryByCondition(SignStockDto record);
    /**
     * 根据主键id删除满足条件的记录
     * @author foss-meiying
     * @date 2013-3-22 下午4:08:32
     * @param id
     * @return
     * @see
     */
    int deleteById(String id);
    /**
     * 根据状态为-待执行 出入库类型 查询是否有满足的记录数
     * @author foss-meiying
     * @date 2013-3-22 下午5:45:51
     * @param record
     * @return
     * @see
     */
    Integer queryCountbyCondition(SignStockEntity record);
    /**
     * 根据id修改记录
     * @author foss-meiying
     * @date 2013-3-22 下午5:47:49
     * @param record
     * @return
     * @see
     */
    int updateById(SignStockEntity record);
}