package com.deppon.foss.module.transfer.unload.api.server.service;

import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.common.api.shared.dto.BaseDataDictDto;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SubForkAreaGoodsEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.SubForkAreaGoodsExpressEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.SubForkAreaGoodsVo;

public interface ISubForkAreaGoodsQueryService extends IService{
	/**
	 * 查询运输性质列表
	 * @author zenghaibin
	 * @date 2014-07-03 上午9:35:34
	 * @return 返回一个参数列别List<BaseDataDictDto>
	 */
	public List<BaseDataDictDto> queryProductList();
	
	/**
	 * 查询待叉区货物数据    零担
	 * @author zenghaibin
	 * @date 2014-07-037 上午9:35:34
	 * @param subForkAreaGoodsVo 查询条件参数
	 * @param limit start 分页参数
	 * @return 返回一个参数列别List<SubForkAreaGoodsEntity> 待叉区货物数据
	 */
	public List<SubForkAreaGoodsEntity> querySubForkAreaGoods(SubForkAreaGoodsVo subForkAreaGoodsVo,int limit,int start);
	/**
	 * 查询待叉区货物数据    快递
	 * @author zenghaibin
	 * @date 2014-07-037 上午9:35:34
	 * @param subForkAreaGoodsVo 查询条件参数
	 * @param limit start 分页参数
	 * @return 返回一个参数列别List<SubForkAreaGoodsEntity> 待叉区货物数据
	 */
	public List<SubForkAreaGoodsExpressEntity> querySubForkAreaGoodsExpress(SubForkAreaGoodsVo subForkAreaGoodsVo,int limit,int start);
	/**
	 *分页  零担
	 *@author zenghaibin
	 *@param subForkAreaGoodsVo 查询参数vo
	 *@return 数据量
	 *
	 ***/
	public Long querySubForkAreaGoodsCount(SubForkAreaGoodsVo subForkAreaGoodsVo);
	
	
	/**
	 *分页  快递
	 *@author zenghaibin
	 *@param subForkAreaGoodsVo 查询参数vo
	 *@return 数据量
	 *
	 ***/
	public Long querySubForkAreaGoodsCountExpress(SubForkAreaGoodsVo subForkAreaGoodsVo);
		
	
}

