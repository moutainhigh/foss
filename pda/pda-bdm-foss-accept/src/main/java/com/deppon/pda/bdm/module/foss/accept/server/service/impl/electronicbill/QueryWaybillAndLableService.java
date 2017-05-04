package com.deppon.pda.bdm.module.foss.accept.server.service.impl.electronicbill;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.pickup.pda.api.server.service.ILTLEWaybillPdaScanService;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelInfoDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.QueryWaybillAndLableEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.electronicbill.QueryWaybillAndLableResult;

/**
 * 
 * @ClassName: QueryWaybillAndLableService 
 * @Description: TODO(查询运单及标签信息service) 
 * @author &268974  wangzhili
 * @date 2016-5-17 下午2:55:38 
 *
 */
public class QueryWaybillAndLableService implements IBusinessService<QueryWaybillAndLableResult, QueryWaybillAndLableEntity>{

	private Logger log = Logger.getLogger(getClass());
	private ILTLEWaybillPdaScanService lTLEWaybillPdaScanService;
	//解析包体
	@Override
	public QueryWaybillAndLableEntity parseBody(AsyncMsg asyncMsg)
			throws PdaBusiException {
		QueryWaybillAndLableEntity entity = JsonUtil.parseJsonToObject(QueryWaybillAndLableEntity.class,asyncMsg.getContent());
		return entity;
	}

	//业务方法入口
	@Override
	public QueryWaybillAndLableResult service(AsyncMsg asyncMsg,
			QueryWaybillAndLableEntity param) throws PdaBusiException {
		//校验参数
		this.validate(asyncMsg, param);
		QueryWaybillAndLableResult result =null;
		//调用foss接口
		try{
			log.info("----调用foss接口开始----参数为："+param.getWblCode()+"--"+param.getDepartDeptCode());
			long startTime = System.currentTimeMillis();
			LabelInfoDto dto = lTLEWaybillPdaScanService.appScan(param.getWblCode(), param.getDepartDeptCode());
			long endTime = System.currentTimeMillis();
			log.info("调用foss接口耗时:" + (endTime - startTime));
			//封装返回值
			result = this.wrapLableResult(dto);
			log.info("----封装返回值结束----"+result.getWblCode());
		} catch(BusinessException e){
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		} catch(Exception e){
			throw new FossInterfaceException(null,"出现未知异常");
		}
		return result;
	}

	//操作类型
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_ERP_WAYBILL_LABLE.VERSION;
	}

	//同步
	@Override
	public boolean isAsync() {
		return false;
	}
	/**
	 * 
	 * @Title: validate 
	 * @Description: TODO(校验参数)  
	 * @return void    返回类型 
	 * @param asyncMsg
	 * @param entity
	 * @author： 268974  wangzhili
	 */
	private void validate(AsyncMsg asyncMsg,QueryWaybillAndLableEntity entity){
		Argument.notNull(asyncMsg, "AsyncMsg");
		Argument.notNull(entity, "entity");
		Argument.hasText(entity.getWblCode(),"wblCode");
		Argument.hasText(entity.getDepartDeptCode(),"departDeptCode");
	}

	public void setlTLEWaybillPdaScanService(
			ILTLEWaybillPdaScanService lTLEWaybillPdaScanService) {
		this.lTLEWaybillPdaScanService = lTLEWaybillPdaScanService;
	}
	
	/**
	 * 封装返回值
	 */
	private QueryWaybillAndLableResult wrapLableResult(LabelInfoDto dto){
		QueryWaybillAndLableResult result = new QueryWaybillAndLableResult();
		//是否集中接货
		result.setIsFocusReceiving(dto.getPickupCentralized());
		//总件数
		result.setTotalPieces(dto.getGoodsQtyTotal());
		//运单号
		result.setWblCode(dto.getWaybillNo());
		//送货类型
		result.setTakeType(dto.getReceiveMethod());
		//运输性质
		result.setTransType(dto.getProductName());
		//包装类型
		result.setPackageType(dto.getGoodsPackage());
		//到达外场名称
		result.setArriveOutfieldName(dto.getArrivedOutFieldName());
		//始发城市名称 ---
		result.setOriginateCityName(dto.getOriginateOutFieldCityName());
		///目的站名称
		result.setDestinationStationName(dto.getArrivedOrgSimpleName());
		//提货网点编码
		result.setCustomerPickupOrgCode(dto.getStationNo());
		//库位编码
		result.setLocations(dto.getLocations());
		//库区编码
		result.setAddrs(dto.getAddrs());
		return result;
	}
	
	
}
