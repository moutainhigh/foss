package com.deppon.pda.bdm.module.foss.unload.server.service.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.shared.util.Argument;
import com.deppon.foss.module.pickup.pda.api.server.service.ILTLEWaybillPdaScanService;
import com.deppon.foss.module.pickup.pda.api.server.service.IPdaWaybillService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WoodenRequireEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.WoodenRequirePdaDto;
import com.deppon.pda.bdm.module.core.server.async.job.QueueMonitorInfo;
import com.deppon.pda.bdm.module.core.server.service.IBusinessService;
import com.deppon.pda.bdm.module.core.shared.constants.version.UnLoadConstant;
import com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg;
import com.deppon.pda.bdm.module.core.shared.exception.PdaBusiException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.ArgumentInvalidException;
import com.deppon.pda.bdm.module.core.shared.exception.sys.common.FossInterfaceException;
import com.deppon.pda.bdm.module.core.shared.util.JsonUtil;
import com.deppon.pda.bdm.module.core.shared.util.UUIDUtils;
import com.deppon.pda.bdm.module.foss.unload.server.dao.IUnloadDao;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.UnldWoodenRequireEntity;
import com.deppon.pda.bdm.module.foss.unload.shared.domain.WoodenRequirePdaEntity;



 
/**   
 * @ClassName CollectionVolueAndWeightService  
 * @Description  集中开单项目--补录重量体积异步接口 
 * @author  092038 张贞献  
 * @date 2014-10-22    
 */ 
