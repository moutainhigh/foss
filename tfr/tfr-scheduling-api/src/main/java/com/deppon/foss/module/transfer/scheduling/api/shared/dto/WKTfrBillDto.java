package com.deppon.foss.module.transfer.scheduling.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.deppon.foss.module.transfer.scheduling.api.shared.vo.WKTfrBillVo;

/**
 * @author 313352   gouyangyang
 * 快递运单和快递交接单查询条件
 */
public class WKTfrBillDto implements Serializable {
	
	 /**
     * 序列号
     */
    private static final long serialVersionUID = -2675622414307981170L;

    private Date billGenerationBeginTime;//单据生成起始查询时间

    private Date billGenerationEndTime;//单据生成结束查询时间

    private String vehicleNo;//车牌号

    private String borrowNo;//租车编号

    private String isWayBill;//是否运单
    
    private WKTfrBillVo wkTfrBillVo;  // 查询条件

    private String isHandoverBill;//是否交接单

    private String isDeliverBill;//是否派送单

    private String isStowageBill;//是否配载单

    private String isHandoverEirBill; // 是否快递交接单

    private String isWayEirBill;  // 是否快递运单

    private String orgCode;//部门编码

    private String createDept;//创建部门编码

    private List<String> handoverBillNoList;//交接单号

    private List<String> deliverbillNoList;//派送单号

    private List<String> stowageBillNoList;//配载单号

    private List<String> expressBillNoList; // 快递交接单号

    private List<String> expressWayBillNoList;   // 快递运单

    private String departmentSignle;//部门标识

    private String queryType;//查询类型

    private String billsType;//检查小票号时的单据类型

    private boolean smallTicketVisbile;//小票单号控件是否可见

    private List<String> smallTicketList;//小票单号List

    private String smallTickets;//错误的小票单号

    private String waybillNos;//校验小票号时的错误运单

    private String incomeCategories;//收入类别

    private String rentalUse;//租车用途

    private String rentalNum;//租车编号

    /**
     * 车队的顶级车队所服务营业部的编码集合*
     */
    private List<String> orgCodeList;

    /**下面4个字段用于更新工作流信息*/
    /**
     * 预提状态*
     */
    private String accruedState;
    /**
     * 预提工作流号*
     */
    private String accruedWorkNo;
    /**
     * 预提工作流处理结果*
     */
    private String accruedWorkResult;
    /**
     * 租车编号*
     */
    private String temprentalMarkNo;
    //269701-lln-2015-08-31 begin
    /**
     * 零担运单号
     */
    private List<String> wayBillNoList;
    /**
     * 快递运单号
     */
    private List<String> expressWaybillNoList;
    public WKTfrBillVo getWkTfrBillVo() {
		return wkTfrBillVo;
	}

	public void setWkTfrBillVo(WKTfrBillVo wkTfrBillVo) {
		this.wkTfrBillVo = wkTfrBillVo;
	}

	/**
     * 是否快递运单
     */
    private String isExpressWayBill;
    //269701-lln-2015-08-31 end
    // 出发部门编码
    private String departOrgCode;

    // 到达部门编码
    private String arriveOrgCode;
    //租车金额
    private BigDecimal rentalAmount;
    // 单号
    private String handoverBillNo;
    //单据类型
    private String billType;
    // 询价编号
    private String consultPriceNo;
    // 约车编号
    private String inviteVehicleNo;
    // 司机
    private String driverName;
    //出发部门名称
    private String departOrgName;
    //到达部门名称
    private String arriveOrgName;
    // 重量
    private BigDecimal totalWeight;
    // 体积
    private BigDecimal totalVolumn;
    //租车金额(装车金额)
    private BigDecimal loadfee;
    // 单据类型(交接类型长途交接单：LONG_DISTANCE_HANDOVER短途交接单：SHORT_DISTANCE_HANDOVER航空交接单：AIR_TRANS_HANDOVER)
    private String handoverType;
    // 约车编号
    private String aboutvehicleNo;
    // 创建时间
    private Date createTime;
    // 是否上门接货
    private String isDoorDeliver;
    // 租车标记部门
    private String markDeptName;
    // 租车标记操作人
    private String markOperator;
    //运单号
    private String waybillNo;
    //重量
    private BigDecimal goodsWeight;
    //体积
    private BigDecimal goodsVolume;
    //货物名称
    private String goodsName;
    //货物包装
    private String goodsPackage;
    //寄件联系人
    private String shipperContactPerson;
    // 寄件地址备注
    private String shipperContactAddMemo;
    // 目的网点名称
    private String destinationPointName;
    //收货联系人
    private String consigneeContactPerson;
    //收件单位
    private String consigneeCompany;
    // 当前页数
    private int currentPageNo;
    // 页大小
    private int pageSize;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public Date getBillGenerationBeginTime() {
        return billGenerationBeginTime;
    }

    public void setBillGenerationBeginTime(Date billGenerationBeginTime) {
        this.billGenerationBeginTime = billGenerationBeginTime;
    }

    public Date getBillGenerationEndTime() {
        return billGenerationEndTime;
    }

