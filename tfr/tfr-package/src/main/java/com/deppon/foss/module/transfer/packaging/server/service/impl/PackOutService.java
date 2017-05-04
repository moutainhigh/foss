/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 TFR TEAM
 *  
 *  Licensed under the DEPPON License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *     http://www.deppon.com/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  Contributors:
 *  038300-foss-pengzhen - initial API and implementation
 * 
 *  PROJECT NAME  : tfr-package
 *  
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/packaging/server/service/impl/PackOutService.java
 *  
 *  FILE NAME          :PackOutService.java
 * 
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 * 
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
/*
 * PROJECT NAME: tfr-package
 * PACKAGE NAME: com.deppon.foss.module.transfer.packaging.server.service.impl
 * FILE    NAME: PackOutService.java
 * COPYRIGHT: Copyright(c) 2012 Deppon All Rights Reserved.
 */ 
package com.deppon.foss.module.transfer.packaging.server.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import com.deppon.foss.module.base.baseinfo.api.server.service.IOrgAdministrativeInfoService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPDAPackagingDao;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IPackOutDao;
import com.deppon.foss.module.transfer.packaging.api.server.dao.IQueryPackedDao;
import com.deppon.foss.module.transfer.packaging.api.server.service.IPackOutService;
import com.deppon.foss.module.transfer.packaging.api.shared.define.PackagingConstants.packing;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireDetailsEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.domain.WaybillPackEntity;
import com.deppon.foss.module.transfer.packaging.api.shared.dto.WaybillNoLogingDateDto;
import com.deppon.foss.module.transfer.packaging.api.shared.exception.PackagingException;
import com.deppon.foss.module.transfer.pda.api.shared.dto.SerialNoAreaDto;
import com.deppon.foss.util.UUIDUtils;
import com.deppon.foss.util.define.FossConstants;

/**
 
 * 对外接口实现类
 * 主要是其他模块需要包装提供接口
 * @author 046130-foss-xuduowei
 * @date 2012-10-26 上午11:42:46
 */
public class PackOutService implements IPackOutService {
	/**
	 * DAO接口
	 */
	private IPackOutDao packOutDao;
	/**
	 * 部门信息接口
	 */
	private IOrgAdministrativeInfoService orgAdministrativeInfoService;
	/**
	 *  完成包装DAO层接口
	 */
	private IQueryPackedDao queryPackedDao;
	/**
	 *  PDA包装录入DAO接口
	 */
	private IPDAPackagingDao pdaPackagingDao;
	/**
	 * 
	 */
	private static Logger LOGGER = LogManager.getLogger(PackOutService.class);
	
	/**	 
	 * 更新运单号
	 * @param origWaybillNo
	 * @param newWaybillNo
	 * @return
	 * @date 2013-03-18 下午6:00:00
	 */
	@Override
	public int updateWaybillNo(String origWaybillNo, String newWaybillNo) {
		//new一个map，封装查询条件
		Map<String,Object> map = new HashMap<String,Object>();
		//原始流水号
		map.put("origWaybillNo", origWaybillNo);
		//新流水号
		map.put("newWaybillNo", newWaybillNo);
		//调用DAO
		//理论上认为有此运单号的包装需求
		//故返回成功标示
		packOutDao.updateWaybillNo(map);
		//返回成功标示
		return FossConstants.SUCCESS;
	}

