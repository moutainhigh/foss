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
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/api/server/dao/IAirAgencySignDao.java
 * 
 * FILE NAME        	: IAirAgencySignDao.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.api.server.dao;

import java.util.List;

import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.AirAgencyQueryDto;
import com.deppon.foss.module.pickup.sign.api.shared.dto.WaybillDto;


/**
 * 
 * 签收偏线空运货物Dao
* @author foss-meiying
 * @date 2012-10-15 下午2:55:55
 * @since
 * @version
 */

public interface IAirAgencySignDao {
	
	/**
	 * 
	 *根据运单号查询运单基本信息
	 * @author foss-meiying
	 * @date 2012-10-16 下午3:33:10
	 * @param waybillNo
	 * @return
	 * @see
	 */
	WaybillDto queryByWaybillNo(String waybillNo);
	/**
	 * 
	 * 根据查询条件(单号，收货人，收货人电话，收货人手机,运输性质)查询空运运单
	 * @author foss-meiying
	 * @date 2012-10-16 下午3:33:48
	 * @param entity
	 * @return
	 * @see
	 */
	List<AirAgencyQueryDto> queryAirInfobyParams(AirAgencyQueryDto entity);
	/**
	 * 签收偏线  根据查询条件(单号，收货人，收货人电话，收货人手机,运输性质) 运输性质为 汽运偏线  partial line 查询运单号,外发单号
	 * @author foss-meiying
	 * @date 2012-11-19 上午11:15:37
	 * @param entity
	 * @return
	 * @see
	 */
	 List<AirAgencyQueryDto> queryWaybillNoByPartialLine(AirAgencyQueryDto entity);
	 /**
	  * 根据运单号查询运单的最终配载部门,运输性质,运单签收结果的运单号
	  * @author foss-meiying
	  * @date 2013-1-12 上午10:40:08
	  * @param dto
	  * @return
	  * @see
	  */
	 AirAgencyDto queryWaybillPartByCondition(AirAgencyQueryDto dto);
	 /**
	  *根据运单号查询该运单是否录外发单
	  * @author foss-meiying
	  * @date 2013-1-12 上午11:53:01
	  * @param dto
	  * @return
	  * @see
	  */
	 Long queryIsTransferExternalByWaybillNo(AirAgencyQueryDto dto);
	 /**
	 * 根据运单号,部门判断登录部门与做航空正单的部门是否一致
	 * @author foss-meiying
	 * @date 2013-7-12 下午1:33:06
	 * @param dto
	 * @return 
	 */
	 Long queryairWaybillOrgCodeIsSameByWaybillNo(AirAgencyQueryDto dto);
	 /**
     * 查询所有空运总调对应的外场
     * @return
     */
    List<String> queryAirTransferCenter();
    
	/**
	 * 签收快递代理理： 根据查询条件(单号，收货人，收货人电话，收货人手机,运输性质) 运输性质为 经济快递  partial line 查询运单号,外发单号
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-20 下午9:29:01
	 */
	List<AirAgencyQueryDto> queryExpressByPartialLine(AirAgencyQueryDto entity);
	
	/**
	 * 根据运单号查询该运单是快递代理外发单
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-20 下午9:15:42
	 */
	Long queryIsExpressExternalByWaybillNo(AirAgencyQueryDto dto);
	
	/**
	 * 根据运单号查询该快递代理递代理外发单审核状态
	 * @author 026123-foss-lifengteng
	 * @date 2013-8-29 下午7:24:38
	 */
	String queryExpressExternalBillStatusByNo(AirAgencyQueryDto dto);
	
	/**
	 * 根据导入xls的运单号进行查询
	 * @param dto
	 * @return
	 */
	List<AirAgencyQueryDto> queryExpressByImportWaybillNo(AirAgencyQueryDto dto);

	/**
	 * 签收快递代理理： 根据查询条件(单号，收货人，收货人电话，收货人手机,运输性质) 运输性质为 经济快递  partial line 查询运单号,外发单号
	 * 一次可以查询多个运单号
	 * @author foss-WeiXing
	 * @date 2015-01-15 下午3:29:01
	 */
	List<AirAgencyQueryDto> queryExpressByPartialLineWaybillNos(AirAgencyQueryDto entity);
	
	/**
	 * 根据运单号查询该快递代理递代理外发单审核状态集合
	 * @author foss-sunyanfei
	 * @date 2015-12-18 上午午8:38:38
	 */
	List<String> queryExpressExternalBillStatusListByNo(AirAgencyQueryDto dto);
}