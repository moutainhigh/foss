package com.deppon.pda.bdm.module.foss.accept.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.dto.LabelPrintDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.AcceptConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.BigCustmerLableReqEntity;
import com.deppon.pda.bdm.module.foss.accept.shared.domain.BigCustmerLableResEntity;



/**
 * 
* @ClassName: BigCustmerLableService 
* @Description: TODO(大客户标签打印) 
* @author &245960 |yangdeming@deppon.com
* @date 2015-9-10 下午1:35:49 
*
 */
public class BigCustmerLableService implements IBusinessService<List<BigCustmerLableResEntity>, BigCustmerLableReqEntity> {
	
	private Logger log = Logger.getLogger(getClass());
	
	// 完成接货接口
	private IPdaWaybillService pdaWaybillService;
	
	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	/**
	 * 
	 * @description 解析json字符串
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public BigCustmerLableReqEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		//解析内容
		//前台body不用传递参数，为了以后扩展用实体存储，方便扩展
		BigCustmerLableReqEntity bigCustmerLableReqEntity = JsonUtil.parseJsonToObject(BigCustmerLableReqEntity.class,asyncMsg.getContent());
		//用户编号
		bigCustmerLableReqEntity.setUserCode(asyncMsg.getUserCode());
		
		return bigCustmerLableReqEntity;
	}
	
	/**
	 * 
	 * @description 校验数据合法性
	 * @param acctFinish
	 * @throws PdaBusiException 
	 * @created 2012-12-28 下午9:08:35
	 */
	private void validate(AsyncMsg asyncMsg, BigCustmerLableReqEntity bigCustmerLableReqEntity) throws PdaBusiException {
		Argument.notNull(asyncMsg, "AsyncMsg");
		//验证部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//验证用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//验证pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
	}

	/**
	 * 
	 * @description (大客户标签打印) 
	 * @param asyncMsg
	 * @param acctFinish
	 * @return
	 * @throws PdaBusiException     
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#service(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg, java.lang.Object)
	 */
	@Override
	public List<BigCustmerLableResEntity> service(AsyncMsg asyncMsg, BigCustmerLableReqEntity bigCustmerLableReqEntity) throws PdaBusiException {
		// 校验数据
		this.validate(asyncMsg, bigCustmerLableReqEntity);
		String userCode = bigCustmerLableReqEntity.getUserCode();
		
		log.debug("----------------调用foss大客户标签打印接口开始,请求员工工号:" + userCode + "--------------------");
		
		//测试数据
		/*for(int i=0; i<2300; i++){
			BigCustmerLableResEntity bigCustmerLableResEntity= new BigCustmerLableResEntity("0", new Date(), "customer"+i, "0" + i, "2000", "3", "987654321", "送", "精准汽运", "5纸", "四川达州", "245960", "达到外场", "出发城市", "货物类型", "送大", "发大", "展", "D11:11|3:518", "ddmd");
			
			list.add(bigCustmerLableResEntity);
		}*/
			
		try {
			long startTime = System.currentTimeMillis();
			List<LabelPrintDto> fossList = pdaWaybillService.downloadCombinateBill(bigCustmerLableReqEntity.getCustomerLableNum());
			long endTime = System.currentTimeMillis();
			log.debug("调用foss接口耗时:" + (endTime - startTime));
			if (fossList == null || fossList.size() == 0) return null;
			
			List<BigCustmerLableResEntity> list = new ArrayList<BigCustmerLableResEntity>();
			BigCustmerLableResEntity bigCustmerLableResEntity;
			Date createTime = new Date();
			
			long pdatransTime = System.currentTimeMillis();
			for(LabelPrintDto lable : fossList) {
				bigCustmerLableResEntity = new BigCustmerLableResEntity();
				//下拉日期
				bigCustmerLableResEntity.setCreateTime(createTime);
				//客户标签号
				bigCustmerLableResEntity.setCustomerLableNum(lable.getCustomerLableNums());
				// 是否发货大客户
				bigCustmerLableResEntity.setDeliveryBigCustomer(lable.getDeliveryBigCustomer());
				//出发城市名字
				bigCustmerLableResEntity.setDepartmentCityName(lable.getDepartmentCityName());
				// 目的站名字
				bigCustmerLableResEntity.setDestinationName(lable.getDestinationName());
				// 最终目的站编码
				bigCustmerLableResEntity.setDestStationNumber(lable.getDestStationNumber());
				// 到达外场名称
				bigCustmerLableResEntity.setDestTransCenterName(lable.getDestTransCenterName());
				// 货物类型
				bigCustmerLableResEntity.setGoodsType(lable.getGoodsType());
				// 否展会货
				bigCustmerLableResEntity.setIsExhibitCargo(lable.getIsExhibitCargo());
				// 总件数
				bigCustmerLableResEntity.setPieces(lable.getPieces());
				// 是否收货大客户
				bigCustmerLableResEntity.setReceiveBigCustomer(lable.getReceiveBigCustomer());
				// 送标记
				bigCustmerLableResEntity.setSend(lable.getSend());
				// 流水号
				bigCustmerLableResEntity.setSerialNo(lable.getSeriCode());
				//状态0，未打印
				bigCustmerLableResEntity.setStatus("0");
				// 运输类型
				bigCustmerLableResEntity.setTransType(lable.getTransType());
				//下拉工号
				bigCustmerLableResEntity.setUserCode(userCode);
				// 运单号
				bigCustmerLableResEntity.setWblCode(lable.getWblCode());
				// 包装类型
				bigCustmerLableResEntity.setWrapType(lable.getWrapType());
				//到达门店编码
				bigCustmerLableResEntity.setArriveStoreNUM(lable.getArriveStoreNUM());
				//路由库位信息
				bigCustmerLableResEntity.setGoodsAreas(lable.getGoodsAreas());
				
				//路由信息
				list.add(bigCustmerLableResEntity);
			}
			long pdatransEndTime = System.currentTimeMillis();
			log.debug("----------------调用foss大客户标签打印接口结束,    耗时:" + (pdatransEndTime - pdatransTime) + "----------------------------------");
			return list;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		} catch (Exception e) {
			throw new FossInterfaceException(null,"调用foss接口出现未知异常");
		}
	}

	
	/**
	 * 业务类型
	 */
	@Override
	public String getOperType() {
		return AcceptConstant.OPER_TYPE_BIG_CUSTMER_LABLE.VERSION;
	}

	/**
	 * 同步还是异步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

}
