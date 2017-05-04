/*******************************************************************************
 * Copyright 2013 BSE TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: bse-baseinfo-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/base/baseinfo/api/server/service/ILeasedVehicleService.java
 * 
 * FILE NAME        	: ILeasedVehicleService.java
 * 
 * AUTHOR			: FOSS综合管理开发组
 * 
 * HOME PAGE		:  http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.base.baseinfo.api.server.service;

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.LeasedTruckEntity;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.BindingLeasedTruckDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.LeasedVehicleWithDriverDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.PaginationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.dto.VehicleAssociationDto;
import com.deppon.foss.module.base.baseinfo.api.shared.exception.LeasedVehicleException;
import com.deppon.foss.module.frameworkimpl.shared.domain.CurrentInfo;
/**
 * 用来操作交互“外请车辆（厢式车、挂车、拖头）”的数据库对应数据访问Service接口：SUC-104、SUC-608、SUC-44、SUC-103
 * <p>需要版本控制</p>
 * <p style="display:none">modifyRecord</p>
 * <p style="display:none">version:V1.0,author:100847-foss-GaoPeng,date:2012-10-22 下午4:16:02</p>
 * @author 100847-foss-GaoPeng
 * @date 2012-10-22 下午4:16:02
 * @since
 * @version
 */
public interface ILeasedVehicleService extends IService {
    
    /**
     * <p>新增一个“外请车（厢式车、挂车、拖头）”实体入库</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:18:55
     * @param leasedTruck “外请车（厢式车、挂车、拖头）”实体
     * @param leasedTruckParameter “车辆类型（厢式车、挂车、拖头）”的值代码
     * @param createUser 创建人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleException
     * @see
     */
     int addLeasedVehicle(LeasedTruckEntity leasedTruckParameter, String createUser, boolean ignoreNull) throws LeasedVehicleException;
    
     /**
     * <p>根据“外请车（厢式车、挂车、拖头）”记录ID集合作废（逻辑删除）多条“外请车（厢式车、挂车、拖头）”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:18:54
     * @param currentInfo 修改人信息
     * @param String 记录唯一标识集合
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleException
     * @see
     */
     int deleteLeasedVehicle(List<String> ids, CurrentInfo modifyInfo) throws LeasedVehicleException;
    
    /**
     * <p>修改一个“外请车（厢式车、挂车、拖头）”实体入库</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:19:00
     * @param leasedTruckParameter “外请车（厢式车、挂车、拖头）”实体
     * @param modifyUser 修改人
     * @param ignoreNull true：忽略空值，false：包含空值
     * @return 1：成功；-1：失败
     * @throws LeasedVehicleException
     * @see
     */
     int updateLeasedVehicle(LeasedTruckEntity leasedTruckParameter, CurrentInfo modifyUser,  boolean ignoreNull) throws LeasedVehicleException;
    
    /**
     * <p>根据“外请车（厢式车、挂车、拖头）”记录唯一标识查询出一条“外请车（厢式车、挂车、拖头）”记录</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:18:57
     * @param id 记录唯一标识
     * @return “外请车（厢式车、挂车、拖头）”实体
     * @throws LeasedVehicleException
     * @see
     */
     LeasedTruckEntity queryLeasedVehicle(String id) throws LeasedVehicleException;
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”实体（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 下午4:56:48
     * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体
     * @throws LeasedVehicleException
     * @see
     */
     LeasedTruckEntity queryLeasedVehicleBySelectiveCondition(LeasedTruckEntity leasedTruck) throws LeasedVehicleException;
    
    /**
     * <p>根据条件有选择的统计出符合条件的“外请车（厢式车、挂车、拖头）”实体记录数（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-24 上午10:35:27
     * @param leasedTruck 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体记录条数
     * @see
     */
     long queryLeasedVehicleRecordCountBySelectiveCondition(LeasedTruckEntity leasedTruck);
    
    /**
     * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）”实体列表（条件做自动判断，只选择实体中非空值）</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-16 上午10:18:58
     * @param airlinesAgent 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
     * @param offset 开始记录数
     * @param limit 限制记录数
     * @return 符合条件的“外请车（厢式车、挂车、拖头）”实体列表
     * @throws LeasedVehicleException
     * @see
     */
     PaginationDto queryLeasedVehicleListBySelectiveCondition(LeasedTruckEntity leasedTruck, int offset, int limit) throws LeasedVehicleException;
     
