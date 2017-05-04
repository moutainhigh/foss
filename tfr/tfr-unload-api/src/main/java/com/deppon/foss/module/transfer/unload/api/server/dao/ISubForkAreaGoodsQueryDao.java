package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;

import com.deppon.foss.module.transfer.unload.api.shared.domain.SubForkAreaGoodsEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.SubForkAreaGoodsVo;

public interface ISubForkAreaGoodsQueryDao {
	
	/**
	 * 查询运输性质列表   零担
	 * @author zenghaibin
	 * @date 2014-07-03 上午9:35:34
	 * @return 返回一个参数列别List<BaseDataDictDto>
	 */
	public List<SubForkAreaGoodsEntity> querySubForkAreaGoods(SubForkAreaGoodsVo subForkAreaGoodsVo,int limit,int start);

	/**
	 * 查询运输性质列表  快递
	 * @author zenghaibin
	 * @date 2014-07-03 上午9:35:34
	 * @return 返回一个参数列别List<BaseDataDictDto>
	 */
	public String querySubForkAreaGoodsExpress(SubForkAreaGoodsVo subForkAreaGoodsVo,int limit,int start);

	
	/**
	 *分页  零担
	 *@author zenghaibin
	 *@param subForkAreaGoodsVo 查询参数vo
	 *@return 数据量
	 **/
	public Long querySubForkAreaGoodsCount(SubForkAreaGoodsVo subForkAreaGoodsVo);
	
	
	/**
	 *分页  快递
	 *@author zenghaibin
	 *@param subForkAreaGoodsVo 查询参数vo
	 *@return 数据量
	 **/
	public Long querySubForkAreaGoodsCountExpress(SubForkAreaGoodsVo subForkAreaGoodsVo);
	
	
}