	@Override
	public boolean queryPackedStatusBySerialNo(String waybillNo, String serialNo) {
		
		return false;
	}
	/** 
	 
	 * 用于接送货发更改时调用，修改需要包装的运单信息
	 * @param packagingRequireEntity 包装需求数据
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-26 上午11:42:46
	 * @see com.deppon.foss.module.transfer.packaging.api.server.service.IPackOutService#addPackagingRequire(com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireEntity)
	 */
	@Transactional
	@Override
	public int updatePackagingRequire(
			PackagingRequireEntity packagingRequireEntity) {
		
		//首先判断包装需求实体是否为空
		//如果为空，抛出异常
		if(packagingRequireEntity==null){
			//抛出异常
			throw new PackagingException("需要修改的包装信息为空，请检查是否需要修改包装信息");
		}
		//校验需要修改的包装信息
		verifyWaybillPackageRequire(packagingRequireEntity);
		//运单号
		String waybillNo = packagingRequireEntity.getWaybillNo();
		//获取中转表中已录入的包装需求，
		List<WaybillPackEntity> waybillPackList = 
				queryPackedDao.queryUnWaybillPack(packagingRequireEntity.getWaybillNo());
		//需要修改的运单包装信息在中转表中不存在
		if(waybillPackList == null || waybillPackList.isEmpty()){
			//此情况是开单没有录入包装需求，
			//更改单可能需要包装，
			//应该是新增包装需求
			//throw new PackagingException("中转系统中没有"+packagingRequireEntity.getWaybillNo()+"的包装需求信息");
			addPackagingRequire(packagingRequireEntity);
		}else{
			//包装需求id
			String id = waybillPackList.get(0).getId();
			/**
			 * 根据部门编码查找对应的部门名称
			 */
			String packedCode = packagingRequireEntity.getPackagingDeptCode();
			//得到部门信息
			OrgAdministrativeInfoEntity packedDept = 
					orgAdministrativeInfoService.queryOrgAdministrativeInfoByCode(packedCode);
			//判断包装部门是否为空
			if(packedDept == null){
				//抛出异常
				throw new PackagingException("找不到对应包装部门编码的部门名称");
			}
			//获取包装部门名称
			packagingRequireEntity.setPackagingDept(packedDept.getName());
			//修改包装信息
			packOutDao.updatePackagingRequire(packagingRequireEntity);		
			/**
			 * 判断新增需要包装的流水号是否为空，如果不为空，则流水号个数是否大于0
			 */
			
			//获取打木架新流水号
			List<String> addWSerialNo = packagingRequireEntity.getAddWSerialNo();
			//获取打木托新流水号
			List<String> addMSerialNo = packagingRequireEntity.getAddMSerialNo();
			
			//只要打木架或者打木托不为空
			if((addWSerialNo != null && !addWSerialNo.isEmpty()) || (addMSerialNo != null && !addMSerialNo.isEmpty())){
				/**			 
				 * 在需要包装流水号明细表中新增
				 */
				List<PackagingRequireDetailsEntity> addDeatils = 
						new ArrayList<PackagingRequireDetailsEntity>();
				/**
				 * 
				 * 变量流水号信息，为每个流水号增加相关信息
				 */
				
				
				//新增MAP对象
				//封装查询条件
				Map<String,Object> map = new HashMap<String,Object>();
				//运单号
				map.put("waybillNo", waybillNo);
				//是否已包装
				//FossConstants.NO 未包装
				map.put("isPacked", null); //不需要次查询条件所以设置成NULL
				//查询符合条件个数
				List<SerialNoAreaDto> areaList = pdaPackagingDao.querySerialArea(map);
				
				//打木架
				PackagingRequireDetailsEntity prd = null;
                if(CollectionUtils.isNotEmpty(addWSerialNo)){
                    for(int i=0;i<addWSerialNo.size();i++){
                        //流水号
                        String serialNo = addWSerialNo.get(i);
                        prd = new PackagingRequireDetailsEntity();
                        boolean isExist = false;
                        //遍历查询结果集
                        if(areaList == null || areaList.isEmpty()){//表示没有需要打包装的流水号
                            isExist = false;
                        }else{
                            for(SerialNoAreaDto sd : areaList){
                                //如果流水号在包装需求表里，则不作任何操作，否则就要新增
                                if(StringUtils.equals(serialNo, sd.getSerialNo()) && packing.WOODEN_FRAME.getValue().equals(sd.getPackageType())){
                                    //System.out.println(serialNo + "=" + sd.getSerialNo());
                                    isExist = true;
                                }else{
                                    //无操作
                                }
                            }
                        }
                        if(!isExist){
                            prd.setId(UUIDUtils.getUUID());
                            //运单号
                            prd.setWaybillNo(waybillNo);
                            //流水号
                            prd.setSerialNo(serialNo);
                            //包装需求主表id
                            prd.setRequireId(id);
                            //开单时间
                            prd.setWaybillCreateDate(packagingRequireEntity.getWaybillCreateDate());
                            //默认为未包装
                            prd.setIsPacked(FossConstants.NO);
                            //设置包装需求类型
                            prd.setPackageType(packing.WOODEN_FRAME.getValue());
                            //加入到list中，进行批量保存
                            addDeatils.add(prd);
                        }
                    }
                }

				//打木托
                if(CollectionUtils.isNotEmpty(addMSerialNo)){
                    for(int i=0;i<addMSerialNo.size();i++){
                        //流水号
                        String serialNo = addMSerialNo.get(i);
                        prd = new PackagingRequireDetailsEntity();
                        boolean isExist = false;
                        //遍历查询结果集
                        if(areaList == null || areaList.isEmpty()){//表示没有需要打包装的流水号
                            isExist = false;
                        }else{
                            for(SerialNoAreaDto sd : areaList){
                                //如果流水号在包装需求表里，则不作任何操作，否则就要新增
                                if(StringUtils.equals(serialNo, sd.getSerialNo()) && packing.MAKE_WOODEN_STOCK.getValue().equals(sd.getPackageType())){
                                    //System.out.println(serialNo + "=" + sd.getSerialNo());
                                    isExist = true;
                                }else{
                                    //无操作
                                }
                            }
                        }
                        if(!isExist){
                            prd.setId(UUIDUtils.getUUID());
                            //运单号
                            prd.setWaybillNo(waybillNo);
                            //流水号
                            prd.setSerialNo(serialNo);
                            //包装需求主表id
                            prd.setRequireId(id);
                            //开单时间
                            prd.setWaybillCreateDate(packagingRequireEntity.getWaybillCreateDate());
                            //默认为未包装
                            prd.setIsPacked(FossConstants.NO);
                            //设置包装需求类型
                            prd.setPackageType(packing.MAKE_WOODEN_STOCK.getValue());
                            //加入到list中，进行批量保存
                            addDeatils.add(prd);
                        }
                    }
                }
				
				//调用DAO接口，保存包装需求明细
				if(addDeatils != null && !addDeatils.isEmpty()){
					LOGGER.debug("接送货更改打木架或者打木托，增加包装流水号，运单号：" + waybillNo);
					packOutDao.addPackagingRequireDetails(addDeatils);
				}
				
			}
			
			/**
			 * 
			 * 判断删除不需要包装的流水号是否为空，
			 * 如果不为空，则流水号个数是否大于0
			 * 
			 */
			
			//获取打木架删除流水号
			List<String> deleteWSerialNo = packagingRequireEntity.getDeleteWSerialNo();
			//获取打木托 删除流水号
			List<String> deleteMSerialNo = packagingRequireEntity.getDeleteMSerialNo();
			
			if((deleteMSerialNo!=null && !deleteMSerialNo.isEmpty())||(deleteWSerialNo!=null && !deleteWSerialNo.isEmpty())){
				//流水号个数是否大于0
				
				/**
				 * 在需要包装流水号明细表中删除
				 */
				List<PackagingRequireDetailsEntity> deleteDeatils = 
						new ArrayList<PackagingRequireDetailsEntity>();
				/**
				 * 遍历需要删除的流水号信息，
				 * 单个流水号不能删除相应的信息，
				 * 需要添加运单号
				 */
				PackagingRequireDetailsEntity prd  =null;
				//删除打木架流水号
                if(CollectionUtils.isNotEmpty(deleteWSerialNo)){
                    for(int i=0;i<deleteWSerialNo.size();i++){
                        prd = new PackagingRequireDetailsEntity();
                        //运单号
                        prd.setWaybillNo(packagingRequireEntity.getWaybillNo());
                        //流水号
                        prd.setSerialNo(deleteWSerialNo.get(i));
                        //设置包装需求类型
                        prd.setPackageType(packing.WOODEN_FRAME.getValue());
                        //加入到list中，进行批量删除
                        deleteDeatils.add(prd);
                    }
                }
                
				//打木托流水号
                if(CollectionUtils.isNotEmpty(deleteMSerialNo)){
                    for(int i=0;i<deleteMSerialNo.size();i++){
                        prd = new PackagingRequireDetailsEntity();
                        //运单号
                        prd.setWaybillNo(packagingRequireEntity.getWaybillNo());
                        //流水号
                        prd.setSerialNo(deleteMSerialNo.get(i));
                        //设置包装需求类型
                        prd.setPackageType(packing.MAKE_WOODEN_STOCK.getValue());
                        //加入到list中，进行批量删除
                        deleteDeatils.add(prd);
                    }
                }

				LOGGER.debug("接送货更改打木架或者打木托，删除包装流水号，运单号：" + waybillNo);
				//批量删除部需要包装的流水号
				packOutDao.deletePackagingRequireDetails(deleteDeatils);			
			}
		}
		//修改需要包装件数
		packOutDao.updateNeedPackNum(waybillNo);
		//修改包装状态
		queryPackedDao.updatePacakageRequirePackedStatus(waybillNo);
		
		return FossConstants.SUCCESS;
	}

