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
 * PROJECT NAME	: pkp-predeliver
 * 
 * FILE PATH        	: src/main/java/com/deppon/foss/module/pickup/predeliver/server/process/HandleTrajectoryResponse.java
 * 
 * FILE NAME        	: HandleTrajectoryResponse.java
 * 
 * AUTHOR			: FOSS接送货系统开发组
 * 
 * HOME PAGE		: http://www.deppon.com
 * 
 * COPYRIGHT		: Copyright (c) 2012  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.pickup.predeliver.server.process;

import java.util.List;

import com.deppon.foss.framework.shared.util.string.StringUtil;
import com.deppon.foss.module.pickup.predeliver.api.shared.define.WayBillNoLocusConstant;
import com.deppon.foss.module.pickup.predeliver.api.shared.dto.WayBillNoLocusDTO;
import com.deppon.foss.util.DateUtils;

/**
 * 
 * PDA运单运行轨迹
 * 
 * @author ibm-wangfei
 * @date Jan 12, 2013 2:13:59 PM
 */
public class HandleTrajectoryResponse {
	/**
	 * @创建人：ibm-wangfei
	 * @创建时间：2012-12-15
	 * @方法描述：处理运单轨迹列表并返回瓶装好的DOM 参照邓夫伟的运行轨迹
	 * @修改记录：
	 * @param wayBillNoLocusDtoList 运单轨迹列表
	 * @return 货物追踪记录DOM
	 */
	public static void handleTrajectoryResponse(List<WayBillNoLocusDTO> wayBillNoLocusDtoList) {
		StringBuilder sb = new StringBuilder();
		// 定义要用到的变量：出发部门，到达部门，目的站，上一步操作类型，当前操作类型
		String operateDept, arrivedDept, destinationDept, preOperateType, operateType;
		WayBillNoLocusDTO wayBillNoLocusDto = wayBillNoLocusDtoList.get(0);
		// 获取目的站
		destinationDept = wayBillNoLocusDto.getDestinationStationOrgName();
		// 第一条默认为揽货成功（开单CREATE） 拼接开单信息
		// 操作类型
		sb.append("揽货成功[");
		// 操作部门所在城市
		sb.append(wayBillNoLocusDto.getOperateCityName());
		sb.append("]营业网点  库存中");
		// 设置轨迹
		wayBillNoLocusDto.setNotes(sb.toString());
		for (int i = 1; i < wayBillNoLocusDtoList.size(); i++) {
			sb = new StringBuilder();
			wayBillNoLocusDto = wayBillNoLocusDtoList.get(i);

			// 获取上一步的操作类型
			preOperateType = wayBillNoLocusDtoList.get(i - 1).getOperateType();
			// 当前操作类型
			operateType = wayBillNoLocusDto.getOperateType();
			// 出发
			if (StringUtil.equals(WayBillNoLocusConstant.OPERATE_TYPE_DEPART, operateType)) {
				// 转换出发部门和下一站部门的名字
				operateDept = handleDeptName(wayBillNoLocusDto.getOperateOrgName(), wayBillNoLocusDto.getOperateCityName(), destinationDept);
				arrivedDept = handleDeptName(wayBillNoLocusDto.getNextOrgName(), wayBillNoLocusDto.getNextCityName(), destinationDept);
				sb.append("运输中，离开");
				sb.append(operateDept);
				sb.append("，下一站");
				sb.append(arrivedDept);
				// 打印预计到达下一站信息
				if (wayBillNoLocusDto.getPlanArriveTime() != null) {
					sb.append("预计到达");
					sb.append(arrivedDept);
					sb.append("时间：");
					sb.append(DateUtils.convert(wayBillNoLocusDto.getPlanArriveTime(), DateUtils.DATE_TIME_FORMAT));
				}
			}
			// 到达
			else if (StringUtil.equals(WayBillNoLocusConstant.OPERATE_TYPE_ARRIVE, operateType)) {
				// 转换出发部门和下一站部门的名字
				operateDept = changeDeptName(wayBillNoLocusDto.getOperateOrgName(), wayBillNoLocusDto.getOperateCityName(), destinationDept, wayBillNoLocusDto.getDestinationStationCityName());
				if (wayBillNoLocusDto.getOperateOrgName().equals(destinationDept)) {
					sb.append("已到达目的地");
					sb.append(operateDept);
				} else {
					sb.append("运输中，已到达");
					sb.append(operateDept);
				}
			}
			// 提货通知
			else if (StringUtil.equals(WayBillNoLocusConstant.OPERATE_TYPE_NOTIFY_PICKUP, operateType)) {
				sb.append("提货通知，");
				sb.append(wayBillNoLocusDto.getOperateContent());
			}
			// 提货通知
			else if (StringUtil.equals(WayBillNoLocusConstant.OPERATE_TYPE_NOTIFY_DELIVER, operateType)) {
				sb.append("送货通知，");
				sb.append(wayBillNoLocusDto.getOperateContent());
			}
			// 提货通知
			else if (StringUtil.equals(WayBillNoLocusConstant.OPERATE_TYPE_NOTIFY_PRE, operateType)) {
				sb.append("提前通知，");
				sb.append(wayBillNoLocusDto.getOperateContent());
			}
			// 派送
			else if (StringUtil.equals(WayBillNoLocusConstant.OPERATE_TYPE_DELIVERY, operateType)) {
				// 如果当前操作类型和上一步操作类型相同，则说明是再次派送
				if (operateType.equals(preOperateType)) {
					sb.append("再次派送（派送人员：");
					sb.append(wayBillNoLocusDto.getDeliveryName());
					sb.append("，电话：");
					sb.append(wayBillNoLocusDto.getDeliveryPhone());
					sb.append("）");
				} else {
					sb.append("派送中（派送人员：");
					sb.append(wayBillNoLocusDto.getDeliveryName());
					sb.append("，电话：");
					sb.append(wayBillNoLocusDto.getDeliveryPhone());
					sb.append("）");
				}
			}
			// 派送拉回
			else if ("DELIVERY_RETURN".equals(operateType)) {
				sb.append("派送失败（原因：");
				sb.append(wayBillNoLocusDto.getOperateContent());
				sb.append("）");
			}
			// 正常签收
			else if ("NORMAL_SIGN".equals(operateType)) {
				sb.append("正常签收，签收人:");
				sb.append(wayBillNoLocusDto.getOperateContent());
			}
			// 终止
			// else if("ABORTED".equals(operateType)){
			//
			// }
			// 异常签收
			else if ("EXCEPTION_SIGN".equals(operateType)) {
				sb.append("异常签收，签收人:");
				sb.append(wayBillNoLocusDto.getOperateContent());
			}
			// 部分签收
			else if ("PART_SIGN".equals(operateType)) {
				sb.append("部分签收，签收人:");
				sb.append(wayBillNoLocusDto.getOperateContent());
			}
			wayBillNoLocusDto.setNotes(sb.toString());
		}
	}

