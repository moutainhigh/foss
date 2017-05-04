package com.deppon.foss.module.transfer.load.api.server.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.transfer.load.api.shared.domain.ExpressPackageEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.HandOverBillDetailEntity;
import com.deppon.foss.module.transfer.load.api.shared.domain.SerialNoStockEntity;
import com.deppon.foss.module.transfer.load.api.shared.dto.LdpHandOverDto;
import com.deppon.foss.module.transfer.load.api.shared.dto.NewHandOverBillDto;

/** 
 * @className: IExpressHandOverBillService
 * @author: ShiWei shiwei@outlook.com
 * @description: 包交接单service接口
 * @date: 2013-7-26 下午2:15:59
 * 
 */
public interface IExpressHandOverBillService extends IService {
	
	/**
	 * 根据包号获取运单信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-26 下午5:05:10
	 */
	List<HandOverBillDetailEntity> queryWaybillListByPackageNo(String packageNo);
	
	/**
	 * 根据包号和运单号获取流水号列表
	 * @author 045923-foss-shiwei
	 * @date 2013-7-26 下午5:06:03
	 */
	List<SerialNoStockEntity> querySerialNoListByPackageNoAndWaybillNo(String packageNo,String waybillNo);
	
	/**
	 * 根据包号获取包基本信息
	 * @author 045923-foss-shiwei
	 * @date 2013-7-29 下午3:24:28
	 */
	ExpressPackageEntity queryExpressPackageByPackageNo(String packageNo);
	
	/**
	 * 根据包号加载包信息，用于包生成交接单时的数据加载
	 * @author 045923-foss-shiwei
	 * @date 2013-7-29 下午3:45:28
	 */
	List<Object> loadExpressPackageInfo(String packageNo);
	
	/**
	 * 保存包交接单
	 * @author 045923-foss-shiwei
	 * @date 2013-7-30 下午3:19:08
	 */
	String saveExpressHandOverBill(NewHandOverBillDto newHandOverBillDto);
	
	/**
	 * 查询落地配交接单
	 * @author 045923-foss-shiwei
	 * @date 2013-8-05 上午9:05:08
	 */
	List<LdpHandOverDto> queryLDPHandOverBill(String handOverBillNo,String agencyCode,Date handOverDate);
/**
 * @author 200968-foss-zwd
 * 调用综合接口，根据部门编码去查询重泡比，如果存在则使用查询到的重泡比，如果不存在则使用公用的重量体积转换参数。
 * @param orgCode
 * @return
 */
	BigDecimal queryExpressConverParameter(String orgCode);
	
}
