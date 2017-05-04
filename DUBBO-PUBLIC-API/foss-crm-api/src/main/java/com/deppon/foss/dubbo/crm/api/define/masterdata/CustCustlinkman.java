package com.deppon.foss.dubbo.crm.api.define.masterdata;

import java.io.Serializable;
import java.sql.Timestamp;
import java.math.BigDecimal;


/**
 * 联系人
 */
public class CustCustlinkman extends BaseEntity implements Serializable {
	
	private static final long serialVersionUID = 6956510427255916874L;
	/**
	 * 主键ID
	 */
	private BigDecimal fid;
	/**
	 * 受理部门ID
	 */
	private BigDecimal facceptdeptid;
	/**
	 * 阿里ID
	 */
	private String falid;
	/**
	 * 出生日期
	 */
	private Timestamp fborndate;
	/**
	 * 账户信_Id
	 */
	private BigDecimal fcid;
	/**
	 * 创建时间
	 */
	private Timestamp fcreatetime;
	/**
	 * 创建人
	 */
	private BigDecimal fcreateuserid;
	/**
	 * 客户ID
	 */
	private BigDecimal fcustid;
	/**
	 * 物流决定权
	 */
	private String fdecisionright;
	/**
	 * 默认账户ID
	 */
	private BigDecimal fdefaultid;
	/**
	 * 职务
	 */
	private String fduty;
	/**
	 * 任职部门
	 */
	private String fdutydept;
	/**
	 * Email
	 */
	private String femail;
	/**
	 * 不明字段，CRM中没有数据，ERP中不存在此字段
	 */
	private Timestamp ferplinkmancreatetime;
	/**
	 * 不明字段，CRM中没有数据，ERP中不存在此字段
	 */
	private String ferplmfid;
	/**
	 * 民族
	 */
	private String ffolk;
	/**
	 * 获知公司途径
	 */
	private String fgainave;
	/**
	 * 身份证号
	 */
	private String fidcard;
	/**
	 * 是否主联系人
	 */
	private String fismainlinkman;
	/**
	 * 最后修改时间
	 */
	private Timestamp flastupdatetime;
	/**
	 * 最后修改人
	 */
	private BigDecimal flastupdateuserid;
	/**
	 * 联系人地址
	 */
	private String flinkmanaddress;
	/**
	 * 联系人类型
	 */
	private String flinkmantype;
	/**
	 * 喜好礼物
	 */
	private String flovegift;
	/**
	 * 手机号码
	 */
	private String fmobiletel;
	/**
	 * msn
	 */
	private String fmsn;
	/**
	 * 联系人姓名
	 */
	private String fname;
	/**
	 * 籍贯
	 */
	private String fnativeplace;
	/**
	 * 联系人编码
	 */
	private String fnumber;
	/**
	 * 固定电话
	 */
	private String foffertel;
	/**
	 * 网营ID
	 */
	private String fonlinebusinessid;
	/**
	 * 个人爱好
	 */
	private String fpersonlove;
	/**
	 * QQ号码
	 */
	private String fqqnumber;
	/**
	 * 性别
	 */
	private String fsex;
	/**
	 * 状态
	 */
	private String fstatus;
	/**
	 * 淘宝ID
	 */
	private String ftaobid;
	/**
	 * 版本号
	 */
	private String fversionid;
	/**
	 * 旺旺
	 */
	private String fww;
	/**
	 * 友商ID
	 */
	private String fyoushangid;
	
	/**
	 * crm2期新增的字段
	 * 传真号码
	 */
	private String fax;

    public CustCustlinkman() {
    }

	public BigDecimal getFid() {
		return this.fid;
	}

	public void setFid(BigDecimal fid) {
		this.fid = fid;
	}

	public BigDecimal getFacceptdeptid() {
		return this.facceptdeptid;
	}

	public void setFacceptdeptid(BigDecimal facceptdeptid) {
		this.facceptdeptid = facceptdeptid;
	}

	public String getFalid() {
		return this.falid;
	}

	public void setFalid(String falid) {
		this.falid = falid;
	}

	public Timestamp getFborndate() {
		return this.fborndate;
	}

	public void setFborndate(Timestamp fborndate) {
		this.fborndate = fborndate;
	}

	public BigDecimal getFcid() {
		return this.fcid;
	}

