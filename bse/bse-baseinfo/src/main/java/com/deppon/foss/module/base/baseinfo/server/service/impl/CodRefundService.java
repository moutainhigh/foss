package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundAdditionalDao;
import com.deppon.foss.module.base.baseinfo.api.server.dao.ICodRefundDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.ICodRefundService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundAdditionalEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.CodRefundException;
import com.deppon.foss.util.define.FossConstants;

/**
 * (CodRefund的Service实现类)
 * 
 * @author 187862-dujunhui
 * @date 2014-7-16 下午1:48:15
 * @since
 * @version v1.0
 */
public class CodRefundService implements ICodRefundService {

	@Value(value="${file.rootDir}")
	private String rootDir;
	
	/**
	 * Dao层注入
	 */
	private ICodRefundDao codRefundDao;
	/**
	 * 附件Dao层注入
	 */
	private ICodRefundAdditionalDao codRefundAdditionalDao;

	/**
	 * @param codRefundDao
	 *            the codRefundDao to set
	 */
	public void setCodRefundDao(ICodRefundDao codRefundDao) {
		this.codRefundDao = codRefundDao;
	}
	
	/**
	 * @param codRefundAdditionalDao the codRefundAdditionalDao to set
	 */
	public void setCodRefundAdditionalDao(
			ICodRefundAdditionalDao codRefundAdditionalDao) {
		this.codRefundAdditionalDao = codRefundAdditionalDao;
	}

	/**
	 * <p>
	 * (新增单条代收货款打包退款基础信息)
	 * </p>
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午1:48:15
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICodRefundService#addCodRefund(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity)
	 */
	@Override
	public CodRefundEntity addCodRefund(CodRefundEntity entity,
			List<String> fileNames) {
		// Auto-generated method stub
		if (entity == null) {
			throw new CodRefundException("基础信息表实体为空！","基础信息表实体为空！");
		}
		CodRefundEntity addEntity = codRefundDao.addCodRefund(entity) > 0 ? entity: null;

		if (null == addEntity) {
			throw new CodRefundException("基础信息表实体为空！","基础信息表实体为空！");
		}
		if(!CollectionUtils.isEmpty(fileNames)){
			for (String fileName : fileNames) {
				CodRefundAdditionalEntity addAdditionalEntity = new CodRefundAdditionalEntity();
				addAdditionalEntity.setActive(FossConstants.ACTIVE);
				addAdditionalEntity.setCustomerCode(addEntity.getCustomerCode());
				addAdditionalEntity.setCustomerName(addEntity.getCustomerName());
				addAdditionalEntity.setAdditional(fileName);
				addAdditionalEntity.setCreateDate(addEntity.getCreateDate());
				addAdditionalEntity.setCreateUser(addEntity.getCreateUser());
				codRefundAdditionalDao.addCodRefundAdditional(addAdditionalEntity);
			}
		}
		return addEntity;
	}

	/**
	 * <p>
	 * (删除单条代收货款打包退款基础信息)
	 * </p>
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午1:48:15
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICodRefundService#deleteCodRefund(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity)
	 */
	@Override
	public CodRefundEntity deleteCodRefund(CodRefundEntity entity) {
		// Auto-generated method stub
		return codRefundDao.deleteCodRefund(entity) > 0 ? entity : null;
	}

	/**
	 * <p>
	 * (批量删除代收货款打包退款基础信息)
	 * </p>
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午1:48:15
	 * @param codeList
	 * @param modifyUser
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICodRefundService#deleteCodRefunds(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public int deleteCodRefunds(String[] codeList, String modifyUser) {
		// Auto-generated method stub
		if (codeList.length <= 0) {
			throw new CodRefundException("传入的参数不能为空","传入的参数不能为空");
		}
		CodRefundAdditionalEntity entity=new CodRefundAdditionalEntity();
		for(int m=0;m<codeList.length;m++){
			entity.setCustomerCode(codeList[m]);
			entity.setActive(FossConstants.ACTIVE);
			List<CodRefundAdditionalEntity> listEntity=codRefundAdditionalDao.
					queryCodRefundAdditionalByCustomerCode(entity, 0, Integer.MAX_VALUE);
			if(CollectionUtils.isEmpty(listEntity)){
				throw new CodRefundException("所删的客户编码没有对应附件信息！","所删的客户编码没有对应附件信息！");
			}
			for(CodRefundAdditionalEntity delRefundAdditionalEntity:listEntity){//循环作废附件
				if(StringUtils.isEmpty(delRefundAdditionalEntity.getAdditional())){
					throw new CodRefundException("所删的客户编码对应附件找不到！","所删的客户编码对应附件找不到！");
				}
				try {
					FileUtils.forceDelete(new File(rootDir + "\\"+ delRefundAdditionalEntity.getAdditional()));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		int i=codRefundDao.deleteCodRefunds(codeList, modifyUser);
		int j=codRefundAdditionalDao.deleteCodRefundAdditionals(codeList, modifyUser);
	    if(i>0&&j>0){
			 return i;
		 }else{
			 return 0;
		 }
	}

	/**
	 * <p>
	 * (单条修改代收货款打包退款基础信息)
	 * </p>
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午1:48:15
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICodRefundService#updateCodRefund(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity)
	 */
	@Override
	public CodRefundEntity updateCodRefund(CodRefundEntity entity) {
		// Auto-generated method stub
		return codRefundDao.updateCodRefund(entity) > 0 ? entity : null;
	}

	/**
	 * <p>
	 * (根据ID查询单条代收货款打包退款基础信息)
	 * </p>
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午1:48:15
	 * @param id
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICodRefundService#queryCodRefundById(java.lang.String)
	 */
	@Override
	public CodRefundEntity queryCodRefundById(String id) {
		// Auto-generated method stub
		if (StringUtils.isEmpty(id)) {
			return null;
		}
		return codRefundDao.queryCodRefundById(id);
	}

	/**
	 * <p>
	 * (根据条件查询代收货款打包退款基础信息)
	 * </p>
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午1:48:15
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICodRefundService#queryCodRefundListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity,
	 *      int, int)
	 */
	@Override
	public List<CodRefundEntity> queryCodRefundListByCondition(
			CodRefundEntity entity, int start, int limit) {
		// Auto-generated method stub
		return codRefundDao.queryCodRefundByCondition(entity, start, limit);
	}

	/**
	 * <p>
	 * (根据条件查询代收货款打包退款基础信息条数)
	 * </p>
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午1:48:15
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICodRefundService#queryCodRefundListByCondition(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity,
	 *      int, int)
	 */
	@Override
	public long queryCodRefundListCountByCondition(CodRefundEntity entity) {
		// Auto-generated method stub
		return codRefundDao.queryCodRefundCountByCondition(entity);
	}

	/**
	 * <p>
	 * (根据实体查询代收货款打包退款基础资料信息用于导出)
	 * </p>
	 * 
	 * @author 187862-dujunhui
	 * @date 2014-7-16 下午2:31:06
	 * @param entity
	 * @return
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.ICodRefundService#queryCodRefundListForExport(com.deppon.foss.module.base.baseinfo.api.shared.domain.CodRefundEntity)
	 */
	@Override
	public List<CodRefundEntity> queryCodRefundListForExport(
			CodRefundEntity entity) {
		// Auto-generated method stub
		return codRefundDao.queryCodRefundListForExport(entity);
	}

}
