/*******************************************************************************
 * initial comments.
 * <p>
 * Copyright 2013 TFR TEAM
 * <p>
 * Licensed under the DEPPON License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.deppon.com/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Contributors:
 * 038300-foss-pengzhen - initial API and implementation
 * <p>
 * PROJECT NAME  : tfr-pda-api
 * <p>
 * FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/LoadGoodsDetailDto.java
 * <p>
 * FILE NAME          :LoadGoodsDetailDto.java
 * <p>
 * AUTHOR  : FOSS中转系统开发组
 * <p>
 * TIME              :
 * <p>
 * HOME PAGE    :  http://www.deppon.com
 * <p>
 * COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 * <p>
 * Copyright 2013 TFR TEAM
 * <p>
 * Licensed under the DEPPON License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.deppon.com/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Contributors:
 * 038300-foss-pengzhen - initial API and implementation
 * <p>
 * PROJECT NAME  : tfr-pda-api
 * <p>
 * FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/LoadGoodsDetailDto.java
 * <p>
 * FILE NAME          :LoadGoodsDetailDto.java
 * <p>
 * AUTHOR  : FOSS中转系统开发组
 * <p>
 * TIME              :
 * <p>
 * HOME PAGE    :  http://www.deppon.com
 * <p>
 * COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
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
 *  PROJECT NAME  : tfr-pda-api
 *
 *  FILE PATH     : src/main/java/com/deppon/foss/module/transfer/pda/api/shared/dto/LoadGoodsDetailDto.java
 *
 *  FILE NAME          :LoadGoodsDetailDto.java
 *
 *  AUTHOR  : FOSS中转系统开发组
 *
 *  TIME              : 
 *
 *  HOME PAGE    :  http://www.deppon.com
 *
 *  COPYRIGHT    : Copyright (c) 2013  Deppon All Rights Reserved.
 ******************************************************************************/
package com.deppon.foss.module.transfer.load.dubbo.api.define;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 返回给PDA装车任务列表Dto
 * @author dp-duyi
 * @date 2012-11-19 上午9:14:56
 */
