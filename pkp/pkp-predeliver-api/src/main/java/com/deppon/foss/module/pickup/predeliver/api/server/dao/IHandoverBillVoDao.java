package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.predeliver.api.shared.vo.HandoverBillVo;

/** 
 * @ClassName: IHandoverBillMatchAddressDao 
 * @Description: 已交单VoDao
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-4 下午4:34:17 
 *  
 */
public interface IHandoverBillVoDao {
	
	/**
	 * @Title: selectList
	 * @Description: 查询当天和第二天的有效交单
	 * @return 交单Vo集合
	 */
	List<HandoverBillVo> selectList();

	/**
	 * @Title: updateResidence
	 * @Description: 更新交单信息(小区Code,精度,纬度,车牌号,匹配方式)
	 * @param paramMap 修改信息Map
	 * @return 受影响的行数
	 */
	int updateResidence(Map<String,Object> paramMap);
	
	/**
	 * @Title: updateIsUsed
	 * @Description: 更改数据使用状态
	 * @param id 主键
	 * @param isUsed 使用状态
	 * @return 受影响的行数
	 */
	int updateIsUsed(String id, String isUsed);
	
	/**
	 * @Title: updateVehicle
	 * @Description: 修改交单车辆
	 * @param paramMap 传入参数
	 * @return 受影响的行数
	 */
	int updateVehicle(Map<String,Object> paramMap);
	
	/** 
	 * 根据预处理建议小区和实际小区查询运单号
	 * @param paramMap 小区和送货日期
	 * @return 运单号集合
	 * @author fangwenjun 237982
	 * @date 2015年12月11日 下午1:48:22 
	 */
	List<String> selectBySmallZone(Map<String,Object> paramMap);
	
}
