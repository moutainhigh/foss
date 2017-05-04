package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.deppon.ecs.inteface.domain.OutfieldTFCompanyInfo;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IOutfieldTFCompanyDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOutfieldTFCompanyService;
import com.deppon.foss.module.base.baseinfo.api.server.service.esb.ISyncOutfieldTFCompanyService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OutfieldTFCompanyEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.OutfieldTFCompanyException;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.UserException;
import com.deppon.foss.util.define.FossConstants;

/**
 * 外场与所属运输财务公司关系Service实现接口
 * 
 * @author 132599-foss-shenweihua
 * @date 2013-11-26 上午10:16:52
 * @since
 * @version
 */
public class OutfieldTFCompanyService implements IOutfieldTFCompanyService {

	// 同步给ECS的操作类型
	private static final String OPTION_ADD = "1";
	// 同步给ECS的操作类型
	private static final String OPTION_DELETE = "3";

	/**
	 * 外场与所属运输财务公司关系信息dao接口
	 */
	private IOutfieldTFCompanyDao outfieldTFCompanyDao;

	/**
	 * 外场与所属运输财务公司关系信息dao接口
	 */
	private ISyncOutfieldTFCompanyService syncOutfieldTFCompanyService;

	/**
	 * 设置外场与所属运输财务公司关系信息dao接口
	 * 
	 * @param outfieldTFCompanyDao
	 */
	public void setOutfieldTFCompanyDao(
			IOutfieldTFCompanyDao outfieldTFCompanyDao) {
		this.outfieldTFCompanyDao = outfieldTFCompanyDao;
	}

	/**
	 * 同步外请车合同主体变更到ECS的service
	 * 
	 * @param syncOutfieldTFCompanyService
	 */
	public void setSyncOutfieldTFCompanyService(
			ISyncOutfieldTFCompanyService syncOutfieldTFCompanyService) {
		this.syncOutfieldTFCompanyService = syncOutfieldTFCompanyService;
	}

	/**
	 * <p>
	 * 新增外场与所属运输财务公司关系信息
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-26 上午10:20:21
	 * @param entity
	 * @return
	 * @see
	 */
	@Override
	public int addOutfieldTFCompany(OutfieldTFCompanyEntity entity,
			String createUser) {
		if (null == entity) {
			throw new OutfieldTFCompanyException("传入的参数不允许为空！");
		}
		// 判断外场编码是否为空
		if (StringUtils.isBlank(entity.getOutfieldCode())) {
			throw new OutfieldTFCompanyException("外场编码为空");
		}
		// 判断外场名称是否为空
		if (StringUtils.isBlank(entity.getOutfieldName())) {
			throw new OutfieldTFCompanyException("外场名称为空");
		}
		// 判断运输财务公司编码是否为空
		if (StringUtils.isBlank(entity.getCompanyCode())) {
			throw new OutfieldTFCompanyException("外场所属运输财务公司编码为空");
		}
		// 判断运输财务公司名称是否为空
		if (StringUtils.isBlank(entity.getCompanyName())) {
			throw new OutfieldTFCompanyException("外场所属运输财务公司名称为空");
		}
		String companyName = outfieldTFCompanyDao
				.queryCompanyNameByOutfieldCode(entity.getOutfieldCode());
		if (StringUtils.isNotBlank(companyName)) {
			throw new OutfieldTFCompanyException("该外场所对应的财务运输公司已存在！");
		} else {
			int result = outfieldTFCompanyDao.addOutfieldTFCompany(entity,
					createUser);
			// 同步信息给ECS
			if (result > 0) {
				OutfieldTFCompanyInfo addInfo = this.transFossToEsb(entity,
						OPTION_ADD);
				syncOutfieldTFCompanyToECS(addInfo);
			}
			return result;
		}

	}

	/**
	 * <p>
	 * 修改外场与所属运输财务公司关系
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-26 上午10:22:21
	 * @param entity
	 * @return
	 * @see
	 */
	@Override
	public int updateOutfieldTFCompany(OutfieldTFCompanyEntity entity,String modifyUser) {
		if(null == entity){
		    throw new OutfieldTFCompanyException("传入的参数不允许为空！");
		}
		// 判断对象ID是否为空
		if (StringUtils.isBlank(entity.getId())) {
			throw new OutfieldTFCompanyException("ID为空");
		}
		// 判断外场编码是否为空
		if (StringUtils.isBlank(entity.getOutfieldCode())) {
					throw new OutfieldTFCompanyException("外场编码为空");
		}
		// 判断外场名称是否为空
		if (StringUtils.isBlank(entity.getOutfieldName())) {
					throw new UserException("外场名称为空");
		}
		// 判断运输财务公司编码是否为空
		if (StringUtils.isBlank(entity.getCompanyCode())) {
					throw new UserException("外场所属运输财务公司编码为空");
		}
		// 判断运输财务公司名称是否为空
		if (StringUtils.isBlank(entity.getCompanyName())) {
					throw new UserException("外场所属运输财务公司名称为空");
		}
		List<String> list = new ArrayList<String>();
		list.add(entity.getId());
		int result = outfieldTFCompanyDao.deleteOutfieldTFCompanyById(list,modifyUser);
		if(result > 0){
			List<OutfieldTFCompanyInfo> infoList = new ArrayList<OutfieldTFCompanyInfo>();
			OutfieldTFCompanyInfo deleteInfo = new OutfieldTFCompanyInfo();
			deleteInfo.setId(entity.getId());
			deleteInfo.setModifyUserCode(modifyUser);
			deleteInfo.setActive(FossConstants.INACTIVE);
			deleteInfo.setModifyTime(new Date());
			deleteInfo.setOptionType(OPTION_DELETE);
			infoList.add(deleteInfo);
		    //作废成功
			int updateResult = outfieldTFCompanyDao.addOutfieldTFCompany(entity,modifyUser);
			//同步信息给ECS
			if(updateResult > 0){
				OutfieldTFCompanyInfo addInfo = this.transFossToEsb(entity, OPTION_ADD);
				infoList.add(addInfo);
			}
			syncOutfieldTFCompanyToECS(infoList);
		    return updateResult;
		}else {
		    return FossConstants.FAILURE;
		}
	}

