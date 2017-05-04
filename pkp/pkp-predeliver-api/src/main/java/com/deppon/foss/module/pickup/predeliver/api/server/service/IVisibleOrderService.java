package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.List;
import java.util.Map;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.DeliverbillConstants;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.VisibleHandBillReturnEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.DeliverbillNewDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisibleAutoSortDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualAddressMarkDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.VisualBillDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.DeliverbillVo;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.VisualBillVo;

/**
 * 可视化排单Service
 * @author 239284
 *
 */
public interface IVisibleOrderService extends IService {

	/**
	 * 可视化排单-根据条件查询运单给GIS信息
	 * @param conditionDto
	 * @return
	 */
	List<VisualBillDto> queryBillInfoByCondition(VisualBillConditionDto conditionDto);
	
	/**
	 * 可视化排单-根据查询条件查询结果
	 * @param conditionDto
	 * @return
	 */
	String queryBillInfoToMap(VisualBillConditionDto conditionDto);
	
	/**
	 * 根据运单号查询待排运单信息列表
	 * @param waybillToArrangeDto
	 * @return
	 */
	List<WaybillToArrangeDto> queryWaybillToArrangeByCondition(WaybillToArrangeDto waybillToArrangeDto);
	
	/**
	 * 保存(新增/更新)派送单
	 * @param deliverbill
	 * @return
	 */
	DeliverbillEntity saveDeliverbill(DeliverbillEntity deliverbill);
	
	/**
	 * 添加运单至派送单.
	 * @param waybillToArrangeDtoList 添加的运单列表
	 * @param currentInfo the current info
	 * @return 添加失败的运单列表
	 * @return
	 */
	 List<WaybillToArrangeDto> addWaybillToArrange(DeliverbillEntity deliverbill, List<WaybillToArrangeDto> waybillToArrangeDtoList, CurrentInfo currentInfo);
	
	 /**
	  * 根据派送单ID查询已排单明细
	  * @param deliverbill_id
	  * @return
	  */
	 List<VisualBillArrageDto> alreadyArrangeListDto(String deliverbill_id);
	 
	 /**
	  * 校验是否存在未处理的更改单(或目的站发生变化)
	  * @param waybillNos
	  * @return
	  */
	 DeliverbillVo checkWaybillNos(List<String> waybillNos);
	 
	 /**
	  * 查询派送单已排单页面下的汇总条数
	  * @param deliverbillVo (派送单vo.车牌号、送货日期计算装载率)
	  * @return
	  */
	 VisualBillVo  queryDeliverbillDetailCount(DeliverbillVo deliverbillVo,  VisualBillVo vo);
	 
	 /**
	  * 查询派送单已排单下的明细信息,分页
	  * @param deliverbillId
	  * @param start
	  * @param limit
	  * @return
	  */
	 List<VisualBillArrageDto>  queryDeliverbillDetailList(String deliverbillId, int start, int limit);
	 
	 /**
	  * 查询派送单已排单下的明细信息 不分页
	  * @param deliverbillId
	  * @param start
	  * @param limit
	  * @return
	  */
	 List<VisualBillArrageDto>  queryDeliverbillDetailAllList(String deliverbillId);
	 
	 /**
	  * 运单退回
	  * @param wayBillNos 待退回运单集合
	  * @param returnReason 退回原因
	  * @param returnReasonNotes 退回原因备注
	  * @return 退回失败的运单
	  */
	 List<VisibleHandBillReturnEntity> visibleBillReturn(String[] wayBillNos, String returnReason, String returnReasonNotes); 
	 
	 /**
	  * 自动排序
	  * @param sortSequen [运单号，顺序号]
	  * @param deliverbillId 派送单id
	  */
	 void autoSortWaybill(Map<String, String>  sortMap,  String deliverbillId);
	 
	 /**
	  * PAD退回接口(把已排单移动到未排单)
	  * @param waybillNo
	  * @param deliverNo
	  * @param returnReason
	  * @return
	  */
	 String deleteDeliverDetailForPDA(String waybillNo, String deliverNo, String returnReason);
	 
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
	  * @return 派送单实体
	  */
	 DeliverbillEntity visibleFindVehilceNoTips(String deliverDate, String vehilceNo, String deliverId);

	/**
	 * 判断该派送单是否存在无坐标
	 * @param deliverId 派送单id
	 * @return 是否存在无坐标(true:存在无坐标运单， false:不存在无坐标运单)
	 */
	 boolean existsNoCoordForDeliverBill(String deliverId);

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
	 
	 /**
	  * 根据派送单明细id更新派送单明细
	  * @param visibleAutoSortDtoList
	  */
	 void updateVisibleDeliverbill(List<VisibleAutoSortDto> visibleAutoSortDtoList,DeliverbillNewDto deliverbillNewDto);
	 
	 /**
	  * 自动排序计算
	  * @param visibleAutoSortDto
	  * @param visibleAutoSortDtoList
	  * @return
	  */
	 Map<String,Object> visibleAutoSortCalculate(DeliverbillNewDto deliverbillNewDto,String deliverbillId);

}
