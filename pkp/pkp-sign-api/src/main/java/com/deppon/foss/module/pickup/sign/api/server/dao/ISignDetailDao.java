/**
 *  initial comments.
 */
/*******************************************************************************
 * Copyright 2013 PKP
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
 * PROJECT NAME	: pkp-sign-api
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/ISignDetailDao.java
 * 
 * FILE NAME        	: ISignDetailDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;
import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
/**
 * 
 * 签收明细Dao接口
 * @author foss-meiying
 * @date 2012-10-22 下午3:44:09
 * @since
 * @version
 */
public interface ISignDetailDao {
	/**
	 * 
	 * <p>根据到达联编号查询签收明细</p> 
	 * @author foss-meiying
	 * @date 2012-10-22 下午3:43:40
	 * @param arrivesheetNo 
	 * @return
	 * @see
	 */
    List<SignDetailEntity> queryByArrivesheetNo(String arrivesheetNo);
    /**
     * 保存签收流水号到签收明细表里
     * @author foss-meiying
     * @date 2012-10-23 上午9:51:19
     * @param list
     * @return
     * @see
     */
    int addSignDetail(List<SignDetailEntity> list);
    /**
     * 根据主键id查询签收明细
     * @author foss-meiying
     * @date 2012-12-6 下午4:36:53
     * @param id
     * @return
     * @see
     */
    SignDetailEntity queryByPrimaryKey(String id);
    
    /**
     * 单条添加签收明细信息
     * @author foss-meiying
     * @date 2012-12-20 上午9:36:15
     * @param signDetailEntity
     * @return
     * @see
     */
    int addSignDetailInfo(SignDetailEntity signDetailEntity);
    /**
     * 给中转组提供 判断货物是否已签收的接口，参数（运单号、流水号）
     * @author foss-meiying
     * @date 2013-6-12 上午16:12:15
     * @param dto 
     * @return String
     * @see
     */
    String querySerialNoIsSign(StockDto dto);
    /**
     * 给综合查询提供 已签收的流水号接口，参数（运单号，有效，是否作废，到达联状态是签收）
     * @author foss-meiying
     * @date 2013-8-6 上午16:12:15
     * @param waybillNo 运单号
     * @param serialNo 流水号
     * @return String
     * @see
     */
    List<String> querySerialNoByWaybillNo(StockDto dto);
    /**
     * 
     * 根据到达联编号，流水号满足条件的信息
     * @author 159231-foss-meiying
     * @date 2013-11-22 下午6:23:44
     */
     List<SignDetailEntity> querySignDetailByParams(SignDetailEntity dto) ;
     /**
 	 * 根据主键删除数据
 	 * @author Foss-105888-Zhangxingwang
 	 * @date 2014年3月14日 09:59:09
 	 */
     int deleteByPrimaryKey(String id);
     
     /**
  	 * 新增数据
  	 * @author Foss-105888-Zhangxingwang
  	 * @date 2014年3月14日 09:59:09
  	 */
     int insert(SignDetailEntity dto);
     /**
  	 * 根据主键可选的更新数据
  	 * @author Foss-105888-Zhangxingwang
  	 * @date 2014年3月14日 09:59:09
  	 */
     int updateByPrimaryKeySelective(SignDetailEntity dto);
     /**
   	 * 根据主键更新数据
   	 * @author Foss-105888-Zhangxingwang
   	 * @date 2014年3月14日 09:59:09
   	 */
     int updateByPrimaryKey(SignDetailEntity dto);
     
     /**
   	 * 根据到达联编号、流水号、旧的签收情况可选的更新数据
   	 * @author 
   	 * @date 
   	 */
 	 int updateByParams(SignDetailEntity dto);
 	 
 	 /**
      * 根据到达联编号更新数据 (签收详情表的situation)
      * DMANA-9716
      * @author Foss-chenjunying 
      * @date 2015-03-25 09:46:50
      */
     int updateByArrivesheetNo(SignDetailEntity entity);
     
     /**
      * 根据运单号查询异常签收明细
      * @author Foss-bieyexiong
      * @date 2016-02-22 14:10:36
      */
     List<SignDetailEntity> querySignDetailByWaybillNo(String waybillNo);
}