     /**
      * <p>根据条件有选择的检索出符合条件的“外请车（厢式车、挂车、拖头）和司机”实体列表（条件做自动判断，只选择实体中非空值）</p> 
      * @author 100847-foss-GaoPeng
      * @date 2012-10-16 上午10:18:58
      * @param airlinesAgent 以“外请车（厢式车、挂车、拖头）”实体承载的条件参数实体
      * @param offset 开始记录数
      * @param limit 限制记录数
      * @return 符合条件的“外请车（厢式车、挂车、拖头）和司机”实体列表
      * @throws LeasedVehicleException
      * @see
      */
     PaginationDto queryLeasedVehicleListDtosBySelectiveCondition(LeasedTruckEntity leasedTruck, int offset, int limit) throws LeasedVehicleException;
    
    /**
     * <p>根据"车牌号"来获取"外请车辆（厢式车、挂车、拖头）"对应的记录数据</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 下午4:20:57
     * @param vehicleNo 车牌号
     * @return "外请车辆（厢式车、挂车、拖头）"实体
     * @throws LeasedVehicleException
     * @see
     */
     LeasedTruckEntity queryLeasedVehicleByVehicleNo(String vehicleNo) throws LeasedVehicleException;
    
    /**
     * <p>根据"车牌号"来获取"外请车辆（厢式车、挂车、拖头）"对应的记录数据实体</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-10-22 下午4:21:00
     * @param vehicleNo 车牌号
     * @return "外请车辆（厢式车、挂车、拖头）"实体
     * @throws LeasedVehicleException
     * @see
     */
     LeasedTruckEntity queryLeasedVehicleByVehicleNoAndType(String vehicleNo, String vehicleType) throws LeasedVehicleException;
    
    /**
     * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合</p>
     * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
     * @author 100847-foss-GaoPeng
     * @date 2012-10-30 下午4:28:53
     * @param vehicleNos 车牌号集合
     * @return 封装了VehicleAssociationDto传送对象集合
     * @throws LeasedVehicleException
     * @see
     */
     List<VehicleAssociationDto> queryLeasedVehicleAssociationDtosByVehicleNos(String[] vehicleNos) throws LeasedVehicleException;
    
     
     /**
      * <p>提供给"中转"模块使用，根据"车牌号"来获取车辆相关信息DTO集合(包括拖头)</p>
      * <p>包括：车辆直属部门、车辆车型、车辆业务、车辆品牌、车辆车牌号、车辆额定载重、车辆净空、是否有GPS、货柜编号</p>
      * @author 313353-foss-qiupeng
      * @date 2016-07-06 下午09:28:53
      * @param vehicleNos 车牌号集合
      * @return 封装了VehicleAssociationDto传送对象集合
      * @throws LeasedVehicleException
      * @see
      */
      List<VehicleAssociationDto> queryLeasedVehicleByVehicleNosIncludeTractors(String[] vehicleNos) throws LeasedVehicleException;
     
    /**
     * <p>提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆和外请司机相关信息DTO集合</p> 
     * @author 100847-foss-GaoPeng
     * @date 2012-11-6 下午6:06:22
     * @param vehicleNo 车牌号
     * @param vehicleTypeCode 车型编码
     * @param driverName 司机姓名
     * @param driverPhone 司机电话
     * @param isOpenVehicle 是否敞篷车（true：是，false：否）
     * @param status Y:可用；N:不可用
     * @return LeasedVehicleWithDriverDto封装了"外请车的基本信息、外请司机的基本信息"
     * @throws LeasedVehicleException
     * @see
     */
     List<LeasedVehicleWithDriverDto> queryLeasedVehicleWithDriverDtosByCondition(String vehicleNo, String vehicleTypeCode, String driverName, String driverPhone, boolean isOpenVehicle, String status) throws LeasedVehicleException;
     /**
      * <p>（分页）提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆和外请司机相关信息DTO集合</p> 
      * @author 100847-foss-GaoPeng
      * @date 2012-11-6 下午6:06:22
      * @param vehicleNo 车牌号
      * @param vehicleTypeCode 车型编码
      * @param driverName 司机姓名
      * @param driverPhone 司机电话
      * @param isOpenVehicle 是否敞篷车（true：是，false：否）
      * @param status Y:可用；N:不可用
      * @return LeasedVehicleWithDriverDto封装了"外请车的基本信息、外请司机的基本信息"
      * @throws LeasedVehicleException
      * @see
      */
      List<LeasedVehicleWithDriverDto> queryLeasedVehicleWithDriverDtosByCondition(String vehicleNo, String vehicleTypeCode, String driverName, String driverPhone, boolean isOpenVehicle, String status, int offset, int limit) throws LeasedVehicleException;
      /**
       * <p>（取分页总条数）提供给"接送货"模块使用，根据不同的"参数组合"来获取外请车辆和外请司机相关信息DTO集合</p> 
       * @author 100847-foss-GaoPeng
       * @date 2012-11-6 下午6:06:22
       * @param vehicleNo 车牌号
       * @param vehicleTypeCode 车型编码
       * @param driverName 司机姓名
       * @param driverPhone 司机电话
       * @param isOpenVehicle 是否敞篷车（true：是，false：否）
       * @param status Y:可用；N:不可用
       * @return LeasedVehicleWithDriverDto封装了"外请车的基本信息、外请司机的基本信息"
       * @throws LeasedVehicleException
       * @see
       */
      long countQueryLeasedVehicleWithDriverDtosByCondition(String vehicleNo, String vehicleTypeCode, String driverName, String driverPhone, boolean isOpenVehicle, String status) throws LeasedVehicleException ;
      /**
       * 
       *<p>导出excel表格，</p>
       *@author 130566-zengJunfan
       *@date   2013-8-2上午10:24:12
       * @param leasedTruck
       * @return
       */
      InputStream exportLeaseVehicle(LeasedTruckEntity leasedTruck);

