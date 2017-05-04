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
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.IndividualCustomEWaybillEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.QryCustomEntity;




/**   
 * @ClassName IndividualCustomEWaybillService  
 * @Description 下拉散客运单   
 * @author  092038 张贞献  
 * @date 2014-7-10    
 */ 
public class IndividualCustomEWaybillService implements IBusinessService<List<IndividualCustomEWaybillEntity>, QryCustomEntity> {

	private Logger log = Logger.getLogger(getClass());

	// foss下拉大散客订单
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
	public QryCustomEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析内容
		QryCustomEntity cusEWayblill = JsonUtil.parseJsonToObject(QryCustomEntity.class, asyncMsg.getContent());
		//添加快递员信息
		cusEWayblill.setUserCode(asyncMsg.getUserCode());
		return cusEWayblill;
	}
	
	/**
	 * @description 校验数据合法性
	 * @param qryActOrder
	 * @throws PdaBusiException 
	 * @created 2012-12-26 下午9:25:05
	 */
	private void validate(AsyncMsg asyncMsg,  QryCustomEntity bigCustom) throws PdaBusiException {
		// pdaInfo信息校验
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		// 包体信息校验
		Argument.notNull(bigCustom, "QryCustomEntity");
		//验证车牌号
		Argument.hasText(bigCustom.getTruckCode(), "QryCustomEntity.truckCode");
		//验证车牌号
		Argument.hasText(bigCustom.getUserCode(), "QryCustomEntity.userCode");
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
	public List<IndividualCustomEWaybillEntity> service(AsyncMsg asyncMsg, QryCustomEntity bigCustom) throws PdaBusiException {
		this.validate(asyncMsg, bigCustom);
		
		log.debug("---调用FOSS接货接收订单接口开始---");
		List<DispatchEWaybillDto> eWaybillDtos = null;
		EWaybillConditionDto edto= new EWaybillConditionDto();
		edto.setDriverCode(asyncMsg.getUserCode());
		edto.setVehicleNo( bigCustom.getTruckCode());
		
		try {
			//根据用户编号,和车牌号进行查询数据
			eWaybillDtos= pdaDispatchOrderService.queryIndividualCustEwaybill(edto);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS接货接收订单接口结束---");
		List<IndividualCustomEWaybillEntity> individualcustomEWaybillrList = new ArrayList<IndividualCustomEWaybillEntity>();
		IndividualCustomEWaybillEntity ieWaybillEntity = null;
		
		if(eWaybillDtos != null && !eWaybillDtos.isEmpty()){
		
			for(DispatchEWaybillDto eWaybillDto:eWaybillDtos){
				ieWaybillEntity =  new IndividualCustomEWaybillEntity();
				//订单号
				ieWaybillEntity.setOrderCode(eWaybillDto.getOrderCode());
				//运单号
				ieWaybillEntity.setWaybillCode(eWaybillDto.getWaybillCode());	
				//快递收入部门编码
				ieWaybillEntity.setRevenueCode(eWaybillDto.getRevenueCode());
				//快递收入部门名称
				ieWaybillEntity.setEvenueName(eWaybillDto.getRevenueName());
	
			   //目的站
				ieWaybillEntity.setDestDeptName(eWaybillDto.getDestDeptName());
				
				//目的站编码
				ieWaybillEntity.setDestDeptCode(eWaybillDto.getDestDeptCode());
			   //第二城市
				ieWaybillEntity.setSecCity(eWaybillDto.getLeaveCity());
			   //第二外场
				ieWaybillEntity.setSecField(eWaybillDto.getSecField());
			   //最终外场
				ieWaybillEntity.setFinalField(eWaybillDto.getFinalField());
			   //路由明细
				ieWaybillEntity.setRouteDetail(eWaybillDto.getRouteInfo());
				//运输方式
				ieWaybillEntity.setTransType(eWaybillDto.getTransType());
				//提货方式
				ieWaybillEntity.setTakeType(eWaybillDto.getTakeType());
				//付款方式
				ieWaybillEntity.setPayType(eWaybillDto.getPayType());
				//件数
				ieWaybillEntity.setPieces(eWaybillDto.getPieces());
				//重量
				ieWaybillEntity.setWeight(eWaybillDto.getWeight().toString());
				//体积
				ieWaybillEntity.setVolume(eWaybillDto.getVolume().toString());
				//保险声明价值
				ieWaybillEntity.setInsuredAmount(eWaybillDto.getInsuredAmount().toString());
				//代收货款类型
				ieWaybillEntity.setReciveLoanType(eWaybillDto.getReciveLoanType());
				//代收货款金额
				ieWaybillEntity.setReviceMoneyAmount(eWaybillDto.getReviceMoneyAmount().toString());
				//代收货款账号
				ieWaybillEntity.setReviceLoadAccount(eWaybillDto.getReviceLoadAccount());
				//返单类别
				ieWaybillEntity.setReturnBillingType(eWaybillDto.getReturnBillingType());
				//是否打印星标
				ieWaybillEntity.setIsStarFlag(eWaybillDto.getIsStarFlag());
			    //是否外发	
				ieWaybillEntity.setIsPrintAt(eWaybillDto.getIsPrintAt());
				
				//下单时间 待确定
				ieWaybillEntity.setOrderTime(eWaybillDto.getBillingTime());
				
	
				//收货客户姓名
				ieWaybillEntity.setAddresseeName(eWaybillDto.getAddresseeName());
				//收货人地址
				ieWaybillEntity.setAddresseeAddr(eWaybillDto.getAddresseeAddr());
				//收货客户手机
				ieWaybillEntity.setAddresseeTel(eWaybillDto.getAddresseeTel());
				//货物名称(品名) 
				ieWaybillEntity.setGoodsName(eWaybillDto.getGoodsName());
				
				//散客特有
			  //发货人客户姓名
				ieWaybillEntity.setSenderName(eWaybillDto.getSenderName());
			  //发货人地址	
				ieWaybillEntity.setSenderAddr(eWaybillDto.getSenderAddr());
			  //发货人手机号
				ieWaybillEntity.setSenderTel(eWaybillDto.getSenderTel());
			 
			  //最早接货时间
			    ieWaybillEntity.setFirstAcctTime(eWaybillDto.getFirstAcctTime());
			  //最晚接货时间
			    ieWaybillEntity.setLastAcctTime(eWaybillDto.getLastAcctTime());
			  //订单渠道
			    ieWaybillEntity.setChannelCode(eWaybillDto.getChannelCode());
			  //备注
				ieWaybillEntity.setRemark(eWaybillDto.getRemark());
			  //优惠券
				ieWaybillEntity.setCouponNum(eWaybillDto.getCouponNum());
			  //是否采集
			    ieWaybillEntity.setIsCollectGps(eWaybillDto.getIsCollectGps());
				
				individualcustomEWaybillrList.add(ieWaybillEntity);
				
			}
		}
	
	    	
		return individualcustomEWaybillrList;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_INDIVIDUALCUST.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
}
