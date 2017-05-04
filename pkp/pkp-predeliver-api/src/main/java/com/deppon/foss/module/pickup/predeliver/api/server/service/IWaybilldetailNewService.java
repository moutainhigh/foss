package com.deppon.foss.module.pickup.predeliver.api.server.service;

import java.util.Date;
import java.util.List;

import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.DeliverbillEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.PermissionControlDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDeliverNewCountDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailBillArrageDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillDetailNewQueryDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WaybillToArrangeDto;



 /**
  * 创建派送单(新) ---运单明细
  * @author 159231 meiying
  * 2015-6-3  下午3:44:11
  */
public interface IWaybilldetailNewService {
	/**
	 * 根据条件查询运单明细总数
	 * @author 159231 meiying
	 * 2015-6-3  下午3:47:47
	 * @param pre
	 * @return
	 */
	WaybillDeliverNewCountDto queryWaybilldetailNewCount(WaybillDetailNewQueryDto pre);
	/**
	 * 根据条件查询运单明细
	 * @author 159231 meiying
	 * 2015-6-3  下午3:48:30
	 * @param pre
	 * @param start
	 * @param limit
	 * @return
	 */
	List<WaybillDetailDto> queryWaybillDetailNewList(
			WaybillDetailNewQueryDto pre, int start, int limit);
	/**
	 * 
	 * @author 159231 meiying
	 * 2015-6-7  下午4:39:55
	 * @param deliverbill
	 * @return
	 */
	DeliverbillEntity saveDeliverbill(DeliverbillEntity deliverbill);
	/**
	 * 
	 * @author 159231 meiying
	 * 2015-6-7  下午4:53:08
	 * @param deliverbill
	 * @param waybillToArrangeDtoList
	 * @param currentInfo
	 * @return
	 */
	List<WaybillToArrangeDto> addWaybillToArrange(DeliverbillEntity deliverbill, List<WaybillToArrangeDto> waybillToArrangeDtoList, 	CurrentInfo currentInfo);
	/**
	 * 
	 * @author 159231 meiying
	 * 2015-6-7  下午6:15:16
	 * @param deliverbillId
	 * @return
	 */
	List<WaybillDetailBillArrageDto>  queryDeliverbillDetailList(String deliverbillId);
	/**
	 * 运单右移时调用接口
	 * @author 159231 meiying
	 * 2015-6-10  下午4:39:28
	 * @param deliverbill
	 * @param waybillNo
	 */
	DeliverbillEntity waybillDetailAddToArrangeByWaybillNo(DeliverbillEntity deliverbill,String waybillNo,Date deliverDate);
	/**
	 * 查询已排运单里的( 装载率(容积/体积): 额定净空(方)/额定载重(吨))
	 * @author 159231 meiying
	 * 2015-6-12  上午10:51:55
	 * @param detail
	 * @param querydto
	 * @param deliverId
	 * @return
	 */
	WaybillDeliverNewCountDto queryRightCount(WaybillDeliverNewCountDto detail,WaybillDetailNewQueryDto querydto, String deliverId);
	
	/**
	 * @Title: initOrgRole
	 * @Description: 初始化部门
	 * @return
	 */
	PermissionControlDto initOrgRole();
	
	/**
	 * 
	 * 根据查询条件查询待排运单
	 * 
	 * @param waybillDetailNewQueryDto
	 *            查询条件
	 * @author foss-sunyanfei
	 * @date 2015-8-4 上午9:32:27
	 */
	List<WaybillDetailDto> queryWaybillDetailNewListByCondition(WaybillDetailNewQueryDto waybillDetailNewQueryDto, int start, int limit);
	
	/**
	 *  查询派送单相关信息
	 * @author 239284
	 * 2015-6-7  下午4:39:55
	 * @param deliverbillId
	 * @return
	 */
	DeliverbillEntity queryDeliverbillById(String deliverbillId);
	
	
	/**
	 * 根据运单号查询收货联系人
	 */
	String queryReceiveCustomerContactByWaybillNo(String waybillNo);
	
	/**
	 * 根据运单号查询交单时间
	 * @param waybillNo
	 * @return
	 */
	Date queryBilltimeByWaybillNo(String waybillNo);
}