	/**
	 * <p>
	 * 作废外场与所属运输财务公司关系
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-4-25 上午10:23:21
	 * @param idList
	 *            外场与所属运输财务公司关系信息ID集合
	 * @return
	 * @see
	 */
	@Override
	public int deleteOutfieldTFCompanyById(List<String> idList,
			String modifyUser) {
		if (CollectionUtils.isEmpty(idList)) {
			throw new OutfieldTFCompanyException("ID不允许为空！");
		} else {
			int result = outfieldTFCompanyDao.deleteOutfieldTFCompanyById(
					idList, modifyUser);
			//同步到ECS
			if (result > 0) {
				List<OutfieldTFCompanyInfo> entitys = new ArrayList<OutfieldTFCompanyInfo>();
				for (String id : idList) {
					OutfieldTFCompanyInfo entity = new OutfieldTFCompanyInfo();
					entity.setId(id);
					entity.setActive(FossConstants.INACTIVE);
					entity.setModifyTime(new Date());
					entity.setOptionType(OPTION_DELETE);
					entitys.add(entity);
				}
				syncOutfieldTFCompanyToECS(entitys);
			}
			return result;
		}
	}

	/**
	 * 统计总记录数
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-26 上午10:25:32
	 * @param entity
	 *            外场与所属运输财务公司关系信息实体
	 * @return
	 * @see
	 */
	@Override
	public Long queryRecordCount(OutfieldTFCompanyEntity entity) {
		if (null == entity) {
			throw new OutfieldTFCompanyException("传入的参数不允许为空！");
		} else {
			entity.setActive(FossConstants.ACTIVE);
			return outfieldTFCompanyDao.queryRecordCount(entity);
		}
	}

	/**
	 * <p>
	 * 根据外场编码查询所属运输财务公司名称
	 * </p>
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-11-26 上午10:35:21
	 * @param code
	 *            外场编码
	 * @return
	 * @see
	 */
	@Override
	public String queryCompanyNameByOutfieldCode(String code) {
		if (StringUtils.isBlank(code)) {
			throw new OutfieldTFCompanyException("传入的外场编码不允许为空！");
		} else {
			return outfieldTFCompanyDao.queryCompanyNameByOutfieldCode(code);
		}

	}

	/**
	 * 根据传入对象查询符合条件所有外场与所属运输财务公司关系信息
	 * 
	 * @author 132599-foss-shenweihua
	 * @date 2013-4-25 上午10:24:21
	 * @param entity
	 *            外场与所属运输财务公司关系信息实体
	 * @param limit
	 *            每页最大显示记录数
	 * @param start
	 *            开始记录数
	 * @return 符合条件的实体列表
	 * @see
	 */
	@Override
	public List<OutfieldTFCompanyEntity> queryAllOutfieldTFCompany(
			OutfieldTFCompanyEntity entity, int limit, int start) {
		if (null == entity) {
			throw new OutfieldTFCompanyException("传入的参数不允许为空！");
		} else {
			entity.setActive(FossConstants.ACTIVE);
			return outfieldTFCompanyDao.queryAllOutfieldTFCompany(entity,
					limit, start);
		}
	}

	/**
	 * 同步外场与所属运输财务公司关系给ECS
	 * 
	 * @date 2016/04/06 13:49
	 * @param entitys
	 */
	private void syncOutfieldTFCompanyToECS(OutfieldTFCompanyInfo entity) {
		if (null == entity) {
			return;
		}
		List<OutfieldTFCompanyInfo> list = new ArrayList<OutfieldTFCompanyInfo>();
		list.add(entity);
		syncOutfieldTFCompanyToECS(list);
	}

	/**
	 * 同步外场与所属运输财务公司关系给ECS
	 * 
	 * @date 2016/04/06 13:49
	 * @param entitys
	 */
	private void syncOutfieldTFCompanyToECS(List<OutfieldTFCompanyInfo> entitys) {
		if (CollectionUtils.isEmpty(entitys)) {
			return;
		}
		syncOutfieldTFCompanyService.syncOutfieldTFCompanyToECS(entitys);
	}

	/**
	 * foss对象转ESB
	 * 
	 * @author 313353-foss
	 * @date 2016-4-25 下午4:01:25
	 */
	private OutfieldTFCompanyInfo transFossToEsb(
			OutfieldTFCompanyEntity fossEntity, String optionType) {
		if (fossEntity == null) {
			return null;
		}

		OutfieldTFCompanyInfo esbInfo = new OutfieldTFCompanyInfo();
		esbInfo.setActive(fossEntity.getActive());
		esbInfo.setCompanyCode(fossEntity.getCompanyCode());
		esbInfo.setCompanyName(fossEntity.getCompanyName());
		esbInfo.setCreateTime(fossEntity.getCreateDate());
		esbInfo.setCreateUserCode(fossEntity.getCreateUser());
		esbInfo.setId(fossEntity.getId());
		esbInfo.setModifyTime(fossEntity.getModifyDate());
		esbInfo.setModifyUserCode(fossEntity.getModifyUser());
		esbInfo.setNotes(fossEntity.getNotes());
		esbInfo.setOutfieldCode(fossEntity.getOutfieldCode());
		esbInfo.setOutfieldName(fossEntity.getOutfieldName());
		esbInfo.setOptionType(optionType);
		return esbInfo;
	}
}
