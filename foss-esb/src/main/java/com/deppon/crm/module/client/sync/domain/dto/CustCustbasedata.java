package com.deppon.crm.module.client.sync.domain.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 客户表
 * 
 */
public class CustCustbasedata extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4186506676247127927L;
	/**
	 * 主键ID
	 */
	private BigDecimal fid;
	/**
	 * 地址类型
	 */
	private String faddrtype;
	/**
	 * 所属区域
	 */
	private BigDecimal fareaid;
	/**
	 * 成为会员时间
	 */
	private Timestamp fbecomememtime;
	/**
	 * 发票抬头
	 */
	private String fbilltitle;
	/**
	 * 品牌价值
	 */
	private String fbrandworth;
	/**
	 * 客户业务类型
	 */
	private String fbusstype;
	/**
	 * 偏好渠道
	 */
	private String fchannel;
	/**
	 * 来源渠道
	 */
	private String fchannelsource;
	/**
	 * 所在城市
	 */
	private String fcity;
	/**
	 * 城市ID
	 */
	private BigDecimal fcityid;
	/**
	 * 公司地址
	 */
	private String fcompaddr;
	/**
	 * 公司性质
	 */
	private String fcompanyproperty;
	/**
	 * 上一年公司规模
	 */
	private String fcompanyscop;
	/**
	 * 主要联系人ID
	 */
	private BigDecimal fcontactid;
	/**
	 * 创建时间
	 */
	private Timestamp fcreatetime;
	/**
	 * 创建人
	 */
	private BigDecimal fcreateuserid;
	/**
	 * 信用等级
	 */
	private String fcreditrate;
	/**
	 * 客户姓名
	 */
	private String fcustname;
	/**
	 * 客户属性
	 */
	private String fcustnature;
	/**
	 * 客户编码
	 */
	private String fcustnumber;
	/**
	 * 客户潜力类型
	 */
	private String fcustpotentialtype;
	/**
	 * 客户状态
	 */
	private String fcuststatus;
	/**
	 * 客户类型
	 */
	private String fcusttype;
	/**
	 * 客户等级
	 */
	private String fdegree;
	/**
	 * 所属部门
	 */
	private BigDecimal fdeptid;
	/**
	 * 集中结算部门
	 */
	private String ffocusdeptid;
	/**
	 * 集中结算部门名称
	 */
	private String ffocusdeptname;
	/**
	 * 是否接受德邦营销
	 */
	private BigDecimal fisacceptmarket;
	/**
	 * 是否集中结算
	 */
	private String fisfocuspay;
	/**
	 * 是否部门重要客户
	 */
	private String fisimportcustor;
	/**
	 * 是否母公司
	 */
	private String fisparentcompany;
	/**
	 * 是否允许联系人兑换积分
	 */
	private String fisredeempoints;
	/**
	 * 是否特殊客户
	 */
	private String fisspecial;
	/**
	 * 是否异地调货
	 */
	private String fistrangoods;
	/**
	 * 上次升降级时间
	 */
	private Timestamp flastchangtime;
	/**
	 * 最近修改时间
	 */
	private BigDecimal flastmodifyuserid;
	/**
	 * 最后修改人
	 */
	private Timestamp flastupdatetime;
	/**
	 * 上一年财务公司收入
	 */
	private BigDecimal flastyearincome;
	/**
	 * 上一年公司利润
	 */
	private BigDecimal flastyearprofit;
	/**
	 * 客户上一等级
	 */
	private String fnextlevel;
	/**
	 * 客户所属母公司
	 */
	private BigDecimal fparentcompanyid;
	/**
	 * 客户所属母公司名称
	 */
	private String fparentnumber;
	/**
	 * 偏好服务
	 */
	private String fpreferenceservice;
	/**
	 * 临欠额度
	 */
	private BigDecimal fprocredit;
	/**
	 * 所在省份
	 */
	private String fprovince;
	/**
	 * 省市ID
	 */
	private BigDecimal fprovinceid;
	/**
	 * 推荐人
	 */
	private String frecommendcust;
	/**
	 * 客户注册地址
	 */
	private String fregistaddress;
	/**
	 * 注册资金
	 */
	private BigDecimal fregisterfund;
	/**
	 * 备注
	 */
	private String fremark;
	/**
	 * 责任维护人
	 */
	private BigDecimal fresponsibillity;
	/**
	 * 客户简称
	 */
	private String fsimplename;
	/**
	 * 客户税务登记号
	 */
	private String ftaxregnumber;
	/**
	 * 一级行业
	 */
	private String ftradeid;
	/**
	 * 升级来源
	 */
	private String fupgradesource;
	/**
	 * 版本号
	 */
	private BigDecimal fversionnumber;

	/**
	 * 会员所属部门的标杆编码
	 */
	private String fdeptidStandardcode;

	/**
	 * 财务作废
	 */
	private boolean financeCancel;

	/**
	 * 发件人短信
	 */
	private String fshipperSms;

	/**
	 * 收件人短信
	 */
	private String freceiverSms;

	/**
	 * 精准代收
	 */
	private boolean fisAccurateCollection;

	/**
	 * 是否大客户
	 */
	private boolean ifBigCustomer;

	/**
	 * 是否快递大客户
	 */
	private boolean fisExpKeycustomer;

	/**
	 * 业务类别
	 */
	private String fcustcategory;
	/*
	 * PC_CUSTOMER 潜客 SC_CUSTOMER 散客 RC_CUSTOMER 固定客户
	 */
	/**
	 * 客户类别
	 */
	private String custGroup;

	/**
	 * 一级行业
	 */
	private String fistTrade;

	/**
	 * 二级行业
	 */
	private String secondTrade;

	/**
	 * fossid
	 */
	private String fossId;
	/**
	 * 是否电子运单大客户
	 */
	private String ifElecBillBigCust;
	/**
	 * 特安上限
	 */
	private Integer fteanLimit;
	/**
	 * 收货人固定手机号
	 */
	private String fixedReceiveMobile;

	/**
	 * 地址备注 客户地址与客户注册地址是一样的，但是注册地址没有维护，故使用客户地址
	 */
	private String fcompaddrRemark;

	/**
	 * 发票标记
	 * 
	 * @return
	 */
	private String finvoiceType;
	/**
	 * 客户分群（CRM那里说月度标签） 2015-07-06 添加 css
	 */
	private String flabelleavemonth;

	public String getFlabelleavemonth() {
		return flabelleavemonth;
	}

	public void setFlabelleavemonth(String flabelleavemonth) {
		this.flabelleavemonth = flabelleavemonth;
	}

	/**
	 * 黑名单类别(差错延期,FOSS也跟着延期) 2015-07-28 添加
	 * */
	private String blackListCategory;

	public String getBlackListCategory() {
		return blackListCategory;
	}

	public void setBlackListCategory(String blackListCategory) {
		this.blackListCategory = blackListCategory;
	}

	/**
	 * 快递装卸费业务类型 2015-09-07添加 css
	 * */
	private String expHandChargeBusiType;

	public String getExpHandChargeBusiType() {
		return expHandChargeBusiType;
	}

	public void setExpHandChargeBusiType(String expHandChargeBusiType) {
		this.expHandChargeBusiType = expHandChargeBusiType;
	}

	/**
	 * 设置比例 2015-09-07 添加 css
	 * */
	private String setProportion;

	public String getSetProportion() {
		return setProportion;
	}

	public void setSetProportion(String setProportion) {
		this.setProportion = setProportion;
	}

	public String getFinvoiceType() {
		return finvoiceType;
	}

	public void setFinvoiceType(String finvoiceType) {
		this.finvoiceType = finvoiceType;
	}

	public boolean isFinanceCancel() {
		return financeCancel;
	}

	public void setFinanceCancel(boolean financeCancel) {
		this.financeCancel = financeCancel;
	}

	public String getFdeptidStandardcode() {
		return fdeptidStandardcode;
	}

	public void setFdeptidStandardcode(String fdeptidStandardcode) {
		this.fdeptidStandardcode = fdeptidStandardcode;
	}

	public CustCustbasedata() {
	}

	public BigDecimal getFid() {
		return this.fid;
	}

	public void setFid(BigDecimal fid) {
		this.fid = fid;
	}

	public String getFaddrtype() {
		return this.faddrtype;
	}

	public void setFaddrtype(String faddrtype) {
		this.faddrtype = faddrtype;
	}

	public BigDecimal getFareaid() {
		return this.fareaid;
	}

	public void setFareaid(BigDecimal fareaid) {
		this.fareaid = fareaid;
	}

	public Timestamp getFbecomememtime() {
		return this.fbecomememtime;
	}

	public void setFbecomememtime(Timestamp fbecomememtime) {
		this.fbecomememtime = fbecomememtime;
	}

	public String getFbilltitle() {
		return this.fbilltitle;
	}

	public void setFbilltitle(String fbilltitle) {
		this.fbilltitle = fbilltitle;
	}

	public String getFbrandworth() {
		return this.fbrandworth;
	}

	public void setFbrandworth(String fbrandworth) {
		this.fbrandworth = fbrandworth;
	}

	public String getFbusstype() {
		return this.fbusstype;
	}

	public void setFbusstype(String fbusstype) {
		this.fbusstype = fbusstype;
	}

	public String getFchannel() {
		return this.fchannel;
	}

	public void setFchannel(String fchannel) {
		this.fchannel = fchannel;
	}

	public String getFchannelsource() {
		return this.fchannelsource;
	}

	public void setFchannelsource(String fchannelsource) {
		this.fchannelsource = fchannelsource;
	}

	public String getFcity() {
		return this.fcity;
	}

	public void setFcity(String fcity) {
		this.fcity = fcity;
	}

	public BigDecimal getFcityid() {
		return this.fcityid;
	}

	public void setFcityid(BigDecimal fcityid) {
		this.fcityid = fcityid;
	}

	public String getFcompaddr() {
		return this.fcompaddr;
	}

	public void setFcompaddr(String fcompaddr) {
		this.fcompaddr = fcompaddr;
	}

	public String getFcompanyproperty() {
		return this.fcompanyproperty;
	}

	public void setFcompanyproperty(String fcompanyproperty) {
		this.fcompanyproperty = fcompanyproperty;
	}

	public String getFcompanyscop() {
		return this.fcompanyscop;
	}

	public void setFcompanyscop(String fcompanyscop) {
		this.fcompanyscop = fcompanyscop;
	}

	public BigDecimal getFcontactid() {
		return this.fcontactid;
	}

	public void setFcontactid(BigDecimal fcontactid) {
		this.fcontactid = fcontactid;
	}

	public Timestamp getFcreatetime() {
		return this.fcreatetime;
	}

	public void setFcreatetime(Timestamp fcreatetime) {
		this.fcreatetime = fcreatetime;
	}

	public BigDecimal getFcreateuserid() {
		return this.fcreateuserid;
	}

	public void setFcreateuserid(BigDecimal fcreateuserid) {
		this.fcreateuserid = fcreateuserid;
	}

	public String getFcreditrate() {
		return this.fcreditrate;
	}

	public void setFcreditrate(String fcreditrate) {
		this.fcreditrate = fcreditrate;
	}

	public String getFcustname() {
		return this.fcustname;
	}

	public void setFcustname(String fcustname) {
		this.fcustname = fcustname;
	}

	public String getFcustnature() {
		return this.fcustnature;
	}

	public void setFcustnature(String fcustnature) {
		this.fcustnature = fcustnature;
	}

	public String getFcustnumber() {
		return this.fcustnumber;
	}

	public void setFcustnumber(String fcustnumber) {
		this.fcustnumber = fcustnumber;
	}

	public String getFcustpotentialtype() {
		return this.fcustpotentialtype;
	}

	public void setFcustpotentialtype(String fcustpotentialtype) {
		this.fcustpotentialtype = fcustpotentialtype;
	}

	public String getFcuststatus() {
		return this.fcuststatus;
	}

	public void setFcuststatus(String fcuststatus) {
		this.fcuststatus = fcuststatus;
	}

	public String getFcusttype() {
		return this.fcusttype;
	}

	public void setFcusttype(String fcusttype) {
		this.fcusttype = fcusttype;
	}

	public String getFdegree() {
		return this.fdegree;
	}

	public void setFdegree(String fdegree) {
		this.fdegree = fdegree;
	}

	public BigDecimal getFdeptid() {
		return this.fdeptid;
	}

	public void setFdeptid(BigDecimal fdeptid) {
		this.fdeptid = fdeptid;
	}

	public String getFfocusdeptid() {
		return this.ffocusdeptid;
	}

	public void setFfocusdeptid(String ffocusdeptid) {
		this.ffocusdeptid = ffocusdeptid;
	}

	public String getFfocusdeptname() {
		return this.ffocusdeptname;
	}

	public void setFfocusdeptname(String ffocusdeptname) {
		this.ffocusdeptname = ffocusdeptname;
	}

	public BigDecimal getFisacceptmarket() {
		return this.fisacceptmarket;
	}

	public void setFisacceptmarket(BigDecimal fisacceptmarket) {
		this.fisacceptmarket = fisacceptmarket;
	}

	public String getFisfocuspay() {
		return this.fisfocuspay;
	}

	public void setFisfocuspay(String fisfocuspay) {
		this.fisfocuspay = fisfocuspay;
	}

	public String getFisimportcustor() {
		return this.fisimportcustor;
	}

	public void setFisimportcustor(String fisimportcustor) {
		this.fisimportcustor = fisimportcustor;
	}

	public String getFisparentcompany() {
		return this.fisparentcompany;
	}

	public void setFisparentcompany(String fisparentcompany) {
		this.fisparentcompany = fisparentcompany;
	}

	public String getFisredeempoints() {
		return this.fisredeempoints;
	}

	public void setFisredeempoints(String fisredeempoints) {
		this.fisredeempoints = fisredeempoints;
	}

	public String getFisspecial() {
		return this.fisspecial;
	}

	public void setFisspecial(String fisspecial) {
		this.fisspecial = fisspecial;
	}

	public String getFistrangoods() {
		return this.fistrangoods;
	}

	public void setFistrangoods(String fistrangoods) {
		this.fistrangoods = fistrangoods;
	}

	public Timestamp getFlastchangtime() {
		return this.flastchangtime;
	}

	public void setFlastchangtime(Timestamp flastchangtime) {
		this.flastchangtime = flastchangtime;
	}

	public BigDecimal getFlastmodifyuserid() {
		return this.flastmodifyuserid;
	}

	public void setFlastmodifyuserid(BigDecimal flastmodifyuserid) {
		this.flastmodifyuserid = flastmodifyuserid;
	}

	public Timestamp getFlastupdatetime() {
		return this.flastupdatetime;
	}

	public void setFlastupdatetime(Timestamp flastupdatetime) {
		this.flastupdatetime = flastupdatetime;
	}

	public BigDecimal getFlastyearincome() {
		return this.flastyearincome;
	}

	public void setFlastyearincome(BigDecimal flastyearincome) {
		this.flastyearincome = flastyearincome;
	}

	public BigDecimal getFlastyearprofit() {
		return this.flastyearprofit;
	}

	public void setFlastyearprofit(BigDecimal flastyearprofit) {
		this.flastyearprofit = flastyearprofit;
	}

	public String getFnextlevel() {
		return this.fnextlevel;
	}

	public void setFnextlevel(String fnextlevel) {
		this.fnextlevel = fnextlevel;
	}

	public BigDecimal getFparentcompanyid() {
		return this.fparentcompanyid;
	}

	public void setFparentcompanyid(BigDecimal fparentcompanyid) {
		this.fparentcompanyid = fparentcompanyid;
	}

	public String getFparentnumber() {
		return this.fparentnumber;
	}

	public void setFparentnumber(String fparentnumber) {
		this.fparentnumber = fparentnumber;
	}

	public String getFpreferenceservice() {
		return this.fpreferenceservice;
	}

	public void setFpreferenceservice(String fpreferenceservice) {
		this.fpreferenceservice = fpreferenceservice;
	}

	public BigDecimal getFprocredit() {
		return this.fprocredit;
	}

	public void setFprocredit(BigDecimal fprocredit) {
		this.fprocredit = fprocredit;
	}

	public String getFprovince() {
		return this.fprovince;
	}

	public void setFprovince(String fprovince) {
		this.fprovince = fprovince;
	}

	public BigDecimal getFprovinceid() {
		return this.fprovinceid;
	}

	public void setFprovinceid(BigDecimal fprovinceid) {
		this.fprovinceid = fprovinceid;
	}

	public String getFrecommendcust() {
		return this.frecommendcust;
	}

	public void setFrecommendcust(String frecommendcust) {
		this.frecommendcust = frecommendcust;
	}

	public String getFregistaddress() {
		return this.fregistaddress;
	}

	public void setFregistaddress(String fregistaddress) {
		this.fregistaddress = fregistaddress;
	}

	public BigDecimal getFregisterfund() {
		return this.fregisterfund;
	}

	public void setFregisterfund(BigDecimal fregisterfund) {
		this.fregisterfund = fregisterfund;
	}

	public String getFremark() {
		return this.fremark;
	}

	public void setFremark(String fremark) {
		this.fremark = fremark;
	}

	public BigDecimal getFresponsibillity() {
		return this.fresponsibillity;
	}

	public void setFresponsibillity(BigDecimal fresponsibillity) {
		this.fresponsibillity = fresponsibillity;
	}

	public String getFsimplename() {
		return this.fsimplename;
	}

	public void setFsimplename(String fsimplename) {
		this.fsimplename = fsimplename;
	}

	public String getFtaxregnumber() {
		return this.ftaxregnumber;
	}

	public void setFtaxregnumber(String ftaxregnumber) {
		this.ftaxregnumber = ftaxregnumber;
	}

	public String getFtradeid() {
		return this.ftradeid;
	}

	public void setFtradeid(String ftradeid) {
		this.ftradeid = ftradeid;
	}

	public String getFupgradesource() {
		return this.fupgradesource;
	}

	public void setFupgradesource(String fupgradesource) {
		this.fupgradesource = fupgradesource;
	}

	public BigDecimal getFversionnumber() {
		return this.fversionnumber;
	}

	public void setFversionnumber(BigDecimal fversionnumber) {
		this.fversionnumber = fversionnumber;
	}

	public String getFcustcategory() {
		return fcustcategory;
	}

	public void setFcustcategory(String fcustcategory) {
		this.fcustcategory = fcustcategory;
	}

	public boolean isIfBigCustomer() {
		return ifBigCustomer;
	}

	public void setIfBigCustomer(boolean ifBigCustomer) {
		this.ifBigCustomer = ifBigCustomer;
	}

	public String getCustGroup() {
		return custGroup;
	}

	public void setCustGroup(String custGroup) {
		this.custGroup = custGroup;
	}

	public String getFistTrade() {
		return fistTrade;
	}

	public void setFistTrade(String fistTrade) {
		this.fistTrade = fistTrade;
	}

	public String getSecondTrade() {
		return secondTrade;
	}

	public void setSecondTrade(String secondTrade) {
		this.secondTrade = secondTrade;
	}

	public String getFossId() {
		return fossId;
	}

	public void setFossId(String fossId) {
		this.fossId = fossId;
	}

	public String getIfElecBillBigCust() {
		return ifElecBillBigCust;
	}

	public void setIfElecBillBigCust(String ifElecBillBigCust) {
		this.ifElecBillBigCust = ifElecBillBigCust;
	}

	public boolean isFisAccurateCollection() {
		return fisAccurateCollection;
	}

	public void setFisAccurateCollection(boolean fisAccurateCollection) {
		this.fisAccurateCollection = fisAccurateCollection;
	}

	public String getFshipperSms() {
		return fshipperSms;
	}

	public void setFshipperSms(String fshipperSms) {
		this.fshipperSms = fshipperSms;
	}

	public String getFreceiverSms() {
		return freceiverSms;
	}

	public void setFreceiverSms(String freceiverSms) {
		this.freceiverSms = freceiverSms;
	}

	public Integer getFteanLimit() {
		return fteanLimit;
	}

	public void setFteanLimit(Integer fteanLimit) {
		this.fteanLimit = fteanLimit;
	}

	public boolean isFisExpKeycustomer() {
		return fisExpKeycustomer;
	}

	public void setFisExpKeycustomer(boolean fisExpKeycustomer) {
		this.fisExpKeycustomer = fisExpKeycustomer;
	}

	public String getFcompaddrRemark() {
		return fcompaddrRemark;
	}

	public void setFcompaddrRemark(String fcompaddrRemark) {
		this.fcompaddrRemark = fcompaddrRemark;
	}

	public String getFixedReceiveMobile() {
		return fixedReceiveMobile;
	}

	public void setFixedReceiveMobile(String fixedReceiveMobile) {
		this.fixedReceiveMobile = fixedReceiveMobile;
	}

	// 是否精准计价
	private String isAccuratePrice;

	public String getIsAccuratePrice() {
		return isAccuratePrice;
	}

	public void setIsAccuratePrice(String isAccuratePrice) {
		this.isAccuratePrice = isAccuratePrice;
	}

	

}