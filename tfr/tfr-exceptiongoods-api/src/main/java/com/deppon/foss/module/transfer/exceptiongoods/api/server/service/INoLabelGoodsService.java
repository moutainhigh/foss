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
 *  PROJECT NAME  : tfr-exceptiongoods-api
 *  
 * package_name  : 
 *  
 *  FILE PATH          :/INoLabelGoodsService.java
 * 
 *  FILE NAME          :INoLabelGoodsService.java
 *  
 *  AUTHOR  : FOSS中转系统开发组
 *  
 *  TIME              : 
 *  
 *  HOME PAGE    :  http://www.deppon.com
 *  
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.exceptiongoods.api.server.service;

import java.util.List;

import com.deppon.foss.framework.exception.BusinessException;
import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.base.baseinfo.api.shared.domain.OrgAdministrativeInfoEntity;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.NolabelRequestFromQMSDto;
import com.deppon.foss.module.transfer.common.api.shared.dto.ResponseFromQmsDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.NoLabelGoodsEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PhotoUploadDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.vo.NoLabelGoodsVo;
/**
 * 定义了无标签多货的业务操作方法
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:02:23
 */
public interface INoLabelGoodsService extends IService{
	
	/**
	 * 保存无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-19 上午10:27:50
	 */
	int addNoLabelGoods(NoLabelGoodsEntity noLabelGoods, PhotoUploadDto photoUploadDto);
	
	/**
	 * 查询无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-20 下午3:11:50
	 */
	List<NoLabelGoodsEntity> queryNoLabelGoods(NoLabelGoodsEntity noLabelGoods, int limit, int start);
	/**
	 * 查询无标签货物总记录数
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-20 下午3:43:25
	 */
	Long queryNoLabelGoodsCount(NoLabelGoodsEntity noLabelGoods);
	
	/**
	 * 生成无标签货物运单号
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-22 下午2:32:10
	 */
	String generateNoLabelGoodsBillNo();
	
	/**
	 * 登入异常货区
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-28 上午10:18:12
	 */
	int loginExceptionStock(List<NoLabelGoodsEntity> noLabelGoodsList, 
			String userCode, String userName, String currentOrgCode);
	/**
	 * 登出异常货区
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-28 下午3:55:14
	 */
	int logoutExceptionStock(List<NoLabelGoodsEntity> noLabelGoodsList, 
			String userCode, String userName, String currentOrgCode) throws BusinessException;
	/**
	 * 更新无标签货物的打印信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-28 上午8:57:09
	 */
	int updateNoLabelGoodsPrintInfo(List<NoLabelGoodsEntity> noLabelGoodsList, 
			String userCode, String userName);
	/**
	 * 上报OA
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-28 下午7:46:56
	 */
	void reportOANoLabelGoods(List<NoLabelGoodsEntity> noLabelGoodsList, 
			String userCode, String userName) throws BusinessException;
	
	/**
	 * 更新无标签货物的OA处理结果
	 * @param noLabelBillNo  无标签运单号
	 * @param findGoodsStatus  找货状态
	 * @param oaErrorNo  OA差错编号
	 * @param originalWaybillNo  原运单号
	 * @param originalSerialNo  原流水号
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-3 上午11:01:53
	 */
	int updateNoLabelGoodsProcessStatus(String noLabelBillNo, String findGoodsStatus, String oaErrorNo,
			String originalWaybillNo, String originalSerialNo);
	
	
	/**
	* @description 转弃货申请传入一个差异编号oaErrorNo，中转调用OA接口获取对应的状态，将OA返回的状态 再返回给异常货处理模块
	* @param oaErrorNo OA差错编号
	* @return 0:变更成功; 1:货物已被认领 ; 2：其他原因变更失败;
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年2月22日 上午9:59:27
	*/
	int updateNoLabelGoodsProcessStatus (String oaErrorNo) throws Exception;
	
	/**
	 * 查询当前用户所在大部门（外场）
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-19 上午10:31:41
	 */
	OrgAdministrativeInfoEntity queryBigOrg(OrgAdministrativeInfoEntity currentOrg);
	
	/**
	 * 验证用户名 密码
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-24 下午3:49:22
	 */
	boolean verifyUser(String userCode, String password);
	
	/**
	 * 根据无标签单号更新该单号下的所有无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-10 下午5:58:13
	 */
	int updateNoLabelGoodsByBillNo(NoLabelGoodsEntity noLabelGoods, PhotoUploadDto photoUploadDto);
	
	/**
	 * 删除无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2013-1-14 下午2:03:22
	 */
	int deleteNoLabelGoods(NoLabelGoodsEntity noLabelGoods);
	/**
	 * 根据无标签运单号、流水号查询
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-22 下午4:19:51
	 */
	NoLabelGoodsEntity queryUniqNoLabelGoods(String noLabelBillNo, String noLabelSerialNo);
	/**
	 * 更新无标签货物
	 * @author 097457-foss-wangqiang
	 * @date 2013-5-22 下午5:45:21
	 */
	int updateNoLabelGoods(NoLabelGoodsEntity noLabelGoods); 
	
	/**
	 * 无标签转弃货的业务流程
	 * @param noLabelGoodsPojo
	 * @throws BusinessException
	 */
	void toAbandonService(NoLabelGoodsVo noLabelGoodsPojo);
	
	
	/**
	* @description 获取打印内容,保存打印指定标签操作信息 同时进行上分拣扫描
	* @param waybillNo
	* @param slist
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午4:37:16
	*/
	List<BarcodePrintLabelDto> printAppointedLabelExpress(String waybillNo , String serialNo, List<String> slist);
	/**
	* @description 上分拣扫描同时判断此外场是否是此运单号的始发外场
	* @param waybillNo
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月3日 下午3:10:08
	*/
	List<BarcodePrintLabelDto> sortingAndPringLabel(String waybillNo ,String serialNo);
	
	/**
	 * @author nly
	 * @date 2015年6月13日 下午2:50:10
	 * @function 上报QMS无标签多货
	 */
	void reportQmsNoLabelGoods();
	/**
	 * @author nly
	 * @date 2015年6月15日 下午4:16:52
	 * @function 根据品名查询品类
	 * @param goodsName
	 * @return
	 */
	String queryTypeByGoodsName(String goodsName);
	/**
	 * @author nly
	 * @date 2015年6月30日 下午3:59:04
	 * @function QMS认领无标签多货的同时进行登出操作
	 * @param nolabelDto
	 * @param responseDto
	 * @return
	 */
	ResponseFromQmsDto findAndLogOut(NolabelRequestFromQMSDto nolabelDto,ResponseFromQmsDto responseDto);
	
	
	/**
	* @description 根据运单号,判断部门是否是第一外场
	* @param waybillNo
	* @param orgCode
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月16日 下午6:40:50
	*/
	String isFirstTransfer(String waybillNo , String serialNo, String orgCode);
}