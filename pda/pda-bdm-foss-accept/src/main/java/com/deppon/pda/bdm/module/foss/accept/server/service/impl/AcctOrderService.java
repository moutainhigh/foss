package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PdaDispatchOrderDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.AcctOrderEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.QryAcctOrderEntity;

/**
 * 
 * @package com.deppon.pda.bdm.module.foss.accept.server.service.impl 
 * @file AcctOrderService.java 
 * @description 接收接货订单服务类
 * @author ChenLiang
 * @created 2012-12-29 上午10:17:21    
 * @version 1.0
 */
public class AcctOrderService implements IBusinessService<List<AcctOrderEntity>, QryAcctOrderEntity> {

	private Logger log = Logger.getLogger(getClass());

	// 查询未接订单接口
	private IPdaDispatchOrderService pdaDispatchOrderService;

	public void setPdaDispatchOrderService(IPdaDispatchOrderService pdaDispatchOrderService) {
		this.pdaDispatchOrderService = pdaDispatchOrderService;
	}
	
	/**
	 * @description 解析包体
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public QryAcctOrderEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析内容
		QryAcctOrderEntity acctOrder = JsonUtil.parseJsonToObject(QryAcctOrderEntity.class, asyncMsg.getContent());
		//用户编号
		acctOrder.setUserCode(asyncMsg.getUserCode());
		//pda编号
		acctOrder.setPdaCode(asyncMsg.getPdaCode());
		return acctOrder;
	}
	
	/**
	 * @description 校验数据合法性
	 * @param qryActOrder
	 * @throws PdaBusiException 
	 * @created 2012-12-26 下午9:25:05
	 */
	private void validate(AsyncMsg asyncMsg, QryAcctOrderEntity qryActOrder) throws PdaBusiException {
		// pdaInfo信息校验
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		// 包体信息校验
		Argument.notNull(qryActOrder, "QryAcctOrderEntity");
		//验证车牌号
		Argument.hasText(qryActOrder.getTruckCode(), "QryAcctOrderEntity.truckCode");
	}

