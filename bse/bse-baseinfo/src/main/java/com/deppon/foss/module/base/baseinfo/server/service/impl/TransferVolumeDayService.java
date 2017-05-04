package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ITransferVolumeDayDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ITransferVolumeDayService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TransferVolumeDayEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.TransferVolumeDayException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
/**
 * 外场日承载货量接口
 * @author 130346
 *
 */
public class TransferVolumeDayService implements ITransferVolumeDayService {

	
	private ITransferVolumeDayDao transferVolumeDayDao;
	private IOutfieldService outfieldService;
	public void setTransferVolumeDayDao(ITransferVolumeDayDao transferVolumeDayDao) {
		this.transferVolumeDayDao = transferVolumeDayDao;
	}
	
	public void setOutfieldService(IOutfieldService outfieldService) {
		this.outfieldService = outfieldService;
	}

	/**
	 *<P>添加日承载货量信息<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:28:02
	 * @param entity
	 * @return
	 */
	@Override
	public int addTransferVolumeDay(
			TransferVolumeDayEntity entity) {
		
		entity.setCreateUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		entity.setModifyUserCode(FossUserContext.getCurrentInfo().getEmpCode());
		long n =transferVolumeDayDao.queryTransferVolumeDayListCount(entity);
		if(n>0){
			throw new TransferVolumeDayException("该外场已经维护数据不能重复维护！");
		}
		//应需求危险值和危险值除以100保存
//		String dangerValue = entity.getDangerValue();
//		String fullValue = entity.getFullValue(); 
		BigDecimal dangerValue = entity.getDangerValue();
		BigDecimal fullValue = entity.getFullValue();
		BigDecimal bigNumber  = new BigDecimal(NumberConstants.NUMERAL_HUNDRED);
//		double danger = new Double(dangerValue);
//		double full = new Double(fullValue);
		
		if((dangerValue.divide(bigNumber)+"").length()>NumberConstants.NUMBER_5){
			entity.setDangerValue(new BigDecimal((dangerValue.divide(bigNumber)+"").substring(NumberConstants.STRING_LOCATION_0,NumberConstants.NUMBER_5)));
		}else{
			entity.setDangerValue(dangerValue.divide(bigNumber));
		}
		if((fullValue.divide(bigNumber)+"").length()>NumberConstants.NUMBER_5){
			entity.setFullValue(new BigDecimal((fullValue.divide(bigNumber)+"").substring(NumberConstants.STRING_LOCATION_0,NumberConstants.NUMBER_5)));
		}else{
			entity.setFullValue(fullValue.divide(bigNumber));
		}
		// TODO Auto-generated method stub
		return transferVolumeDayDao.addTransferVolumeDay(entity);
	}
	/**
	 *<P>根据CODE作废信息<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 * @param entity
	 * @return
	 */
	@Override
	public int deleteTransferVolumeDayEntityByCode(
			TransferVolumeDayEntity entity) {
		if(StringUtils.isBlank(entity.getCode())){
			throw new TransferVolumeDayException("外场不能为空");
		}
		// TODO Auto-generated method stub
		return transferVolumeDayDao.deleteTransferVolumeDayEntityByCode(entity);
	}
	/**
	 *<P>根据条件分页查询实体<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 * @param satelliteDeptCode
	 * @param salesDeptcode
	 * @return
	 */
	@Override
	public List<TransferVolumeDayEntity> queryTransferVolumeDayList(
			TransferVolumeDayEntity entity, int start, int limit) {
//		if(StringUtils.isBlank(entity.getCode())){
//			throw new TransferVolumeDayException("外场不能为空");
//		}
		return transferVolumeDayDao.queryTransferVolumeDayList(entity, start, limit);
	}
	/**
	 *<P>查询总数<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 * @param entity
	 * @return
	 */
	@Override
	public long queryTransferVolumeDayListCount(TransferVolumeDayEntity entity) {
//		if(StringUtils.isBlank(entity.getCode())){
//			throw new TransferVolumeDayException("外场不能为空");
//		}
		return transferVolumeDayDao.queryTransferVolumeDayListCount(entity);
	}
	/**
     * 更新 
     * 
     * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
     */
	@Override
	public TransferVolumeDayEntity updateTransferVolumeDay(
			TransferVolumeDayEntity entity ) {
		if(StringUtils.isBlank(entity.getCode())){
			throw new TransferVolumeDayException("外场不能为空");
		}
		entity.setModifyUserCode(FossUserContext.getCurrentUser().getEmpCode());
		//应需求危险值和危险值除以100保存
//				String dangerValue = entity.getDangerValue();
//				String fullValue = entity.getFullValue();
//				double danger = new Double(dangerValue);
//				double full = new Double(fullValue);
//				if((danger/100+"").length()>5){
//					entity.setDangerValue((danger/100+"").substring(0,5));
//				}else{
//					entity.setDangerValue((danger/100+""));
//				}
//				if((full/100+"").length()>5){
//					entity.setFullValue((full/100+"").substring(0,5));
//				}else{
//					entity.setFullValue(full/100+"");
//				}
				BigDecimal dangerValue = entity.getDangerValue();
				BigDecimal fullValue = entity.getFullValue();
				BigDecimal bigNumber  = new BigDecimal(NumberConstants.NUMERAL_HUNDRED);
				if((dangerValue.divide(bigNumber)+"").length()>NumberConstants.NUMBER_5){
					entity.setDangerValue(new BigDecimal((dangerValue.divide(bigNumber)+"").substring(NumberConstants.STRING_LOCATION_0,NumberConstants.NUMBER_5)));
				}else{
					entity.setDangerValue(dangerValue.divide(bigNumber));
				}
				if((fullValue.divide(bigNumber)+"").length()>NumberConstants.NUMBER_5){
					entity.setFullValue(new BigDecimal((fullValue.divide(bigNumber)+"").substring(NumberConstants.STRING_LOCATION_0,NumberConstants.NUMBER_5)));
				}else{
					entity.setFullValue(fullValue.divide(bigNumber));
				}
		
		return transferVolumeDayDao.updateTransferVolumeDay(entity);
	}
	/**
     * 查询外场对应仓库饱和度危险值和预警值
     * 
     * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
     */
	@Override
	public  List<TransferVolumeDayEntity> selectTransferFullAndDangerValue(
			TransferVolumeDayEntity entity) {
		if(StringUtils.isBlank(entity.getQueryTime()+"")){
			throw new TransferVolumeDayException("查询时间不能为空");
		}
//		entity.setModifyUserCode(FossUserContext.getCurrentUser().getEmpCode());
//		List<TransferVolumeDayEntity> transferVolumeDayEntitys= new ArrayList<TransferVolumeDayEntity>();
		//返回值
		List<TransferVolumeDayEntity> queryTransferVolumeDayEntitys= new ArrayList<TransferVolumeDayEntity>();
	//	List<String> result = new ArrayList<String>();
		BigDecimal zero  = new BigDecimal(NumberConstants.ZERO);
		TransferVolumeDayEntity transferVolumeDayEntity = new TransferVolumeDayEntity();
		List<TransferVolumeDayEntity> transferVolumeDayEntitys = transferVolumeDayDao.selectTransferFullAndDangerValue(entity);
		//查询全国外场，如果在日承载货量表里没有配置这填充0
		if(StringUtils.isBlank(entity.getCode())){
			List<String> result = outfieldService.queryActiveOrgCodeList();
			 for(String code :result){
				 for(TransferVolumeDayEntity queryentity:transferVolumeDayEntitys){
					 if(!StringUtils.equals(code, queryentity.getCode())){
						//查询全国外场，如果在日承载货量表里没有配置这填充0
						 transferVolumeDayEntity.setCode(code);
						 transferVolumeDayEntity.setVolumeDay(zero);
						 transferVolumeDayEntity.setFullValue(zero);
						 transferVolumeDayEntity.setDangerValue(zero);
						 transferVolumeDayEntity.setVolumeMonth(zero);
						 queryTransferVolumeDayEntitys.add(transferVolumeDayEntity);
					 }
				 }
			 }
			 
		}
		//吧个集合相加
		if(CollectionUtils.isNotEmpty(transferVolumeDayEntitys)){
			for(TransferVolumeDayEntity addEntity : transferVolumeDayEntitys){
				queryTransferVolumeDayEntitys.add(addEntity);
			}
		}
		return queryTransferVolumeDayEntitys;
	}
	
//	/**
//     * 查询外场的月承载货量
//     * 
//     * @author :130346-lifanghong
//	 * @date : 2014-4-16下午3:33:35
//     */
//	@Override
//	public  List<TransferVolumeDayEntity> selectTransferMonthValue(
//			TransferVolumeDayEntity entity) {
//		if(StringUtils.isBlank(entity.getQueryTime()+"")){
//			throw new TransferVolumeDayException("查询时间不能为空");
//		}
//		List<TransferVolumeDayEntity> entitys  = transferVolumeDayDao.selectTransferMonthValue(entity);
//		
//		return transferVolumeDayDao.selectTransferFullAndDangerValue(entity);
//	}
//	


}