	/**
	 * @作者：易水清
	 * @创建时间：2012-2-8
	 * @功能描述：
	 * @param deptName
	 * @param cityName
	 * @return
	 * @是否废弃：否
	 * @修改描述：
	 */
	private static String changeDeptName(String deptName, String cityName, String destiDept, String destination) {
		if (deptName.indexOf("专线") > 0) {
			return "[" + cityName + "]运输中心";
		}
		else if (deptName.indexOf("偏线") > 0 || deptName.indexOf("外场") > 0 || deptName.indexOf("运作部") > 0){
			return "[" + cityName + "]运输中心";
		}
		else if (deptName.indexOf("空运总调") > 0){
			return "[" + cityName + "]空运调度中心";
		}
		else if (deptName.equals(destiDept)){
			return "[" + destination + "]"; // 交接到提货网点就是目的站
		}
		else if (deptName.indexOf("营业") > 0 || deptName.indexOf("接货开单查询组") > 0){
			return "[" + cityName + "]营业网点";
		}
		else if (deptName.indexOf("派送部") > 0){
			return "[" + cityName + "]派送网点";
		}
		else {
			return deptName;
		}
	}

	/**
	 * @创建人：邓夫伟
	 * @创建时间：2012-12-18
	 * @方法描述：重新写的处理部门名字的方法
	 * @修改记录：
	 * @param operateDept 当前操作部门名称
	 * @param operateDeptCity 当前操作部门城市
	 * @param destDeptName 目的站部门名称
	 * @return
	 */
	private static String handleDeptName(String operateDept, String operateDeptCity, String destDeptName) {
		String operateDeptNew = "";
		if (StringUtil.isEmpty(operateDept) || StringUtil.isEmpty(operateDeptCity) || StringUtil.isEmpty(destDeptName)) {
			return null;
		}
		if (operateDept.indexOf("专线") > 0 || operateDept.indexOf("偏线") > 0 || operateDept.indexOf("外场") > 0 || operateDept.indexOf("运作部") > 0) {
			operateDeptNew = "[" + operateDeptCity + "]运输中心";
		} else if (operateDept.indexOf("空运总调") > 0) {
			operateDeptNew = "[" + operateDeptCity + "]空运调度中心";
		} else if (operateDept.equals(destDeptName)) {
			// TODO 目的站与之前的不符合，现在的目的站是以前的提货网点，以前的目的地是另外一个字符串
			operateDeptNew = "目的地 [" + destDeptName + "]";
		} else if (operateDept.indexOf("营业") > 0 || operateDept.indexOf("接货开单查询组") > 0) {
			operateDeptNew = "[" + operateDeptCity + "]营业网点";
		} else if (operateDept.indexOf("派送部") > 0) {
			operateDeptNew = "[" + operateDeptCity + "]派送网点";
		} else {
			operateDeptNew = operateDept;
		}
		return operateDeptNew;
	}
}