	/**
	 * @description 服务方法
	 * @param asyncMsg
	 * @param qryActOrder
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	public List<AcctOrderEntity> service(AsyncMsg asyncMsg, QryAcctOrderEntity qryActOrder) throws PdaBusiException {
		this.validate(asyncMsg, qryActOrder);
		
		log.debug("---调用FOSS接货接收订单接口开始---");
		List<PdaDispatchOrderDto> acctOrders = null;
		try {
			//根据用户编号,和车牌号进行查询数据
			acctOrders = pdaDispatchOrderService.queryDispatchOrderByVehicle(asyncMsg.getUserCode(), qryActOrder.getTruckCode());
		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
			//throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS接货接收订单接口结束---");
		List<AcctOrderEntity> orderList = new ArrayList<AcctOrderEntity>();
		if (acctOrders != null && !acctOrders.isEmpty()) {
			AcctOrderEntity acctOrder = null;
			for (PdaDispatchOrderDto pdaDispatchOrderDto : acctOrders) {
				acctOrder = new AcctOrderEntity();
				// 订单编号
				acctOrder.setOrderCode(pdaDispatchOrderDto.getOrderNo());
				// 营业部编号
				acctOrder.setDeptCode(pdaDispatchOrderDto.getSalesDepartmentCode());
				// 提货方式
//				acctOrder.setTakeType(pdaDispatchOrderDto.getReceiveRethod());
				acctOrder.setTakeType(pdaDispatchOrderDto.getReceiveMethod());
				// 运输性质
				acctOrder.setTransType(pdaDispatchOrderDto.getProductCode());
				// 提货网点编号
				acctOrder.setCustomerPickupOrgCode(pdaDispatchOrderDto.getCustomerPickupOrgCode());
				// 接货省
				acctOrder.setPickupProvince(pdaDispatchOrderDto.getPickupProvince());
				// 接货市
				acctOrder.setPickupCity(pdaDispatchOrderDto.getPickupCity());
				// 接货区县
				acctOrder.setPickupCounty(pdaDispatchOrderDto.getPickupCounty());
				// 接货地址
				acctOrder.setAcctAddress(StringUtils.convert(pdaDispatchOrderDto.getPickupElseAddress()));
				  // 客户电话
                String cusTel="";
                
                /***
                 * 客户手机号码和电话显示4中情况
                 *      1、手机
                 *      2、电话
                 *      3、手机/电话
                 *      4、
                 */                         
                if(pdaDispatchOrderDto.getMobile()==null
                		||"".equals(pdaDispatchOrderDto.getMobile().trim())){
                 //手机号码为空	
                	//电话不为空  情况2
                	if(pdaDispatchOrderDto.getTel()!=null
                    		&&!"".equals(pdaDispatchOrderDto.getTel().trim())){
                		cusTel=StringUtils.convert(pdaDispatchOrderDto.getTel());
                	} 
                	//手机和电话都为空，情况 4
                }else if(pdaDispatchOrderDto.getTel()==null
                		||"".equals(pdaDispatchOrderDto.getTel().trim())){
                //手机不为空；电话为空   情况 1	
                	cusTel= StringUtils.convert(pdaDispatchOrderDto.getMobile());
                }else{
                //手机不为空，电话不为空 情况 3	
                	cusTel=  StringUtils.convert(pdaDispatchOrderDto.getMobile())+"/"
                            +StringUtils.convert(pdaDispatchOrderDto.getTel());
                }
                
                
                acctOrder.setCustomerPhone(cusTel);
				// 客户姓名
				acctOrder.setCustomerName(StringUtils.convert(pdaDispatchOrderDto.getCustomerName()));
				// 最早接货时间
				acctOrder.setFirstAcctTime(pdaDispatchOrderDto.getEarliestPickupTime());
				// 最晚接货时间
				acctOrder.setLastAcctTime(pdaDispatchOrderDto.getLatestPickupTime());
				// 体积
				acctOrder.setVolume(pdaDispatchOrderDto.getVolume()==null?0:pdaDispatchOrderDto.getVolume().doubleValue());
				// 重量
				acctOrder.setWeight(pdaDispatchOrderDto.getWeight()==null?0:pdaDispatchOrderDto.getWeight().doubleValue());
				// 件数
				acctOrder.setPieces(pdaDispatchOrderDto.getGoodsQty());
				// 包装类型
				acctOrder.setWrapType(StringUtils.convert(pdaDispatchOrderDto.getGoodsPackage()));
				// 营业部联系电话
				acctOrder.setDeptPhone(StringUtils.convert(pdaDispatchOrderDto.getSalesDepartmentTel()));
				// 下单时间
				acctOrder.setOrderTime(pdaDispatchOrderDto.getOrderTime());
				// 订单类型
				acctOrder.setOrderType(pdaDispatchOrderDto.getOrderType());
				// 货物类型
				acctOrder.setCrgType(pdaDispatchOrderDto.getGoodsType());
				// 请车专员姓名
				acctOrder.setCarCommissionerName(pdaDispatchOrderDto.getCreateUserName());
				// 受理人
				acctOrder.setAssignees(pdaDispatchOrderDto.getOperator());
				// 车型需求
				acctOrder.setModelsDemand(pdaDispatchOrderDto.getVehicleType());
				// 营业部联系人
				acctOrder.setDeptContactName(pdaDispatchOrderDto.getCreateUserName());
				// 备注
				acctOrder.setRemark(StringUtils.convert(pdaDispatchOrderDto.getOrderNotes()));
				//运单号
				acctOrder.setWaybill_no(pdaDispatchOrderDto.getWaybillNo());
				//订单渠道,QQ
				acctOrder.setChannelCode(pdaDispatchOrderDto.getOrderSource());
				//订单付款方式
				acctOrder.setPaidType(pdaDispatchOrderDto.getPaidMethod());
				//是否采集GPS地址
                acctOrder.setIsCollectGps(pdaDispatchOrderDto.getIsCollect());
                //代收货款类型
                acctOrder.setReciveLoanType(pdaDispatchOrderDto.getReciveLoanType());
                //代收货款金额
                acctOrder.setReviceMoneyAmount(pdaDispatchOrderDto.getReviceMoneyAmount()==null?"0":pdaDispatchOrderDto.getReviceMoneyAmount().toString());
                //保价声明价值
                acctOrder.setInsuredAmount(pdaDispatchOrderDto.getInsuredAmount()==null?"0":pdaDispatchOrderDto.getInsuredAmount().toString());
                //优惠券编码
                acctOrder.setCouponNumber(pdaDispatchOrderDto.getCouponNumber());
                //转发订单到司机工号
                acctOrder.setFromDriverCode(pdaDispatchOrderDto.getForwardDriverCode());
                //转发订单到司机姓名
                acctOrder.setFromDriverName(pdaDispatchOrderDto.getForwardDriverName());
                //收货地址             
                acctOrder.setDeliverCounty(pdaDispatchOrderDto.getConsigneeAddress());
                //设置渠道订单号  add 298403 增加渠道订单号
                acctOrder.setChannelNumber(pdaDispatchOrderDto.getChannelNumber());
                
              //接货
                //省
                acctOrder.setPickupProvince(pdaDispatchOrderDto.getPickupProvince());
                //市
                acctOrder.setPickupCity(pdaDispatchOrderDto.getPickupCity());
                //区             
                acctOrder.setPickupCounty(pdaDispatchOrderDto.getPickupCounty());
                    
                //送货
                //省
                acctOrder.setProvince(pdaDispatchOrderDto.getConsigneeProvince());
                //市
                acctOrder.setCity(pdaDispatchOrderDto.getConsigneeCity());
                //区             
                acctOrder.setRegion(pdaDispatchOrderDto.getConsigneeCounty());
                
                //author:245960 Date:2015-08-11 comment:对接王刚添加客户分群
                //客户分群
                acctOrder.setCustomerGroup(pdaDispatchOrderDto.getCustomerGroup());
                    
				orderList.add(acctOrder);
			}
			log.debug("---返回订单信息成功---");
		}
		return orderList;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_ORDER_QRY.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
}
