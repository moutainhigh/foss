package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaDispatchOrderService;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillConditionDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillCustomerDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.StringUtils;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.BigCustomEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.QryCustomEntity;



/**   
 * @ClassName BigCustomService  
 * @Description 下拉大客户列表   
 * @author  092038 张贞献  
 * @date 2014-7-10    
 */ 
public class BigCustomListService implements IBusinessService<List<BigCustomEntity>, QryCustomEntity> {

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
	public QryCustomEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析内容
		QryCustomEntity bigCustom = JsonUtil.parseJsonToObject(QryCustomEntity.class, asyncMsg.getContent());
		//用户编号
		bigCustom.setUserCode(asyncMsg.getUserCode());

		return bigCustom;
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
		Argument.notNull(bigCustom, "QryBigCustomEntity");
		//验证车牌号
		Argument.hasText(bigCustom.getTruckCode(), "QryBigCustomEntity.truckCode");
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
	public List<BigCustomEntity> service(AsyncMsg asyncMsg, QryCustomEntity bigCustom) throws PdaBusiException {
		this.validate(asyncMsg, bigCustom);
		
		log.debug("---调用FOSS接货接收订单接口开始---");
		List<EWaybillCustomerDto> eWaybillCustomers = null ;
		
		EWaybillConditionDto edto= new EWaybillConditionDto();
		edto.setDriverCode(asyncMsg.getUserCode());
		edto.setVehicleNo( bigCustom.getTruckCode());
		
		try {
			//根据用户编号,和车牌号进行查询数据
			eWaybillCustomers = pdaDispatchOrderService.queryEWaybillOrderBigCust(edto);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		log.debug("---调用FOSS接货接收订单接口结束---");
		List<BigCustomEntity> bigCustomList = new ArrayList<BigCustomEntity>();
		BigCustomEntity bigCustomEntity=null;
		if(eWaybillCustomers != null && !eWaybillCustomers.isEmpty()){
			for(EWaybillCustomerDto  eWaybillCustomerDto:eWaybillCustomers){
		    	 bigCustomEntity = new BigCustomEntity();
		    	// 客户编码
		    	bigCustomEntity.setCurstomerCode(eWaybillCustomerDto.getContactCode());
		    	// 客户名称
		    	bigCustomEntity.setCurstomerName(eWaybillCustomerDto.getContactName());
		    	// 客户地址
		    	bigCustomEntity.setBigCurstomAddr(eWaybillCustomerDto.getAddress());
		    	// 客户总票数
		    	bigCustomEntity.setCountWayBills(eWaybillCustomerDto.geteWaybillTotal());
		    	
		    	//是否已采集地址
		    	bigCustomEntity.setIsCollectGps(eWaybillCustomerDto.getIsCollectGps());
		    	
		    	  /***
	             * 客户手机号码和电话显示4中情况
	             *      1、手机
	             *      2、电话
	             *      3、手机/电话
	             *      4、
	             */ 
		    	 // 客户电话
	            String cusTel="";
	            if(eWaybillCustomerDto.getMobilePhone()==null
	            		||"".equals(eWaybillCustomerDto.getMobilePhone().trim())){
	             //手机号码为空	
	            	//电话不为空  情况2
	            	if(eWaybillCustomerDto.getOfficePhone()!=null
	                		&&!"".equals(eWaybillCustomerDto.getOfficePhone().trim())){
	            		cusTel=StringUtils.convert(eWaybillCustomerDto.getOfficePhone());
	            	} 
	            	//手机和电话都为空，情况 4
	            }else if(eWaybillCustomerDto.getOfficePhone()==null
	            		||"".equals(eWaybillCustomerDto.getOfficePhone().trim())){
	            //手机不为空；电话为空   情况 1	
	            	cusTel= StringUtils.convert(eWaybillCustomerDto.getMobilePhone());
	            }else{
	            //手机不为空，电话不为空 情况 3	
	            	cusTel=  StringUtils.convert(eWaybillCustomerDto.getMobilePhone()).trim()+"/"
	                        +StringUtils.convert(eWaybillCustomerDto.getOfficePhone()).trim();
	            }
	            
	          //客户手机号码
		    	bigCustomEntity.setBigCurstomTel(cusTel);
	    
		    	
		    	
		    	bigCustomList.add(bigCustomEntity);
		    }
		}
		return bigCustomList;
	}

	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ACCT_KD_BIGCUSTLIST.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}
	
}
