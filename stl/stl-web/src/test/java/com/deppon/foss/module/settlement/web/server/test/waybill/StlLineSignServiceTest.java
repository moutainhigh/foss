/**
 * Copyright 2013 STL TEAM
 */
/*******************************************************************************
 * Copyright 2013 STL TEAM
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * PROJECT NAME	: stl-web
 * 
 * FILE PATH        	: src/test/java/com/deppon/foss/module/settlement/web/server/test/waybill/StlLineSignServiceTest.java
 * 
 * FILE NAME        	: StlLineSignServiceTest.java
 * 
 * AUTHOR			: FOSS结算系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/

package com.deppon.foss.module.settlement.web.server.test.waybill;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deppon.foss.module.settlement.common.api.shared.define.SettlementConstants;
import com.deppon.foss.module.settlement.common.api.shared.dto.LineSignDto;
import com.deppon.foss.module.settlement.common.api.shared.exception.SettlementException;
import com.deppon.foss.module.settlement.consumer.api.server.service.ILineSignService;
import com.deppon.foss.module.settlement.web.test.util.TestUtil;
import com.deppon.foss.util.define.FossConstants;

/**
 * 运单签收测试类
 * 
 * @author 099995-foss-wujiangtao
 * @date 2013-1-29 下午7:22:48
 * @since
 * @version
 */
public class StlLineSignServiceTest extends TestCase{
	
	protected final Logger logger = LoggerFactory.getLogger(StlLineSignServiceTest.class);
	
	
	private LineSignDto  singDto;
	
	private ILineSignService lineSignService;
	
	/**
	 * 运单号
	 */
	private String waybillNo;
	
	/**
	 * 到达部门编码
	 */
	private String destOrgCode;
	
	/**
	 * 到达部门名称
	 */
	private String destOrgName;
	
	/**
	 * 到达操作者编码
	 */
	private String destUserCode;
	
	/**
	 * 到达操作者名称
	 */
	private String destUserName;
	
	public StlLineSignServiceTest(){
		
	}
	
	public StlLineSignServiceTest(String testMethod){
		super(testMethod);
	}
	
