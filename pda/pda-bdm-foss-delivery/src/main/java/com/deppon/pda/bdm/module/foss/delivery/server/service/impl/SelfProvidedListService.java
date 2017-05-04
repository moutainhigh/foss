package com.deppon.pda.bdm.module.foss.delivery.server.service.impl;

import java.util.ArrayList;
import java.util.List;


import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaPickupService;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupDto;
import com.deppon.foss.module.pickup.pda.api.shared.dto.PadPickupResultDto;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.DeliveryConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.Argument;
import com.deppon.pda.bdm.module.foss.delivery.shared.domain.SelfProvidedEntity;

/**   
 * @ClassName SelfProvidedListService  
 * @Description 客户自提列表清单 
 * @author  092038 张贞献  
 * @date 2014-12-9    
 */ 
public class SelfProvidedListService implements IBusinessService<List<SelfProvidedEntity>,Void > {

	private IPdaPickupService pdaPickupService;

	/**
	 * 解析包体
	 */
	@Override
	public Void parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
         return null;
	}

	/**
	 * 服务方法
	 */
	@Override
	public List<SelfProvidedEntity> service(AsyncMsg asyncMsg, Void param) throws PdaBusiException {
		
		List<SelfProvidedEntity>  selfProvideds = new ArrayList<SelfProvidedEntity>();
		List<PadPickupResultDto>  padPickupResultDto = null;
		
		PadPickupDto pickupDto = new PadPickupDto(); 
		pickupDto.setCurrentDeptCode(asyncMsg.getDeptCode());
		
		try {
			// 参数验证
			this.validate(asyncMsg);
			
			//根据司机工号、车牌号查询送货任务接口
			padPickupResultDto = pdaPickupService.getPadPickupList(pickupDto);
			
			if(null == padPickupResultDto){
				return selfProvideds;
			}
			//封装实体
			selfProvideds = wrapSelfProvidedEntityLists(padPickupResultDto);
			
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(),e.getErrorCode());
		}
		
		
		
		return selfProvideds;
	}

	/**
	 * 封装实体
	 * @param asyncMsg
	 * @param getDeryTask
	 * @return
	 */
	private List<SelfProvidedEntity> wrapSelfProvidedEntityLists(List<PadPickupResultDto> padPickupResultDtos){
		
		List<SelfProvidedEntity>  selfProvidedEntitys = new ArrayList<SelfProvidedEntity>();
		SelfProvidedEntity entity = null;
		for(PadPickupResultDto pdto : padPickupResultDtos){
			entity= new SelfProvidedEntity();
			//货物尺寸
			entity.setGoodsSize(pdto.getGoodSize());
			//操作时间
			entity.setOperTime(pdto.getOperTime());
			//包装类型
			entity.setPackageType(pdto.getGoodPackage());
			//件数
			entity.setPieces(pdto.getBillingGoodsQty().toString());
			//状态
			entity.setStatus(pdto.getState());
			//体积
			entity.setVolume(pdto.getGoodVolume().toString());
			//运单号
			entity.setWayBillCode(pdto.getWaybillNo());
			//重量
			entity.setWeight(pdto.getGoodWeight().toString());
			
			selfProvidedEntitys.add(entity);
		}
		
		
		
		return selfProvidedEntitys;
	}
	
	/**
	 * 验证数据有效性
	 * @param asyncMsg
	 * @param getDeryTask
	 */
	private void validate(AsyncMsg asyncMsg){
		Argument.notNull(asyncMsg, "AsyncMsg");
		//pda编号
		Argument.hasText(asyncMsg.getPdaCode(), "AsyncMsg.pdaCode");
		//部门编号
		Argument.hasText(asyncMsg.getDeptCode(), "AsyncMsg.deptCode");
		//用户编号
		Argument.hasText(asyncMsg.getUserCode(), "AsyncMsg.userCode");
		//操作类型
		Argument.hasText(asyncMsg.getOperType(), "AsyncMsg.operType");
			}

	/**
	 * 服务类型
	 */
	@Override
	public String getOperType() {
		return DeliveryConstant.OPER_TYPE_SELF_PROVIDED_LIST.VERSION;
	}

	/**
	 * 是否同步
	 */
	@Override
	public boolean isAsync() {
		return false;
	}

	public void setPdaPickupService(IPdaPickupService pdaPickupService) {
		this.pdaPickupService = pdaPickupService;
	}





}










