package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressBranchSalesDeptDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressBranchSalesDeptService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressBranchSalesDeptEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressBranchSalesDeptException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressBranchSalesDeptVo;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
import com.deppon.foss.util.CollectionUtils;

/**
 * 快递分部营业部映射关系Service实现
 * 
 * @author foss-WeiXing
 * @date 2014-9-23 上午10:14:43
 */
public class ExpressBranchSalesDeptService implements
		IExpressBranchSalesDeptService {
	private IExpressBranchSalesDeptDao expressBranchSalesDeptDao;
	// 组织的service
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;

	public void setExpressBranchSalesDeptDao(
			IExpressBranchSalesDeptDao expressBranchSalesDeptDao) {
		this.expressBranchSalesDeptDao = expressBranchSalesDeptDao;
	}

	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * <P>
	 * 添加实体
	 * <P>
	 * 
	 * @author :WeiXing
	 * @date : 2014-9-23下午1:59:46
	 * @param entity
	 * @return
	 * 
	 */
	@Override
	@Transactional
	public ExpressBranchSalesDeptEntity addExpressBranchSales(
			ExpressBranchSalesDeptEntity entity) {
		// 非空检查
		if (null == entity || StringUtils.isBlank(entity.getSalesDeptCode())
				|| StringUtils.isBlank(entity.getExpressBranchCode())) {
			throw new ExpressBranchSalesDeptException("传入的参数为空");
		}
		//查询是否已经存在该快递分部编码
		ExpressBranchSalesDeptEntity resultEntity = expressBranchSalesDeptDao
				.queryExpressBranchSalesbyExpressBranchSalesDeptCode(entity
						.getExpressBranchCode(),entity.getSalesDeptCode());
		if (null != resultEntity) {
			throw new ExpressBranchSalesDeptException("已经存在该快递分部对应的营业部关系了，无法新增");
		}
		// 给编码加上名称
		this.attachDeptName(entity);
		// 执行新增操作
		return expressBranchSalesDeptDao.addExpressBranchSales(entity);
	}

	/**
	 * <P>
	 * 给部门设置部门名称
	 * <P>
	 * 
	 * @author :WeiXing
	 * @date : 2014-9-23下午2:24:36
	 * @param entity
	 */
	private ExpressBranchSalesDeptEntity attachDeptName(
			ExpressBranchSalesDeptEntity entity) {
		if (null == entity) {
			return null;
		}
		// 若营业部编码不为空,设置营业部名称
		if (StringUtils.isNotBlank(entity.getSalesDeptCode())) {
			entity.setSalesDeptName(orgAdministrativeInfoService
					.queryOrgAdministrativeInfoNameByCode(entity
							.getSalesDeptCode()));
		}
		return entity;
	}

	/**
	 * <P>根据id 作废<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午2:37:15
	 * @param entity
	 * @return
	 */
	@Override
	@Transactional
	public int deleteExpressBranchSalesById(ExpressBranchSalesDeptEntity entity) {
		if (null == entity || StringUtils.isBlank(entity.getId())) {
			throw new ExpressBranchSalesDeptException("传入的参数ID为空");
		}
		return expressBranchSalesDeptDao.deleteExpressBranchSalesById(entity);
	}
	/**
	 * 
	 *<P>批量作废<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午6:10:32
	 * @param idList
	 * @param modifyUser
	 * @return
	 */
	@Override
	public int deleteExpressBranchSalesByIdList(List<String> idList,
			CurrentInfo modifyUser) {
		if(CollectionUtils.isEmpty(idList)||null ==modifyUser){
			throw new ExpressBranchSalesDeptException("传入的参数为空");
		}
		int count =0;
		for (String id : idList) {
			ExpressBranchSalesDeptEntity entity =new ExpressBranchSalesDeptEntity();
			entity.setId(id);
			entity.setModifyUser(modifyUser.getEmpCode());
			//作废
			count +=this.deleteExpressBranchSalesById(entity);
		}
		return count==idList.size()?NumberConstants.ONE:NumberConstants.ZERO;
	}
	/**
	 *<P>根据快递分部编码、营业部编码作废实体<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午2:39:24
	 * @param entity
	 * @return
	 */
	@Transactional
	@Override
	public ExpressBranchSalesDeptEntity deleteExpressBranchByExpressBranchCodeAndSalesCode(
			ExpressBranchSalesDeptEntity entity) {
		//两个编码都不能为空
		if(null ==entity ||StringUtils.isBlank(entity.getSalesDeptCode())||StringUtils.isBlank(entity.getExpressBranchCode())){
			throw new ExpressBranchSalesDeptException("传入的参数为空");
		}
		return expressBranchSalesDeptDao.deleteExpressBranchSales(entity);
	}
	
	/**
	 * <P>
	 * 分页查询
	 * <P>
	 * 
	 * @author :WeiXing
	 * @date : 2014-9-23下午2:54:26
	 * @param entity
	 * @param start
	 * @param limit
	 * @return
	 */
	@Override
	public List<ExpressBranchSalesDeptEntity> queryExpressBranchSalesList(
			ExpressBranchSalesDeptEntity entity, int start, int limit) {
		if(null == entity){
			entity =new ExpressBranchSalesDeptEntity();
		}
		return expressBranchSalesDeptDao.queryExpressBranchSalesList(entity,
				start, limit);
	}

	/**
	 * <P>
	 * 查询总数
	 * <P>
	 * 
	 * @author :WeiXing
	 * @date : 2014-9-23下午2:59:09
	 * @param entity
	 * @return
	 */
	@Override
	public long queryExpressBranchSalesCount(ExpressBranchSalesDeptEntity entity) {
		return expressBranchSalesDeptDao.queryExpressBranchSalesCount(entity);
	}

	/**
	 * <P>
	 * 根据营业部编码查询实体列表
	 * <P>
	 * 
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:00:39
	 * @param entity
	 * @return
	 */
	@Override
	public List<ExpressBranchSalesDeptEntity> queryExpressBranchSalesBySalesCode(
			String salesDeptcode) {
		if (StringUtils.isBlank(salesDeptcode)) {
			throw new ExpressBranchSalesDeptException("营业部编码为空");
		}
		return expressBranchSalesDeptDao
				.queryExpressBranchSalesListbySalesCode(salesDeptcode);
	}

	/**
	 * <P>
	 * 根据快递分部编码查询
	 * <P>
	 * 
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:05:25
	 * @param expressBranchCode
	 * @return
	 */
	@Override
	public ExpressBranchSalesDeptEntity queryExpressBranchsalesByExpressBranchCode(
			String expressBranchCode) {
		if (StringUtils.isBlank(expressBranchCode)) {
			throw new ExpressBranchSalesDeptException("快递分部编码为空");
		}
		return expressBranchSalesDeptDao
				.queryExpressBranchSalesbyExpressBranchCode(expressBranchCode);
	}
	
	/**
	 * <P>
	 * 根据快递分部编码,营业部编码查询
	 * <P>
	 * 
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:05:25
	 * @param expressBranchCode,salesDeptCode
	 * @return
	 */
	@Override
	public ExpressBranchSalesDeptEntity queryExpressBranchsalesByExpressBranchSalesDeptCode(
			String expressBranchCode,String salesDeptCode) {
		if (StringUtils.isBlank(expressBranchCode)) {
			throw new ExpressBranchSalesDeptException("快递分部编码为空");
		}
		return expressBranchSalesDeptDao
				.queryExpressBranchSalesbyExpressBranchSalesDeptCode(expressBranchCode,salesDeptCode);
	}
	
	/**
	 * <P>
	 * 根据快递分部编码或营业部编码查询
	 * <P>
	 * 
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:05:25
	 * @param ExpressBranchSalesDeptEntity entity
	 * @return
	 */
	@Override
	public ExpressBranchSalesDeptEntity queryByExpressBranchSalesDeptCode(ExpressBranchSalesDeptEntity entity) {
		if (entity==null) {
			throw new ExpressBranchSalesDeptException("传入的实体为NULL");
		}
		if (StringUtils.isEmpty(entity.getExpressBranchCode())&&StringUtils.isEmpty(entity.getSalesDeptCode())) {
			throw new ExpressBranchSalesDeptException("快递分部和营业部编码不能同时为空值");
		}
		return expressBranchSalesDeptDao
				.queryByExpressBranchSalesDeptCode(entity);
	}
	
	/**
	 *<P>新增实体列表<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午7:10:22
	 * @param addDeptEntities
	 * @param currentInfo
	 */
	@Override
	public List<ExpressBranchSalesDeptEntity> addExpressBranchSalesList(
			List<ExpressBranchSalesDeptEntity> addDeptEntities,
			String modifyUser) {
		if(CollectionUtils.isEmpty(addDeptEntities)){
			throw new ExpressBranchSalesDeptException("新增实体列表为空");
		}
		List<ExpressBranchSalesDeptEntity> resultList =new ArrayList<ExpressBranchSalesDeptEntity>();
		for (ExpressBranchSalesDeptEntity expressBranchSalesDeptEntity : addDeptEntities) {
			expressBranchSalesDeptEntity.setCreateUser(modifyUser);
			//执行新增操作
			resultList.add(this.addExpressBranchSales(expressBranchSalesDeptEntity));
		}
		return resultList;
	}
	/**
	 *<P>修改映射关系列表<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午2:14:39
	 * @param vo
	 * @param modifyUser
	 * @return
	 */
	@Transactional
	@Override
	public List<ExpressBranchSalesDeptEntity> updateExpressBranchSalesList(
			ExpressBranchSalesDeptVo vo, String modifyUser) {
		if(null ==vo){
			return null;
		}
		List<ExpressBranchSalesDeptEntity> resultList =new ArrayList<ExpressBranchSalesDeptEntity>();
		//先新增
		//若新增列表不为空
		if(CollectionUtils.isNotEmpty(vo.getAddExpressBranchSalesList())){
			resultList.addAll(this.addExpressBranchSalesList(vo.getAddExpressBranchSalesList(), modifyUser));
		}
		//再作废（作废列表）
		if(CollectionUtils.isNotEmpty(vo.getDeleteExpressBranchSalesList())){
			//循环作废
			for (ExpressBranchSalesDeptEntity deleteEntity : vo.getDeleteExpressBranchSalesList()) {
				deleteEntity.setModifyUser(modifyUser);
				resultList.add(this.deleteExpressBranchByExpressBranchCodeAndSalesCode(deleteEntity));
			}
		}
		return resultList;
	}
	//*******提供给行政组织中使用的*********
	/**
	 *<P>根据快递分编码作废<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:38:31
	 * @param entity
	 * @return
	 */
	@Override
	public ExpressBranchSalesDeptEntity deleteExpressBranchByExpressBranchCode(
			ExpressBranchSalesDeptEntity entity) {
		//快递分部编码都不能为空
		if(null ==entity||StringUtils.isBlank(entity.getExpressBranchCode())){
			throw new ExpressBranchSalesDeptException("传入的参数为空");
		}
		return expressBranchSalesDeptDao.deleteExpressBranchSales(entity);
	}
	/**
	 *<P>根据营业部编码作废<P>
	 * @author :WeiXing
	 * @date : 2014-9-23下午3:38:31
	 * @param entity
	 * @return
	 */
	@Override
	public ExpressBranchSalesDeptEntity deleteExpressBranchBySalesCode(
			ExpressBranchSalesDeptEntity entity) {
		//营业部编码都不能为空
		if(null ==entity ||StringUtils.isBlank(entity.getSalesDeptCode())){
			throw new ExpressBranchSalesDeptException("传入的参数为空");
		}
		return expressBranchSalesDeptDao.deleteExpressBranchSales(entity);
	}
}
