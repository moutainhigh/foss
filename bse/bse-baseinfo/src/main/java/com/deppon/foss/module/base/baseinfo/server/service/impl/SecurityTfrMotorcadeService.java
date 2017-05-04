package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.List;

import org.apache.cxf.common.util.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ISecurityTfrMotorcadeDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.SecurityTfrMotorcadeException;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 * TODO(实现ISecurityTfrMotorcadeService接口)
 * @author 187862-dujunhui
 * @date 2014-5-15 上午10:55:02
 * @since
 * @version
 */
public class SecurityTfrMotorcadeService implements
		ISecurityTfrMotorcadeService {
	
	private ISecurityTfrMotorcadeDao securityTfrMotorcadeDao;

	/**
	 * @param securityTfrMotorcadeDao the securityTfrMotorcadeDao to set
	 */
	public void setSecurityTfrMotorcadeDao(
			ISecurityTfrMotorcadeDao securityTfrMotorcadeDao) {
		this.securityTfrMotorcadeDao = securityTfrMotorcadeDao;
	}

	/** 
	 * <p>TODO(根据保安组编码查询信息)</p> 
	 * @author 187862
	 * @date 2014-5-15 上午10:55:02
	 * @param entity
	 * @param limit
	 * @param start
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService#querySecurityTfrMotorcadeListBySecurityCode(com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity, int, int)
	 */
	@Override
	public List<SecurityTfrMotorcadeEntity> querySecurityTfrMotorcadeListBySecurityCode(
			SecurityTfrMotorcadeEntity entity, int start, int limit) {
		// TODO Auto-generated method stub
		if (null == entity) {
			throw new SecurityTfrMotorcadeException("传入的参数不允许为空！");
		} else {
			return securityTfrMotorcadeDao.querySecurityTfrMotorcadeListBySecurityCode(entity, start, limit);
		}
	}

	/** 
	 * <p>TODO(统计同一保安组信息条数)</p> 
	 * @author 187862
	 * @date 2014-5-15 上午10:55:03
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService#querySecurityCodeCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity)
	 */
	@Override
	public Long querySecurityCodeCount(SecurityTfrMotorcadeEntity entity) {
		// TODO Auto-generated method stub
		return this.securityTfrMotorcadeDao.querySecurityCodeCount(entity);
	}

	/** 
	 * <p>TODO(统计所有保安组信息条数)</p> 
	 * @author 187862
	 * @date 2014-5-15 上午10:55:03
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService#queryRecordCount(com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity)
	 */
	@Override
	public Long queryRecordCount(SecurityTfrMotorcadeEntity entity) {
		// TODO Auto-generated method stub
		if (null == entity) {
			throw new SecurityTfrMotorcadeException("传入的参数不允许为空！");
		} else {
			return securityTfrMotorcadeDao.queryRecordCount(entity);
		}
	}

	/** 
	 * <p>TODO(增加保安组信息)</p> 
	 * @author 187862
	 * @date 2014-5-16 下午5:02:04
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService#addSecurityTfrMotorcade(com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity)
	 */
	@Override
	@Transactional
	public int addSecurityTfrMotorcade(SecurityTfrMotorcadeEntity entity) {
		// TODO Auto-generated method stub
		if (null == entity) {
		    return FossConstants.FAILURE;
		}
		entity.setId(UUIDUtils.getUUID());	
		entity.setVirtualCode(entity.getId());
		return this.securityTfrMotorcadeDao.addSecurityTfrMotorcade(entity);
	}

	/** 
	 * <p>TODO(删除保安组信息)</p> 
	 * @author 187862
	 * @date 2014-5-16 下午5:02:07
	 * @param codeList
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService#deleteSecurityTfrMotorcade(java.lang.String[])
	 */
	@Override
	@Transactional
	public int deleteSecurityTfrMotorcade(String[] codeList) {
		// TODO Auto-generated method stub
		return this.securityTfrMotorcadeDao.deleteSecurityTfrMotorcade(codeList);
	}

	/** 
	 * <p>TODO(更新保安组信息)</p> 
	 * @author 187862
	 * @date 2014-5-16 下午5:02:07
	 * @param entity
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ISecurityTfrMotorcadeService#updateSecurityTfrMotorcade(com.deppon.foss.module.base.baseinfo.api.shared.domain.SecurityTfrMotorcadeEntity)
	 */
	@Override
	@Transactional
	public int updateSecurityTfrMotorcade(SecurityTfrMotorcadeEntity entity) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(entity.getId())){
			throw new SecurityTfrMotorcadeException("传入的参数不允许为空！");
		}else{	
			return this.securityTfrMotorcadeDao.updateSecurityTfrMotorcade(entity);
		}
	}

}