public class LoadGoodsDetailDto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1831453138530519134L;

    /**是否合车*/
    private String beJoinCar;
    /**库存件数*/
    private int stockQty;
    /**贵重物品货区编码*/
    private String valueGoodsAreaCode;
    /**包装货区编码*/
    private String packGoodsAreaCode;
    /**运输性质编码*/
    private String transportTypeCode;
    /**库区名称*/
    private String stockAreaName;
    /**库区编码*/
    private String stockAreaCode;
    /**库区类型*/
    private String stockAreaType;
    /**任务编号*/
    private String taskNo;
    /**运单号*/
    private String wayBillNo;
    /**流水号*/
    private List<PDAGoodsSerialNoDto> serialNos;
    /**开单件数*/
    private int wayBillQty;
    /**重量*/
    private double weight;
    /**体积*/
    private double volume;
    /**货名*/
    private String goodsName;
    /**入库时间*/
    private Date stockTime;
    /**运输性质*/
    private String transportType;
    /**收货部门编码*/
    private String receiveOrgCode;
    /**收货部门名称*/
    private String receiveOrgName;
    /**到达部门编码*/
    private String reachOrgCode;
    /**到达部门名称*/
    private String reachOrgName;
    /**包装*/
    private String packing;
    /**是否必走货*/
    private String bePriorityGoods;
    /**是否有更改*/
    //private String modifyState;
    private String stationNumber;
    /**是否贵重物品*/
    private String isValue;
    /**更改内容*/
    //private String modifyContent;
    /**备注*/
    private String notes;
    /**操作件数*/
    private int operateQty;
    /**行政区域名称*/
    private String adminiRegions;
    /***收货人地址区域**/
    private String receiveCustDistName; //RECEIVE_CUSTOMER_DIST_CODE

    private String packageRemark;
    /***是否电子面单**/
    private String beEWaybill;

    public String getValueGoodsAreaCode() {
        return valueGoodsAreaCode;
    }

    public void setValueGoodsAreaCode(String valueGoodsAreaCode) {
        this.valueGoodsAreaCode = valueGoodsAreaCode;
    }

    public String getPackGoodsAreaCode() {
        return packGoodsAreaCode;
    }

    public void setPackGoodsAreaCode(String packGoodsAreaCode) {
        this.packGoodsAreaCode = packGoodsAreaCode;
    }

    public String getTransportTypeCode() {
        return transportTypeCode;
    }

    public void setTransportTypeCode(String transportTypeCode) {
        this.transportTypeCode = transportTypeCode;
    }

    /**
     * Gets the 库存件数.
     *
     * @return the 库存件数
     */
    public int getStockQty() {
        return stockQty;
    }

    /**
     * Sets the 库存件数.
     *
     * @param stockQty the new 库存件数
     */
    public void setStockQty(int stockQty) {
        this.stockQty = stockQty;
    }

    /**
     * Gets the 是否合车.
     *
     * @return the 是否合车
     */
    public String getBeJoinCar() {
        return beJoinCar;
    }

    /**
     * Sets the 是否合车.
     *
     * @param beJoinCar the new 是否合车
     */
    public void setBeJoinCar(String beJoinCar) {
        this.beJoinCar = beJoinCar;
    }

    public String getStationNumber() {
        return stationNumber;
    }

    public void setStationNumber(String stationNumber) {
        this.stationNumber = stationNumber;
    }

    public String getAdminiRegions() {
        return adminiRegions;
    }

    public void setAdminiRegions(String adminiRegions) {
        this.adminiRegions = adminiRegions;
    }

    /**
     * Gets the 操作件数.
     *
     * @return the 操作件数
     */
    public int getOperateQty() {
        return operateQty;
    }

    /**
     * Sets the 操作件数.
     *
     * @param operateQty the new 操作件数
     */
    public void setOperateQty(int operateQty) {
        this.operateQty = operateQty;
    }

    /**
     * Gets the 库区名称.
     *
     * @return the 库区名称
     */
    public String getStockAreaName() {
        return stockAreaName;
    }

    /**
     * Sets the 库区名称.
     *
     * @param stockAreaName the new 库区名称
     */
    public void setStockAreaName(String stockAreaName) {
        this.stockAreaName = stockAreaName;
    }

    /**
     * Gets the 库区编码.
     *
     * @return the 库区编码
     */
    public String getStockAreaCode() {
        return stockAreaCode;
    }

    /**
     * Sets the 库区编码.
     *
     * @param stockAreaCode the new 库区编码
     */
    public void setStockAreaCode(String stockAreaCode) {
        this.stockAreaCode = stockAreaCode;
    }

    /**
     * Gets the 库区类型.
     *
     * @return the 库区类型
     */
    public String getStockAreaType() {
        return stockAreaType;
    }

    /**
     * Sets the 库区类型.
     *
     * @param stockAreaType the new 库区类型
     */
    public void setStockAreaType(String stockAreaType) {
        this.stockAreaType = stockAreaType;
    }

    /**
     * Gets the 任务编号.
     *
     * @return the 任务编号
     */
    public String getTaskNo() {
        return taskNo;
    }

    /**
     * Sets the 任务编号.
     *
     * @param taskNo the new 任务编号
     */
    public void setTaskNo(String taskNo) {
        this.taskNo = taskNo;
    }

    /**
     * Gets the 运单号.
     *
     * @return the 运单号
     */
    public String getWayBillNo() {
        return wayBillNo;
    }

    /**
     * Sets the 运单号.
     *
     * @param wayBillNo the new 运单号
     */
    public void setWayBillNo(String wayBillNo) {
        this.wayBillNo = wayBillNo;
    }

    /**
     * Gets the 流水号.
     *
     * @return the 流水号
     */
    public List<PDAGoodsSerialNoDto> getSerialNos() {
        return serialNos;
    }

    /**
     * Sets the 流水号.
     *
     * @param serialNos the new 流水号
     */
    public void setSerialNos(List<PDAGoodsSerialNoDto> serialNos) {
        this.serialNos = serialNos;
    }

    /**
     * Gets the 开单件数.
     *
     * @return the 开单件数
     */
    public int getWayBillQty() {
        return wayBillQty;
    }

    /**
     * Sets the 开单件数.
     *
     * @param wayBillQty the new 开单件数
     */
    public void setWayBillQty(int wayBillQty) {
        this.wayBillQty = wayBillQty;
    }

    /**
     * Gets the 重量.
     *
     * @return the 重量
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the 重量.
     *
     * @param weight the new 重量
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

    /**
     * Gets the 体积.
     *
     * @return the 体积
     */
    public double getVolume() {
        return volume;
    }

    /**
     * Sets the 体积.
     *
     * @param volume the new 体积
     */
    public void setVolume(double volume) {
        this.volume = volume;
    }

    /**
     * Gets the 货名.
     *
     * @return the 货名
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * Sets the 货名.
     *
     * @param goodsName the new 货名
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * Gets the 入库时间.
     *
     * @return the 入库时间
     */
    public Date getStockTime() {
        return stockTime;
    }

    /**
     * Sets the 入库时间.
     *
     * @param stockTime the new 入库时间
     */
    public void setStockTime(Date stockTime) {
        this.stockTime = stockTime;
    }

    /**
     * Gets the 运输性质.
     *
     * @return the 运输性质
     */
    public String getTransportType() {
        return transportType;
    }

    /**
     * Sets the 运输性质.
     *
     * @param transportType the new 运输性质
     */
    public void setTransportType(String transportType) {
        this.transportType = transportType;
    }

    /**
     * Gets the 收货部门编码.
     *
     * @return the 收货部门编码
     */
    public String getReceiveOrgCode() {
        return receiveOrgCode;
    }

    /**
     * Sets the 收货部门编码.
     *
     * @param receiveOrgCode the new 收货部门编码
     */
    public void setReceiveOrgCode(String receiveOrgCode) {
        this.receiveOrgCode = receiveOrgCode;
    }

    /**
     * Gets the 收货部门名称.
     *
     * @return the 收货部门名称
     */
    public String getReceiveOrgName() {
        return receiveOrgName;
    }

    /**
     * Sets the 收货部门名称.
     *
     * @param receiveOrgName the new 收货部门名称
     */
    public void setReceiveOrgName(String receiveOrgName) {
        this.receiveOrgName = receiveOrgName;
    }

    /**
     * Gets the 到达部门编码.
     *
     * @return the 到达部门编码
     */
    public String getReachOrgCode() {
        return reachOrgCode;
    }

    /**
     * Sets the 到达部门编码.
     *
     * @param reachOrgCode the new 到达部门编码
     */
    public void setReachOrgCode(String reachOrgCode) {
        this.reachOrgCode = reachOrgCode;
    }

    /**
     * Gets the 到达部门名称.
     *
     * @return the 到达部门名称
     */
    public String getReachOrgName() {
        return reachOrgName;
    }

    /**
     * Sets the 到达部门名称.
     *
     * @param reachOrgName the new 到达部门名称
     */
    public void setReachOrgName(String reachOrgName) {
        this.reachOrgName = reachOrgName;
    }

    /**
     * Gets the 包装.
     *
     * @return the 包装
     */
    public String getPacking() {
        return packing;
    }

    /**
     * Sets the 包装.
     *
     * @param packing the new 包装
     */
    public void setPacking(String packing) {
        this.packing = packing;
    }

    /**
     * Gets the 是否必走货.
     *
     * @return the 是否必走货
     */
    public String getBePriorityGoods() {
        return bePriorityGoods;
    }

    /**
     * Sets the 是否必走货.
     *
     * @param bePriorityGoods the new 是否必走货
     */
    public void setBePriorityGoods(String bePriorityGoods) {
        this.bePriorityGoods = bePriorityGoods;
    }

	/*public String getModifyState() {
        return modifyState;
	}

	public void setModifyState(String modifyState) {
		this.modifyState = modifyState;
	}*/

    /**
     * Gets the 是否贵重物品.
     *
     * @return the 是否贵重物品
     */
    public String getIsValue() {
        return isValue;
    }

    /**
     * Sets the 是否贵重物品.
     *
     * @param isValue the new 是否贵重物品
     */
    public void setIsValue(String isValue) {
        this.isValue = isValue;
    }

    /**
     * Gets the 更改内容.
     *
     * @return the 更改内容
     */
    /*public String getModifyContent() {
		return modifyContent;
	}*/

    /**
     * Sets the 更改内容.
     *
     * @param modifyContent the new 更改内容
     */
	/*public void setModifyContent(String modifyContent) {
		this.modifyContent = modifyContent;
	}*/

    /**
     * Gets the 备注.
     *
     * @return the 备注
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the 备注.
     *
     * @param notes the new 备注
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * get 收货人地址区域
     * @return
     */
    public String getReceiveCustDistName() {
        return receiveCustDistName;
    }

    /**
     * set 收货人地址区域
     * @param receiveCustDistName
     */
    public void setReceiveCustDistName(String receiveCustDistName) {
        this.receiveCustDistName = receiveCustDistName;
    }

    public final String getPackageRemark() {
        return packageRemark;
    }

    public final void setPackageRemark(String packageRemark) {
        this.packageRemark = packageRemark;
    }

    public String getBeEWaybill() {
        return beEWaybill;
    }

    public void setBeEWaybill(String beEWaybill) {
        this.beEWaybill = beEWaybill;
    }

    @Override
    public String toString() {
        return "LoadGoodsDetailDto [beJoinCar=" + beJoinCar + ", stockQty=" + stockQty + ", valueGoodsAreaCode="
                + valueGoodsAreaCode + ", packGoodsAreaCode=" + packGoodsAreaCode + ", transportTypeCode="
                + transportTypeCode + ", getValueGoodsAreaCode()=" + getValueGoodsAreaCode()
                + ", getPackGoodsAreaCode()=" + getPackGoodsAreaCode() + ", getTransportTypeCode()="
                + getTransportTypeCode() + ", getStockQty()=" + getStockQty() + ", getBeJoinCar()=" + getBeJoinCar()
                + ", getStationNumber()=" + getStationNumber() + ", getAdminiRegions()=" + getAdminiRegions()
                + ", getOperateQty()=" + getOperateQty() + ", getStockAreaName()=" + getStockAreaName()
                + ", getStockAreaCode()=" + getStockAreaCode() + ", getStockAreaType()=" + getStockAreaType()
                + ", getTaskNo()=" + getTaskNo() + ", getWayBillNo()=" + getWayBillNo() + ", getSerialNos()="
                + getSerialNos() + ", getWayBillQty()=" + getWayBillQty() + ", getWeight()=" + getWeight()
                + ", getVolume()=" + getVolume() + ", getGoodsName()=" + getGoodsName() + ", getStockTime()="
                + getStockTime() + ", getTransportType()=" + getTransportType() + ", getReceiveOrgCode()="
                + getReceiveOrgCode() + ", getReceiveOrgName()=" + getReceiveOrgName() + ", getReachOrgCode()="
                + getReachOrgCode() + ", getReachOrgName()=" + getReachOrgName() + ", getPacking()=" + getPacking()
                + ", getBePriorityGoods()=" + getBePriorityGoods() + ", getIsValue()=" + getIsValue() + ", getNotes()="
                + getNotes() + ", getReceiveCustDistName()=" + getReceiveCustDistName() + ", getPackageRemark()="
                + getPackageRemark() + ", getBeEWaybill()=" + getBeEWaybill() + ", getClass()=" + getClass()
                + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }
}
