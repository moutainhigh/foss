package com.deppon.foss.module.pickup.common.client.dao;

import java.util.List;

import com.deppon.foss.module.pickup.common.client.vo.DataConsistencyCheckVo;



public interface IDataConsistencyCheckDao {
	/**
     * 
     * <p>查询本地下载数据</p> 
     * @author foss-dengyao
     * @date 2013-04-16 下午5:30:43
     * @param id
     * @return
     * @see
     */
	List<DataConsistencyCheckVo> queryDownlodeTableDate();
	/**
     * 
     * <p>查询本地下载数据条目数</p> 
     * @author foss-dengyao
     * @date 2013-04-26 下午5:30:43
     * @param id
     * @return
     * @see
     */
	List<DataConsistencyCheckVo> countQuerylocalTable(List<DataConsistencyCheckVo> localData);

}