    public void setBillGenerationEndTime(Date billGenerationEndTime) {
        this.billGenerationEndTime = billGenerationEndTime;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getBorrowNo() {
        return borrowNo;
    }

    public void setBorrowNo(String borrowNo) {
        this.borrowNo = borrowNo;
    }

    public String getIsWayBill() {
        return isWayBill;
    }

    public void setIsWayBill(String isWayBill) {
        this.isWayBill = isWayBill;
    }

    public String getIsHandoverBill() {
        return isHandoverBill;
    }

    public void setIsHandoverBill(String isHandoverBill) {
        this.isHandoverBill = isHandoverBill;
    }

    public String getIsDeliverBill() {
        return isDeliverBill;
    }

    public void setIsDeliverBill(String isDeliverBill) {
        this.isDeliverBill = isDeliverBill;
    }

    public String getIsStowageBill() {
        return isStowageBill;
    }

    public void setIsStowageBill(String isStowageBill) {
        this.isStowageBill = isStowageBill;
    }

    public String getIsHandoverEirBill() {
        return isHandoverEirBill;
    }

    public void setIsHandoverEirBill(String isHandoverEirBill) {
        this.isHandoverEirBill = isHandoverEirBill;
    }

    public String getIsWayEirBill() {
        return isWayEirBill;
    }

    public String getRentalNum() {
        return rentalNum;
    }

    public void setRentalNum(String rentalNum) {
        this.rentalNum = rentalNum;
    }

    public void setIsWayEirBill(String isWayEirBill) {
        this.isWayEirBill = isWayEirBill;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getCreateDept() {
        return createDept;
    }

    public void setCreateDept(String createDept) {
        this.createDept = createDept;
    }

    public List<String> getHandoverBillNoList() {
        return handoverBillNoList;
    }

    public BigDecimal getLoadfee() {
        return loadfee;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public BigDecimal getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(BigDecimal goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public BigDecimal getGoodsVolume() {
        return goodsVolume;
    }

    public void setGoodsVolume(BigDecimal goodsVolume) {
        this.goodsVolume = goodsVolume;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsPackage() {
        return goodsPackage;
    }

    public void setGoodsPackage(String goodsPackage) {
        this.goodsPackage = goodsPackage;
    }

    public String getShipperContactPerson() {
        return shipperContactPerson;
    }

    public void setShipperContactPerson(String shipperContactPerson) {
        this.shipperContactPerson = shipperContactPerson;
    }

    public String getShipperContactAddMemo() {
        return shipperContactAddMemo;
    }

    public void setShipperContactAddMemo(String shipperContactAddMemo) {
        this.shipperContactAddMemo = shipperContactAddMemo;
    }

    public String getDestinationPointName() {
        return destinationPointName;
    }

    public void setDestinationPointName(String destinationPointName) {
        this.destinationPointName = destinationPointName;
    }

    public String getConsigneeContactPerson() {
        return consigneeContactPerson;
    }

    public void setConsigneeContactPerson(String consigneeContactPerson) {
        this.consigneeContactPerson = consigneeContactPerson;
    }

    public String getConsigneeCompany() {
        return consigneeCompany;
    }

    public void setConsigneeCompany(String consigneeCompany) {
        this.consigneeCompany = consigneeCompany;
    }

    public void setLoadfee(BigDecimal loadfee) {
        this.loadfee = loadfee;
    }

    public String getMarkDeptName() {
        return markDeptName;
    }

    public void setMarkDeptName(String markDeptName) {
        this.markDeptName = markDeptName;
    }

    public String getMarkOperator() {
        return markOperator;
    }

    public void setMarkOperator(String markOperator) {
        this.markOperator = markOperator;
    }

    public String getIsDoorDeliver() {
        return isDoorDeliver;
    }

    public void setIsDoorDeliver(String isDoorDeliver) {
        this.isDoorDeliver = isDoorDeliver;
    }

    public String getDepartOrgName() {
        return departOrgName;
    }

    public BigDecimal getTotalWeight() {
        return totalWeight;
    }

    public String getAboutvehicleNo() {
        return aboutvehicleNo;
    }

    public void setAboutvehicleNo(String aboutvehicleNo) {
        this.aboutvehicleNo = aboutvehicleNo;
    }

    public String getHandoverType() {
        return handoverType;
    }

    public void setHandoverType(String handoverType) {
        this.handoverType = handoverType;
    }

    public void setTotalWeight(BigDecimal totalWeight) {
        this.totalWeight = totalWeight;
    }

    public BigDecimal getTotalVolumn() {
        return totalVolumn;
    }

    public void setTotalVolumn(BigDecimal totalVolumn) {
        this.totalVolumn = totalVolumn;
    }

    public void setDepartOrgName(String departOrgName) {
        this.departOrgName = departOrgName;
    }

    public String getArriveOrgName() {
        return arriveOrgName;
    }

    public void setArriveOrgName(String arriveOrgName) {
        this.arriveOrgName = arriveOrgName;
    }

    public void setHandoverBillNoList(List<String> handoverBillNoList) {
        this.handoverBillNoList = handoverBillNoList;
    }

    public List<String> getDeliverbillNoList() {
        return deliverbillNoList;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getConsultPriceNo() {
        return consultPriceNo;
    }

    public void setConsultPriceNo(String consultPriceNo) {
        this.consultPriceNo = consultPriceNo;
    }

    public String getHandoverBillNo() {
        return handoverBillNo;
    }

    public void setHandoverBillNo(String handoverBillNo) {
        this.handoverBillNo = handoverBillNo;
    }

    public void setDeliverbillNoList(List<String> deliverbillNoList) {
        this.deliverbillNoList = deliverbillNoList;
    }

    public List<String> getStowageBillNoList() {
        return stowageBillNoList;
    }

    public void setStowageBillNoList(List<String> stowageBillNoList) {
        this.stowageBillNoList = stowageBillNoList;
    }

    public List<String> getExpressBillNoList() {
        return expressBillNoList;
    }

    public void setExpressBillNoList(List<String> expressBillNoList) {
        this.expressBillNoList = expressBillNoList;
    }

    public List<String> getExpressWayBillNoList() {
        return expressWayBillNoList;
    }

    public void setExpressWayBillNoList(List<String> expressWayBillNoList) {
        this.expressWayBillNoList = expressWayBillNoList;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDepartmentSignle() {
        return departmentSignle;
    }

    public void setDepartmentSignle(String departmentSignle) {
        this.departmentSignle = departmentSignle;
    }

    public String getQueryType() {
        return queryType;
    }

    public void setQueryType(String queryType) {
        this.queryType = queryType;
    }

    public String getBillsType() {
        return billsType;
    }

    public void setBillsType(String billsType) {
        this.billsType = billsType;
    }

    public boolean isSmallTicketVisbile() {
        return smallTicketVisbile;
    }

    public void setSmallTicketVisbile(boolean smallTicketVisbile) {
        this.smallTicketVisbile = smallTicketVisbile;
    }

    public List<String> getSmallTicketList() {
        return smallTicketList;
    }

    public void setSmallTicketList(List<String> smallTicketList) {
        this.smallTicketList = smallTicketList;
    }

    public String getSmallTickets() {
        return smallTickets;
    }

    public void setSmallTickets(String smallTickets) {
        this.smallTickets = smallTickets;
    }

    public String getWaybillNos() {
        return waybillNos;
    }

    public void setWaybillNos(String waybillNos) {
        this.waybillNos = waybillNos;
    }

    public String getIncomeCategories() {
        return incomeCategories;
    }

    public void setIncomeCategories(String incomeCategories) {
        this.incomeCategories = incomeCategories;
    }

    public String getInviteVehicleNo() {
        return inviteVehicleNo;
    }

    public void setInviteVehicleNo(String inviteVehicleNo) {
        this.inviteVehicleNo = inviteVehicleNo;
    }

    public String getRentalUse() {
        return rentalUse;
    }

    public void setRentalUse(String rentalUse) {
        this.rentalUse = rentalUse;
    }

    public List<String> getOrgCodeList() {
        return orgCodeList;
    }

    public void setOrgCodeList(List<String> orgCodeList) {
        this.orgCodeList = orgCodeList;
    }

    public String getAccruedState() {
        return accruedState;
    }

    public void setAccruedState(String accruedState) {
        this.accruedState = accruedState;
    }

    public String getAccruedWorkNo() {
        return accruedWorkNo;
    }

    public void setAccruedWorkNo(String accruedWorkNo) {
        this.accruedWorkNo = accruedWorkNo;
    }

    public String getAccruedWorkResult() {
        return accruedWorkResult;
    }

    public void setAccruedWorkResult(String accruedWorkResult) {
        this.accruedWorkResult = accruedWorkResult;
    }

    public String getTemprentalMarkNo() {
        return temprentalMarkNo;
    }

    public void setTemprentalMarkNo(String temprentalMarkNo) {
        this.temprentalMarkNo = temprentalMarkNo;
    }

    public List<String> getWayBillNoList() {
        return wayBillNoList;
    }

    public void setWayBillNoList(List<String> wayBillNoList) {
        this.wayBillNoList = wayBillNoList;
    }

    public List<String> getExpressWaybillNoList() {
        return expressWaybillNoList;
    }

    public void setExpressWaybillNoList(List<String> expressWaybillNoList) {
        this.expressWaybillNoList = expressWaybillNoList;
    }

    public String getIsExpressWayBill() {
        return isExpressWayBill;
    }

    public void setIsExpressWayBill(String isExpressWayBill) {
        this.isExpressWayBill = isExpressWayBill;
    }

    public String getDepartOrgCode() {
        return departOrgCode;
    }

    public void setDepartOrgCode(String departOrgCode) {
        this.departOrgCode = departOrgCode;
    }

    public String getArriveOrgCode() {
        return arriveOrgCode;
    }

    public void setArriveOrgCode(String arriveOrgCode) {
        this.arriveOrgCode = arriveOrgCode;
    }

    public BigDecimal getRentalAmount() {
        return rentalAmount;
    }

    public void setRentalAmount(BigDecimal rentalAmount) {
        this.rentalAmount = rentalAmount;
    }


}
