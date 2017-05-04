/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/action/NotifyCustomerAction.java
 * 
 * FILE NAME        	: NotifyCustomerAction.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.action;

import org.apache.commons.lang3.StringUtils;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.module.pickup.predeliver.api.server.service.ICustomerReceiptHabitService;
import com.deppon.foss.module.pickup.predeliver.api.server.service.INotifyCustomerService;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.CustomerReceiptHabitEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.domain.NotificationEntity;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.NotifyCustomerConditionDto;
import com.deppon.foss.module.pickup.predeliver.api.shared.vo.NotifyCustomerVo;
import com.deppon.foss.module.pickup.pricing.api.server.service.IPricingValueAddedService;
import com.deppon.foss.module.settlement.common.api.server.service.IBillReceivableService;
import com.deppon.foss.module.settlement.common.api.shared.define.SettlementDictionaryConstants;

/**
 * 通知客户ACTION，包括通知客户，查询待通知的运单等操作。
 * 
 * 营业员通过单一或组合条件查询，
 * 获得需要通知客户的运单信息
 * 以及运单相关的历史通知，
 * 通过短信，
 * 语音或者电话等方式通知客户。
 * 
 * @author ibm-wangfei
 * @date 2012-10-11
 * @version 1.0
 */
public class NotifyCustomerAction extends AbstractAction {
	/**
	 * serial version Id
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 客户通知VO
	 */
	private NotifyCustomerVo vo = new NotifyCustomerVo();
	/**
	 * 通知客户Service
	 */
	private INotifyCustomerService notifyCustomerService;
	/**
	 * 增值服务service
	 */
	private IPricingValueAddedService pricingValueAddedService;
	
	private IBillReceivableService billReceivableService;
	
	//客户收货习惯
	private ICustomerReceiptHabitService customerReceiptHabitService;

	/**
	 * 运单通知页面初始加载.
	 * 
	 * 正常的运单以及申请了运单变更，
	 * 但状态为“已受理”的运单是查询对象。
	 * 根据输入条件不同，查询数据时必须遵循以下规则：
	 * 1. 
	 * 到达部门必须为本部门
	 * 2. 
	 * 派送方式为送货、已通知成功且约定了送货日期，在送货日期当日可查询出来
	 * 3. 
	 * 运单付款方式为非临时欠款等收款放货
	 * 4. 
	 * 货物（运单或货件）状态为非异常
	 * 
	 * 默认查询到达本地以及本地库存的运单
	 * （输入运单号、派送方式、运输性质、通知情况、在库天数、入库时间条件查询）显示图1界面
	 * 1.	
	 * 到达件数为该票运单总共到达件数，货物出库后数量不减
	 * 
	 * 输入交接单号、车次号、预计到达时间查询，
	 * 则按照交接单查询，显示图2界面
	 * 1.	
	 * 交接单到达，则列表中“到达件数”等于“交接单件数”，交接单未到达，则列表中到达件数等于0
	 * 
	 * 查询条件规则：
 	 * 若输入“运单号”查询条件，则忽略所有其他查询条件；
 	 *  否则，
 	 *  	若输入“交接单号”查询条件，则忽略“在库天数”、“车次号”和“入库时间”查询条件；
 	 *  否则，
 	 *  	若输入“车次号”查询条件，则忽略“在库天数”和“入库时间”查询条件；
 	 *  否则，
 	 *  	若输入“入库时间”查询条件，则忽略“预计到达时间”查询条件；
	 *  否则，
	 *   	若输入“预计到达时间”查询条件，则忽略“在库天数”查询条件。
	 *   
	 * 查询条件中，
	 * 入库时间与预计到达时间可以不输入；
	 * 如果需要指定入库时间或者预计到达时间做为查询条件，	
	 * 允许不输入起始时间，仅输入截至时间。
	 * 
	 * @return 
	 * 			the string
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Oct 18, 2012 11:42:29 AM
	 */
	@JSON
	public String notifyCustomer() {
		try {
			// 初始化vo
			vo = new NotifyCustomerVo();
			// 设置仓库免费保管天数
			vo.setWarehouseFreeSafeDataNum(String.valueOf(this.notifyCustomerService.getWarehouseFreeSafeDataNum()));
		} catch (BusinessException e) {
			// 有异常是，return error message
			return super.returnError(e);
		}
		// return success message
		return super.returnSuccess();
	}