	/**
	 * 签收
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-1-29 下午7:23:13
	 */
	public void testConfirmTaking(){
		try{
			lineSignService.confirmTaking(singDto, TestUtil.getCurrentInfo(
					this.getDestUserCode(),this.getDestUserName(),this.getDestOrgCode(),
					this.getDestOrgName()
					));
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 专线签收
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-2 上午8:05:10
	 */
	public void testConfirmTakingByLineSign(){
		try{
			LineSignDto signDto=TestUtil.getLineSignDto(null, waybillNo, destOrgCode, 
					destOrgName, SettlementConstants.LINE_SIGN, FossConstants.NO, FossConstants.NO);
			lineSignService.confirmTaking(signDto, TestUtil.getCurrentInfo(
					this.getDestUserCode(),this.getDestUserName(),this.getDestOrgCode(),
					this.getDestOrgName()
					));
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 偏线签收
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-2 上午8:09:43
	 */
	public void testConfirmTakingByPartialLineSign(){
		try{
			LineSignDto signDto=TestUtil.getLineSignDto(null, waybillNo, destOrgCode, 
					destOrgName, SettlementConstants.PARTIAL_LINE_SIGN, FossConstants.NO, FossConstants.NO);
			lineSignService.confirmTaking(signDto, TestUtil.getCurrentInfo(
					this.getDestUserCode(),this.getDestUserName(),this.getDestOrgCode(),
					this.getDestOrgName()
					));
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 空运签收
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-2 上午8:09:43
	 */
	public void testConfirmTakingByAirSign(){
		try{
			LineSignDto signDto=TestUtil.getLineSignDto(null, waybillNo, destOrgCode, 
					destOrgName, SettlementConstants.AIR_SIGN, FossConstants.NO, FossConstants.NO);
			lineSignService.confirmTaking(signDto, TestUtil.getCurrentInfo(
					this.getDestUserCode(),this.getDestUserName(),this.getDestOrgCode(),
					this.getDestOrgName()
					));
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	
	/**
	 * 反签收测试
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-1 下午3:47:14
	 */
	public void testReverseConfirmTaking(){
		try{
			lineSignService.reverseConfirmTaking(singDto, TestUtil.getCurrentInfo(
					this.getDestUserCode(),this.getDestUserName(),this.getDestOrgCode(),
					this.getDestOrgName())
					);
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 专线反签收
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-2 上午8:19:50
	 */
	public void testReverseConfirmTakingByLineSign(){
		try{
			LineSignDto signDto=TestUtil.getLineSignDto(null, waybillNo, destOrgCode, 
					destOrgName, SettlementConstants.LINE_SIGN, FossConstants.NO, 
					FossConstants.NO);
			lineSignService.confirmTaking(signDto, TestUtil.getCurrentInfo(
					this.getDestUserCode(),this.getDestUserName(),this.getDestOrgCode(),
					this.getDestOrgName()
					));
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 偏线反签收
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-2 上午8:19:50
	 */
	public void testReverseConfirmTakingByPartialLineSign(){
		try{
			LineSignDto signDto=TestUtil.getLineSignDto(null, waybillNo, destOrgCode, 
					destOrgName, SettlementConstants.PARTIAL_LINE_SIGN, FossConstants.NO,
					FossConstants.NO);
			lineSignService.confirmTaking(signDto, TestUtil.getCurrentInfo(
					this.getDestUserCode(),this.getDestUserName(),this.getDestOrgCode(),
					this.getDestOrgName()
					));
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}
	
	/**
	 * 空运反签收
	 * 
	 * @author 099995-foss-wujiangtao
	 * @date 2013-2-2 上午8:19:50
	 */
	public void testReverseConfirmTakingByAirSign(){
		try{
			LineSignDto signDto=TestUtil.getLineSignDto(null, waybillNo, destOrgCode, 
					destOrgName, SettlementConstants.AIR_SIGN, FossConstants.NO,
					FossConstants.NO);
			lineSignService.confirmTaking(signDto, TestUtil.getCurrentInfo(
					this.getDestUserCode(),this.getDestUserName(),this.getDestOrgCode(),
					this.getDestOrgName()
					));
		}catch(SettlementException e){
			logger.error(e.getErrorCode(),e);
		}
	}

	
	/**
	 * @return  the singDto
	 */
	public LineSignDto getSingDto() {
		return singDto;
	}

	
	/**
	 * @param singDto the singDto to set
	 */
	public void setSingDto(LineSignDto singDto) {
		this.singDto = singDto;
	}

	
	/**
	 * @return  the lineSignService
	 */
	public ILineSignService getLineSignService() {
		return lineSignService;
	}

	
	/**
	 * @param lineSignService the lineSignService to set
	 */
	public void setLineSignService(ILineSignService lineSignService) {
		this.lineSignService = lineSignService;
	}

	
	/**
	 * @return  the destOrgCode
	 */
	public String getDestOrgCode() {
		return destOrgCode;
	}

	
	/**
	 * @param destOrgCode the destOrgCode to set
	 */
	public void setDestOrgCode(String destOrgCode) {
		this.destOrgCode = destOrgCode;
	}

	
	/**
	 * @return  the destOrgName
	 */
	public String getDestOrgName() {
		return destOrgName;
	}

	
	/**
	 * @param destOrgName the destOrgName to set
	 */
	public void setDestOrgName(String destOrgName) {
		this.destOrgName = destOrgName;
	}

	
	/**
	 * @return  the destUserCode
	 */
	public String getDestUserCode() {
		return destUserCode;
	}

	
	/**
	 * @param destUserCode the destUserCode to set
	 */
	public void setDestUserCode(String destUserCode) {
		this.destUserCode = destUserCode;
	}

	
	/**
	 * @return  the destUserName
	 */
	public String getDestUserName() {
		return destUserName;
	}

	
	/**
	 * @param destUserName the destUserName to set
	 */
	public void setDestUserName(String destUserName) {
		this.destUserName = destUserName;
	}

	
	/**
	 * @return  the waybillNo
	 */
	public String getWaybillNo() {
		return waybillNo;
	}

	
	/**
	 * @param waybillNo the waybillNo to set
	 */
	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}
}
