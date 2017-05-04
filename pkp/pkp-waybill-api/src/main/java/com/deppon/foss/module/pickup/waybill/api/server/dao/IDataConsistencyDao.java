package com.deppon.foss.module.pickup.waybill.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.waybill.shared.vo.DataConsistencyVo;

/**
 * 
 * 数据一致性检查接口
 * <p style="display:none">version:V1.0,author:foss-dengyao,date:2013-04-27 下午3:10:53, </p>
 * @author foss-dengyao
 * @date 2013-04-27 下午3:10:53
 * @since
 * @version
 */
public interface IDataConsistencyDao {
	/**
     * 
     * <p>通过表名查询各表记录数</p> 
     * @author foss-dengyao
     * @date 2013-04-27 下午3:10:53
     * @param List<Integer>
     * @return
     * @see
     */
	List<DataConsistencyVo> countQueryTableDate(List<DataConsistencyVo> tableName,String userCode);

}
