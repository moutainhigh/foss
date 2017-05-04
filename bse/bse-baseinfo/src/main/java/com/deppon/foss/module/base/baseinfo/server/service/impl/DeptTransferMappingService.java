package com.deppon.foss.module.base.baseinfo.server.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.dao.IDeptTransferMappingDao;
import com.deppon.foss.module.base.baseinfo.api.server.service.IDeptTransferMappingService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.DeptTransferMappingEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.UserEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.DeptTransferMappingDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.DeptTransferMappingException;
import com.deppon.foss.module.frameworkimpl.server.context.FossUserContext;
import com.deppon.foss.util.UUIDUtils;

/**
 * 营业部交接映射管理-service实现类
 * @author 273296
 *
 */
public class DeptTransferMappingService implements IDeptTransferMappingService{

	private IDeptTransferMappingDao deptTransferMappingDao;
	
	public void setDeptTransferMappingDao(
			IDeptTransferMappingDao deptTransferMappingDao) {
		this.deptTransferMappingDao = deptTransferMappingDao;
	}
	
	/**
	 * 根据部门code查询是否外场
	 *273296
	 * @param vo
	 * @return
	 */
	@Override
	public String findOutFieldByCode(String deptCode) throws DeptTransferMappingException{
		return deptTransferMappingDao.findOutFieldByCode(deptCode);
	}

	/**
	 *新增 营业部 交接映射管理
	 *273296
	 * @param vo
	 * @return
	 * @throws DeptTransferMappingException 
	 */
	@Override
	@Transactional
	public void addDeptTransferMapping(
			List<DeptTransferMappingEntity> deptTransferMappingList) throws DeptTransferMappingException {
		if(CollectionUtils.isEmpty(deptTransferMappingList)){
			throw new DeptTransferMappingException("数据为空");
			
		}
		 UserEntity user =  FossUserContext.getCurrentUser();// 获取当前登录用户
		  String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
		  String userName=user.getEmployee().getEmpName();
		  
		  SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		  if(deptTransferMappingList.size()==1){
			  addDeptTransferMapping(deptTransferMappingList.get(0),userCode,userName,format);
			  return;
		  }
		for(int i=0;i<deptTransferMappingList.size();i++){
			DeptTransferMappingEntity entity=deptTransferMappingList.get(i);
			if(entity.getSecNetworkCode().equals("")){
				continue;
			}
			addDeptTransferMapping(entity,userCode,userName,format);
		}
		
	}

	/**
	 * 查询营业部 交接映射管理数据
	 *273296
	 * @return
	 */
	@Override
	public List<DeptTransferMappingEntity> queryDeptTransferMappingList(
			DeptTransferMappingDto vo,int start, int limit) throws DeptTransferMappingException {
		DeptTransferMappingEntity entity=new DeptTransferMappingEntity();
		entity.setDeptCode(vo.getDeptCode());
		entity.setIsOutfield(vo.getIsOutfield());
		entity.setFthNetworkCode(vo.getFthNetworkCode());
		return deptTransferMappingDao.queryDeptTransferMappingList(entity,start, limit);
	}

	/**
	 * 查询营业部 交接映射管理数据总数
	 *273296
	 * @return
	 */
	@Override
	public long queryDeptTransferMappingCount(DeptTransferMappingDto vo) throws DeptTransferMappingException {
		DeptTransferMappingEntity entity=new DeptTransferMappingEntity();
		entity.setDeptCode(vo.getDeptCode());
		entity.setIsOutfield(vo.getIsOutfield());
		entity.setFthNetworkCode(vo.getFthNetworkCode());
		return deptTransferMappingDao.queryDeptTransferMappingCount(entity);
	}

	/**
	 * 作废营业部 交接映射管理数据
	 *273296
	 * @return
	 */
	@Override
	public long deleteDeptTransferMappingById(String id) throws DeptTransferMappingException{
		return deptTransferMappingDao.deleteDeptTransferMappingById(id);
	}

