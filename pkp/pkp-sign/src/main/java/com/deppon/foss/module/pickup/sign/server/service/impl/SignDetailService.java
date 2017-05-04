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
 * PROJECT NAME	: pkp-sign
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/sign/server/service/impl/SignDetailService.java
 * 
 * FILE NAME        	: SignDetailService.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.sign.server.service.impl;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.ArriveSheetConstants;
import com.deppon.foss.module.pickup.pricing.api.shared.define.PricingConstants.ProductEntityConstants;
import com.deppon.foss.module.pickup.sign.api.server.dao.ISignDetailDao;
import com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService;
import com.deppon.foss.module.pickup.sign.api.server.service.IWaybillSignResultService;
import com.deppon.foss.module.pickup.sign.api.shared.define.SignConstants;
import com.deppon.foss.module.pickup.sign.api.shared.domain.SignDetailEntity;
import com.deppon.foss.module.pickup.sign.api.shared.domain.WaybillSignResultEntity;
import com.deppon.foss.module.pickup.sign.api.shared.dto.StockDto;
import com.deppon.foss.module.pickup.waybill.api.server.service.IWaybillManagerService;
import com.deppon.foss.module.pickup.waybill.shared.domain.WaybillEntity;
import com.deppon.foss.util.define.FossConstants;
/**
 * 
	签收明细
 * @author foss-meiying
 * @date 2012-10-22 下午3:48:43
 * @since
 * @version
 */
public class SignDetailService implements ISignDetailService {
	/**
	 * 签收明细dao
	 */
	private ISignDetailDao signDetailDao;
	/**
	 * 运单管理接口
	 */
	private IWaybillManagerService waybillManagerService;
	/**
	 *  运单签收结果
	 *  接口
	 */
	private IWaybillSignResultService waybillSignResultService;
	/**
	 * 根据到达联编号查询签收明细
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:12:06
	 * @param arrivesheetNo
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService#queryByArrivesheetId(java.lang.String)
	 */
	@Override
	public List<SignDetailEntity> queryByArrivesheetNo(String arrivesheetNo) {
		return signDetailDao.queryByArrivesheetNo(arrivesheetNo);
	}
	/**
	 * 保存签收流水号到签收明细表里
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:12:21
	 * @param list
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService#addSignDetail(java.util.List)
	 */
	@Override
	@Transactional
	public int addSignDetail(List<SignDetailEntity> list) {
		return signDetailDao.addSignDetail(list);
	}
	
	/**
	 * 根据主键id查询签收明细
	 * @author foss-meiying
	 * @date 2012-12-18 下午7:12:38
	 * @param id
	 * @return 
	 * @see com.deppon.foss.module.pickup.sign.api.server.service.ISignDetailService#queryByPrimaryKey(java.lang.String)
	 */
	@Override
	public SignDetailEntity queryByPrimaryKey(String id) {
		return signDetailDao.queryByPrimaryKey(id);
	}
	/**
     * 单条添加签收明细信息
     * @author foss-meiying
     * @date 2012-12-20 上午9:36:15
     * @param signDetailEntity
     * @return
     * @see
     */
	@Override
	public int addSignDetailInfo(SignDetailEntity signDetailEntity) {
		return signDetailDao.addSignDetailInfo(signDetailEntity);
	}
	 /**
     * 给中转组提供 判断货物是否已签收的接口，参数（运单号、流水号）
     * @author foss-meiying
     * @date 2013-6-12 上午16:12:15
     * @param waybillNo 运单号
     * @param serialNo 流水号
     * @return String
     * @see
     */
   public String querySerialNoIsSign(String waybillNo,String serialNo){
	   if(StringUtils.isBlank(waybillNo)){
		   return FossConstants.NO;
	   }
		WaybillEntity waybill = waybillManagerService.queryPartWaybillByNo(waybillNo);
		if(waybill != null){
			if(ProductEntityConstants.PRICING_PRODUCT_PARTIAL_LINE.equals(waybill.getProductCode()) ||ProductEntityConstants.PRICING_PRODUCT_AIR_FREIGHT.equals(waybill.getProductCode())){
				WaybillSignResultEntity entity = new WaybillSignResultEntity(waybillNo,FossConstants.YES);
				if(waybillSignResultService.queryWaybillSignResultByWaybillNo(entity)!=null){
					return FossConstants.YES;
				}else {
					return FossConstants.NO;
				}
			}else {
				WaybillSignResultEntity entity = new WaybillSignResultEntity(waybillNo,FossConstants.YES);
				WaybillSignResultEntity result =waybillSignResultService.queryWaybillSignResultByWaybillNo(entity);
				if(result != null && SignConstants.SIGN_STATUS_ALL.equals(result.getSignStatus())){
					return FossConstants.YES;
				}
				StockDto dto = new StockDto();
				dto.setWaybillNo(waybillNo);
				dto.setSerialNo(serialNo);
				dto.setActive(FossConstants.YES);
				dto.setDestroyed(FossConstants.NO);
				dto.setStatus(ArriveSheetConstants.STATUS_SIGN);
				return signDetailDao.querySerialNoIsSign(dto);
			}
			
		}else {
			return FossConstants.NO;
		}
	   
    }
   /**
    * 给综合查询提供 已签收的流水号接口，参数（运单号，有效，是否作废，到达联状态是签收）
    * @author foss-meiying
    * @date 2013-6-12 上午16:12:15
    * @param waybillNo 运单号
    * @param serialNo 流水号
    * @return String
    * @see
    */
  public List<String> querySerialNoByWaybillNo(StockDto dto){
	  return signDetailDao.querySerialNoByWaybillNo(dto);
  }
  /**
   * 
   * 根据到达联编号，流水号满足条件的信息
   * @author 159231-foss-meiying
   * @date 2013-11-22 下午6:23:44
   */
	public List<SignDetailEntity> querySignDetailByParams(SignDetailEntity dto) {
		return (List<SignDetailEntity>) signDetailDao.querySignDetailByParams(dto);
	}
	
    /**
     * 根据到达联编号更新流水号签收
     * @author Foss-chenjunying DMANA-9716
     * @date 2015-03-25 10:36:50
     */
	@Override
	public int updateByArrivesheetNo(SignDetailEntity entity) {
		return signDetailDao.updateByArrivesheetNo(entity);
	}

	/**
     * 根据运单号查询异常签收明细
     * @author Foss-bieyexiong
     * @date 2016-02-22 14:10:36
     */
	@Override
	public List<SignDetailEntity> querySignDetailByWaybillNo(String waybillNo) {
		return signDetailDao.querySignDetailByWaybillNo(waybillNo);
	}

	
	/**
	 * Gets the 签收明细dao.
	 *
	 * @return the 签收明细dao
	 */
	public ISignDetailDao getSignDetailDao() {
		return signDetailDao;
	}

	/**
	 * Sets the 签收明细dao.
	 *
	 * @param signDetailDao the new 签收明细dao
	 */
	public void setSignDetailDao(ISignDetailDao signDetailDao) {
		this.signDetailDao = signDetailDao;
	}
	public void setWaybillManagerService(
			IWaybillManagerService waybillManagerService) {
		this.waybillManagerService = waybillManagerService;
	}
	public void setWaybillSignResultService(
			IWaybillSignResultService waybillSignResultService) {
		this.waybillSignResultService = waybillSignResultService;
	}
	/**
  	 * 根据到达联编号、流水号、旧的签收情况可选的更新数据
  	 * @author 
  	 * @date 
  	 */
	@Override
	public int updateByParams(SignDetailEntity dto) {
		return signDetailDao.updateByParams(dto);
	}
}