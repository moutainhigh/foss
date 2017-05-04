package com.deppon.pda.bdm.module.foss.delivery.server.service.impl;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IValidateArriveSheetService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.ValidateArriveSheetDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.DateUtils;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.QrySelfDeryInfoEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.SelfDeryInfoEntity;
import com.deppon.pda.bdm.module.foss.delivery.shared.exception.ClientTimeNotMatchServerException;

/**
 * 
  * @ClassName ArrInfoCodeChckService 
  * @Description 到达联校验 
  * @author xujun cometzb@126.com 
  * @date 2012-12-26
 */
public class ArrInfoCodeChckService implements IBusinessService<SelfDeryInfoEntity, QrySelfDeryInfoEntity> {
	
	private static final Log LOG = LogFactory.getLog(ArrInfoCodeChckService.class);
	public final static long TIME = 1800000;
	private IValidateArriveSheetService validateArriveSheetService;
	
	/**
	 * 解析包体
	 */
	@Override
	public QrySelfDeryInfoEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		QrySelfDeryInfoEntity derySelfDeryInfo = JsonUtil.parseJsonToObject(QrySelfDeryInfoEntity.class, asyncMsg.getContent());
		return derySelfDeryInfo;
	}

	/**
	 * 服务方法
	 */
	@Override
	public SelfDeryInfoEntity service(AsyncMsg asyncMsg, QrySelfDeryInfoEntity derySelfDeryInfo) throws PdaBusiException {
		LOG.info(derySelfDeryInfo);
		ValidateArriveSheetDto validateArriveSheetDto = null;
		try {
			//验证数据有效性
			this.validate(asyncMsg,derySelfDeryInfo);
			//签收出库PDA校验到达联接口
			validateArriveSheetDto = validateArriveSheetService.validateArriveSheet(derySelfDeryInfo.getArrInfoCode(),asyncMsg.getDeptCode());
			LOG.info(validateArriveSheetDto);
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		SelfDeryInfoEntity deryInfo = this.wrapValidateArriveSheetDto(validateArriveSheetDto);
		return deryInfo;
	}

	/**
	 * 拼接输出实体
	 * @param validateArriveSheetDto
	 * @return
	 */
	private SelfDeryInfoEntity wrapValidateArriveSheetDto(ValidateArriveSheetDto validateArriveSheetDto){
		SelfDeryInfoEntity selfDeryInfo = new SelfDeryInfoEntity();
		//提货方式  自提 和 派送
		selfDeryInfo.setDeliveryType(validateArriveSheetDto.getReceiveMethod());
		// 提货网点
		selfDeryInfo.setDestinationCode(validateArriveSheetDto.getCustomerPickupOrgCode());
		// 件数
		selfDeryInfo.setPieces(validateArriveSheetDto.getArriveSheetGoodsQty());
		// 运输性质
		selfDeryInfo.setTransType(validateArriveSheetDto.getProductCode());
		// 货物总体积
		selfDeryInfo.setVolume(validateArriveSheetDto.getGoodsVolumeTotal().doubleValue());
		//运单号
		selfDeryInfo.setWblCode(validateArriveSheetDto.getWaybillNo());
		//selfDeryInfo.setWeight(0.0);  //TODO 无重量返回给客户端
		//selfDeryInfo.setPcVotes(null);//TODO 只有件数  无票件关系
		//2013-08-14
		selfDeryInfo.setLabelCodes(validateArriveSheetDto.getSerialNos());
		//投诉变更状态
		selfDeryInfo.setComplainStatus(validateArriveSheetDto.getComplainStatus());
		return selfDeryInfo;
	}

	/**
	 * 数据合法性验证
	 * @param asyncMsg
	 * @param param
	 */
	private void validate(AsyncMsg asyncMsg, QrySelfDeryInfoEntity param){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
		Argument.notNull(param, "QrySelfDeryInfoEntity");
		//到达联编号
		Argument.hasText(param.getArrInfoCode(), "QrySelfDeryInfoEntity.arrInfoCode");
		//扫描时间
		Argument.notNull(param.getScanTime(), "QrySelfDeryInfoEntity.scanTime");
		System.out.println(new Date());
		long scantime = param.getScanTime().getTime();
		long cuurenttime = new Date().getTime();
		/*
		 * PDA客户端时间与服务器时间相差半小时以上抛出异常
		 * 2014-02-21 gaojia 
		 */
		if(scantime<cuurenttime&&cuurenttime-scantime>TIME){
			throw new ClientTimeNotMatchServerException(DateUtils.formatDateTime(param.getScanTime()));
		}else if(scantime>cuurenttime&&scantime-cuurenttime>TIME){
			throw new ClientTimeNotMatchServerException(DateUtils.formatDateTime(param.getScanTime()));
		}
		
	}
	
	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_DERY_WBL_INFO_GET.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setValidateArriveSheetService(IValidateArriveSheetService validateArriveSheetService) {
		this.validateArriveSheetService = validateArriveSheetService;
	}

}