	/**
	 * 批量作废营业部 交接映射管理数据
	 *273296
	 * @return
	 */
	@Override
	@Transactional
	public long deleteDeptTransferMappingsByIdList(List<String> idList) throws DeptTransferMappingException{
		if(CollectionUtils.isEmpty(idList)){
			throw new DeptTransferMappingException("未选择任何记录");
		}
		for(int i=0;i<idList.size();i++){
			DeptTransferMappingEntity entity=deptTransferMappingDao.findById(idList.get(i));
			deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode(entity);
		}
		return idList.size();
	}

	/**
	 *修改 营业部 交接映射管理
	 *273296
	 * @param vo
	 * @return
	 */
	@Override
	@Transactional
	public void updateDeptTransferMapping(DeptTransferMappingDto vo) throws DeptTransferMappingException{
		if(vo==null){
			throw new DeptTransferMappingException("参数错误");
		}
		UserEntity user =  FossUserContext.getCurrentUser();// 获取当前登录用户
		  String userCode = user.getEmployee().getEmpCode();// 当前登录用户empcode
		  String userName=user.getEmployee().getEmpName();
		  SimpleDateFormat format=new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		List<DeptTransferMappingEntity> updates=vo.getDeptTransferMappingList();
		if(CollectionUtils.isNotEmpty(updates)){
			DeptTransferMappingEntity selectEntity=new DeptTransferMappingEntity();
			selectEntity.setDeptCode(updates.get(0).getDeptCode());
			selectEntity.setFthNetworkCode(updates.get(0).getFthNetworkCode());
			List<DeptTransferMappingEntity>  selectList=deptTransferMappingDao.queryDeptTransferMappingListByDeptCodeAndFthNetwork(selectEntity);
			if(CollectionUtils.isNotEmpty(selectList)&&selectList.size()>0){
				for(int i=0;i<selectList.size();i++){
					DeptTransferMappingEntity entity=selectList.get(i);
					if(StringUtils.isEmpty(entity.getSecNetworkCode())){
						deptTransferMappingDao.deleteDeptTransferMappingById(entity.getId());
					}
				}
			}
			for(int i=0;i<updates.size();i++){
				if(StringUtils.isEmpty(updates.get(i).getSecNetworkCode())){
					continue;
				}
				long count=deptTransferMappingDao.queryDeptTransferMappingCount(updates.get(i));
				if(count>0){
					DeptTransferMappingEntity entity=updates.get(i);
					entity.setCreateUserCode(userCode);
					entity.setCreateUserName(userName);
					entity.setUpdateUserCode(userCode);
					entity.setUpdateUserName(userName);
					entity.setModifyTime(format.format(new Date()));
					entity.setCreateTime(format.format(new Date()));
					entity.setActive("Y");
					entity.setSecNetworkCode(updates.get(i).getSecNetworkCode());
					entity.setSecNetworkName(updates.get(i).getSecNetworkName());
					deptTransferMappingDao.updateDeptTransferMapping(entity);
				}else{
					addDeptTransferMapping(updates.get(i),userCode,userName,format);
				}
			}
		}
		
	}

	//根据对接营业部和一级网点作废数据
	private void deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode(
			DeptTransferMappingEntity entity) throws DeptTransferMappingException{
		DeptTransferMappingEntity deleteEntity=new DeptTransferMappingEntity();
		deleteEntity.setDeptCode(entity.getDeptCode());
		deleteEntity.setFthNetworkCode(entity.getFthNetworkCode());
		deleteEntity.setActive("N");
		deptTransferMappingDao.deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode(deleteEntity);
	}

	//新增营业部交接映射
	private void addDeptTransferMapping(
			DeptTransferMappingEntity entity, String userCode, String userName, SimpleDateFormat format)
					throws DeptTransferMappingException{
		entity.setId(UUIDUtils.getUUID());
		entity.setCreateUserCode(userCode);
		entity.setCreateUserName(userName);
		entity.setUpdateUserCode(userCode);
		entity.setUpdateUserName(userName);
		entity.setModifyTime(format.format(new Date()));
		entity.setCreateTime(format.format(new Date()));
		entity.setActive("Y");
		deptTransferMappingDao.saveDeptTransferMappingEntity(entity);
		
	}

