package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;
import com.deppon.foss.module.pickup.waybill.shared.dto.DispatchEWaybillDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.BigCustomEWaybillEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.QryBigCustomEWaybillEntity;




/**   
 * @ClassName BigCustomEWaybillService  
 * @Description 下拉单个大客户运单明细  
 * @author  092038 张贞献  
 * @date 2014-7-10    
 */ 
public class BigCustomEWaybillService implements IBusinessService<List<BigCustomEWaybillEntity>, QryBigCustomEWaybillEntity> {

	private Logger log = Logger.getLogger(getClass());

	// foss下拉大客户接口
	private IPdaDispatchOrderService pdaDispatchOrderService;

	public void setPdaDispatchOrderService(IPdaDispatchOrderService pdaDispatchOrderService) {
		this.pdaDispatchOrderService = pdaDispatchOrderService;
	}
	


	/** (非 Javadoc)  
	 * <p>Title: parseBody</p> 
	 * <p>Description: </p> 
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException  
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg) 
	 */
	@Override
	public QryBigCustomEWaybillEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析内容
		QryBigCustomEWaybillEntity cusEWayblill = JsonUtil.parseJsonToObject(QryBigCustomEWaybillEntity.class, asyncMsg.getContent());
	
		return cusEWayblill;
	}
	
	/**
	 * @description 校验数据合法性
	 * @param qryActOrder
	 * @throws PdaBusiException 
	 * @created 2012-12-26 下午9:25:05
	 */
	private void validate(AsyncMsg asyncMsg,  QryBigCustomEWaybillEntity bigCustom) throws PdaBusiException {
		// pdaInfo信息校验
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		// 包体信息校验
		Argument.notNull(bigCustom, "QryBigCustomEWaybillEntity");
		//验证车牌号
		Argument.hasText(bigCustom.getCustomCode(), "QryBigCustomEWaybillEntity.customCode");
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
	public List<BigCustomEWaybillEntity> service(AsyncMsg asyncMsg, QryBigCustomEWaybillEntity bigCustom) throws PdaBusiException {
		this.validate(asyncMsg, bigCustom);
		
		log.debug("---调用FOSS接货接收订单接口开始---");
		List<DispatchEWaybillDto> dispatchEWaybillDto = null;
		List<String> customerCodeList = new ArrayList<String>();
		customerCodeList.add(bigCustom.getCustomCode());
		
		EWaybillConditionDto edto= new EWaybillConditionDto();
		edto.setCustomerCodeList(customerCodeList);
		edto.setDriverCode(asyncMsg.getUserCode());
		edto.setVehicleNo(bigCustom.getTruckCode());
		edto.setAddress(bigCustom.getAddress());
		edto.setMobilePhone(bigCustom.getMobilePhone());
		
		
		try {
			
			//根据大客户编码获取客户明细
			 dispatchEWaybillDto= pdaDispatchOrderService.queryEWaybillOrderByCust(edto);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS接货接收订单接口结束---");
		List<BigCustomEWaybillEntity> bigCustomEWaybillrList = new ArrayList<BigCustomEWaybillEntity>();
		BigCustomEWaybillEntity beWayBillEntity = null;
		if(dispatchEWaybillDto != null && !dispatchEWaybillDto.isEmpty()){
		
			for(DispatchEWaybillDto eWaybillDto:dispatchEWaybillDto){
				beWayBillEntity = new BigCustomEWaybillEntity();
				
				
				
				//订单号
				beWayBillEntity.setOrc(eWaybillDto.getOrderCode());
				/*
				 * 返单类型
				 * NONE  无需返单
				 * ARRIVESHEET_FAX  运单到达联传真返回
				 * FAX   客户签收单传真返回
				 * ORIGINAL 客户签收单原件返单
				 * */
				beWayBillEntity.setReBT(eWaybillDto.getReturnBillingType());
				//件数
				beWayBillEntity.setPie(eWaybillDto.getPieces());
				 //运单号
				beWayBillEntity.setWabi(eWaybillDto.getWaybillCode());
		
				
			/*	//运单号
				beWayBillEntity.setWaybillCode(eWaybillDto.getWaybillCode());	
				//快递收入部门编码
				beWayBillEntity.setRevenueCode(eWaybillDto.getRevenueCode());
				//快递收入部门名称
				beWayBillEntity.setEvenueName(eWaybillDto.getRevenueName());	
			   //目的站
				beWayBillEntity.setDestDeptName(eWaybillDto.getDestDeptName());
				//目的站编码
				beWayBillEntity.setDestDeptCode(eWaybillDto.getDestDeptCode());
			   //第二城市
				beWayBillEntity.setSecCity(eWaybillDto.getLeaveCity());
			   //第二外场
				beWayBillEntity.setSecField(eWaybillDto.getSecField());
			   //最终外场
				beWayBillEntity.setFinalField(eWaybillDto.getFinalField());
			   //路由明细
				beWayBillEntity.setRouteDetail(eWaybillDto.getRouteInfo());		
				//运输方式
				beWayBillEntity.setTransType(eWaybillDto.getTransType());
				//提货方式
				beWayBillEntity.setTakeType(eWaybillDto.getTakeType());
				//付款方式
				beWayBillEntity.setPayType(eWaybillDto.getPayType());			
				//件数
				beWayBillEntity.setPieces(eWaybillDto.getPieces());
				//重量
				beWayBillEntity.setWeight(eWaybillDto.getWeight().toString());
				//体积
				beWayBillEntity.setVolume(eWaybillDto.getVolume().toString());
				//保险声明价值
				beWayBillEntity.setInsuredAmount(eWaybillDto.getInsuredAmount().toString());
				//代收货款类型
				beWayBillEntity.setReciveLoanType(eWaybillDto.getReciveLoanType());
				//代收货款金额
				beWayBillEntity.setReviceMoneyAmount(eWaybillDto.getReviceMoneyAmount().toString());
				//代收货款账号
				beWayBillEntity.setReviceLoadAccount(eWaybillDto.getReviceLoadAccount());
				
				//是否打印星标
				beWayBillEntity.setIsStarFlag(eWaybillDto.getIsStarFlag());
			    //是否外发	
				beWayBillEntity.setIsPrintAt(eWaybillDto.getIsPrintAt());
				
				//下单时间   *  待确定
				beWayBillEntity.setOrderTime(eWaybillDto.getBillingTime());
			
				
				//收货客户姓名
				beWayBillEntity.setAddresseeName(eWaybillDto.getAddresseeName());
				//收货人地址
				beWayBillEntity.setAddresseeAddr(eWaybillDto.getAddresseeAddr());
				//收货客户手机
				beWayBillEntity.setAddresseeTel(eWaybillDto.getAddresseeTel());
				//货物名称(品名) 
				beWayBillEntity.setGoodsName(eWaybillDto.getGoodsName());*/
				
				bigCustomEWaybillrList.add(beWayBillEntity);
				
			}
		}
		return bigCustomEWaybillrList;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_BIGCUSEWAYBILL.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
}