public class CollectionVolueAndWeightService implements
		IBusinessService<Void, WoodenRequirePdaEntity> {

	private Logger log = Logger.getLogger(getClass());

	private IPdaWaybillService pdaWaybillService;
	
	private IUnloadDao unloadDao;
	
	private ILTLEWaybillPdaScanService lTLEWaybillPdaScanService;
	private  final static int LENGTH = 9; 
	private  final static String START = "7"; 
	
	
	public void setUnloadDao(IUnloadDao unloadDao) {
		this.unloadDao = unloadDao;
	}

	public void setLog(Logger log) {
		this.log = log;
	}

	public void setPdaWaybillService(IPdaWaybillService pdaWaybillService) {
		this.pdaWaybillService = pdaWaybillService;
	}

	
	public ILTLEWaybillPdaScanService getlTLEWaybillPdaScanService() {
		return lTLEWaybillPdaScanService;
	}

	public void setlTLEWaybillPdaScanService(
			ILTLEWaybillPdaScanService lTLEWaybillPdaScanService) {
		this.lTLEWaybillPdaScanService = lTLEWaybillPdaScanService;
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:21
	 * @param asyncMsg
	 * @return
	 * @throws PdaBusiException
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#parseBody(com.deppon.pda.bdm.module.core.shared.domain.AsyncMsg)
	 */
	@Override
	public WoodenRequirePdaEntity parseBody(AsyncMsg asyncMsg) throws PdaBusiException {
		WoodenRequirePdaEntity entity  = JsonUtil.parseJsonToObject(WoodenRequirePdaEntity.class,
				asyncMsg.getContent());
		
		entity.setDeptCode(asyncMsg.getDeptCode());
		entity.setPdaCode(asyncMsg.getPdaCode());
		entity.setScanUser(asyncMsg.getUserCode());
		entity.setScanType(asyncMsg.getOperType());
		entity.setUploadTime(asyncMsg.getUploadTime());		
		return entity;
	}

	@Transactional
	@Override
	public Void service(AsyncMsg asyncMsg, WoodenRequirePdaEntity param) 
			 {
		this.validate(asyncMsg,param);
		WoodenRequirePdaDto woodenRequirePdaDto = new WoodenRequirePdaDto();
		try {
			woodenRequirePdaDto = getFossReqEntity(asyncMsg,
				 param);

			long startTime = System.currentTimeMillis();
			//获取运单长度
			int len = param.getWblCode().trim().length();
			String start = param.getWblCode().trim().substring(0, 1);
		    if(LENGTH==len&&START.equals(start)){
		    	//以7开头的运单长度为9位的电子面单
		    	lTLEWaybillPdaScanService.pdaScan(woodenRequirePdaDto);
		    } else{
				pdaWaybillService.autoSubmitWaybillByPDA(woodenRequirePdaDto);
		    }
			long endTime = System.currentTimeMillis();
			QueueMonitorInfo.addTotalFossTime(endTime-startTime);
			log.info("[asyncinfo]补录重量体积接口消耗时间:"+(endTime-startTime)+"ms");
			
			return null;
		} catch (BusinessException e) {
			throw new FossInterfaceException(e.getCause(), e.getErrorCode());
		} catch (PdaBusiException e) {
			throw new PdaBusiException(e.getCause());
		} catch (Exception e) {
			log.info(e);
		}
		return null;

	}
	private WoodenRequirePdaDto getFossReqEntity(AsyncMsg asyncMsg,
			WoodenRequirePdaEntity param) {
		//封装foss 请求数据
		WoodenRequirePdaDto  woodenRequirePdaDto = new WoodenRequirePdaDto();
		//货物总体积
		woodenRequirePdaDto.setGoodsVolumeTotal(BigDecimal.valueOf(param.getGoodsVolumeTotal()));
		//货物总重量
		woodenRequirePdaDto.setGoodsWeightTotal(BigDecimal.valueOf(param.getGoodsWeightTotal()));
		//补录人
		woodenRequirePdaDto.setOperatorCode(asyncMsg.getUserCode());
		//补录单号
		woodenRequirePdaDto.setWaybillNo(param.getWblCode());
		//所属外场
		woodenRequirePdaDto.setOuterCode(param.getOuterCode());
		//是否大件上楼
		woodenRequirePdaDto.setIsBigUp(param.getIsBigUp());		
	    //1000KG到2000KG超重件数
		woodenRequirePdaDto.setFhToOtOverQty(param.getFhToOtOverQty());
		// 500KG到1000KG超重件数
		woodenRequirePdaDto.setOtToTtOverQty(param.getOtToTtOverQty());
		//打木托个数
		woodenRequirePdaDto.setWoodenStockNum(param.getWoodenStockNum());

		List<WoodenRequireEntity> woodenRequireList = new ArrayList<WoodenRequireEntity>();
		WoodenRequireEntity woodenRequireEntity = null;
		if(null!=param.getWoodenRequireEntityLis()){
			
			for(UnldWoodenRequireEntity entity :param.getWoodenRequireEntityLis()){
				
				entity.setId(UUIDUtils.getUUID());
				entity.setGoodsWeightTotal(param.getGoodsWeightTotal());//总重量
				entity.setGoodsVolumeTotal(param.getGoodsVolumeTotal());//总体积
				entity.setWblCode(param.getWblCode());	//运单号
				entity.setUserCode(asyncMsg.getUserCode());//不录入工号
				entity.setOuterCode(param.getOuterCode());//外场编码
				entity.setUploadTime(param.getUploadTime());//提交时间
				entity.setScanTime(param.getScanTime());//进入界面时间
				entity.setIsBigUp(param.getIsBigUp());//是否大件上楼				 
		        entity.setFhToOtOverQty(param.getFhToOtOverQty());  //1000KG到2000KG超重件数    
		        entity.setOtToTtOverQty(param.getOtToTtOverQty());  // 500KG到1000KG超重件数		     
		        entity.setWoodenStockNum(param.getWoodenStockNum()); //打木托个数
		        
				woodenRequireEntity = new WoodenRequireEntity();
				//件数
				woodenRequireEntity.setGoodsNum(entity.getGoodsNum());
				// 包装体积
				//woodenRequireEntity.setGoodsSize(BigDecimal.valueOf(entity.getGoodsSize()));
				//高
				woodenRequireEntity.setHeight(BigDecimal.valueOf(entity.getHeight()));
				//长
				woodenRequireEntity.setLength(BigDecimal.valueOf(entity.getLength()));
				//宽
				woodenRequireEntity.setWidth(BigDecimal.valueOf(entity.getWidth()));
				//包装类型   stand=打木架 ; box=打木箱 ； noPack=没打木架打木箱
				woodenRequireEntity.setPackType(entity.getPackType());						
				woodenRequireList.add(woodenRequireEntity);
				log.info("保存补录的重量体积");
				unloadDao.saveWeightandVolume(entity);
			}			
		}else{
			//无提交明细
			UnldWoodenRequireEntity entity = new UnldWoodenRequireEntity();
			entity.setId(UUIDUtils.getUUID());
			entity.setGoodsWeightTotal(param.getGoodsWeightTotal());
			entity.setGoodsVolumeTotal(param.getGoodsVolumeTotal());
			entity.setWblCode(param.getWblCode());	
			entity.setUserCode(asyncMsg.getUserCode());
			entity.setOuterCode(param.getOuterCode());
			entity.setUploadTime(param.getUploadTime());//提交时间
			entity.setScanTime(param.getScanTime());//进入界面时间
			entity.setIsBigUp(param.getIsBigUp());//是否大件上楼				 
	        entity.setFhToOtOverQty(param.getFhToOtOverQty());  //1000KG到2000KG超重件数    
	        entity.setOtToTtOverQty(param.getOtToTtOverQty());  // 500KG到1000KG超重件数
	        entity.setWoodenStockNum(param.getWoodenStockNum()); //打木托个数
	        
			unloadDao.saveWeightandNullVolume (entity);
		}
		
	
		woodenRequirePdaDto.setWoodenRequireEntityLis(woodenRequireList);
		return woodenRequirePdaDto;
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:27
	 * @param unldScanEnity
	 * @throws ArgumentInvalidException
	 * @see
	 */
	private void validate(AsyncMsg asyncMsg,WoodenRequirePdaEntity woodenRequirePdaEntity)
			throws ArgumentInvalidException {
		// 检验卸车扫描非空
		Argument.notNull(woodenRequirePdaEntity, "woodenRequirePdaEntity");
		// 检验标签号非空
		Argument.hasText(woodenRequirePdaEntity.getWblCode(),
				"woodenRequirePdaEntity.wblCode");
		
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:31
	 * @return
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#getOperType()
	 */
	@Override
	public String getOperType() {
		return UnLoadConstant.OPER_TYPE_UNLD_VOLUMEANDWEIGHT_SUBMIT.VERSION;
	}

	/**
	 * 
	 * <p>
	 * TODO(方法详细描述说明、方法参数的具体涵义)
	 * </p>
	 * 
	 * @author Administrator
	 * @date 2013-3-20 下午6:36:35
	 * @return true 异步
	 * @see com.deppon.pda.bdm.module.core.server.service.IBusinessService#isAsync()
	 */
	@Override
	public boolean isAsync() {
		return true;
	}


	

}
