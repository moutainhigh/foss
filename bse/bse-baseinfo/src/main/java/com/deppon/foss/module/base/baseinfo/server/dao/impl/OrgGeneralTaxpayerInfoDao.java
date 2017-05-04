package com.deppon.foss.module.base.baseinfo.server.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOrgGeneralTaxpayerInfoDao;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.GeneralTaxpayerInfoEntity;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
 * 同步一般纳税人信息Dao实现
 * @author 308861 
 * @date 2016-2-28 下午2:36:20
 * @since
 * @version
 */
public class OrgGeneralTaxpayerInfoDao extends SqlSessionDaoSupport implements IOrgGeneralTaxpayerInfoDao{
	
	/**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrgGeneralTaxpayerInfoDao.class);
	
	/**
     * mybatis 命名空间
     */
    private static final String NAMESPACE = "foss.bse.bse-baseinfo.generalTaxpayer.";
	
    /**
     * 
     * 插入 
     * @author 308861 
     * @date 2016-2-24 下午6:29:08
     * @param entity
     * @return 
     */
	@Override
	public GeneralTaxpayerInfoEntity addGeneralTaxpayerInfo(
			GeneralTaxpayerInfoEntity entity) {
		// 请求合法性验证：
		if (null == entity) {
		    return entity;
		}
		Date now = new Date();
		entity.setId(UUIDUtils.getUUID());	
		// CreateUser为传入的用户编码，CreateDate为当前时间
		entity.setCreateDate(now);
		// ModifyDate为当前时间
		entity.setModifyDate(now);
		entity.setCreateUser("CRM");
		entity.setModifyUser(entity.getCreateUser());
		entity.setVersionNo(String.valueOf(now.getTime()));
		//插入数据active为Y
		entity.setActive(FossConstants.ACTIVE);
		int result = getSqlSession().insert(NAMESPACE + "addGeneralTaxpayerInfo", entity);
		return result > 0 ? entity : null;
	}

	/**
	 * 
	 *  修改
	 * @author 308861 
	 * @date 2016-2-24 下午6:30:11
	 * @param entity
	 * @return 
	 */
	@Override
	public GeneralTaxpayerInfoEntity updateTaxpayerInfo(
			GeneralTaxpayerInfoEntity entity) {
		// 请求合法性判断：
		if (null == entity || StringUtils.isBlank(entity.getCrmId())) {
		    return entity;
		}
		// 更新要先删除旧的数据：
		GeneralTaxpayerInfoEntity result=this.deleteTaxpayerInfo(entity);
		if(result == null){
		    String msg = "更新时，作废失败";
		    LOGGER.error(msg);
		}
		// 组装插入参数
		entity.setId(UUIDUtils.getUUID());
		// 版本号始终取当前时间
		entity.setVersionNo(String.valueOf(new Date().getTime()));
		// CreateUser为传入的用户编码，CreateDate为当前时间
		Date now = new Date();
		entity.setCreateDate(now);
		// ModifyDate为当前时间
		entity.setModifyDate(now);
		entity.setCreateUser("CRM");
		entity.setModifyUser("CRM");
		entity.setActive("Y");
		int resultNum = getSqlSession().insert(NAMESPACE + "addGeneralTaxpayerInfo", entity);
		return resultNum > 0 ? entity : null;
	}
	
	/**
	 * 
	 * 作废 
	 * @author 308861 
	 * @date 2016-2-24 下午6:30:29
	 * @param entity
	 * @return 
	 */
	@Override
	public GeneralTaxpayerInfoEntity deleteTaxpayerInfo(
			GeneralTaxpayerInfoEntity entity) {
		// 请求参数合法性验证
		if (null == entity || StringUtils.isBlank(entity.getCrmId())) {
		    return entity;
		}
		// 处理作废时要更新的数据
		Date now = new Date();
		entity.setModifyDate(now);
		entity.setModifyUser("CRM");
		entity.setVersionNo(String.valueOf(now.getTime()));
		int result = getSqlSession().update(NAMESPACE + "deleteTaxpayerInfo", entity);
		return result > NumberConstants.ZERO ? entity : null;
	}

	/**
	 * 
	 * 根据CRMID查询 
	 * @author 308861 
	 * @date 2016-2-24 下午6:30:43
	 * @param entity
	 * @return 
	 */
	@Override
	public GeneralTaxpayerInfoEntity queryTaxpayerInfoById(
			String crmId) {
		if (StringUtils.isBlank(crmId)) {
			return null;
		}
		GeneralTaxpayerInfoEntity entity=new GeneralTaxpayerInfoEntity();
		entity.setCrmId(crmId);
		// 构造查询条件：
		entity.setActive("Y");
		@SuppressWarnings("unchecked")
		List<GeneralTaxpayerInfoEntity> list=getSqlSession().selectList(NAMESPACE+"queryTaxpayerInfoBycrmId",entity);
		if(!CollectionUtils.isEmpty(list)){
			return list.get(0);
		}else{
			return null;
		}
	}

}
