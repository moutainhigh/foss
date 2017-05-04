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
 *  FILE PATH          :/IPrintLabelService.java
 * 
 *  FILE NAME          :IPrintLabelService.java
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

import java.io.InputStream;
import java.util.List;

import com.deppon.foss.framework.service.IService;
import com.deppon.foss.module.pickup.waybill.shared.dto.BarcodePrintLabelDto;
import com.deppon.foss.module.pickup.waybill.shared.dto.EWaybillPrintDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.domain.PrintLabelEntity;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.GoodsLabelPrintDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.PrintLabelDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.dto.SortingAndPringLabelDto;
import com.deppon.foss.module.transfer.exceptiongoods.api.shared.exception.NoLabelGoodsException;
/**
 * 定义了标签打印信息的相关操作
 * @author 097457-foss-wangqiang
 * @date 2012-12-25 下午12:01:40
 */
public interface IPrintLabelService extends IService{
	
	/**
	 * 增加打印操作信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午5:20:30
	 */
	int addPrintLabel(PrintLabelEntity printLabelEntity);
	
	/**
	 * 查询货物标签打印信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午7:03:43
	 */
	List<PrintLabelDto> queryPrintLabelInfo(PrintLabelDto printLabelDto);
	
	
	/**
	* @description 查询货物打印信息
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:03:23
	*/
	List<PrintLabelDto> queryPrintLabelInfoExpress(PrintLabelDto printLabelDto,int start,int limit);
	
	
	
	

	/**
	* @description 查询货物打印信息
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午3:52:31
	*/
	Long queryPrintLabelInfoExpressCount(PrintLabelDto printLabelDto);
	
	/**
	* @description 保存打印指定标签操作信息 
	* @param waybillNo
	* @param serialNoList
	* @param userCode
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2015年2月4日 下午2:57:15
	*/
	int addPrintAppointedLabel(String waybillNo, List<String> serialNoList, String userCode,int printType);
	
	/**	 * 打印指定货件标签
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-26 下午7:14:42
	 */
	List<BarcodePrintLabelDto> printAppointedLabel(String waybillNo, List<String> serialNoList) throws NoLabelGoodsException;
	
	
	
	
	/**
	* @description 打印指定货件标签 (中转场打印快递标签信息封装)
	* @param waybillNo
	* @param serialNoList
	* @return
	* @throws NoLabelGoodsException
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年2月5日 下午3:56:20
	*/
	List<BarcodePrintLabelDto> getLabelPrintInfoExpress(String waybillNo, List<String> serialNoList) throws NoLabelGoodsException;
	
	/**
	* @description 补打电子运单
	* @param waybillNo
	* @param serialNoList
	* @return
	* @throws NoLabelGoodsException
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年9月2日 上午10:41:08
	*/
	List<EWaybillPrintDto> printEWaybillLabel(String waybillNo, List<String> serialNoList) throws NoLabelGoodsException;
	
	/**
	 * 保存打印指定标签操作信息 
	 * 更新相关联无标签货物是否已重打标签状态
	 * @author 097457-foss-wangqiang
	 * @date 2012-11-27 下午4:25:17
	 */
	int addPrintAppointedLabel(String waybillNo, List<String> serialNoList, String userCode);
	
	
	
	/**
	 * 提供给综合查询：根据运单号查询货件标签打印信息
	 * @author 097457-foss-wangqiang
	 * @date 2012-12-24 上午10:03:42
	 */
	List<GoodsLabelPrintDto> queryLabelPrintByWaybillNo(String waybillNo);
	
	
	/**
	* @description 根据运单号判断是否电子运单
	* @param waybillNo
	* @return
	* @version 1.0
	* @author 14022-foss-songjie
	* @update 2014年9月2日 上午10:50:35
	*/
	boolean isEWaybillInfoByWaybillNo(String waybillNo);
	

	/**
	* @param waybillNo
	* @return
	* @description 提供给综合查询：根据运单号查询货件标签打印信息 明细
	* @version 1.0
	* @author 140022-foss-songjie
	* @update 2013-7-6 上午10:18:33
	*/
	List<GoodsLabelPrintDto> queryLabelPrintByWaybillNoDetail(String waybillNo);
	/**
	 * @author nly
	 * @date 2015年4月21日 下午5:07:44
	 * @function 查询最晚标签打印记录
	 * @param waybillNo
	 * @return
	 */
	PrintLabelEntity queryLastLabelPrintByWaybillNo(String waybillNo);
	
	
	/**
	* @description 根据部门code查询标签打印记录
	* @param printLabelDto
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 下午2:23:30
	*/
	List<SortingAndPringLabelDto> queryPrintLabelInfoExpressByOrgCode(PrintLabelDto printLabelDto,int start,int limit);
	
	
	/**
	* @description 根据部门code查询标签打印count
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月9日 下午2:53:37
	*/
	Long queryPrintLabelInfoExpressByOrgCodeCount(PrintLabelDto printLabelDto);
	/**
	* @description 根据部门code查询上分拣记录
	* @param printLabelDto
	* @param start
	* @param limit
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月8日 下午5:26:57
	*/
	List<SortingAndPringLabelDto> querySortingInfoByOrgCode(PrintLabelDto printLabelDto,int start,int limit);
	
	
	/**
	* @description 根据部门code查询上分拣count
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月9日 下午3:19:36
	*/
	Long querySortingInfoByOrgCodeCount(PrintLabelDto printLabelDto);
	
	
	
	/**
	* @description 导出标签打印记录或上分拣记录到Excel
	* @param ids
	* @param printLabelDto
	* @return
	* @version 1.0
	* @author 218381-foss-lijie
	* @update 2015年7月10日 下午2:43:49
	*/
	InputStream exportExcelStream(String ids,SortingAndPringLabelDto sortingAndPringLabelDto);
}








