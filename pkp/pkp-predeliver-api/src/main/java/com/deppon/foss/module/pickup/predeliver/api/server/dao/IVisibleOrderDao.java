package com.deppon.foss.module.pickup.predeliver.api.server.dao;

import java.util.List;
import java.util.Map;

import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillNewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisibleAutoSortDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualAddressMarkDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;

/**
 * 可视化排单Dao接口
 * @author 239284
 *
 */
public interface IVisibleOrderDao {

	/**
	 * 可视化排单-(预计到达、已到达)根据查询条件查询结果
	 * @param conditionDto
	 * @return
	 */
	List<VisualBillDto> queryBillInfoByCondition(VisualBillConditionDto conditionDto);
	
	/**
	 * 可视化排单-(库存中)根据查询条件查询结果
	 * @param conditionDto
	 * @return
	 */
	List<VisualBillDto> queryBillInfoInStockByCondition(VisualBillConditionDto conditionDto);
	
	/**
	 * 根据运单查询坐标聚合信息
	 * @param wayBills
	 * @return
	 */
	List<VisualBillDto>  queryWayBillTogether(String[] wayBills,  int leval) ;
	
	/**
	 * 根据省市区及运单号查询区下的小区和车牌号
	 * @param condition
	 * @return 省、市、区下的小区及车牌号
	 */
	List<VisualBillDto> querySmallVehicleNo(VisualBillConditionDto condition);
	
	/**
	 * 根据运单号查询相关信息到已排单
	 * @param wayBillNos
	 * @return
	 */
	List<VisualBillArrageDto>  addWaybillToArrage(String[] wayBillNos);
	
	/**
	 * 保存(新增/更新)派送单
	 * @param deliverbill
	 * @return
	 */
	//	DeliverbillEntity saveDeliverbill(DeliverbillEntity deliverbill);
	
	/**
	 * 根据运单号查询待排运单信息列表
	 * @param waybillToArrangeDto
	 * @return
	 */
	List<WaybillToArrangeDto> queryWaybillToArrangeByCondition(WaybillToArrangeDto waybillToArrangeDto);
	
	 /**
	  * 根据派送单ID查询已排单明细, 分页
	  * @param deliverbill
	  * @return
	  */
	 List<VisualBillArrageDto> alreadyArrangeListDto(Map<Object, Object> map, int start, int limit);
	 
	 /**
	  * 根据派送单ID查询已排单明细， 不分页
	  * @param deliverbill
	  * @return
	  */
	 List<VisualBillArrageDto> alreadyArrangeListAllDto(Map<Object, Object> map);
	 
	 /**
	  * 可视化派送单已排单页面下的汇总条数
	  * @param deliverbillId
	  * @return
	  */
	 Map<String, Object> queryDeliverbillDetailCount(String deliverbillId);
	
	 /**
	  * 是否已经排单(actual_freight中排单件数大于0,则已进入排单) 
	  * @param waybillNo
	  * @return 
	  */
	 Map<String, Object> queryIsAlreadyArrange(String waybillNo);
	 
	 /**
	  * 运单排序
	  * @param waybillNo 运单号
	  * @param serialNo 顺序号
	  * @param deliverbillId 派送单id
	  */
	 void sortWaybillByDeliverbillId(String waybillNo,  int serialNo,   String deliverbillId);
	 
	 /**
	  *  根据运单号、派送单号查询派送单id、运单号对应派送单明细id
	  * @param wayBillNo
	  * @param deliverNo
	  * @return
	  */
	 Map<String, String> queryDeliverIdAndDeliverDetailId(String wayBillNo, String deliverNo);

	 /**
	  * 根据运单号查询
	  * 实际收货地址，包括对应的四级结构
	  * 经纬度，小区编码、小区名称，电话等
	  * @return
	  */
	 List<VisualAddressMarkDto> visibleQueryForCoordMark(String[] wayBillNos);
	 
	 /**
	  *  根据送货日期、车牌号、派送单id(忽略当前)查询该车辆是否已经存在预排状态(SVAED)
	  * @param deliverDate 送货日期
	  * @param vehilceNo  车牌号
	  * @param deliverId 派送单id
	  * @param deliverStatus 派送单状态
	  * @param carTaskType[] 出车任务类型
	  * @return 存在记录数
	  */
	 Long visibleVehilceNoExistDeliverEntity(String deliverDate, String vehilceNo, String deliverId, String deliverStatus, String[] carTaskType);
	 
	 /**
	  * 根据送货日期、车牌号、派送单id(忽略当前)查询该车辆相关提示
	  * 提示内容：此车牌号XX，送货日期XX，有派送单X，为XX状态
	  * @param deliverDate 送货日期
	  * @param vehilceNo 车牌号
	  * @param deliverId 派送单id
	  * @param deliverStatus 派送单状态(已保存、已提交、已分配、装车中)
	  * @return 派送单实体
	  */
	 List<DeliverbillEntity> visibleFindVehilceNoTips(String deliverDate, String vehilceNo, String deliverId, String[] deliverStatus);

	/**
	 * 判断该派送单是否存在无坐标
	 * @param deliverId
	 * @return 如果有结果则表明派送单存在无坐标
	 */
	 List<String> existsNoCoordForDeliverBill(String deliverId);
	 /**
	  * 根据派送单id查询已排单运单详细信息
	  * @param id
	  * @return
	  */
	 List<VisibleAutoSortDto> queryVisiblebillInfoList(String id);
	 
	 /**
	  * 根据派送单id查询派送单信息
	  * @param id
	  * @return
	  */
	 DeliverbillNewDto visiblebillInfo(String id);
}
