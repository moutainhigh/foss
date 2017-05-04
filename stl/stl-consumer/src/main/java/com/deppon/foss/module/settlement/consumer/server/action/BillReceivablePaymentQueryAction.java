package com.deppon.foss.module.settlement.consumer.server.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillCashCollectionEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPayableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillPaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillReceivableEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillRepaymentEntity;
import com.deppon.foss.module.settlement.common.api.shared.domain.BillWriteoffEntity;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.IBillReceivablePaymentQueryService;
import com.deppon.foss.module.settlement.consumer.api.shared.vo.BillReceivablePaymentVo;
import com.deppon.foss.util.NumberUtils;

/**
 * 应收应付查询Service
 * 
 * @author foss-zhangxiaohui
 * @date Oct 24, 2012 9:19:22 AM
 */
public class BillReceivablePaymentQueryAction extends AbstractAction {

	/**
	 * 实体序列号
	 */
	private static final long serialVersionUID = 2770700390332965194L;

	/**
	 * 声明日志对象
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(BillReceivablePaymentQueryAction.class);

	/**
	 * 应收应付查询VO
	 */
	private BillReceivablePaymentVo billReceivablePaymentVO;


	/**
	 * 应收应付单Service
	 */
	private IBillReceivablePaymentQueryService billReceivablePaymentQueryService;

