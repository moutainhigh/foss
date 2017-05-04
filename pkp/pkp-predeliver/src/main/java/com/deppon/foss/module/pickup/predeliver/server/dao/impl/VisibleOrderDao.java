package com.deppon.foss.module.pickup.predeliver.server.dao.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.deppon.foss.framework.server.components.dataaccess.ibatis.iBatis3DaoImpl;
import com.deppon.foss.module.pickup.predeliver.api.server.dao.IVisibleOrderDao;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillNewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisibleAutoSortDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualAddressMarkDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.util.define.FossConstants;

/**
 * 可视化排单Dao实现
 * @author 239284
 *
 */
public class VisibleOrderDao  extends iBatis3DaoImpl  implements IVisibleOrderDao{

	//可视化排单 name space
	private static final String NAMESPACE = "com.deppon.foss.module.pickup.predeliver.api.server.dao.IVisibleOrderDao";

	@Override
	/**
	 * 根据查询条件获取运单相关信息(预计达到、已到达)
	 */
	public List<VisualBillDto> queryBillInfoByCondition(
			VisualBillConditionDto conditionDto) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectByParam", conditionDto);
	}
	
	/**
	 * 可视化排单-(库存中)根据查询条件查询结果
	 * @param conditionDto
	 * @return
	 */
	public List<VisualBillDto> queryBillInfoInStockByCondition(VisualBillConditionDto conditionDto) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectInStockByWaybillNos", conditionDto);
	}

	/**
	 * 根据运单查询坐标聚合信息
	 * @param wayBills
	 * @return
	 */
	public List<VisualBillDto>  queryWayBillTogether(String[] wayBills, int leval) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("wayBills", wayBills);
		map.put("leval", leval);
		map.put("active", FossConstants.ACTIVE);
		return this.getSqlSession().selectList(NAMESPACE + ".selectTogetherByWaybills", map);
	}
	
	/**
	 * 根据省市区及运单号查询区下的小区和车牌号
	 * @param condition
	 * @return 省、市、区下的小区及车牌号
	 */
	public List<VisualBillDto> querySmallVehicleNo(VisualBillConditionDto condition) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectSmallVehicleNoByParam", condition);
	}
	
	/**
	 * 根据运单号查询相关信息到已排单
	 * @param wayBillNos
	 * @return
	 */
	public List<VisualBillArrageDto> addWaybillToArrage(String[] wayBillNos) {
		
		return null;
	}

	/**
	 * 根据运单号查询待排运单信息列表
	 * @param waybillToArrangeDto
	 * @return
	 */
	@Override
	public List<WaybillToArrangeDto> queryWaybillToArrangeByCondition(WaybillToArrangeDto waybillToArrangeDto) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectWaybillToArrangeByCondition", waybillToArrangeDto);
	}

	/**
	 * 根据派送单ID查询已排单明细
	 */
	public List<VisualBillArrageDto> alreadyArrangeListDto(Map<Object, Object> map, int start, int limit) {
		RowBounds rowBounds = new RowBounds(start, limit);
		return this.getSqlSession().selectList(NAMESPACE + ".selectByDeliverbillId", map, rowBounds);
	}

	 /**
	  * 根据派送单ID查询已排单明细， 不分页
	  * @param deliverbill
	  * @return
	  */
	public List<VisualBillArrageDto> alreadyArrangeListAllDto(Map<Object, Object> map) {
		return this.getSqlSession().selectList(NAMESPACE + ".selectByDeliverbillId", map);
	}
	
	 /**
	  * 可视化派送单已排单页面下的汇总条数
	  * @param deliverbillId
	  * @return
	  */
	public Map<String, Object> queryDeliverbillDetailCount(String deliverbillId) {
		return (Map<String, Object>)this.getSqlSession().selectOne(NAMESPACE + ".selectCountByDeliverbillId", deliverbillId);
	}

	 /**
	  * 是否已经排单(actual_freight中排单件数大于0,则已进入排单) 
	  * @param waybillNo
	  * @return 
	  */
	public Map<String, Object> queryIsAlreadyArrange(String waybillNo) {
		Map map = new HashMap();
		map.put("waybillNo", waybillNo);
		return (Map)this.getSqlSession().selectOne(NAMESPACE + ".selectAlreadyArrangeByWaybillNo", map); 
	}

	 /**
	  * 运单排序
	  * @param waybillNo 运单号
	  * @param serialNo 顺序号
	  * @param deliverbillId 派送单id
	  */
	public void sortWaybillByDeliverbillId(String waybillNo, int  serialNo, String deliverbillId) {
		Map map = new HashMap();
		map.put("waybillNo", waybillNo);
		map.put("serialNo", serialNo);
		map.put("deliverbillId", deliverbillId);
		this.getSqlSession().update(NAMESPACE + ".upDetailWaybillSortById", map);
	}

	 /**
	  *  根据运单号、派送单号查询派送单id、运单号对应派送单明细id
	  * @param wayBillNo
	  * @param deliverNo
	  * @return
	  */
	public Map<String, String> queryDeliverIdAndDeliverDetailId(String wayBillNo, String deliverNo) {
		Map map = new HashMap();
		map.put("wayBillNo", wayBillNo);
		map.put("deliverNo", deliverNo);
		return (Map)this.getSqlSession().selectOne(NAMESPACE + ".selectDeliverIdAndDeliverDetailID", map);
	}

	 /**
	  * 根据运单号查询
	  * 实际收货地址，包括对应的四级结构
	  * 经纬度，小区编码、小区名称，电话等
	  * @return
	  */
	public List<VisualAddressMarkDto> visibleQueryForCoordMark(String[] wayBillNos) {
		Map map = new HashMap();
		map.put("wayBillNos", wayBillNos);
		return this.getSqlSession().selectList(NAMESPACE + ".selectCoordMarkByWayBillNos", map);
	}

	 /**
	  *  根据送货日期、车牌号、派送单id(忽略当前)查询该车辆是否已经存在预排状态(SVAED)
	  * @param deliverDate 送货日期
	  * @param vehilceNo  车牌号
	  * @param deliverId 派送单id
	  * @param deliverStatus 派送单状态
	  * @param carTaskType[] 出车任务类型
	  * @return 存在记录数
	  */
	public Long visibleVehilceNoExistDeliverEntity(String deliverDate, String vehilceNo, String deliverId, String deliverStatus, String[] carTaskType) {
		Map map = new HashMap<String, String>();
		map.put("deliverDate", deliverDate);
		map.put("vehilceNo", vehilceNo );
		map.put("deliverId", deliverId );
		map.put("status", deliverStatus);
		map.put("carTaskType", carTaskType);
		Map resultMap = (Map)this.getSqlSession().selectOne(NAMESPACE + ".selectVehilceNoIsExistSaved", map);
		BigDecimal returnValue =  (BigDecimal) resultMap.get("TOTALCOUNT");
		return returnValue == null ? 0L: Long.valueOf(returnValue.longValue());
	}


	/**
	 * 根据查询条件获取运单相关信息(预计达到、已到达)
	 */
	@Override
	public List<VisibleAutoSortDto> queryVisiblebillInfoList(String id){
		return this.getSqlSession().selectList(NAMESPACE + ".queryVisiblebillInfoList", id);
	}
	
	/**
	 * 根据id查询派送单
	 */
	@Override
	public DeliverbillNewDto visiblebillInfo(String id){
		return (DeliverbillNewDto) this.getSqlSession().selectOne(NAMESPACE + ".visibleOrderService", id);
	}
	
	 /**
	  * 根据送货日期、车牌号、派送单id(忽略当前)查询该车辆相关提示
	  * 提示内容：此车牌号XX，送货日期XX，有派送单X，为XX状态
	  * @param deliverDate 送货日期
	  * @param vehilceNo 车牌号
	  * @param deliverId 派送单id
	  * @param deliverStatus 派送单状态(已保存、已提交、已分配、装车中)
	  * @return 派送单实体
	  */
	public List<DeliverbillEntity> visibleFindVehilceNoTips(String deliverDate, String vehilceNo, String deliverId, String[] deliverStatus) {
		Map map = new HashMap<String, Object>();
		map.put("deliverDate", deliverDate);
		map.put("vehilceNo", vehilceNo);
		map.put("deliverId", deliverId);
		map.put("deliverStatus", deliverStatus);
		
		return this.getSqlSession().selectList(NAMESPACE + ".selectVehilceNoTipsByParam", map);
	}

	/**
	 * 判断该派送单是否存在无坐标
	 * @param deliverId
	 * @return 如果有结果则表明派送单存在无坐标
	 */
	public List<String> existsNoCoordForDeliverBill(String deliverId) {
		Map map = new HashMap<String, Object>();
		map.put("deliverId", deliverId);
		return this.getSqlSession().selectList(NAMESPACE + ".selectExistsNoCoordForDeliverBill",map);
	}

}
