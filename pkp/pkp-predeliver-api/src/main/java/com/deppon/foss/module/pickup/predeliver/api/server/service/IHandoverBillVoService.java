package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.HandoverBillVo;

/** 
 * @ClassName: IHandoverBillMatchAddressService 
 * @Description: 已交单VoService 
 * @author 237982-foss-fangwenjun 
 * @date 2015-5-4 下午5:24:17 
 *  
 */
public interface IHandoverBillVoService extends IService {
	
	/**
	 * @Title: selectHBMatchCommunityList
	 * @Description: 查询没有跑过Gis的有效交单
	 * @return  已交单Vo对象集合
	 */
	List<HandoverBillVo> selectHBMatchCommunityList();
	
	/**
	 * @Title: updateHandoverBill
	 * @Description: 更新交单表
	 * @param updateParamMap 传入参数
	 * @return 受影响的行数
	 */
	int updateHandoverBill(Map<String,Object> updateParamMap);
	
	/**
	 * @Title: updateHandoverBillIsUsed
	 * @Description: 修改交单的使用状态
	 * @param id 交单主键
	 * @param isUsed 使用状态
	 * @return 受影响的行数
	 */
	int updateHandoverBillIsUsed(String id, String isUsed);
	
	/**
	 * @Title: updateHandoverBillVehicle
	 * @Description: 根据小区和送货日期修改交单车辆
	 * @param smallZoneCode 小区编码
	 * @param vechileNo 车牌号
	 * @param preDeliverDate 预计送货日期
	 * @param isVehicleScheduling 是否排班
	 * @return
	 */
	int updateHandoverBillVehicle(String smallZoneCode, String vechileNo, Date preDeliverDate, String isVehicleScheduling);
	
	/**
	 * @Title: preprocess
	 * @Description: 车辆排班发生变更交单表也变
	 */
	void preprocess();
}