	public void setFcid(BigDecimal fcid) {
		this.fcid = fcid;
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

	public BigDecimal getFcustid() {
		return this.fcustid;
	}

	public void setFcustid(BigDecimal fcustid) {
		this.fcustid = fcustid;
	}

	public String getFdecisionright() {
		return this.fdecisionright;
	}

	public void setFdecisionright(String fdecisionright) {
		this.fdecisionright = fdecisionright;
	}

	public BigDecimal getFdefaultid() {
		return this.fdefaultid;
	}

	public void setFdefaultid(BigDecimal fdefaultid) {
		this.fdefaultid = fdefaultid;
	}

	public String getFduty() {
		return this.fduty;
	}

	public void setFduty(String fduty) {
		this.fduty = fduty;
	}

	public String getFdutydept() {
		return this.fdutydept;
	}

	public void setFdutydept(String fdutydept) {
		this.fdutydept = fdutydept;
	}

	public String getFemail() {
		return this.femail;
	}

	public void setFemail(String femail) {
		this.femail = femail;
	}

	public Timestamp getFerplinkmancreatetime() {
		return this.ferplinkmancreatetime;
	}

	public void setFerplinkmancreatetime(Timestamp ferplinkmancreatetime) {
		this.ferplinkmancreatetime = ferplinkmancreatetime;
	}

	public String getFerplmfid() {
		return this.ferplmfid;
	}

	public void setFerplmfid(String ferplmfid) {
		this.ferplmfid = ferplmfid;
	}

	public String getFfolk() {
		return this.ffolk;
	}

	public void setFfolk(String ffolk) {
		this.ffolk = ffolk;
	}

	public String getFgainave() {
		return this.fgainave;
	}

	public void setFgainave(String fgainave) {
		this.fgainave = fgainave;
	}

	public String getFidcard() {
		return this.fidcard;
	}

	public void setFidcard(String fidcard) {
		this.fidcard = fidcard;
	}

	public String getFismainlinkman() {
		return this.fismainlinkman;
	}

	public void setFismainlinkman(String fismainlinkman) {
		this.fismainlinkman = fismainlinkman;
	}

	public Timestamp getFlastupdatetime() {
		return this.flastupdatetime;
	}

	public void setFlastupdatetime(Timestamp flastupdatetime) {
		this.flastupdatetime = flastupdatetime;
	}

	public BigDecimal getFlastupdateuserid() {
		return this.flastupdateuserid;
	}

	public void setFlastupdateuserid(BigDecimal flastupdateuserid) {
		this.flastupdateuserid = flastupdateuserid;
	}

	public String getFlinkmanaddress() {
		return this.flinkmanaddress;
	}

	public void setFlinkmanaddress(String flinkmanaddress) {
		this.flinkmanaddress = flinkmanaddress;
	}

	public String getFlinkmantype() {
		return this.flinkmantype;
	}

	public void setFlinkmantype(String flinkmantype) {
		this.flinkmantype = flinkmantype;
	}

	public String getFlovegift() {
		return this.flovegift;
	}

	public void setFlovegift(String flovegift) {
		this.flovegift = flovegift;
	}

	public String getFmobiletel() {
		return this.fmobiletel;
	}

	public void setFmobiletel(String fmobiletel) {
		this.fmobiletel = fmobiletel;
	}

	public String getFmsn() {
		return this.fmsn;
	}

	public void setFmsn(String fmsn) {
		this.fmsn = fmsn;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getFnativeplace() {
		return this.fnativeplace;
	}

	public void setFnativeplace(String fnativeplace) {
		this.fnativeplace = fnativeplace;
	}

	public String getFnumber() {
		return this.fnumber;
	}

	public void setFnumber(String fnumber) {
		this.fnumber = fnumber;
	}

	public String getFoffertel() {
		return this.foffertel;
	}

	public void setFoffertel(String foffertel) {
		this.foffertel = foffertel;
	}

	public String getFonlinebusinessid() {
		return this.fonlinebusinessid;
	}

	public void setFonlinebusinessid(String fonlinebusinessid) {
		this.fonlinebusinessid = fonlinebusinessid;
	}

	public String getFpersonlove() {
		return this.fpersonlove;
	}

	public void setFpersonlove(String fpersonlove) {
		this.fpersonlove = fpersonlove;
	}

	public String getFqqnumber() {
		return this.fqqnumber;
	}

	public void setFqqnumber(String fqqnumber) {
		this.fqqnumber = fqqnumber;
	}

	public String getFsex() {
		return this.fsex;
	}

	public void setFsex(String fsex) {
		this.fsex = fsex;
	}

	public String getFstatus() {
		return this.fstatus;
	}

	public void setFstatus(String fstatus) {
		this.fstatus = fstatus;
	}

	public String getFtaobid() {
		return this.ftaobid;
	}

	public void setFtaobid(String ftaobid) {
		this.ftaobid = ftaobid;
	}

	public String getFversionid() {
		return this.fversionid;
	}

	public void setFversionid(String fversionid) {
		this.fversionid = fversionid;
	}

	public String getFww() {
		return this.fww;
	}

	public void setFww(String fww) {
		this.fww = fww;
	}

	public String getFyoushangid() {
		return this.fyoushangid;
	}

	public void setFyoushangid(String fyoushangid) {
		this.fyoushangid = fyoushangid;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Override
	public String toString() {
		return "CustCustlinkman [fid=" + fid + ", facceptdeptid=" + facceptdeptid + ", falid=" + falid + ", fborndate="
				+ fborndate + ", fcid=" + fcid + ", fcreatetime=" + fcreatetime + ", fcreateuserid=" + fcreateuserid
				+ ", fcustid=" + fcustid + ", fdecisionright=" + fdecisionright + ", fdefaultid=" + fdefaultid
				+ ", fduty=" + fduty + ", fdutydept=" + fdutydept + ", femail=" + femail + ", ferplinkmancreatetime="
				+ ferplinkmancreatetime + ", ferplmfid=" + ferplmfid + ", ffolk=" + ffolk + ", fgainave=" + fgainave
				+ ", fidcard=" + fidcard + ", fismainlinkman=" + fismainlinkman + ", flastupdatetime=" + flastupdatetime
				+ ", flastupdateuserid=" + flastupdateuserid + ", flinkmanaddress=" + flinkmanaddress
				+ ", flinkmantype=" + flinkmantype + ", flovegift=" + flovegift + ", fmobiletel=" + fmobiletel
				+ ", fmsn=" + fmsn + ", fname=" + fname + ", fnativeplace=" + fnativeplace + ", fnumber=" + fnumber
				+ ", foffertel=" + foffertel + ", fonlinebusinessid=" + fonlinebusinessid + ", fpersonlove="
				+ fpersonlove + ", fqqnumber=" + fqqnumber + ", fsex=" + fsex + ", fstatus=" + fstatus + ", ftaobid="
				+ ftaobid + ", fversionid=" + fversionid + ", fww=" + fww + ", fyoushangid=" + fyoushangid + ", fax="
				+ fax + "]";
	}

}