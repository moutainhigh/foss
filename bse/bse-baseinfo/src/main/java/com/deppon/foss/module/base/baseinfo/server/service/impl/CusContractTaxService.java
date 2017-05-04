package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusContractTaxDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusContractTaxService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusContractTaxEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.CustomerQueryConditionDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CusContractTaxException;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * 客户发票标记信息Service接口实现
 * @author 132599-foss-shenweihua
 * @date 2013-11-19 下午3:06:51
 * @since
 * @version
 */
public class CusContractTaxService implements ICusContractTaxService{
	
	 /**
     * 日志.
     */
    //private static final Logger LOGGER = LoggerFactory
	   // .getLogger(CusContractTaxService.class);

    /**
     * 客户发票标记信息DAO接口.
     */
    private ICusContractTaxDao cusContractTaxDao;
    
    /**
     * 设置客户发票标记信息Dao接口
     * @param cusContractTaxDao
     */
	public void setCusContractTaxDao(ICusContractTaxDao cusContractTaxDao) {
		this.cusContractTaxDao = cusContractTaxDao;
	}
	
	/**
	 * 新增客户发票标记信息
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-19 下午3:15:51
	 * @param entity
	 *            客户发票标记信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int addCusContractTax(CusContractTaxEntity entity) throws CusContractTaxException{
		if (null == entity) {

		    return FossConstants.FAILURE;
		}
		if (null == entity.getCrmId()) {
		    throw new CusContractTaxException("客户发票标记信息CRM_ID不允许为空！");
		}
		if(!DictionaryValueConstants.SETTLEMENT_INVOICE_MARK_ONE.equals(entity.getInvoiceType()) && !DictionaryValueConstants.SETTLEMENT_INVOICE_MARK_TWO.equals(entity.getInvoiceType())
			&& null!=entity.getInvoiceType()){
			throw new CusContractTaxException("发票标记不允许为规定的发票标记和空之外的第四方发票标记！");
		}
		
		// 先验证在数据库是否存在
		boolean isFlag = cusContractTaxDao.queryCusContractTaxByCrmId(entity.getCrmId(), null);
		
		if (isFlag) {// 存在就修改
			cusContractTaxDao.updateCusContractTax(entity);
		} else {
		    entity.setId(UUIDUtils.getUUID());

		    cusContractTaxDao.addCusContractTax(entity);
		}
		
		return FossConstants.SUCCESS;
		
		
	}
	
	
	/**
	 * 修改客户发票标记信息
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-19 下午3:03:51
	 * @param entity
	 *            客户发票标记信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int updateCusContractTax(CusContractTaxEntity entity) throws CusContractTaxException{
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		if (null == entity.getCrmId()) {
		    throw new CusContractTaxException("客户合同优惠信息CRM_ID不允许为空！");
		}
		return cusContractTaxDao.updateCusContractTax(entity);
	}
	
	/**
	 * <p>
	 * 根据crmId,最后一次修改时间查询客户发票标记信息是否存在
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-19 下午3:04:29
	 * @param crmId
	 * @param lastupdatetime
	 * @return
	 * @see
	 */
	@Override
	public boolean queryCusContractTaxByCrmId(BigDecimal crmId,
			Date lastupdatetime) throws CusContractTaxException{
		return cusContractTaxDao.queryCusContractTaxByCrmId(crmId, lastupdatetime);
	}

	
	/**
	 * <p>
	 * 根据客户编码,客户联系方式查询客户当前可以使用的发票标记信息
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-22 下午1:50:29
	 * @param condition
	 * @return
	 * @see
	 */
	@Override
	public CusContractTaxEntity queryCurrentUseInvoiceMark(
			CustomerQueryConditionDto condition, Date date) {
		if (condition==null) {
		    return null;
		}else {
		    if(null == date){
			date = new Date();
		    }
		    CusContractTaxEntity entity = null;
		    List<CusContractTaxEntity> cusContractTxtList = cusContractTaxDao.queryCurrentUseInvoiceMark(condition, date);
		    if (CollectionUtils.isNotEmpty(cusContractTxtList)) {
		    	this.checkGetTime(entity,cusContractTxtList,date);
				/*for (CusContractTaxEntity infoEntity : cusContractTxtList) {
				    if (null != infoEntity) {
					// 获取发票使用开始时间
					Date beginTime = infoEntity.getBeginTime();
					// 获取发票使用结束时间
					Date endTime = infoEntity.getEndTime();
					    if(null != beginTime && null != endTime){
						// 根据传入的日期找出正在使用的发票标记
						if (beginTime.getTime() <= date.getTime()
							&& endTime.getTime() > date.getTime() 
								) {
							entity = infoEntity;
						    break;
						}
					    }
				    }
				}*/
				return entity;
			    }
		    	return entity;
			}
		}
	/**
	 * 多层嵌套不合sonar规则
	 * @author 273311
	 * @param entity
	 * @param cusContractTxtList
	 * @param date
	 */
	  private void checkGetTime(CusContractTaxEntity entity, List<CusContractTaxEntity> cusContractTxtList, Date date) {
		  for (CusContractTaxEntity infoEntity : cusContractTxtList) {
			    if (null != infoEntity) {
				// 获取发票使用开始时间
				Date beginTime = infoEntity.getBeginTime();
				// 获取发票使用结束时间
				Date endTime = infoEntity.getEndTime();
				    if(null != beginTime && null != endTime){
					// 根据传入的日期找出正在使用的发票标记
					if (beginTime.getTime() <= date.getTime()
						&& endTime.getTime() > date.getTime() 
							) {
						entity = infoEntity;
					    break;
					}
				    }
			    }
			}
	}

}