	/** 
	 * 保存开单时需要打木架的需求
	 * @param packagingRequireEntity 包装需求数据
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-26 上午11:42:46
	 * @see com.deppon.foss.module.transfer.packaging.api.server.service.IPackOutService#addPackagingRequire(com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireEntity)
	 */
	@Transactional
	@Override
	public int addPackagingRequire(PackagingRequireEntity packagingRequireEntity) {
		//校验包装需求实体，
		//维护人员可以根据需要在此方法中添加自己需要的验证判断
		verifyWaybillPackageRequire(packagingRequireEntity);
		//打木架
		List<String> addWserialNo = packagingRequireEntity.getAddWSerialNo();
		//打木托
		List<String> addMserialNo = packagingRequireEntity.getAddMSerialNo();
		
		
		if((addMserialNo == null || addMserialNo.isEmpty()) && (addWserialNo == null || addWserialNo.isEmpty())){
			//抛出异常
			throw new PackagingException("需要包装的流水号不能为空");
		}
		
		//获取已包装货物信息
		int num = packOutDao.queryPackagingRequire(packagingRequireEntity.getWaybillNo());
		//如果存在此运单的包装需求实体，
		//则不能重复新增，需要抛出异常
		if(num>0){
			//抛出异常
			throw new PackagingException("已存在该运单的包装需求信息");
		}
		//包装部门编码
		String packedCode = packagingRequireEntity.getPackagingDeptCode();
		//得到组织信息
		OrgAdministrativeInfoEntity packedDept = 
				orgAdministrativeInfoService.querySimpleOrgAdministrativeInfoByCode(packedCode);
		//判断组织信息是否存在
		if(packedDept == null){
			//抛出异常
			throw new PackagingException("包装部门信息不存在");
		}
		//ID
		packagingRequireEntity.setId(UUIDUtils.getUUID());
		//包装状态
		packagingRequireEntity.setPackedStatus(FossConstants.NO);
		//默认为未作废
		packagingRequireEntity.setIsDisable(FossConstants.NO);
		//已包装件数为0
		packagingRequireEntity.setPackedNum(0);
		//获取包装部门名称
		packagingRequireEntity.setPackagingDept(packedDept.getName());
		//获取执行成功的行数
		int isSuccess = packOutDao.addPackagingRequire(packagingRequireEntity);
		if(isSuccess==0){
			//抛出异常
			throw new PackagingException("保存失败");
		}		
		/**
		 * 保存包装需求流水号明细
		 */
		//新装包装需求明细
		List<PackagingRequireDetailsEntity> packagingDeatils = new ArrayList<PackagingRequireDetailsEntity>();
		PackagingRequireDetailsEntity prd = null;
		//打木架
       if(CollectionUtils.isNotEmpty(addWserialNo)){
            for(int i=0;i<addWserialNo.size();i++){
                prd = new PackagingRequireDetailsEntity();
                //设置id
                prd.setId(UUIDUtils.getUUID());
                //运单号
                prd.setWaybillNo(packagingRequireEntity.getWaybillNo());
                //流水号
                prd.setSerialNo(addWserialNo.get(i));
                //包装需求主表id
                prd.setRequireId(packagingRequireEntity.getId());
                //开单时间
                prd.setWaybillCreateDate(packagingRequireEntity.getWaybillCreateDate());
                //默认为未包装
                prd.setIsPacked(FossConstants.NO);
                //设置包装需求类型
                prd.setPackageType(packing.WOODEN_FRAME.getValue());
                //加入到list中，
                //进行批量保存
                packagingDeatils.add(prd);
            }
        }
		//打木托
        if(CollectionUtils.isNotEmpty(addMserialNo)){
            for(int i=0;i<addMserialNo.size();i++){
                prd = new PackagingRequireDetailsEntity();
                //设置id
                prd.setId(UUIDUtils.getUUID());
                //运单号
                prd.setWaybillNo(packagingRequireEntity.getWaybillNo());
                //流水号
                prd.setSerialNo(addMserialNo.get(i));
                //包装需求主表id
                prd.setRequireId(packagingRequireEntity.getId());
                //开单时间
                prd.setWaybillCreateDate(packagingRequireEntity.getWaybillCreateDate());
                //默认为未包装
                prd.setIsPacked(FossConstants.NO);
                //设置包装需求类型
                prd.setPackageType(packing.MAKE_WOODEN_STOCK.getValue());
                //加入到list中，
                //进行批量保存
                packagingDeatils.add(prd);
            }
        }
		//调用dao，
		//执行保存操作
		packOutDao.addPackagingRequireDetails(packagingDeatils);
		//返回成功
		return FossConstants.SUCCESS;
	}	
	/** 
	 * 作废运单时，作废包装需求
	 * @param packagingRequireEntity 包装需求数据
	 * @author 046130-foss-xuduowei
	 * @date 2012-10-26 上午11:42:46
	 * @see com.deppon.foss.module.transfer.packaging.api.server.service.IPackOutService#addPackagingRequire(com.deppon.foss.module.transfer.packaging.api.shared.domain.PackagingRequireEntity)
	 */
	@Override
	public int disablePackagingRequire(String waybillNo) {
		//作废包装需求
		return packOutDao.disablePackagingRequire(waybillNo);
	}	
	/**
	 * 
	 * 校验包装信息是否完整
	 * @author 046130-foss-xuduowei
	 * @date 2012-11-28 下午4:29:50
	 */
	private boolean verifyWaybillPackageRequire(PackagingRequireEntity packagingRequireEntity){
		//获取运单号
		String waybillNo =  packagingRequireEntity.getWaybillNo();
		Date date = packagingRequireEntity.getWaybillCreateDate();
		//运单号显然不能为空，
		//维护人员可以根据需要添加信息进行判断
		if(StringUtils.isEmpty(waybillNo)){
			//抛出异常
			throw new PackagingException("运单号不能为空");
		}
		if(date==null){
			//抛出异常
			throw new PackagingException("开单时间不能为空");
		}
		if(StringUtils.isEmpty(packagingRequireEntity.getGoodsName())){
			//抛出异常
			throw new PackagingException("货物名称不能为空");
		}
		if(StringUtils.isEmpty(packagingRequireEntity.getWaybillCreateDeptCode())){
			//抛出异常
			throw new PackagingException("开单部门不能为空");
		}
		if(StringUtils.isEmpty(packagingRequireEntity.getPackagingRequire())){
			//抛出异常
			throw new PackagingException("包装要求不能为空");
		}
		if(StringUtils.isEmpty(packagingRequireEntity.getPackagingDeptCode())){
			//抛出异常
			throw new PackagingException("代包装部门不能为空");
		}
		if(StringUtils.isEmpty(packagingRequireEntity.getProductType())){
			//抛出异常
			throw new PackagingException("运输类型不能为空");
		}
		return true;
	}
	
