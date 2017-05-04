package com.deppon.foss.module.settlement.consumer.api.server.service;

import java.util.List;

import com.deppon.foss.module.base.baseinfo.api.shared.domain.BusinessPartnerEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerDto;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.pickup.waybill.shared.dto.WaybillDto;
import com.deppon.foss.module.settlement.common.api.shared.dto.AbnormalBillAmountCalculatedDto;
import com.deppon.foss.module.settlement.consumer.api.shared.domain.OtherRevenueEntity;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCOtherRevenueRequestDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CUBCOtherRevenueResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.CustomerInfoDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRenvenueNoTotalPrtResultDto;
import com.deppon.foss.module.settlement.consumer.api.shared.dto.OtherRevenueDto;

/**
 * 小票Dao
 * 
 * @author 101911-foss-zhouChunlai
 * @date 2012-11-20 上午9:59:17
 */
public interface IOtherRevenueService {

	/**
	 * 
	 * 判断小票号是否连续
	 * 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-2-27 上午10:11:14
	 */
	public boolean isConsecutiveNum(CurrentInfo currentInfo,
			String otherRevenueNo);

	/**
	 * 新加小票记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:48:42
	 */
	String addOtherRevenue(OtherRevenueDto otherRevenueDto,
			CurrentInfo currentInfo);

	/**
	 * 根据客户编码获取客户信息
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-21 下午5:27:20
	 */
	CustomerDto getCustomerInfo(String customerCode);
	
	/**
	 *
	 * 根据客户代理编码获取偏线、空运代理信息
	 * @Title: getPartLineAgencyInfo 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-26 下午4:14:12
	 * @param @param customerCode
	 * @param @return    设定文件 
	 * @return BusinessPartnerEntity    返回类型 
	 * @throws
	 */
	BusinessPartnerEntity getAgencyInfo(String customerCode,String customerType);
	
	/**
	 * 
	 * 查询客户信息
	 * @Title: queryCustomerInfo 
	 * @author 046644-foss-zengbinwen
	 * @date 2013-3-26 下午4:39:03
	 * @param @param dto
	 * @param @return    设定文件 
	 * @return CustomerInfoDto    返回类型 
	 * @throws
	 */
	CustomerInfoDto queryCustomerInfo(CustomerInfoDto dto);

	/**
	 * 作废小票
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:48:56
	 */
	void cancelOtherRevenue(OtherRevenueDto otherRevenueDto,
			CurrentInfo currentInfo);

	/**
	 * 查询小票记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	List<OtherRevenueEntity> queryOtherRevenue(OtherRevenueDto otherRevenueDto,
			int start, int limit);

	/**
	 * 按小票ID查询小票记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	OtherRevenueEntity queryOtherRevenueById(String id);

	/**
	 * 按时间查询打印小票记录
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-20 上午9:49:10
	 */
	public OtherRenvenueNoTotalPrtResultDto queryOtherRevenue(
			OtherRevenueDto otherRevenueDto);

	/**
	 * 查询小票记录总条数
	 * 
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-10-15 下午2:10:20
	 */
	OtherRevenueDto countQueryOtherRevenue(OtherRevenueDto otherRevenueDto);

	/**
	 * 根据小票单号查询
	 * 
	 * @otherRevenueNos 小票单号集合
	 * @active 是否有效
	 * @author 101911-foss-zhouChunlai
	 * @date 2012-11-23 上午11:05:23
	 */
	List<OtherRevenueEntity> queryOtherRevenueByOtherRevenueNos(
			List<String> otherRevenueNos, String active);
	
	/**
	 * 根据运单号查询小票记录
	 *
	 *
	 * @author 092036-foss-bochenlong
	 * @date 2013-10-10 上午8:43:01 
	 * @param waybillNO
	 * @return List<OtherRevenueEntity>
	 */
	List<OtherRevenueEntity> queryOtherRevenueByWayBillNO(String waybillNO);

	/**
	 * 查询运单
	 * @param trim
	 * @return
	 */
	public WaybillDto queryWaybillByNo(String waybillNo);
	/**
	 * @Function: com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService.queryOtherRevenueByWayBillNO
	 * @Description:根据运单号和发票产生类别 发票产生部门 查询小票记录
	 * @param waybillNO
	 * @return
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-20 上午9:29:30
	 */
	List<OtherRevenueEntity> queryOtherRevenueByWayBillNOAndInvoiceCategory(String waybillNO,CurrentInfo currentInfo);
	/**
	 * @Function: com.deppon.foss.module.settlement.consumer.api.server.service.IOtherRevenueService.addOtherRevenueByWayBill
	 * @Description:在结清货款的时候生成保管费小票记录
	 * @param otherRevenueDto
	 * @param currentInfo
	 * @return
	 * @version:v1.0
	 * @author:130376-YANGKANG
	 * @date:2014-5-20 下午4:46:03
	 */
	String addOtherRevenueByWayBill(OtherRevenueDto otherRevenueDto,
			CurrentInfo currentInfo);
	
	/**
	 *@author 310970
	 *@param   otherRevenueDto,currentInfo
	 *针对于财务那作废还借支小票
	 */
	public void cancelOtherRevenueOfRB(OtherRevenueDto otherRevenueDto,
			CurrentInfo currentInfo);
	
	/**
	 * 347069
	 * @param dto
	 * @return
	 * 为QMS提供小票异常货处置EH类型的小票金额
	 */
	AbnormalBillAmountCalculatedDto queryOtherRevenueByRevenueNo(AbnormalBillAmountCalculatedDto dto);
	
	/**
	 * 353654 调用CUBC小票新增接口FOSS客户端接口
	 * @param CUBCOtherRevenueResultDto dto
	 * @return
	 */
	CUBCOtherRevenueResultDto addOtherRevenueToCUBC(CUBCOtherRevenueRequestDto requestDto);
	
}
