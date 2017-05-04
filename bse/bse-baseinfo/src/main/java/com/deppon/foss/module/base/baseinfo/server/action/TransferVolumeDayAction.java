package com.deppon.foss.module.base.baseinfo.server.action;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.server.web.action.AbstractAction;
import com.deppon.foss.framework.server.web.result.json.annotation.JSON;
import com.deppon.foss.module.base.baseinfo.api.server.service.ITransferVolumeDayService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.TransferVolumeDayEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.TransferVolumeDayVo;
/**
 * 外场日承载货量
 * @author 130346
 *
 */
public class TransferVolumeDayAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 573282036886233272L;
    /**
     * 日承载货量service
     */
	private ITransferVolumeDayService transferVolumeDayService;
	
	
	private TransferVolumeDayVo transferVolumeDayVo;
	
	/**
	 *<P>添加日承载货量信息<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:28:02
	 * @param entity
	 * @return
	 */
	 public String addTransferVolumeDay() {
			try {
				TransferVolumeDayEntity entity = transferVolumeDayVo.getTransferVolumeDayEntity();
			    transferVolumeDayService.addTransferVolumeDay(entity);// 查询总个数
			    return returnSuccess("新增成功！");
			} catch (BusinessException e) {
			    return returnError(e);
				}
		    }
	 
	 /**
	 *<P>根据CODE作废信息<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 * @param entity
	 * @return
	 */
	 public String deleteTransferVolumeDayEntityByCode(){
			try {
				
				 List<String> codes = transferVolumeDayVo.getCodes();
				 for(String code :codes){
					 TransferVolumeDayEntity entity = new TransferVolumeDayEntity();
					 entity.setCode(code);
					 //int n = transferVolumeDayService.deleteTransferVolumeDayEntityByCode(entity);
					 transferVolumeDayService.deleteTransferVolumeDayEntityByCode(entity);
				 }
			   
			    return returnSuccess("删除成功！");
			} catch (BusinessException e) {
			    return returnError(e);
				}
		    }
		
	/**
	 *<P>根据条件分页查询实体<P>
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 * @param satelliteDeptCode
	 * @param salesDeptcode
	 * @return
	 */
    @JSON
	 public String  queryTransferVolumeDayList(){
			try {
				TransferVolumeDayEntity entity = transferVolumeDayVo.getTransferVolumeDayEntity();
			    List<TransferVolumeDayEntity> transferVolumeDayEntitys= transferVolumeDayService.queryTransferVolumeDayList(entity, start, limit);// 查询总个数
			    List<TransferVolumeDayEntity> entitys;
			    //给危险值和预警值包装用来显示
			    if(!CollectionUtils.isEmpty(transferVolumeDayEntitys)){
			    	entitys = new ArrayList<TransferVolumeDayEntity>();
			    	for(TransferVolumeDayEntity transferVolumeDayentity:transferVolumeDayEntitys){
			    		BigDecimal dangerValue = transferVolumeDayentity.getDangerValue();
			    		BigDecimal fullValue = transferVolumeDayentity.getFullValue();
//			    		double danger = new Double(dangerValue);
//			    		double full = new Double(fullValue);
			    		//因为double类型剩法会失去精度所以要处理之后才计算
//			    		BigDecimal bigDanger = new BigDecimal(Double.toString(danger));
//			    		BigDecimal bigFull = new BigDecimal(Double.toString(full));
			    		BigDecimal  big = new BigDecimal(Double.toString(NumberConstants.NUMBER_100));
//			    		DecimalFormat fnum = new DecimalFormat("##0.00");
			    		
			    		transferVolumeDayentity.setLookDangerValue(dangerValue.multiply(big).setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
			    		transferVolumeDayentity.setLookFullValue(fullValue.multiply(big).setScale(2, BigDecimal.ROUND_HALF_UP)+"%");
			    		entitys.add(transferVolumeDayentity);
			    	}
			    }else{
			    	entitys = transferVolumeDayEntitys;
			    }
			    
			    transferVolumeDayVo.setTransferVolumeDayEntitys(entitys);
			    this.setTotalCount(transferVolumeDayService.queryTransferVolumeDayListCount(entity));
			    return returnSuccess();
			} catch (BusinessException e) {
			    return returnError(e);
			}
			
			
		    }
		    
		    
	/**
	 *<P>根据条件查询实体<P>
	* @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
	 * @param satelliteDeptCode
	 * @param salesDeptcode
	 * @return
	 */
    @JSON
	 public String  queryTransferVolumeDay(){
			try {
					TransferVolumeDayEntity entity = transferVolumeDayVo.getTransferVolumeDayEntity();
					List<TransferVolumeDayEntity> transferVolumeDayEntitys= transferVolumeDayService.queryTransferVolumeDayList(entity, NumberConstants.NUMERAL_ZORE, NumberConstants.NUMBER_10);// 查询总个数
//					 transferVolumeDayVo.setTransferVolumeDayEntitys(transferVolumeDayEntitys);
					//给危险值和预警值包装用来显示
					 List<TransferVolumeDayEntity> entitys = new ArrayList<TransferVolumeDayEntity>();
				    if(!CollectionUtils.isEmpty(transferVolumeDayEntitys)){
				    	for(TransferVolumeDayEntity transferVolumeDayentity:transferVolumeDayEntitys){
				    		BigDecimal dangerValue = transferVolumeDayentity.getDangerValue();
				    		BigDecimal fullValue = transferVolumeDayentity.getFullValue();
				    		BigDecimal  big = new BigDecimal(Double.toString(NumberConstants.NUMBER_100));
//				    		double danger = new Double(dangerValue);
//				    		double full = new Double(fullValue);
				    		transferVolumeDayentity.setDangerValue(dangerValue.multiply(big));
				    		transferVolumeDayentity.setFullValue(fullValue.multiply(big));
				    		entitys.add(transferVolumeDayentity);
				    	}
				    }else{
				    	entitys = transferVolumeDayEntitys;
				    }
					
				   transferVolumeDayVo.setTransferVolumeDayEntity(transferVolumeDayEntitys.get(NumberConstants.NUMERAL_ZORE));
				 return returnSuccess();
			} catch (BusinessException e) {
					    return returnError(e);
					}
		 }	    
	/**
	 * 更新 
	 * 
	 * @author :130346-lifanghong
	 * @date : 2014-4-16下午3:33:35
     */
	 public String updateTransferVolumeDay(){
			try {
				TransferVolumeDayEntity entity = transferVolumeDayVo.getTransferVolumeDayEntity();
				entity  = transferVolumeDayService.updateTransferVolumeDay(entity);// 查询总个数
			    return returnSuccess("保存成功！");
			} catch (BusinessException e) {
			    return returnError(e);
				}
	 }

	


	public void setTransferVolumeDayService(
			ITransferVolumeDayService transferVolumeDayService) {
		this.transferVolumeDayService = transferVolumeDayService;
	}

	public void setTransferVolumeDayVo(TransferVolumeDayVo transferVolumeDayVo) {
		this.transferVolumeDayVo = transferVolumeDayVo;
	}

	public TransferVolumeDayVo getTransferVolumeDayVo() {
		return transferVolumeDayVo;
	}
	
	
}