	/**
	 * 插入包装货物登入包装货去或登出包装货区的时
	 * @return
	 * @date 2013-03-18 下午6:00:00
	 */
	@Override
	public int insertWaybillNoLogingDate(
			WaybillNoLogingDateDto waybillNoLogingDateDto) {
		try {
			packOutDao.insertWaybillNoLogingDate(waybillNoLogingDateDto);
		} catch (Exception e) {
			LOGGER.error(e);
		}
		return 0;
	}

	/**
	 * 设置 dAO接口.
	 *
	 * @param packOutDao the new dAO接口
	 */
	public void setPackOutDao(IPackOutDao packOutDao) {
		this.packOutDao = packOutDao;
	}

	/**
	 * 设置 部门信息接口.
	 *
	 * @param orgAdministrativeInfoService the new 部门信息接口
	 */
	public void setOrgAdministrativeInfoService(
			IOrgAdministrativeInfoService orgAdministrativeInfoService) {
		this.orgAdministrativeInfoService = orgAdministrativeInfoService;
	}

	/**
	 * 设置 完成包装DAO层接口.
	 *
	 * @param queryPackedDao the new 完成包装DAO层接口
	 */
	public void setQueryPackedDao(IQueryPackedDao queryPackedDao) {
		this.queryPackedDao = queryPackedDao;
	}

	public void setPdaPackagingDao(IPDAPackagingDao pdaPackagingDao) {
		this.pdaPackagingDao = pdaPackagingDao;
	}
	
	
}