package com.deppon.foss.module.transfer.unload.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.transfer.unload.api.shared.domain.ForkliftDriverEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.ForkliftEfficientEntity;
import com.deppon.foss.module.transfer.unload.api.shared.domain.TransferFieldEntity;
import com.deppon.foss.module.transfer.unload.api.shared.vo.ForkliftEfficientVo;

public interface IForkliftEfficientQueryDao {
	
	/**
	 *查询叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return List<ForkliftEfficientEntity> 叉车数量信息Entity
	 * @param forkliftEfficientVo 前台页面传递的查询条件 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	public List<ForkliftEfficientEntity> queryForkliftData(ForkliftEfficientVo forkliftEfficientVo,int limit,int start);
	
	/**
	 *查询叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return 数据总数 
	 * @param forkliftEfficientVo 前台页面传递的查询条件 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	public Long queryForkliftDataCount(ForkliftEfficientVo forkliftEfficientVo);
	/**
	 *添加叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-08 下午4:50:53
	 * @return List<ForkliftEfficientEntity> 叉车数量信息Entity
	 * @param forkliftEfficientEntity 需添加的数据实体
	 ***/
	public void addForkliftData(ForkliftEfficientEntity forkliftEfficientEntity);
	/**
	 *修改叉车数量信息 
	 * @author zenghaibin
	 * @date 2014-7-10 上午9:50:53
	 * @return void
	 * @param forkliftEfficientVo 前台页面传递参数 forkliftEfficientVo.configDate 配置日期
	 * 	forkliftEfficientVo.transfField 转运场
	 ***/
	public void updateForkliftData(ForkliftEfficientEntity forkliftEfficientEntity);
	/**
	 *查询该转运场是否存在 
	 * @author zenghaibin
	 * @date 2014-7-10 下上午9:50:53
	 * @return long
	 * @param transfFieldCode 转运场编码
	 ***/
	public Long queryTransfFieldExist(String transfFieldCode);
	/**
	 *作废叉车数量信息 即添加有效截至日期
	 * @author zenghaibin
	 * @date 2014-7-10 下上午9:50:53
	 * @return void
	 * @param forkliftEfficientEntity 数据forkliftEfficientEntity
	 ***/
	public void invalidForkliftData(ForkliftEfficientEntity forkliftEfficientEntity);
	
	/**
	 *电叉司机数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@return 
	 **/
	public List<ForkliftDriverEntity>  queryForkliftDriverData(ForkliftEfficientVo forkliftEfficientVo,int limit,int start);
	
	/**
	 *电叉司机数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@return long  叉车司机数据数， 分页用
	 **/
	public Long queryForkliftDriverDataCount(ForkliftEfficientVo forkliftEfficientVo);
	/**
	 *转运场数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@return 
	 **/
	public List<TransferFieldEntity> queryTransferFieldData(ForkliftEfficientVo forkliftEfficientVo,int limit,int start);
	
	/**
	 *转运场数据信息查询
	 *@author zenghain
	 *@date 2014-7-12
	 *@param forkliftEfficientVo 参数数据
	 *@return Long 分页用
	 **/
	public Long queryTransferFieldDataCount(ForkliftEfficientVo forkliftEfficientVo);
	/**
	 * @author niuly
	 * @date 2014-7-18下午1:40:10
	 * @function 统计叉车效率
	 * @return String
	 */
	Map<String,String> queryForkliftEfficiency();
}