	/**
	 * 通过单号查询应收单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:04:56 PM
	 */
	@JSON
	public String queryReceivableBillByBillNo() {
		try {
			// 查询参数运单数组非空校验
			if (null == billReceivablePaymentVO
					|| null == billReceivablePaymentVO.getWayBillNosArray()
					|| billReceivablePaymentVO.getWayBillNosArray().length == 0) {
				// 运单数组为空则抛出异常
				throw new SettlementException("查询单号不能为空");
			}
			// 声明运单号List
			List<String> wayBillNoList = new ArrayList<String>();
			// 判断页面传过来的运单号数组是否为空及大小然后赋值给运单号的List，再传入Service
			for (int i = 0; i < billReceivablePaymentVO.getWayBillNosArray().length; i++) {
				// 把遍历出来的运单数组赋值给运单List
				wayBillNoList.add(billReceivablePaymentVO.getWayBillNosArray()[i]);
			}
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			//改成按运单号查询的方法
			List<BillReceivableEntity> billReceivableList = billReceivablePaymentQueryService
					.queryByWaybillNosAndOrgCodes(wayBillNoList, null, null,cInfo);
			
			// 返回的结果不为空的话则传回页面
			if (CollectionUtils.isNotEmpty(billReceivableList)) {
				billReceivablePaymentVO.setBillReceivableList(billReceivableList);
				// 得到查询出来的数据总条数
				billReceivablePaymentVO.setTotalRecordsInDB(BigDecimal.valueOf(billReceivableList.size()));
				// 声明合计总金额
				BigDecimal totalAmount = null;
				// 声明 核销总金额/实收总金额
				BigDecimal totalVerifyAmount = null;
				// 声明未核销总金额/未收金额
				BigDecimal totalUnverifyAmount = null;
				// 计算数据中总金额 核销金额 未核销金额，遍历之后相加
				for (int j = 0; j < billReceivableList.size(); j++) {
					// 累加合计总金额
					totalAmount = NumberUtils.add(totalAmount,billReceivableList.get(j).getAmount());
					// 累加核销总金额
					totalVerifyAmount = NumberUtils.add(totalVerifyAmount,billReceivableList.get(j).getVerifyAmount());
					// 累加未核销总金额
					totalUnverifyAmount = NumberUtils.add(totalUnverifyAmount,billReceivableList.get(j).getUnverifyAmount());
				}
				// 设置总数据条数到页面
				billReceivablePaymentVO.setTotalAmount(totalAmount);
				// 设置核销总金额到页面
				billReceivablePaymentVO.setTotalVerifyAmount(totalVerifyAmount);
				// 设置未核销总金额到页面
				billReceivablePaymentVO.setTotalUnverifyAmount(totalUnverifyAmount);
			} else {
				// 这里不做处理
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误的log信息
			LOGGER.error("应收应付查询--查询应收单" + e.getMessage(), e);
			// 返回错误信息
			return returnError("应收应付查询--查询应收单" + e);
		}
		// 返回结果
		return returnSuccess();
	}

	/**
	 * 通过单号查询应付单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:05:56 PM
	 */
	@JSON
	public String queryPayableBillByBillNo() {
		try {
			// 判断页面传过来的运单单号数组是否有值
			if (null == billReceivablePaymentVO.getWayBillNosArray()
					|| billReceivablePaymentVO.getWayBillNosArray().length == 0) {
				// 如果查询单号的数组集合为空则抛出异常
				throw new SettlementException("查询单号不能为空");
			}
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();			
			// 声明运单号List去存放页面传过来的非空运单单号数组
			List<String> wayBillNoList = new ArrayList<String>();
			// 遍历页面传过来的运单数组
			for (int i = 0; i < billReceivablePaymentVO.getWayBillNosArray().length; i++) {
				// 把遍历出来的运单数组结果添加到运单的List
				wayBillNoList.add(billReceivablePaymentVO.getWayBillNosArray()[i]);
			}
			// 把运单号List传给Service从而获取应收单数据  
			List<BillPayableEntity> billPayableList = billReceivablePaymentQueryService
					.queryPayableByWaybillNosAndOrgCodes(wayBillNoList, null, null,cInfo);
			
			
			// 应付单数据不为空则赋值给页面上的List
			if (CollectionUtils.isNotEmpty(billPayableList)) {
				// 设置查询出来的List到页面显示
				billReceivablePaymentVO.setBillPayableList(billPayableList);
				// 得到查询出来的数据总条数
				billReceivablePaymentVO.setTotalRecordsInDB(BigDecimal.valueOf(billPayableList.size()));
				// 声明合计总金额
				BigDecimal totalAmount = null;
				// 声明 核销总金额/实收总金额
				BigDecimal totalVerifyAmount = null;
				// 声明未核销总金额/未收金额
				BigDecimal totalUnverifyAmount = null;
				// 计算数据中总金额 核销金额 未核销金额，遍历之后相加
				for (int j = 0; j < billPayableList.size(); j++) {
					// 累加合计总金额
					totalAmount = NumberUtils.add(totalAmount, billPayableList.get(j).getAmount());
					// 累加核销总金额
					totalVerifyAmount = NumberUtils.add(totalVerifyAmount,billPayableList.get(j).getVerifyAmount());
					// 累加未核销总金额
					totalUnverifyAmount = NumberUtils.add(totalUnverifyAmount,billPayableList.get(j).getUnverifyAmount());
				}
				// 设置总数据条数到页面
				billReceivablePaymentVO.setTotalAmount(totalAmount);
				// 设置核销总金额到页面
				billReceivablePaymentVO.setTotalVerifyAmount(totalVerifyAmount);
				// 设置未核销总金额到页面
				billReceivablePaymentVO.setTotalUnverifyAmount(totalUnverifyAmount);
			} else {
				// 这里不做处理
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("应收应付查询--查询应付单" + e.getMessage(), e);
			// 返回错误信息
			return returnError("应收应付查询--查询应付单" + e);
		}
		// 返回结果
		return returnSuccess();
	}

	/**
	 * 通过单号查询还款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:06:56 PM
	 */
	@JSON
	public String queryRepaymentBillByBillNo() {
		try {
			// 判断页面传过来的运单单号数组是否有值
			if (null == billReceivablePaymentVO.getWayBillNosArray()
					|| billReceivablePaymentVO.getWayBillNosArray().length == 0) {

				// 查询参数的对象Dto为空则抛出异常
				throw new SettlementException("查询单号不能为空");
			}
			
			// 声明运单号List去存放页面传过来的非空运单单号数组
			List<String> wayBillNoList = new ArrayList<String>();
			// 遍历页面传过来的运单号数组
			for (int i = 0; i < billReceivablePaymentVO.getWayBillNosArray().length; i++) {
				// 把遍历的运单号数组添加到运单的LIst
				wayBillNoList.add(billReceivablePaymentVO.getWayBillNosArray()[i]);
			}
			// 把运单号List传给Service从而获取还款单数据
			List<BillRepaymentEntity> billRepaymentList = billReceivablePaymentQueryService
					.queryRepaymentBillByWayBillNOsAndOrgCodes(wayBillNoList , null, "",SettlementDictionaryConstants.SETTLEMENT__IS_RED_BACK__NO);
			// 还款单数据不为空的话则赋值给List
			if (CollectionUtils.isNotEmpty(billRepaymentList)) {
				// 设置查询返回到页面显示
				billReceivablePaymentVO.setBillRepaymentList(billRepaymentList);
				// 得到查询出来的数据总条数
				billReceivablePaymentVO.setTotalRecordsInDB(BigDecimal.valueOf(billRepaymentList.size()));
				// 声明合计总金额
				BigDecimal totalAmount = null;
				// 在内存中计算返回的结果集中的总金额
				for (int i = 0; i < billRepaymentList.size(); i++) {
					// 累加合计总金额
					totalAmount = NumberUtils.add(totalAmount,billRepaymentList.get(i).getAmount());
				}
				// 计算总金额并赋值到Vo
				billReceivablePaymentVO.setTotalAmount(totalAmount);
			} else {
				// 这里不做处理
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("应收应付查询--查询付款单" + e.getMessage(), e);
			// 返回错误信息
			return returnError("应收应付查询--查询付款单" + e);
		}
		// 返回结果
		return returnSuccess();
	}

	/**
	 * 通过单号查询现金收款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:07:56 PM
	 */
	@JSON
	public String queryCashCollectionBillByBillNo() {
		try {
			// 声明运单号List去存放页面传过来的非空运单单号数组
			if (null == billReceivablePaymentVO.getWayBillNosArray()|| billReceivablePaymentVO.getWayBillNosArray().length == 0) {
				// 如果运单数组为空则抛出异常
				throw new SettlementException("查询单号不能为空");
			}
			// 声明运单号List去存放页面传过来的非空运单单号数组
			List<String> wayBillNoList = new ArrayList<String>();
			// 遍历页面传过来的参数运单数组
			for (int i = 0; i < billReceivablePaymentVO.getWayBillNosArray().length; i++) {
				// 把遍历的结果赋值到声明的运单List
				wayBillNoList.add(billReceivablePaymentVO.getWayBillNosArray()[i]);
			}
			// 获取当前登录用户信息
			CurrentInfo cInfo = FossUserContext.getCurrentInfo();
			// 把运单号List传给Service从而获取现金收款单数据
			List<BillCashCollectionEntity> billCashCollectionList = billReceivablePaymentQueryService
					.queryCashinBySourceBillNOsAndOrgCodes(wayBillNoList,SettlementDictionaryConstants.BILL_RECEIVABLE__SOURCE_BILL_TYPE__WAYBILL,null,null,cInfo);
			
//			List<BillPayableEntity> billPayableList = billReceivablePaymentQueryService
//					.queryPayableByWaybillNosAndOrgCodes(wayBillNoList, null, null,cInfo);
			// 现金收款单数据不为空的话则赋值给List
			if (CollectionUtils.isNotEmpty(billCashCollectionList)) {
				// 设置查询结果到Vo返回页面显示
				billReceivablePaymentVO.setBillCashCollectionList(billCashCollectionList);
				// 得到查询出来的数据总条数
				billReceivablePaymentVO.setTotalRecordsInDB(BigDecimal.valueOf(billCashCollectionList.size()));
				// 声明合计总金额
				BigDecimal totalAmount = null;
				// 在内存中计算返回的结果集中的总金额
				for (int i = 0; i < billCashCollectionList.size(); i++) {
					// 累加合计总金额
					totalAmount = NumberUtils.add(totalAmount,billCashCollectionList.get(i).getAmount());
				}
				// 计算总金额并赋值到Vo
				billReceivablePaymentVO.setTotalAmount(totalAmount);
			} else {
				// 这里不做处理
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("应收应付查询--查询现金收款单" + e.getMessage(), e);
			// 返回错误信息
			return returnError("应收应付查询--查询现金收款单" + e);
		}
		// 返回结果
		return returnSuccess();
	}

	/**
	 * 通过单号查询付款单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:08:56 PM
	 */
	@JSON
	public String queryPaymentBillByBillNo() {
		try {
			// 声明运单号List去存放页面传过来的非空运单单号数组
			if (null == billReceivablePaymentVO.getWayBillNosArray()|| billReceivablePaymentVO.getWayBillNosArray().length == 0) {
				// 如果运单数组参数为空则抛出异常
				throw new SettlementException("查询单号不能为空");
			}		
			// 声明运单号List去存放页面传过来的非空运单单号数组
			List<String> wayBillNoList = new ArrayList<String>();
			// 遍历参数运单号数组
			for (int i = 0; i < billReceivablePaymentVO.getWayBillNosArray().length; i++) {
				// 把遍历出来的运单号数组赋值到声明的运单List
				wayBillNoList.add(billReceivablePaymentVO.getWayBillNosArray()[i]);
			}
			// 把运单号List传给Service从而获取付款单数据
			List<BillPaymentEntity> billPaymentList = billReceivablePaymentQueryService
					.queryPaymentBillByWayBillNOsAndOrgCodes(wayBillNoList, null, "");
			// 付款单数据不为空则传回页面
			if (CollectionUtils.isNotEmpty(billPaymentList)) {
				// 把查询结果赋值到Vo返回页面显示
				billReceivablePaymentVO.setBillPaymentList(billPaymentList);
				// 得到查询出来的数据总条数
				billReceivablePaymentVO.setTotalRecordsInDB(BigDecimal.valueOf(billPaymentList.size()));
				// 声明总金额
				BigDecimal totalAmount = null;
				// 在内存中计算返回结果的总金额
				for (int i = 0; i < billPaymentList.size(); i++) {
					// 累加总金额
					totalAmount = NumberUtils.add(totalAmount, billPaymentList.get(i).getAmount());
				}
				// 计算查询出来的结果集的总金额并赋值到Vo
				billReceivablePaymentVO.setTotalAmount(totalAmount);
			} else {
				// 这里不做处理
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("应收应付查询--查询付款单" + e.getMessage(), e);
			// 返回错误信息
			return returnError("应收应付查询--查询付款单" + e);
		}
		// 返回结果
		return returnSuccess();
	}

	/**
	 * 通过单号查询核销单
	 * 
	 * @author foss-zhangxiaohui
	 * @date Oct 30, 2012 4:10:56 PM
	 */
	@JSON
	public String queryWriteoffBillByBillNo() {
		try {
			// 声明运单号List去存放页面传过来的非空运单单号数组
			if (null == billReceivablePaymentVO.getWayBillNosArray()|| billReceivablePaymentVO.getWayBillNosArray().length == 0) {
				// 如果传过来的运单数组为空则抛出异常
				throw new SettlementException("查询单号不能为空");
			}			
			// 声明运单号List去存放页面传过来的非空运单单号数组
			List<String> wayBillNoList = new ArrayList<String>();
			// 遍历运单数组
			for (int i = 0; i < billReceivablePaymentVO.getWayBillNosArray().length; i++) {
				// 把运单数组添加到声明的运单List
				wayBillNoList.add(billReceivablePaymentVO.getWayBillNosArray()[i]);
			}
			// 把运单号List传给Service从而获取核销单数据
			List<BillWriteoffEntity> billWriteoffList = billReceivablePaymentQueryService
					.queryWriteoffBillByWayBillNOsAndOrgCodes(wayBillNoList , null, "");
			// 核销单数据不为空则传回页面
			if (CollectionUtils.isNotEmpty(billWriteoffList)) {
				// 把查询结果赋值到Vo返回页面显示
				billReceivablePaymentVO.setBillWriteoffList(billWriteoffList);
				// 得到查询出来的数据总条数
				billReceivablePaymentVO.setTotalRecordsInDB(BigDecimal.valueOf(billWriteoffList.size()));
				// 声明总金额
				BigDecimal totalAmount = null;
				// 在内存中计算返回结果的总金额
				for (int i = 0; i < billWriteoffList.size(); i++) {
					// 累加总金额
					totalAmount = NumberUtils.add(totalAmount, billWriteoffList.get(i).getAmount());
				}
				// 计算查询出来的结果集的总金额并赋值到Vo
				billReceivablePaymentVO.setTotalAmount(totalAmount);
			} else {
				// 这里不做处理
			}
		}
		// 捕获异常
		catch (BusinessException e) {
			// 打印错误信息
			LOGGER.error("应收应付查询--查询核销单" + e.getMessage(), e);
			// 返回错误信息
			return returnError("应收应付查询--查询核销单" + e);
		}
		// 返回结果
		return returnSuccess();
	}
	
	/**
	 * @return billReceivablePaymentVO
	 */
	public BillReceivablePaymentVo getBillReceivablePaymentVO() {
		return billReceivablePaymentVO;
	}

	/**
	 * @param billReceivablePaymentVO
	 */
	public void setBillReceivablePaymentVO(
			BillReceivablePaymentVo billReceivablePaymentVO) {
		this.billReceivablePaymentVO = billReceivablePaymentVO;
	}

	/**
	 * @param billReceivablePaymentQueryService
	 */
	public void setBillReceivablePaymentQueryService(
			IBillReceivablePaymentQueryService billReceivablePaymentQueryService) {
		this.billReceivablePaymentQueryService = billReceivablePaymentQueryService;
	}
}