	/**
	 * 根据部门code,一级网点查找记录 
	 *273296
	 * @param vo
	 * @return
	 */
	@Override
	public List<DeptTransferMappingEntity> queryDeptTransferMappingListByDeptCodeAndFthNetwork(
			DeptTransferMappingDto vo) throws DeptTransferMappingException{
		DeptTransferMappingEntity entity=new DeptTransferMappingEntity();
		entity.setDeptCode(vo.getDeptCode());
		entity.setFthNetworkCode(vo.getFthNetworkCode());
		entity.setActive("Y");
		return deptTransferMappingDao.queryDeptTransferMappingListByDeptCodeAndFthNetwork(entity);
	}

	/**
	 *  根据营业部和一级网点作废记录
	 *273296
	 * @return
	 */
	@Override
	@Transactional
	public long deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode(
			DeptTransferMappingDto vo) throws DeptTransferMappingException{
		DeptTransferMappingEntity entity=new DeptTransferMappingEntity();
		entity.setDeptCode(vo.getDeptCode());
		entity.setFthNetworkCode(vo.getFthNetworkCode());
		entity.setActive("N");
		return deptTransferMappingDao.deleteDeptTransferMappingsByDeptCodeAndFthNetworkCode(entity);
	}

	/**
	 * 查询是否存在二级网点
	 *273296
	 * @return
	 */
	@Override
	public long queryDeptTransferMappingModelBySecNetworkCode(
			String secNetworkCode) throws DeptTransferMappingException{
		if(StringUtils.isEmpty(secNetworkCode)){
			throw new DeptTransferMappingException("未选择二级网点");
		}
		return deptTransferMappingDao.queryDeptTransferMappingModelBySecNetworkCode(secNetworkCode);
	}

	/**
	 * 根据code 查询数据的接口
	 *273296
	 * @return
	 */
	@Override
	public List<DeptTransferMappingEntity> queryDeptTransferMappingListByCode(
			String code) throws DeptTransferMappingException{
		if(code==null||"".equals(code)){
			throw new DeptTransferMappingException("部门编码不能为空");
		}
		return deptTransferMappingDao.queryDeptTransferMappingListByCode(code);
	}


	/**
	 * 查询是否存在一级网点
	 *273296
	 * @return
	 */
	@Override
	public long queryDeptTransferMappingModelByFthNetworkCode(
			String fthNetworkCode) throws DeptTransferMappingException{
		if(StringUtils.isEmpty(fthNetworkCode)){
			throw new DeptTransferMappingException("未选择一级网点");
		}
		return deptTransferMappingDao.queryDeptTransferMappingModelByFthNetworkCode(fthNetworkCode);
	}

	/**
	 * 根据code查询 部门名称
	 * 查询规则：
	 * 1。二级编码 并且是外场，返回 一级名称
	 * 2.一级编码或者是二级编码并且不是外场，返回对接部门名称
	 *273296
	 * @param code
	 * @return
	 */
	@Override
	public String queryDeptDeptTransferForNameByCode(String code) {
		if(StringUtils.isEmpty(code)){
			throw new DeptTransferMappingException("部门编码为空！");
		}
		//验证是否一级网点
		List<DeptTransferMappingEntity> entitys=deptTransferMappingDao.queryDeptTransferMappingByFthNetworkCode(code);
		if(CollectionUtils.isEmpty(entitys)){
			//验证是否二级网点
			DeptTransferMappingEntity entity= deptTransferMappingDao.queryDeptTransferMappingBySecNetworkCode(code);
			if(entity==null){
				return null;
			}
			//验证是否外场
			if(StringUtils.isNotEmpty(entity.getIsOutfield())&&entity.getIsOutfield().equals("Y")){
				return entity.getFthNetworkName();
			}else{
				return entity.getDeptName();
			}
		}else{
			DeptTransferMappingEntity entity=entitys.get(0);
			if(StringUtils.isNotEmpty(entity.getIsOutfield())&&entity.getIsOutfield().equals("Y")){
				return null;
			}else{
				return entity.getDeptName();
			}
		}
	}



	
	
	
}