	/**
	 * <p>通过车牌号查询该车的种类，ownVehicle为公司车，leasedVehicle为外请车，
     * 		   expressVehicle为快递车辆，没查到就返回null</p> 
	 * @author 232607 
	 * @date 2015-9-22 下午5:28:41
	 * @param vehicleNo
	 * @return
	 * @see
	 */
	String queryVehicleType(String vehicleNo);

	/**
	 * <p>申请修改净空</p> 
	 * @author 232607 
	 * @date 2015-10-12 下午9:18:01
	 * @param entity
	 * @see
	 */
	void updateSelfVolume(LeasedTruckEntity entity);
	
	/**
	 * 外清车服务资料,可查询【外请厢式车】信息里的“白名单状态”为“可用”的车辆信息
	 * @author 310854
	 * @date 2016-5-15
	 */
	PaginationDto queryLeasedServiceDateList(LeasedTruckEntity leasedTruck, int offset, int limit) throws LeasedVehicleException;
	
	/**
	 * 释放车队
	 * @author 310854
	 * @date 2016-5-15
	 */
	int deleteLeasedServiceDateTream(List<String> codes, CurrentInfo modifyInfo, List<String> bindingOgrCodes) throws LeasedVehicleException;
	
	/**
	 * 外清车绑定
	 * @author 310854
	 * @date 2016-5-16
	 */
	int addLeasedServiceDateTream(List<String> codes, CurrentInfo modifyInfo) throws LeasedVehicleException;
	
	/**
	 * 通过外请车车牌号获取服务车队
	 * @author 310854
	 * @param vehicleNo
	 * @return
	 * @throws LeasedVehicleException
	 */
	public LeasedTruckEntity queryLeasedVehicleTeam (String vehicleNo) throws LeasedVehicleException;
	
	/**
	 * 查询已绑定的外请车列表
	 * @Title: queryBindingLeasedVehicleList 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param leasedTruck
	 * @param @param offset
	 * @param @param limit
	 * @param @return
	 * @param @throws LeasedVehicleException    设定文件 
	 * @return List<BindingLeasedTruckDto>    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	List<BindingLeasedTruckDto> queryBindingLeasedVehicleList(BindingLeasedTruckDto leasedTruck, int offset, int limit) throws LeasedVehicleException;

	/**
	 * 查询已绑定的外请车的总数
	 * @Title: queryBindingLeasedVehicleListTotal 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param leasedTruck
	 * @param @return
	 * @param @throws LeasedVehicleException    设定文件 
	 * @return long    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	long queryBindingLeasedVehicleListTotal(BindingLeasedTruckDto leasedTruck) throws LeasedVehicleException;
	
	/**
	 * 通过车牌号获取绑定的顶级车队
	 * @Title: queryOrgNameByVehicleNo 
	 * @Description: TODO(这里用一句话描述这个方法的作用) 
	 * @param @param vehicleNo
	 * @param @return    设定文件 
	 * @return String    返回类型 
	 * @throws 
	 * @user 310854-liuzhenhua
	 */
	public String queryOrgNameByVehicleNo(String vehicleNo);
	/**
	 * 
	 * @param empCode
	 * @param titleNum 员工职位参数 0：员工；1：测量经理；2：测量高级经理
	 * @return
	 */
	public String queryTitleByEmpCode(String empCode,String titleNum);
	
}
