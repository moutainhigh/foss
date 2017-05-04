package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressDeliveryMapManageDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IAdministrativeRegionsService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IEmployeeService;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressDeliveryMapManageService;
import com.deppon.foss.module.base.baseinfo.api.server.service.ISaleDepartmentService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.SaleDepartmentEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressDeliveryMapManageException;
import com.deppon.foss.module.base.baseinfo.api.shared.vo.ExpressDeliveryMapManageVo;
import com.deppon.foss.module.base.dict.api.shared.define.DictionaryValueConstants;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.CollectionUtils;
import com.deppon.foss.util.define.FossConstants;
import com.eos.system.utility.StringUtil;

/**
 * 快递派送电子地图管理Service实现
 * 
 * @author dujunhui-187862
 * @date 2014-12-19-上午10:36:49
 */
public class ExpressDeliveryMapManageService implements
		IExpressDeliveryMapManageService {
	/*
	 * Vo类
	 */
	private ExpressDeliveryMapManageVo expressDeliveryMapManageVo;
	/*
	 * 快递派送电子地图管理Dao
	 */
	private IExpressDeliveryMapManageDao expressDeliveryMapManageDao;
	/*
	 * 行政区域Service
	 */
	private IAdministrativeRegionsService administrativeRegionsService;
	/*
	 * 人员Service
	 */
	private IEmployeeService employeeService;
	/*
	 * 营业部Service
	 */
	private ISaleDepartmentService saleDepartmentService;

	public ExpressDeliveryMapManageVo getExpressDeliveryMapManageVo() {
		return expressDeliveryMapManageVo;
	}

	public void setExpressDeliveryMapManageVo(
			ExpressDeliveryMapManageVo expressDeliveryMapManageVo) {
		this.expressDeliveryMapManageVo = expressDeliveryMapManageVo;
	}

	public void setExpressDeliveryMapManageDao(
			IExpressDeliveryMapManageDao expressDeliveryMapManageDao) {
		this.expressDeliveryMapManageDao = expressDeliveryMapManageDao;
	}

	public void setAdministrativeRegionsService(
			IAdministrativeRegionsService administrativeRegionsService) {
		this.administrativeRegionsService = administrativeRegionsService;
	}

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	public ISaleDepartmentService getSaleDepartmentService() {
		return saleDepartmentService;
	}

	public void setSaleDepartmentService(
			ISaleDepartmentService saleDepartmentService) {
		this.saleDepartmentService = saleDepartmentService;
	}

	/*
	 * 批量查看选中的快递派送电子地图管理信息 2014-12-19-上午10:36:49
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.
	 * IExpressDeliveryMapManageService
	 * #queryExpressDeliveryMapManageBatchView(java.lang.String[])
	 */
	@Override
	public List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageBatchView(
			String[] codes) {
		if (codes.length < 1) {
			throw new ExpressDeliveryMapManageException("请选择你所要查看的营业部！",
					"请选择你所要查看的营业部！");
		}

		ExpressDeliveryMapManageEntity entity = new ExpressDeliveryMapManageEntity();
		entity.setCodeList(codes);
		List<ExpressDeliveryMapManageEntity> list = expressDeliveryMapManageDao
				.queryExpressDeliveryMapManageEntityByCode(entity);

		if (CollectionUtils.isEmpty(list)) {
			throw new ExpressDeliveryMapManageException("数据异常！", "数据异常！");
		}

		List<String> cityCodes = new ArrayList<String>();
		List<String> cityCodeRepeats = new ArrayList<String>();

		// Map<String,String> map=new HashMap<String,String>();
		for (int i = 0; i < list.size(); i++) {
			String cityCode = list.get(i).getCityCode();
			cityCodes.add(cityCode);
			if (StringUtil.isEmpty(cityCode)) {
				throw new ExpressDeliveryMapManageException("所选营业部必须有对应的地级市！",
						"所选营业部必须有对应的地级市！");
			}
			this.transformExpressDeliveryMapManageEntity(list.get(i));

		}
		cityCodeRepeats.add(cityCodes.get(0));
		cityCodes.removeAll(cityCodeRepeats);
		if (cityCodes.size() > 1) {

			throw new ExpressDeliveryMapManageException("请选择同一城市营业部进行查看!",
					"请选择同一城市营业部进行查看");
		}

		return list;

		/*
		 * if(codes.length>1){//数组长度大于1,批量查看至少选中两条 //判断所选多条数据是否属于同一城市
		 * ExpressDeliveryMapManageEntity
		 * firstEntity=this.queryExpressDeliveryMapManageByCode(codes[0]);
		 * String cityCode=""; if(firstEntity!=null){
		 * if(StringUtil.isEmpty(firstEntity.getCityCode())){ throw new
		 * ExpressDeliveryMapManageException
		 * ("所选营业部必须有对应的地级市！","所选营业部必须有对应的地级市！"); }
		 * cityCode=firstEntity.getCityCode();//以第一条数据中城市编码为准 }else{ throw new
		 * ExpressDeliveryMapManageException("所选营业部中有空实体！","所选营业部中有空实体！"); }
		 * 
		 * for(int i=1;i<codes.length;i++){//其他条数据与第一条基准数据作比较
		 * ExpressDeliveryMapManageEntity
		 * nowEntity=this.queryExpressDeliveryMapManageByCode(codes[i]);
		 * if(nowEntity!=null){
		 * if(StringUtil.isNotEmpty(nowEntity.getCityCode())){
		 * //确保所选择的各营业部同一城市营业部进行查看
		 * if(!StringUtil.equals(nowEntity.getCityCode(), cityCode)){ throw new
		 * ExpressDeliveryMapManageException
		 * ("请选择同一城市营业部进行查看!","请选择同一城市营业部进行查看"); } }else{ throw new
		 * ExpressDeliveryMapManageException
		 * ("所选营业部必须有对应的地级市！","所选营业部必须有对应的地级市！"); } }else{ throw new
		 * ExpressDeliveryMapManageException("所选营业部中有空实体！","所选营业部中有空实体！"); } }
		 * 
		 * if(CollectionUtils.isNotEmpty(list)){
		 * for(ExpressDeliveryMapManageEntity
		 * myEntity:list){//封装省市区名称以及申请人和审核人名称
		 * this.transformExpressDeliveryMapManageEntity(myEntity); } return
		 * list; } } return null;
		 */
	}

	/*
	 * 根据salesDepartmentCode查询快递派送电子地图管理信息 2014-12-19-上午10:36:49
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.
	 * IExpressDeliveryMapManageService
	 * #queryExpressDeliveryMapManageBysalesDepartmentCode(java.lang.String)
	 */
	@Override
	public ExpressDeliveryMapManageEntity queryExpressDeliveryMapManageByCode(
			String salesDepartmentCode) {
		if (StringUtil.isNotEmpty(salesDepartmentCode)) {
			ExpressDeliveryMapManageEntity entity = expressDeliveryMapManageDao
					.queryExpressDeliveryMapManageByCode(salesDepartmentCode);
			if (null==entity) {
				return null;
			}
			// 封装实体
			this.transformExpressDeliveryMapManageEntity(entity);
			return entity == null ? null : entity;
		} else {
			return null;
		}
	}

	/*
	 * 根据条件查询快递派送电子地图管理信息 2014-12-19-上午10:36:49
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.
	 * IExpressDeliveryMapManageService
	 * #queryExpressDeliveryMapManageByCondition(
	 * com.deppon.foss.module.base.baseinfo
	 * .api.shared.domain.ExpressDeliveryMapManageEntity, int, int)
	 */
	@Override
	public List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageByCondition(
			ExpressDeliveryMapManageEntity entity, int start, int limit) {
		if (StringUtil.equals(entity.getVerifyState(),
				DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_ALL)) {// 未编辑状态
			entity.setVerifyState(null);
		}
		if(StringUtil.isNotEmpty(entity.getSalesDepartmentCode())){
			String salesCode=entity.getSalesDepartmentCode();
			entity=new ExpressDeliveryMapManageEntity();
			entity.setSalesDepartmentCode(salesCode);
		}
		List<ExpressDeliveryMapManageEntity> entityList = expressDeliveryMapManageDao
				.queryExpressDeliveryMapManageByCondition(entity, start, limit);
		if (CollectionUtils.isNotEmpty(entityList)) {
			// 根据编码封装行政区域和人员,以及地图未编辑状态的设置
			for (ExpressDeliveryMapManageEntity myEntity : entityList) {
				this.transformExpressDeliveryMapManageEntity(myEntity);
			}
			return entityList;
		}
		return null;
	}

	/*
	 * 根据编码封装行政区域和人员,以及地图未编辑状态的设置
	 * 
	 * @author dujunhui-187862
	 * 
	 * @date 2014-12-26 上午9:24:33
	 */
	public void transformExpressDeliveryMapManageEntity(
			ExpressDeliveryMapManageEntity myEntity) {
		if (StringUtil.isNotEmpty(myEntity.getProvCode())) {// 封装省份名称
			myEntity.setProvName(administrativeRegionsService
					.queryAdministrativeRegionsNameByCode(myEntity
							.getProvCode()));
		}
		if (StringUtil.isNotEmpty(myEntity.getCityCode())) {// 封装市名称
			myEntity.setCityName(administrativeRegionsService
					.queryAdministrativeRegionsNameByCode(myEntity
							.getCityCode()));
		}
		if (StringUtil.isNotEmpty(myEntity.getCountyCode())) {// 封装区县名称
			myEntity.setCountyName(administrativeRegionsService
					.queryAdministrativeRegionsNameByCode(myEntity
							.getCountyCode()));
		}
		// 封装行政区域字段，用于列表显示
		myEntity.setDistrictName(myEntity.getProvName() + "-"
				+ myEntity.getCityName() + "-" + myEntity.getCountyName());

		if (StringUtil.isNotEmpty(myEntity.getApplyManCode())) {// 封装申请人名称
			myEntity.setApplyManName(employeeService
					.queryEmpNameByEmpCode(myEntity.getApplyManCode()));
		}
		if (StringUtil.isNotEmpty(myEntity.getVerifyManCode())) {// 封装审核人名称
			myEntity.setVerifyManName(employeeService
					.queryEmpNameByEmpCode(myEntity.getVerifyManCode()));
		}
		// 未编辑和未审核状态的设置，因为这两状态不存在于数据库字段
		// 地图未编辑状态的设置(地图坐标为空且审核人一项为空)
		if (StringUtil.isEmpty(myEntity.getVerifyState())) {
			myEntity.setVerifyState(DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_NOT_EDITED);// 未编辑状态
		}
		/*// 地图未审核状态的设置(地图坐标不为空且审核人一栏为空)
		if ((StringUtil.isNotEmpty(myEntity.getExpressDeliveryCoordinate()))
				&& (StringUtil.isEmpty(myEntity.getVerifyManCode()))) {
			myEntity.setVerifyState(DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_NOT_VERIFIED);// 未审核状态
		}*/
	}

	/*
	 * 统计根据条件查询的快递派送电子地图管理信息条数 2014-12-19-上午10:36:49
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.
	 * IExpressDeliveryMapManageService
	 * #queryExpressDeliveryMapManageCountByCondition
	 * (com.deppon.foss.module.base
	 * .baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity)
	 */
	@Override
	public Long queryExpressDeliveryMapManageCountByCondition(
			ExpressDeliveryMapManageEntity entity) {
		return expressDeliveryMapManageDao
				.queryExpressDeliveryMapManageCountByCondition(entity);
	}

	/*
	 * 根据实体查询快递派送电子地图管理信息用于导出（已作废） 2014-12-19-上午10:36:49
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.
	 * IExpressDeliveryMapManageService
	 * #queryExpressDeliveryMapManageListForExport
	 * (com.deppon.foss.module.base.baseinfo
	 * .api.shared.domain.ExpressDeliveryMapManageEntity)
	 */
	@Override
	public List<ExpressDeliveryMapManageEntity> queryExpressDeliveryMapManageListForExport(
			ExpressDeliveryMapManageEntity entity) {
		return null;
	}

	/*
	 * 审核快递派送区域电子地图
	 * 
	 * @author dujunhui-187862
	 * 
	 * @date 2014-12-30 下午6:15:02
	 */
	@Transactional
	@Override
	public void verifyExpressDeliveryMapManage(String[] codes) {
		if (codes.length <= 0) {
			throw new ExpressDeliveryMapManageException("请选择你所要审核的营业部！");
		}
		
		ExpressDeliveryMapManageEntity param = new ExpressDeliveryMapManageEntity();
		param.setCodeList(codes);
		List<ExpressDeliveryMapManageEntity> entitys = expressDeliveryMapManageDao.queryExpressDeliveryMapManageEntityByCode(param);
		
		if (CollectionUtils.isEmpty(entitys)) {
			throw new ExpressDeliveryMapManageException("数据异常,请联系业务组!");
		}
		for (int i = 0; i < entitys.size(); i++) {
			
			ExpressDeliveryMapManageEntity myEntity = entitys.get(i);
			// 如果不是未审核状态(审核人、审核时间、审核状态为空，派送坐标不为空)，则提醒
			if (!(StringUtil.equals(myEntity.getVerifyState(),DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_NOT_VERIFIED))) {
				throw new ExpressDeliveryMapManageException("请选择“未审核”状态营业部!");
			}
			myEntity.setSalesDepartmentCode(myEntity.getSalesDepartmentCode());
			// 已审核
			myEntity.setVerifyState(DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_IS_VERIFIED);
			myEntity.setVerifyManCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			myEntity.setVerifyTime(new Date());
			myEntity.setVerifyManName(FossUserContext.getCurrentUser().getEmployee().getEmpName());
			myEntity.setCodeList(new String[]{myEntity.getSalesDepartmentCode()});
			this.addSalesDepartmentInfo(myEntity);

		}

	}

	/*
	 * 生效营业部快递派送电子地图 187862-dujunhui 2014-12-31 上午11:23:57
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.
	 * IExpressDeliveryMapManageService
	 * #activateExpressDeliveryMapManage(java.lang.String[])
	 */
	@Transactional
	@Override
	public void activateExpressDeliveryMapManage(String[] codes) {
		
		if(codes.length<=0){
			
			throw new ExpressDeliveryMapManageException("请选择你所要生效的营业部！");
		}
		
		ExpressDeliveryMapManageEntity params = new ExpressDeliveryMapManageEntity();
		params.setCodeList(codes);
		List<ExpressDeliveryMapManageEntity> entitys = expressDeliveryMapManageDao.queryExpressDeliveryMapManageEntityByCode(params);

		if (CollectionUtils.isEmpty(entitys)) {
			throw new ExpressDeliveryMapManageException("数据异常,请联系业务组!");
		}
		 
		for (int i = 0; i < entitys.size(); i++) {

			// 根据编码查询快递派送区域信息实体
			ExpressDeliveryMapManageEntity myEntity = entitys.get(i);
			 
			if (!StringUtil.equals(myEntity.getVerifyState(),DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_IS_VERIFIED)) {
				
				throw new ExpressDeliveryMapManageException("请选择“已审核”状态营业部");
			}
			// 已生效
			myEntity.setVerifyState(DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_IS_ACTIVED);
			myEntity.setCodeList(new String[]{myEntity.getSalesDepartmentCode()});
			ExpressDeliveryMapManageEntity returnEntity = this.addSalesDepartmentInfo(myEntity);

			if (null != returnEntity) {
				SaleDepartmentEntity entity = saleDepartmentService.querySaleDepartmentInfoByCode(myEntity.getSalesDepartmentCode());
//				List<SaleDepartmentEntity> saleDepartmentEntitys =saleDepartmentService.querySaleDepartmentBatchByCode(new String[]{myEntity.getSalesDepartmentCode()});
//				SaleDepartmentEntity entity=saleDepartmentEntitys.get(0);
				entity.setExpressDeliveryCoordinate(returnEntity.getExpressDeliveryCoordinate());
				entity.setDepartServiceArea(returnEntity.getDepartServiceArea());
				entity.setCanExpressDelivery(FossConstants.ACTIVE);
				saleDepartmentService.updateSaleDepartment(entity);
			}
	}
	}

	/*
	 * 退回营业部快递派送电子地图 187862-dujunhui 2014-12-31 下午3:17:23
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.
	 * IExpressDeliveryMapManageService
	 * #turnBackExpressDeliveryMapManage(java.lang.String[])
	 */
	@Transactional
	@Override
	public void turnBackExpressDeliveryMapManage(ExpressDeliveryMapManageVo vo) {
		String[] codes=vo.getCodeList();
		if(codes.length<=0){
			throw new ExpressDeliveryMapManageException("请选择你所要退回的营业部！");
		}
		// 根据编码查询快递派送区域信息实体
		ExpressDeliveryMapManageEntity params = new ExpressDeliveryMapManageEntity();
		params.setCodeList(codes);
		List<ExpressDeliveryMapManageEntity> entitys = expressDeliveryMapManageDao.queryExpressDeliveryMapManageEntityByCode(params);
		
		if (CollectionUtils.isEmpty(entitys)) {
			throw new ExpressDeliveryMapManageException("所选实体为空");
		}
		
		for (int i = 0; i < entitys.size(); i++) {
			 ExpressDeliveryMapManageEntity myEntity = entitys.get(i);
			 if(StringUtil.equal(myEntity.getVerifyState(), DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_NOT_EDITED)||
					 StringUtil.equal(myEntity.getVerifyState(), DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_IS_TURNBACK)){
				 
				 throw new ExpressDeliveryMapManageException("请选择“未审核”状态营业部!");// 按需求文档提示
			 }
			 
			 myEntity.setVerifyTime(new Date());
			 myEntity.setVerifyState(DictionaryValueConstants.EXPRESS_DELIVERY_MAP_MANAGE_IS_TURNBACK);
			 myEntity.setVerifyManCode(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			 myEntity.setVerifyManName(FossUserContext.getCurrentUser().getEmployee().getEmpName());
			 myEntity.setModifyDate(new Date());
			 myEntity.setModifyUser(FossUserContext.getCurrentUser().getEmployee().getEmpCode());
			 myEntity.setCodeList(new String[]{myEntity.getSalesDepartmentCode()});
			 myEntity.setNote(vo.getEntity().getNote());
			 this.addSalesDepartmentInfo(myEntity);
//			 expressDeliveryMapManageDao.turnBackExpressDeliveryMapManage(codes);
		}
	}

	/*
	 * 更新保存GIS派送区域电子地图坐标 187862-dujunhui 2015-1-7 下午3:34:16
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.
	 * IExpressDeliveryMapManageService
	 * #updateExpressDeliveryGISMap(com.deppon.foss
	 * .module.base.baseinfo.api.shared.domain.ExpressDeliveryMapManageEntity)
	 */
	@Override
	public void updateExpressDeliveryGISMap(
			ExpressDeliveryMapManageEntity entity) {
		if (entity != null
				&& StringUtil.isNotEmpty(entity.getSalesDepartmentCode())) {
			// 根据编码查询当前营业部
			SaleDepartmentEntity myEntity = saleDepartmentService
					.querySaleDepartmentByCode(entity.getSalesDepartmentCode());
			// 创建营业部实体对象
			SaleDepartmentEntity updateEntity = new SaleDepartmentEntity();
			// 根据当前Active='Y'和营业部编码查询到的营业部信息myEntity以及审核相关信息复制到刚创建的空对象中
			// 1.复制原始营业部信息
			try {
				PropertyUtils.copyProperties(updateEntity, myEntity);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			// 2.设置审核相关信息
			updateEntity.setVerifyTime(new Date()); // 设置审核时间
			updateEntity.setVerifyManCode(FossUserContext.getCurrentUser()
					.getEmployee().getEmpCode());// 设置审核人工号

			updateEntity.setExpressDeliveryCoordinate(entity
					.getExpressDeliveryCoordinate());// 设置修改后的快递派送区域坐标
			updateEntity.setDepartServiceArea(entity.getDepartServiceArea());// 部门服务面积

			updateEntity.setModifyUser(FossUserContext.getCurrentUser()
					.getEmployee().getEmpCode());// 修改人设置
			if (StringUtil.isNotEmpty(updateEntity
					.getExpressDeliveryCoordinate())) {// 地图坐标不为空
				updateEntity.setVerifyState(null);// 审核状态设置为未审核，审核状态为空
				updateEntity.setVerifyManCode(null);// 未审核状态时审核人为空
				updateEntity.setVerifyTime(null);// 未审核状态时审核时间为空
				updateEntity.setApplyTime(new Date());// 申请时间为当前时间
				updateEntity.setApplyManCode(FossUserContext.getCurrentInfo()
						.getEmpCode());// 申请人为当前用户
			} else {// 地图坐标为空,则为未编辑状态
				updateEntity.setVerifyState(null);// 未编辑状态时审核状态为空
				updateEntity.setVerifyManCode(null);// 未编辑状态时审核人为空
				updateEntity.setVerifyTime(null);// 未编辑状态时审核时间为空
				updateEntity.setApplyTime(null);// 未编辑状态时申请时间为空
				updateEntity.setApplyManCode(null);// 未编辑状态时申请人为空
				updateEntity.setDepartServiceArea(null);// 未编辑状态时服务面积为空
			}
			// 更新保存快递派送区域电子地图操作:调用营业部service
			saleDepartmentService.updateSaleDepartment(updateEntity);
		} else {
			throw new ExpressDeliveryMapManageException("所选营业部实体为空");
		}
	}

	/*
	 * 根据编码作废营业部信息（已作废） 187862-dujunhui 2015-1-7 下午4:37:22
	 * 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.
	 * IExpressDeliveryMapManageService
	 * #voidSalesDepartmentInfo(java.lang.String[])
	 */
	@Override
	public void voidSalesDepartmentInfo(String[] codes) {
		expressDeliveryMapManageDao.voidSalesDepartmentInfo(codes);
	}

	/**
	 * ------------------------------------------------------------------------
	 * ---------------------------------------
	 **/

	/**
	 * 新增快递派送区域地图信息
	 */
	@Override
	public ExpressDeliveryMapManageEntity addSalesDepartmentInfo(ExpressDeliveryMapManageEntity updateEntity) {

		List<ExpressDeliveryMapManageEntity> entitys = expressDeliveryMapManageDao.queryExpressDeliveryMapManageEntityByCode(updateEntity);
		if (!CollectionUtils.isEmpty(entitys)) {
			expressDeliveryMapManageDao.deleteExpressDeliveryMapManageEntityById(entitys.get(0));
		}
		return expressDeliveryMapManageDao.addSalesDepartmentInfo(updateEntity);
	}

	/**
	 * 根据Code查询派送区域地图信息
	 */
	@Override
	public ExpressDeliveryMapManageEntity queryExpressDeliveryMapManageEntityByCode(
			String orgCode) {
		ExpressDeliveryMapManageEntity updateEntity = new ExpressDeliveryMapManageEntity();
		updateEntity.setCodeList(new String[]{orgCode});
		List<ExpressDeliveryMapManageEntity> queryEntitys=expressDeliveryMapManageDao.queryExpressDeliveryMapManageEntityByCode(updateEntity);
		return CollectionUtils.isEmpty(queryEntitys)?null:queryEntitys.get(0);
	}

	/**
	 * 作废地图信息
	 */
	@Override
	public long deleteExpressDeliveryMapManageEntityById(ExpressDeliveryMapManageEntity updateEntity) {
		updateEntity.setCodeList(new String[]{updateEntity.getSalesDepartmentCode()});
		List<ExpressDeliveryMapManageEntity> entitys = expressDeliveryMapManageDao.queryExpressDeliveryMapManageEntityByCode(updateEntity);
		if(CollectionUtils.isEmpty(entitys)){
			throw new ExpressDeliveryMapManageException("数据异常!");
		}
		return expressDeliveryMapManageDao.deleteExpressDeliveryMapManageEntityById(entitys.get(0));
	}

}
