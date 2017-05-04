package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.deppon.foss.base.util.define.NumberConstants;
import com.deppon.foss.framework.server.context.UserContext;
import com.deppon.foss.module.base.baseinfo.api.server.dao.IExpressAutoComplementManageDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IExpressAutoComplementManageService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.ExpressAutoComplementManageEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.ExpressAutoComplementManageException;
import com.deppon.foss.util.UUIDUtils;

/**
 * 
 * @author 218392 zhangyongxue
 * @date 2015-05-18 10:13:36
 * 快递自动补吗管理Service
 *
 */
public class ExpressAutoComplementManageService implements IExpressAutoComplementManageService{
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TemporaryRentalCarMarkTimeManagementService.class);
	
	/**
	 * 注入快递自动补码管理Dao
	 */
	private IExpressAutoComplementManageDao expressAutoComplementManageDao;

	/**
	 * set注入设置快递自动补码expressAutoComplementManageDao接口
	 * @param expressAutoComplementManageDao
	 */
	public void setExpressAutoComplementManageDao(
			IExpressAutoComplementManageDao expressAutoComplementManageDao) {
		this.expressAutoComplementManageDao = expressAutoComplementManageDao;
	}

	/**
	 * 新增快递自动补码管理信息
	 */
	@Override
	public int addExpressAutoComplementManage(
			ExpressAutoComplementManageEntity expressAutoComplementManageEntity) {
		if(null == expressAutoComplementManageEntity){
			throw new ExpressAutoComplementManageException("传入的参数不允许为空!");
		}
		//判断城市是否已经存在，要保证城市唯一
		Long num = expressAutoComplementManageDao.queryCountByCityCode(expressAutoComplementManageEntity);
		if(num > 0){
			throw new ExpressAutoComplementManageException("城市已经存在，请重新选择！");
		}
		//第一次记录新增时，虚拟编码为记录的id
		expressAutoComplementManageEntity.setId(UUIDUtils.getUUID());
		
		UserEntity user = (UserEntity)UserContext.getCurrentUser();
		String userCode = user.getEmployee().getEmpCode();
		String userName = user.getEmployee().getEmpName();
		
		expressAutoComplementManageEntity.setCreateUser(userCode);
		expressAutoComplementManageEntity.setCreateUserName(userName);
		expressAutoComplementManageEntity.setCreateDate(new Date());
		expressAutoComplementManageEntity.setModifyDate(new Date(NumberConstants.ENDTIME));
		//打印日志
		LOGGER.debug("id: " + expressAutoComplementManageEntity.getId());
		
		return expressAutoComplementManageDao.addExpressAutoComplementManageDao(expressAutoComplementManageEntity);
	}

	/**
	 * 查询快递自动补码信息
	 */
	@Override
	public List<ExpressAutoComplementManageEntity> queryExpressAutoComplementManage(
			ExpressAutoComplementManageEntity entity, int limit, int start) {
		//往后台查询的时候，也有可能什么参数都不传，所以，这里判断传入的实体类是否为空，无意义
		/*if(entity == null){
			throw new ExpressAutoComplementManageException("传入的参数不允许为空!");  
		}else{*/
		List<ExpressAutoComplementManageEntity> entityList =
			 expressAutoComplementManageDao.queryAllExpressAutoComplementManage(entity, limit, start);
		for(ExpressAutoComplementManageEntity entitys : entityList){
			/**
			 * @author 218392  zhangyongxue
			 * @date 2015-05-22 15:28:06
			 * 该处理是为了“前台Grid中显示的操作时间，操作人，工号---但是后台却是分别对应的两种比如操作人对应后台创建人和修改人，这种该如何往前台显示”
			 * 
			 *查询出的数据中，如果该条数据修改人工号为空，说明该条数据为新增的，还未被修改，那么将创建时间，创建人name，创建人工号分别赋给
			 *操作时间，操作人name，操作人工号；
			 *反之将修改人name，修改人工号，赋给操作人name，操作工号；但是操作时间依然是创建时间：因为新的要求是创建一条记录的时候，创建时间为系统时间，修改时间为
			 *“2999-12-31 23:59:59”,当对这个记录进行修改的时候，先将原来的记录进行逻辑上作废，然后再新增。（所以修改的本质还是新增，在新增的时候创建时间为当前系统时间，
			 *修改时间为2999-12-31 23:59:59）
			 *我本次的需求往后的时间传送是这样的： 因为我不涉及逻辑删除和物理删除，只有update操作，所以我往后台传送的时间是这样---第一次新增的时候创建时间为当前系统时间，修改时间
			 *为2999-12-31 23:59:59；第二次对这个条数据进行修改（比如开启，关闭，备注修改的时候），我只需将创建时间改成修改动作的当前时间，而修改时间不变，前台grid中显示的
			 *操作时间永远是创建时间即可！
			 * 
			 */
			if(entitys.getModifyUser() == null){
				entitys.setOperationTime(entitys.getCreateDate());
				entitys.setOperator(entitys.getCreateUserName());
				entitys.setJobNumber(entitys.getCreateUser());
			}else{
				entitys.setOperationTime(entitys.getCreateDate());
				entitys.setOperator(entitys.getModifyUserName());
				entitys.setJobNumber(entitys.getModifyUser());
			}
		}
		return entityList;
		//}
	}
	
	/** 
	 * 给中转的接口，传入城市编码，返回城市状态，1为关闭，2为静默，3位开启，如果没有该城市记录、或者该城市状态为空，返回1。
	 * @author 232607 
	 * @date 2015-7-23 下午8:14:22
	 * @param cityCode
	 * @param status
	 * @return 
	 * @see com.deppon.foss.module.base.baseinfo.api.server.service.IExpressAutoComplementManageService#queryListCityCodeByCodeStatus(java.lang.String, java.lang.String)
	 */
	@Override
	public String queryListCityCodeByCodeStatus(String cityCode) {
		ExpressAutoComplementManageEntity entity = new ExpressAutoComplementManageEntity();
		if(!StringUtils.isBlank(cityCode)){
			entity.setCityCode(cityCode);
		}
		String result=expressAutoComplementManageDao.queryListCityCodeByCodeStatus(entity);
		if(StringUtils.isBlank(result)){
			result="1";
		}
		return result;
	}

	/**
	 * 统计记录数
	 */
	@Override
	public Long queryCount(ExpressAutoComplementManageEntity entity) {
		return expressAutoComplementManageDao.queryCount(entity);
		
	}

	/**
	 * 开启自动补码管理信息
	 */
	@Override
	public int openExpressAutoComplementManage(
			List<String> idList) {
		if(CollectionUtils.isEmpty(idList)){
			throw new ExpressAutoComplementManageException("虚拟编码不允许为空!");
		}
//		else if(idList.size() > 1){
//			throw new ExpressAutoComplementManageException("只能开启一条数据，请重新选择！");
//		}
		//根据id，查询数据库中该条数据的状态是否开启，如果已经开启了，则本次开启操作将不允许
//		String singleId = idList.get(0);
//		ExpressAutoComplementManageEntity entity = new ExpressAutoComplementManageEntity();
//		entity.setId(singleId);
//		entity.setStatus("3");
//		Long num = expressAutoComplementManageDao.queryCount(entity);
//		if(num > 0){
//			throw new ExpressAutoComplementManageException("该数据已经开启，重新选择!");
//		}
		return expressAutoComplementManageDao.openExpressAutoComplementManage(idList);
	}

	/**
	 * 关闭自动补码管理信息
	 */
	@Override
	public int closeExpressAutoComplementManage(
			List<String> idList) {
		if(CollectionUtils.isEmpty(idList)){
			throw new ExpressAutoComplementManageException("虚拟编码不允许为空!");
		}
//		else if(idList.size() > 1){
//			throw new ExpressAutoComplementManageException("只能关闭一条数据，请重新选择！");
//		}
		//根据id，查询数据库中该条数据的状态是否关闭，如果已经关闭了，则本次关闭操作将不允许
//		String singleId = idList.get(0);
//		ExpressAutoComplementManageEntity entity = new ExpressAutoComplementManageEntity();
//		entity.setId(singleId);
//		entity.setStatus("1");
//		Long num = expressAutoComplementManageDao.queryCount(entity);
//		if(num > 0){
//			throw new ExpressAutoComplementManageException("该数据已经关闭，重新选择!");
//		}
		//如果该条数据的状态没有关闭，则执行关闭操作
		return expressAutoComplementManageDao.closeExpressAutoComplementManage(idList);
	}
	/**
	 * 静默操作
	 */
	@Override
	public int silentExpressAutoComplementManage(List<String> idList) {
		if(CollectionUtils.isEmpty(idList)){
			throw new ExpressAutoComplementManageException("虚拟编码不允许为空!");
		}
//		else if(idList.size() > 1){
//			throw new ExpressAutoComplementManageException("只能静默一条数据，请重新选择！");
//		}
		//根据id，查询数据库中该条数据的状态是否关闭，如果已经关闭了，则本次关闭操作将不允许
//		String singleId = idList.get(0);
//		ExpressAutoComplementManageEntity entity = new ExpressAutoComplementManageEntity();
//		entity.setId(singleId);
//		entity.setStatus("2");
//		Long num = expressAutoComplementManageDao.queryCount(entity);
//		if(num > 0){
//			throw new ExpressAutoComplementManageException("该数据已经静默，重新选择!");
//		}
		//如果该条数据的状态不是静默，则执行静默操作
		return expressAutoComplementManageDao.silentExpressAutoComplementManage(idList);
	}

	@Override
	public int updateExpressAutoComplementManage(
			ExpressAutoComplementManageEntity entity) {
		if(null == entity){
			throw new ExpressAutoComplementManageException("修改备注信息传入参数不允许为空！");
		}else if(StringUtils.isBlank(entity.getId())){
			throw new ExpressAutoComplementManageException("ID不允许为空!");
		}
		return expressAutoComplementManageDao.updateExpressAutoComplementManage(entity);
	}

	/**
	 * 根据城市编码查询数据库中是否有该条记录,供前台判别城市唯一使用
	 */
	@Override
	public Long queryCountbyCityCode(ExpressAutoComplementManageEntity entity) {
		return expressAutoComplementManageDao.queryCountByCityCode(entity);
	}


}