	/**
	 * 查询运单列表.
	 * 
	 * @return 
	 * 			the string
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Oct 11, 2012 5:25:33 PM
	 */
	@JSON
	public String queryStayNotificationBill() {
		try {
			// 查询符合条件的运单总记录数
			NotifyCustomerConditionDto condition = this.notifyCustomerService.queryWaybillInfoCount(vo.getConditionDto());
			// 根据运单总记录数
			if (condition != null && condition.getTotalVotes()> 0) {
				this.setTotalCount(condition.getTotalVotes());
				// 运单总记录数   ≥ 0
				// 查询符合条件的记录列表
				vo.setDtoList(this.notifyCustomerService.queryWaybillInfoList(vo.getConditionDto(), this.getStart(), this.getLimit()));
				vo.setConditionDto(condition);
			} else {
				// 运单记录  ≤ 0
				vo.setDtoList(null);
				// 设置页面显示的记录总数
				this.setTotalCount(Long.valueOf(0));
				vo.setConditionDto(null);
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 查询运单信息.
	 * 
	 * 界面中“到达件数”为库存件数，
	 * 当库存件数和开单件数不一致时，
	 * 使用粉色标识
	 * 
	 * 未通知、
	 * 开单库存件数不一致、
	 * 通知三天未提货颜色冲突时，
	 * 按未通知、
	 * 开单库存件数不一致、
	 * 通知三天未提货顺序优先级显示颜色
	 * 
	 * @return 
	 * 			the string
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Oct 11, 2012 5:25:33 PM
	 */
	@JSON
	public String queryWaybillInfo() {
		try {
			// 判断vo属性及vo的waybillno属性
			if (vo != null && vo.getConditionDto().getWaybillNo() != null) {
				// waybill_no存在的时候，进行运单详细信息查询
				NotifyCustomerConditionDto conditionDto = this.notifyCustomerService.queryWaybillInfo(vo.getConditionDto());
				
				//判断开单为到付, 是否已经网上支付-239284
				String payType = vo.getConditionDto().getNotifyCustomerDto().getPaidMethodVir();
				if (StringUtils.isNotBlank(payType) 
						&& SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__FREIGHT_COLLECT.equals(payType)) {
					String[] billTypes = new String[]{SettlementDictionaryConstants.BILL_RECEIVABLE__BILL_TYPE__DESTINATION_RECEIVABLE};
//					String wayBillNo =  "14111708".equals(vo.getConditionDto().getWaybillNo()) ? "":"AAA"; //vo.getConditionDto().getWaybillNo();
					String wayBillNo = vo.getConditionDto().getWaybillNo();
					String actualType = SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE; //SettlementDictionaryConstants.SETTLEMENT__PAYMENT_TYPE__ONLINE;
					int count = billReceivableService.queryIsOrPayByBillNo(billTypes, wayBillNo, null, null, actualType);
					if (count > 0) {
						conditionDto.getNotifyCustomerDto().setIsOrPayStatus("网上支付（已付）");
					}
				}
				
				//客户收货习惯历史信息  
				CustomerReceiptHabitEntity customerReceiptHabitEntity = new CustomerReceiptHabitEntity();
				//设置查询条件-客户代码
				customerReceiptHabitEntity.setCustomerCode(vo.getConditionDto().getNotifyCustomerDto().getReceiveCustomerCode());
				//设置查询条件-客户名称
				customerReceiptHabitEntity.setCustomerName(vo.getConditionDto().getNotifyCustomerDto().getReceiveCustomerName());
				//查询条件-手机
				customerReceiptHabitEntity.setCustomerMobilePhone(vo.getConditionDto().getNotifyCustomerDto().getReceiveCustomerMobilephone());
				//查询条件-座机
				customerReceiptHabitEntity.setCustomerPhone(vo.getConditionDto().getNotifyCustomerDto().getReceiveCustomerPhone());
				//查询条件-客户联系人
				customerReceiptHabitEntity.setCustomerContactName(vo.getConditionDto().getNotifyCustomerDto().getReceiveCustomerContact());
				//查询条件-当前操作人代码
				customerReceiptHabitEntity.setOperateOrgCode(FossUserContext.getCurrentDeptCode());
				
				CustomerReceiptHabitEntity newCustEntity = this.customerReceiptHabitService.selectReceiptHabitByParam(customerReceiptHabitEntity);
				if (null != newCustEntity) {
					conditionDto.getNotificationEntity().setDeliveryTimeInterval(newCustEntity.getDeliveryTimeInterval());
					conditionDto.getNotificationEntity().setDeliveryTimeStart(newCustEntity.getDeliveryTimeStart());
					conditionDto.getNotificationEntity().setDeliveryTimeOver(newCustEntity.getDeliveryTimeOver());
					conditionDto.getNotificationEntity().setInvoiceType(newCustEntity.getInvoiceType());
					conditionDto.getNotificationEntity().setInvoiceDetail(newCustEntity.getInvoiceDetail());
					conditionDto.getNotificationEntity().setReceiptHabitRemark(newCustEntity.getReceiptHabitRemark());
				}
				
				vo.setConditionDto(conditionDto);
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 添加单票通知.
	 * 
	 * 通知客户的方式包括：
	 * 	短信通知、
	 * 	电话通知、
	 * 	语音通知；
	 * 
	 * 如果货物的状态不是“库存中”，
	 * 	营业员可以通过电话提前通知。
	 * 	不能通过语音或者短信通知客户。
	 * 	通知结果需要保存到历史通知。
	 * 
	 * 如果运单是分批配载且货物的状态都是在库存中，
	 * 那么向客户通知该运单所有在库货物的件数信息。
	 * 通知结果需要保存到历史通知。
	 * 
	 * 送货货物需营业员打电话通知；
	 * 	自提货物优先发送语音通知、短信通知，其次打电话，
	 * 	电话通知方法同送货的电话通知一致。
	 * 
	 * 营业员只能在8:00-20:00发送语音通知、单条短信；时间段可配置。
	 * 
	 * 如果派送方式为自提，
	 * 短信或语音通知发送后，
	 * 待语音服务商返回结果需保存在系统中。
	 * 用户可以通过综合查询以及查询派送情况查询到。 
	 * 
	 * 对于派送已通知成功的，
	 * 若通知信息有更改，
	 * 则营业员可以单票再次通知客户，
	 * 并记录通知信息及通知人信息；
	 * 在到达联打印时，
	 * 打印最后通知成功的通知信息。
	 * 
	 * 语音模板，
	 * 	短信模板是可以由公司统一定制和修改的。
	 * 语音与短信的通知内容是系统根据模板自动生成，不可修改。
	 * 
	 * 若运单的付款方式为现付，
	 * 隐藏通知信息窗口中“付款方式”这一项的名称与内容。
	 * 若运单的付款方式为到付，
	 * 显示通知信息窗口中“付款方式”这一项的名称与内容。
	 * 付款方式显示为到付。
	 * 根据客户的资质，
	 * 显示“月结”或者“临欠”。
	 * 允许修改付款方式。
	 * 
	 * “是否需要发票”的默认值是“否”，
	 * 
	 * 隐藏窗口中“税号/公司名称/电话/帐号/开户行/地址”这些项的名称与内容。
	 * 
	 * 当选择“是否需要发票”的内容为“是”。
	 * 显示窗口中“税号/公司名称/电话/帐号/开户行/地址”这些项的名称与内容。
	 * 针对会员客户，
	 * 弹窗显示多列发票信息，
	 * 发票信息包括：税号、公司名称、电话、帐号、开户行、地址。
	 * 针对非会员客户，这些信息都是必须输入的。
	 * 
	 * 1. 只有电话通知时才可以选择通知状态，
	 * 通知失败的情况下，
	 * 必须输入失败原因。
	 * 
	 * 2. 选择语音、短信通知方式时，
	 * 通知状态、
	 * 付款方式、
	 * 是否需要发票、
	 * 税号、
	 * 公司名称、
	 * 电话、
	 * 账号、
	 * 开户行、
	 * 地址、
	 * 送货日期、
	 * 送货要求、
	 * 小票费用控件不可见
	 * 
	 * 3. 语音、短信通知后默认的状态为“语音通知中”、“短信通知中”
	 * 
	 * @return 
	 * 			the string
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Oct 19, 2012 11:37:21 AM
	 */
	@JSON
	public String addNotifyCustomer() {
		try {
			// 判断vo属性
			if (vo != null) {
				// 新增通知相关信息
				this.notifyCustomerService.addNotificationInfo(vo.getConditionDto());
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 批量通知，获取运单信息.
	 * 
	 * @return 
	 * 			the string
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Oct 31, 2012 6:04:53 PM
	 */
	@JSON
	public String batchNotifyList() {
		try {
			// 获取传入参数通知信息entity
			NotificationEntity notificationEntity = vo.getConditionDto().getNotificationEntity();
			// 判断通知信息entity的null属性
			if (notificationEntity == null) {
				// 如果页面传入的notificationEntity为NULL，说明通过点击批量通知按钮进去，则实例化notificationEntity
				// 不等于NULL，说明弹出的批量通知页面动态加载进去本方法
				notificationEntity = new NotificationEntity();
			}
			// 获取并设置批量通知页面显示列表信息
			vo.setDtoList(this.notifyCustomerService.queryWaybillsByNos(vo.getConditionDto().getWaybillNos(), notificationEntity, vo.getConditionDto().getNotificationEntityList()));
			// 设置通知信息发送时间范围
			vo.setInformationReceiveTimeRange(this.notifyCustomerService.getInformationReceiveTimeRange());
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 执行批量通知.
	 * 
	 * @return 
	 * 		the string
	 * @author 
	 * 		ibm-wangfei
	 * @date 
	 * 		Nov 2, 2012 4:44:46 PM
	 */
	@JSON
	public String batchNotify() {
		try {
			// 执行批量通知
			this.notifyCustomerService.batchNotify(vo.getConditionDto());
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 查询保管费.
	 * 
	 * 根据用户输入的 查询条件，
	 * 系统筛选出已经逾期，
	 * 需要征收仓储费的运单列表。
	 * 弹出窗口显示需要征收仓储费的运单信息列表。
	 * 
	 * 1、	仓储费计算方法：
	 * 【到达时间】：
	 * 		（派送出库总件数+库存件数）≥开单件数时，
	 * 		库存中的“操作时间”即为【到达时间】
	 * 		（即货物全票入库的时间）； 
	 * 【入库日期】：
	 * 		到达时间<12:00，
	 * 		则【入库日期】=当天日期；
	 * 		到达时间≥12:00，
	 * 		则【入库日期】=（当天日期+1）
	 * 【出库日期】:
	 * 		全票派送出库日期，
	 * 		且派送出库当日不计入仓储费 
	 * 【库存天数】:
	 * 		当前日期-入库日期，当前日期和入库日期的运算按工作日的日期进行计算；
	 * 		（当前日期≥入库日期）
	 * 【超期天数】:
	 * 		（库存天数-3），
	 * 		超期天数＞0，
	 * 		则计算仓储费；
	 * 		否则，仓储费=0
	 * 【仓储费】:
	 * 		货物体积*超期天数*10元/方/天，
	 * 		仓储费收取标准为10元/方/天，
	 * 		最低5元/票，
	 * 		最高1000元/票；
	 * 2、 停止计算仓储费规则：
	 * 		若当前日期≥出库日期，
	 * 		则不再计算仓储费，
	 * 		仓储费金额显示最后一次计算的数值不变；
	 * 		若库存天数≥90天，
	 * 		则不再计算仓储费，
	 * 		并且仓储费=0
	 * 3、 仓储费只起到提示营业员收费的作用，
	 * 		后台独立计算，
	 * 		不纳入应收金额。
	 * 		与界面其他金额不相关。
	 * 4、空运、
	 * 	          偏线、
	 * 		中转下线的不计算仓储费
	 * 
	 * @return 
	 * 			the string
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Nov 5, 2012 11:34:21 AM
	 */
	@JSON
	public String queryStorageList() {
		try {
			// 查询保管费
			vo.setDtoList(this.notifyCustomerService.queryStorageList(vo.getConditionDto().getWaybillNos()));
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 获取客户通知短信、语音内容.
	 * 
	 * 自提语音模板：
	 * 		XXX您好！
	 * 		这里是德邦物流提货通知，
	 * 		由XX给您发来的单号为XXXXXXXX的X件货正在卸车，
	 * 		到付款：XXX，
	 * 		请您于X小时后携带您及提货人的有效证件原件， 
	 * 		于XX小时内到XXX提货，咨询电话：XXX-XXXXXXXX.
	 * 自提短信模板：
	 * 		XXX您好，
	 * 		这是德邦物流提货通知。
	 * 		您有从 XXX发来的单号为XXXXXXXX的X件货物已到达XXX，
	 * 		到付金额：XX 元，
	 * 		请您于X小时后内到携带提货人的有效证件，
	 * 		于XX小时内到XXX提货。欢迎致电XXX-XXXXXXXX  【德邦物流】
	 * 送货短信模板：
	 * 		XXX，您好！
	 * 		由XX给您发来单号XXXXXXXX的X件货  正在卸车，
	 * 		送货前我们将会联系您【德邦物流】
	 * (1)短信通知及语音通知内容统一为：
	 * 		xxx,您好！
	 * 		请于xx小时后携带您及收货方有效证件原件，
	 * 		到xxx提取由xx给您发来单号xxxxxxxx的x件货，
	 * 		逾期3天将产生仓储费，
	 * 		询xxx-xxxxxxxx〖德邦物流〗(无到付款)
	 * (2)短信通知及语音通知内容统一为：
	 * 		xxx,您好！
	 * 		请于xx小时后携带您及收货方有效证件原件，
	 * 		到xxx提取由xx给您发来单号xxxxxxxx的x件货，
	 * 		到付款xx元，逾期3天将产生仓储费，
	 * 		询xxx-xxxxxxxx〖德邦物流〗(有到付款)
	 * @return 
	 * 			the string
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Dec 5, 2012 2:16:50 PM
	 */
	@JSON
	public String queryNoticeContent() {
		try {
			// 获取客户通知短信、语音内容.
			String [] noticeContents = this.notifyCustomerService.queryNoticeContent(this.vo.getConditionDto().getNotificationEntity());
			vo.getConditionDto().getNotificationEntity().setNoticeContent(noticeContents[0]);
			vo.getConditionDto().getNotificationEntity().setNoticeContentVoice(noticeContents[1]);
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}

	/**
	 * 查询产品列表.
	 * 
	 * @return 
	 * 			the string
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Dec 13, 2012 11:09:15 AM
	 */
	@JSON
	public String queryProduct() {
		try {
			// 查询产品列表
			vo.setProductEntitys(pricingValueAddedService.findProductByCondition());
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	/**
	 * 查询产品列表.
	 * 
	 * @return 
	 * 			the string
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Dec 13, 2012 11:09:15 AM
	 */
	@JSON
	public String queryThreeLevelProductAll() {
		try {
			// 查询产品列表
			vo.setProductEntitys(pricingValueAddedService.findThreeLevelProduct());
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	/**
	 * 根据运单号查询通知明细信息
	 * 
	 * @return 
	 * 			the string
	 * @author 
	 * 			ibm-wangfei
	 * @date 
	 * 			Dec 13, 2012 11:09:15 AM
	 */
	@JSON
	public String queryNoticeListByWaybillNo() {
		try {
			// 判断vo属性及vo的waybillno属性
			if (vo != null && vo.getConditionDto().getWaybillNo() != null) {
				// waybillNo存在的时候，进行通知明细查询
				NotifyCustomerConditionDto conditionDto = this.notifyCustomerService.queryNoticeList(vo.getConditionDto().getWaybillNo());
				// 查出的dto对象设置到vo.conditionDto
				vo.setConditionDto(conditionDto);
			}
		} catch (BusinessException e) {
			// 有异常时，返回异常信息
			return super.returnError(e);
		}
		// 返回成功信息
		return super.returnSuccess();
	}
	/**
	 * @param notifyCustomerService 
	 * 		the notifyCustomerService to see
	 */
	public void setNotifyCustomerService(INotifyCustomerService notifyCustomerService) {
		this.notifyCustomerService = notifyCustomerService;
	}

	/**
	 * @return 
	 * 		the vo
	 */
	public NotifyCustomerVo getVo() {
		return vo;
	}

	/**
	 * @param vo 
	 * 		the vo to see
	 */
	public void setVo(NotifyCustomerVo vo) {
		this.vo = vo;
	}

	/**
	 * @param pricingValueAddedService 
	 * 		the pricingValueAddedService to see
	 */
	public void setPricingValueAddedService(IPricingValueAddedService pricingValueAddedService) {
		this.pricingValueAddedService = pricingValueAddedService;
	}

	public void setBillReceivableService(
			IBillReceivableService billReceivableService) {
		this.billReceivableService = billReceivableService;
	}

	public void setCustomerReceiptHabitService(
			ICustomerReceiptHabitService customerReceiptHabitService) {
		this.customerReceiptHabitService = customerReceiptHabitService;
	}
	
}