package com.deppon.foss.dubbo.crm.api.define.masterdata;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
/**
 * 优惠信息
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class CustPreferential extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 9200008755875215800L;
	//是否精准电商, crm 传值为0/1
	
	private String   ifAccurateElect;
	
	private BigDecimal lttWeightBubbleRate;
		
	public String getIfAccurateElect() {
		return ifAccurateElect;
	}
	public void setIfAccurateElect(String ifAccurateElect) {
		this.ifAccurateElect = ifAccurateElect;
	}
	public BigDecimal getLttWeightBubbleRate(){
		 return lttWeightBubbleRate;
	}
	public void setLttWeightBubbleRate(BigDecimal lttWeightBubbleRate){
		this.lttWeightBubbleRate =lttWeightBubbleRate;
	}
	/**
     * 主键ID
     */
    private BigDecimal fid;
  
    /**
     * 创建时间
     */
    private Date fcreatetime;

    /**
     * 创建人
     */
    private BigDecimal fcreateuserid;

    /**
     * 最后修改时间
     */
    private Date flastupdatetime;

    /**
     * 最后修改人
     */
    private BigDecimal flastmodifyuserid;

    /**
     * 对应合同id
     */
    private BigDecimal fcontractid;

    /**
     * 运费折扣费率
     */
    private BigDecimal fchargerebate;

    /**
     * 代收货款费率
     */
    private BigDecimal fagentgathrate;

    /**
     * 保价费率
     */
    private BigDecimal finsuredpricerate;

    /**
     * 接货费率
     */
    private BigDecimal freceivepricerate;

    /**
     * 送货费率

     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    private BigDecimal fdeliverypricerate;
    
    /**
     * 执行起始日期
     */
    private Date effectiveDate;
    /**
     * 执行到期日期
     */
    private Date expirationDate;

	  /**
	   * 优惠所属类型(取值LTT或者EXPRESS,分别表示零担和快递)
	   */
    private String ftype;
    
    /**
     * 包装
     */
    private BigDecimal fpackingRate;

    /**
     * 超重
     */
    private BigDecimal foverWeightRate;

    /**
     * 签收单返还
     */
    private BigDecimal fsignBillRate;
    
    /**
     * 代收最低手续费
     */
    private BigDecimal flowestCharge;

   
    /**
	  * 重泡比值
	  */
	private BigDecimal fexpweightbubblerate;
	
	/**
	 * 大件上楼费折扣
	 * @author 218392  张永雪
	 * @date 创建时间：2014-12-27 下午3:12:26
	 *
	 */
	private BigDecimal fbiguprate;
	
	/**
	 * 续重最低费率
	 * @author 132599  shenweihua
	 * @date 创建时间：2015-01-30 下午3:12:26
	 */
	private BigDecimal conHeavyLowestRate;
	
	/**
	 * 续重折扣
	 */
	private BigDecimal conHeavyDisCount;
	
	/**
	 * 快递返货运费折扣类型
	 */
	private String expBackPreType;
	
	/**
	 * 快递返货保价折扣类型
	 */
	private String expBackCollPreType;
	
	/**
	 * 快递返货运费固定值
	 */
	private BigDecimal carriageFixed;
	
	/**
	 * 快递返货保价固定值
	 */
	private BigDecimal collFixed;
	
	/**
	 * 单票代收手续费
	 */
	private BigDecimal oneTickCollCharge;
	
	/**
	 * 单票保价手续费
	 */
	private BigDecimal ensuredPriceCharge;
	
	/**
	 * 单票包装费
	 */
	private BigDecimal oneTickPackCharge;
	
	/**
	 * 代收退费
	 */
	private boolean collReturnCharge;
	
	/**
	 * @author 218392 zhangyongxue
	 * @date 2015-05-15
	 * accessDepotRate 进仓派送费折扣
	 */
	private BigDecimal faccessDepotRate;

	public BigDecimal getFaccessDepotRate() {
		return faccessDepotRate;
	}

	public void setFaccessDepotRate(BigDecimal faccessDepotRate) {
		this.faccessDepotRate = faccessDepotRate;
	}

	/**
	 *  获取大件上楼费折扣
	 */
	public BigDecimal getFbiguprate() {
		return fbiguprate;
	}

	/**
	 *  设置大件上楼费折扣
	 */
	public void setFbiguprate(BigDecimal fbiguprate) {
		this.fbiguprate = fbiguprate;
	}

	public BigDecimal getFexpweightbubblerate() {
		return fexpweightbubblerate;
	}

	public void setFexpweightbubblerate(BigDecimal fexpweightbubblerate) {
		this.fexpweightbubblerate = fexpweightbubblerate;
	}

	/**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FID
     *
     * @return the value of DEVELOP.T_CUST_PREFERENTIAL.FID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFid() {
        return fid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FID
     *
     * @param fid the value for DEVELOP.T_CUST_PREFERENTIAL.FID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFid(BigDecimal fid) {
        this.fid = fid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FCREATETIME
     *
     * @return the value of DEVELOP.T_CUST_PREFERENTIAL.FCREATETIME
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public Date getFcreatetime() {
        return fcreatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FCREATETIME
     *
     * @param fcreatetime the value for DEVELOP.T_CUST_PREFERENTIAL.FCREATETIME
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFcreatetime(Date fcreatetime) {
        this.fcreatetime = fcreatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FCREATEUSERID
     *
     * @return the value of DEVELOP.T_CUST_PREFERENTIAL.FCREATEUSERID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFcreateuserid() {
        return fcreateuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FCREATEUSERID
     *
     * @param fcreateuserid the value for DEVELOP.T_CUST_PREFERENTIAL.FCREATEUSERID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFcreateuserid(BigDecimal fcreateuserid) {
        this.fcreateuserid = fcreateuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FLASTUPDATETIME
     *
     * @return the value of DEVELOP.T_CUST_PREFERENTIAL.FLASTUPDATETIME
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public Date getFlastupdatetime() {
        return flastupdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FLASTUPDATETIME
     *
     * @param flastupdatetime the value for DEVELOP.T_CUST_PREFERENTIAL.FLASTUPDATETIME
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFlastupdatetime(Date flastupdatetime) {
        this.flastupdatetime = flastupdatetime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FLASTMODIFYUSERID
     *
     * @return the value of DEVELOP.T_CUST_PREFERENTIAL.FLASTMODIFYUSERID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFlastmodifyuserid() {
        return flastmodifyuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FLASTMODIFYUSERID
     *
     * @param flastmodifyuserid the value for DEVELOP.T_CUST_PREFERENTIAL.FLASTMODIFYUSERID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFlastmodifyuserid(BigDecimal flastmodifyuserid) {
        this.flastmodifyuserid = flastmodifyuserid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FCONTRACTID
     *
     * @return the value of DEVELOP.T_CUST_PREFERENTIAL.FCONTRACTID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFcontractid() {
        return fcontractid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FCONTRACTID
     *
     * @param fcontractid the value for DEVELOP.T_CUST_PREFERENTIAL.FCONTRACTID
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFcontractid(BigDecimal fcontractid) {
        this.fcontractid = fcontractid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FCHARGEREBATE
     *
     * @return the value of DEVELOP.T_CUST_PREFERENTIAL.FCHARGEREBATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFchargerebate() {
        return fchargerebate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FCHARGEREBATE
     *
     * @param fchargerebate the value for DEVELOP.T_CUST_PREFERENTIAL.FCHARGEREBATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFchargerebate(BigDecimal fchargerebate) {
        this.fchargerebate = fchargerebate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FAGENTGATHRATE
     *
     * @return the value of DEVELOP.T_CUST_PREFERENTIAL.FAGENTGATHRATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFagentgathrate() {
        return fagentgathrate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FAGENTGATHRATE
     *
     * @param fagentgathrate the value for DEVELOP.T_CUST_PREFERENTIAL.FAGENTGATHRATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFagentgathrate(BigDecimal fagentgathrate) {
        this.fagentgathrate = fagentgathrate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FINSUREDPRICERATE
     *
     * @return the value of DEVELOP.T_CUST_PREFERENTIAL.FINSUREDPRICERATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFinsuredpricerate() {
        return finsuredpricerate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FINSUREDPRICERATE
     *
     * @param finsuredpricerate the value for DEVELOP.T_CUST_PREFERENTIAL.FINSUREDPRICERATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFinsuredpricerate(BigDecimal finsuredpricerate) {
        this.finsuredpricerate = finsuredpricerate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FRECEIVEPRICERATE
     *
     * @return the value of DEVELOP.T_CUST_PREFERENTIAL.FRECEIVEPRICERATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFreceivepricerate() {
        return freceivepricerate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FRECEIVEPRICERATE
     *
     * @param freceivepricerate the value for DEVELOP.T_CUST_PREFERENTIAL.FRECEIVEPRICERATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFreceivepricerate(BigDecimal freceivepricerate) {
        this.freceivepricerate = freceivepricerate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FDELIVERYPRICERATE
     *
     * @return the value of DEVELOP.T_CUST_PREFERENTIAL.FDELIVERYPRICERATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public BigDecimal getFdeliverypricerate() {
        return fdeliverypricerate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column DEVELOP.T_CUST_PREFERENTIAL.FDELIVERYPRICERATE
     *
     * @param fdeliverypricerate the value for DEVELOP.T_CUST_PREFERENTIAL.FDELIVERYPRICERATE
     *
     * @mbggenerated Tue May 29 20:34:45 CST 2012
     */
    public void setFdeliverypricerate(BigDecimal fdeliverypricerate) {
        this.fdeliverypricerate = fdeliverypricerate;
    }

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getFtype() {
		return ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	public BigDecimal getFpackingRate() {
		return fpackingRate;
	}

	public void setFpackingRate(BigDecimal fpackingRate) {
		this.fpackingRate = fpackingRate;
	}

	public BigDecimal getFoverWeightRate() {
		return foverWeightRate;
	}

	public void setFoverWeightRate(BigDecimal foverWeightRate) {
		this.foverWeightRate = foverWeightRate;
	}

	public BigDecimal getFsignBillRate() {
		return fsignBillRate;
	}

	public void setFsignBillRate(BigDecimal fsignBillRate) {
		this.fsignBillRate = fsignBillRate;
	}

	public BigDecimal getFlowestCharge() {
		return flowestCharge;
	}

	public void setFlowestCharge(BigDecimal flowestCharge) {
		this.flowestCharge = flowestCharge;
	}

	public BigDecimal getConHeavyLowestRate() {
		return conHeavyLowestRate;
	}

	public void setConHeavyLowestRate(BigDecimal conHeavyLowestRate) {
		this.conHeavyLowestRate = conHeavyLowestRate;
	}

	public BigDecimal getConHeavyDisCount() {
		return conHeavyDisCount;
	}

	public void setConHeavyDisCount(BigDecimal conHeavyDisCount) {
		this.conHeavyDisCount = conHeavyDisCount;
	}

	public String getExpBackPreType() {
		return expBackPreType;
	}

	public void setExpBackPreType(String expBackPreType) {
		this.expBackPreType = expBackPreType;
	}

	public String getExpBackCollPreType() {
		return expBackCollPreType;
	}

	public void setExpBackCollPreType(String expBackCollPreType) {
		this.expBackCollPreType = expBackCollPreType;
	}

	public BigDecimal getCarriageFixed() {
		return carriageFixed;
	}

	public void setCarriageFixed(BigDecimal carriageFixed) {
		this.carriageFixed = carriageFixed;
	}

	public BigDecimal getCollFixed() {
		return collFixed;
	}

	public void setCollFixed(BigDecimal collFixed) {
		this.collFixed = collFixed;
	}

	public BigDecimal getOneTickCollCharge() {
		return oneTickCollCharge;
	}

	public void setOneTickCollCharge(BigDecimal oneTickCollCharge) {
		this.oneTickCollCharge = oneTickCollCharge;
	}

	public BigDecimal getEnsuredPriceCharge() {
		return ensuredPriceCharge;
	}

	public void setEnsuredPriceCharge(BigDecimal ensuredPriceCharge) {
		this.ensuredPriceCharge = ensuredPriceCharge;
	}

	public BigDecimal getOneTickPackCharge() {
		return oneTickPackCharge;
	}

	public void setOneTickPackCharge(BigDecimal oneTickPackCharge) {
		this.oneTickPackCharge = oneTickPackCharge;
	}

	public boolean isCollReturnCharge() {
		return collReturnCharge;
	}

	public void setCollReturnCharge(boolean collReturnCharge) {
		this.collReturnCharge = collReturnCharge;
	}
	@Override
	public String toString() {
		return "CustPreferential [ifAccurateElect=" + ifAccurateElect + ", lttWeightBubbleRate=" + lttWeightBubbleRate
				+ ", fid=" + fid + ", fcreatetime=" + fcreatetime + ", fcreateuserid=" + fcreateuserid
				+ ", flastupdatetime=" + flastupdatetime + ", flastmodifyuserid=" + flastmodifyuserid + ", fcontractid="
				+ fcontractid + ", fchargerebate=" + fchargerebate + ", fagentgathrate=" + fagentgathrate
				+ ", finsuredpricerate=" + finsuredpricerate + ", freceivepricerate=" + freceivepricerate
				+ ", fdeliverypricerate=" + fdeliverypricerate + ", effectiveDate=" + effectiveDate
				+ ", expirationDate=" + expirationDate + ", ftype=" + ftype + ", fpackingRate=" + fpackingRate
				+ ", foverWeightRate=" + foverWeightRate + ", fsignBillRate=" + fsignBillRate + ", flowestCharge="
				+ flowestCharge + ", fexpweightbubblerate=" + fexpweightbubblerate + ", fbiguprate=" + fbiguprate
				+ ", conHeavyLowestRate=" + conHeavyLowestRate + ", conHeavyDisCount=" + conHeavyDisCount
				+ ", expBackPreType=" + expBackPreType + ", expBackCollPreType=" + expBackCollPreType
				+ ", carriageFixed=" + carriageFixed + ", collFixed=" + collFixed + ", oneTickCollCharge="
				+ oneTickCollCharge + ", ensuredPriceCharge=" + ensuredPriceCharge + ", oneTickPackCharge="
				+ oneTickPackCharge + ", collReturnCharge=" + collReturnCharge + ", faccessDepotRate="
				+ faccessDepotRate + "]";
	}


}