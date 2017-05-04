package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICusOrderSourceDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICusOrderSourceService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CusOrderSourceEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CusOrderSourceException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * CRM行业、客户等级、订单来源信息Service接口实现
 * @author dujunhui-187862
 * @date 2014-9-25 下午4:01:27
 * @since
 * @version
 */
public class CusOrderSourceService implements ICusOrderSourceService{

    /**
     * CRM行业、客户等级、订单来源信息DAO接口.
     */
    private ICusOrderSourceDao cusOrderSourceDao;
    
    /**
     * 设置CRM行业、客户等级、订单来源信息Dao接口
     * @param cusProfessionDao
     */
	public void setCusOrderSourceDao(ICusOrderSourceDao cusOrderSourceDao) {
		this.cusOrderSourceDao = cusOrderSourceDao;
	}

	/**
	 * 新增CRM行业、客户等级、订单来源信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:06:22
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int addCusOrderSource(CusOrderSourceEntity entity) throws CusOrderSourceException{
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		// 先验证在数据库是否存在
		String queryCode=null;
		if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_ONE)){
			queryCode=entity.getSecDegreeProfessionCode();
		}else if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_TWO)){
			queryCode=entity.getCustomerDegreeCode();
		}else if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_THREE)){
			queryCode=entity.getOrderSourceCode();
		}else{
			return FossConstants.FAILURE;
		}
		//根据传入的实体类型和主编码查询，是否已存在于数据库
		CusOrderSourceEntity isFlag = cusOrderSourceDao.
				queryCusOrderSourceByCode(entity.getImportPattern(), queryCode);
		
		if (isFlag!=null) {//存在就作废
			isFlag.setImportPattern(entity.getImportPattern());//设置作废的实体类型
			cusOrderSourceDao.deleteCusOrderSource(isFlag);//先作废数据库中数据
		}
		//再新增
		entity.setId(UUIDUtils.getUUID());
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));//用于行业信息时间建模
		cusOrderSourceDao.addCusOrderSource(entity);
		
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 修改CRM行业、客户等级、订单来源信息
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:06:22
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int updateCusOrderSource(CusOrderSourceEntity entity) throws CusOrderSourceException{
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		// 先验证在数据库是否存在
		String queryCode=null;
		if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_ONE)){
			queryCode=entity.getSecDegreeProfessionCode();
		}else if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_TWO)){
			queryCode=entity.getCustomerDegreeCode();
		}else if(StringUtils.equals(entity.getImportPattern(), NumberConstants.NUMERAL_S_THREE)){
			queryCode=entity.getOrderSourceCode();
		}else{
			return FossConstants.FAILURE;
		}
		//根据传入的实体类型和主编码查询，是否已存在于数据库
		CusOrderSourceEntity isFlag = cusOrderSourceDao.
						queryCusOrderSourceByCode(entity.getImportPattern(), queryCode);
		if(isFlag!=null){//先作废数据库中数据
			isFlag.setImportPattern(entity.getImportPattern());//设置作废的实体类型
			isFlag.setActive(entity.getActive());
			isFlag.setModifyDate(entity.getModifyDate());
			isFlag.setModifyUser(entity.getModifyUser());
			isFlag.setVersionNo(entity.getVersionNo());
			cusOrderSourceDao.deleteCusOrderSource(isFlag);
		}
		//再新增
		entity.setId(UUIDUtils.getUUID());
		entity.setActive(FossConstants.ACTIVE);
		entity.setModifyDate(new Date(NumberConstants.ENDTIME));//用于行业信息时间建模
		entity.setCreateDate(new Date());
		
		entity.setCreateUser(entity.getModifyUser());
		cusOrderSourceDao.addCusOrderSource(entity);
		
		return FossConstants.SUCCESS;
	}
	
	/**
	 * 作废CRM行业、客户等级、订单来源信息
	 * @author dujunhui-187862
	 * @date 2014-11-14 上 午9:31:53
	 * @param entity 客户行业信息实体
	 * @return 1：成功；-1：失败
	 * @see
	 */
	@Override
	public int deleteCusOrderSource(CusOrderSourceEntity entity) {
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		return cusOrderSourceDao.deleteCusOrderSource(entity);
	}
	
	/**
	 * <p>根据主编码查询信息实体</p>
	 * @author dujunhui-187862
	 * @date 2014-9-25 下午4:06:22
	 * @param 
	 * @return
	 * @see
	 */
	@Override
	public CusOrderSourceEntity queryCusOrderSourceByCode(String importPattern,String cusOrderSourceCode) 
			throws CusOrderSourceException{
		return cusOrderSourceDao.queryCusOrderSourceByCode(importPattern,cusOrderSourceCode);
	}

}