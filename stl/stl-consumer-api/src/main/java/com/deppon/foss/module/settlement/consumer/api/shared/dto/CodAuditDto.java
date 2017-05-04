package com.deppon.foss.module.settlement.consumer.api.shared.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by 073615 on 2015/11/30.
 */
public class CodAuditDto implements Serializable {
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	//运单号
	private String  waybillNo;
	//运单号集合
    private List<String> waybillNos;
    /**
     * 代收货款类别
     */
    private String codType;
    /**
     * 签收时间截止
     */
    private Date signTime;
    
    /**
     * 开单时间
     */
    private Date billTime;
    
    /**
     * 退款金额起
     */
    private BigDecimal codAmountBegin;
    /**
     *退款金额终止
     */
    private BigDecimal codAmountEnd;
    /**
     * 付款方式
     */
    private String paymentType;
    /**
     * 签收-开单时长
     */
    private BigDecimal signBillDiffer;
    /**
     * 审核状态
     */
    private String lockStatus;
    /**
     * 到达部门
     */
    private String destOrgCod;
    /**
     * 代收货款更改金额
     */
    private BigDecimal codAmountDiffer;
    /**
     * 是否有货物轨迹
     */
    private String hasTrack;
    /**
     * 出发部门是否等于到达部门
     */
    private String destEqOrig;

    /**
     * 合计条数
     */
    private int totalCount;
    /**
     * 锁定条数
     */
    private int lockCount;
    /**
     * 未锁定条数
     */
    private int unlockCount;
    /**
     * 短期冻结
     */
    private int shortFreeze;
    /**
     * 长期冻结
     */
    private int longFreeze;
	/**
	 * 开户行
	 */
	private String bank;
	/**
	 * 开户行集合
	 */
	private List<String> bankList;
	/**
	 * 
	 * */
	private List<String> bankNameList;
	/**
	 *经营本部 
	 */
	private String manageDepartment;
	/**
	 *经营本部集合
	 */
	private String manageDepartmentList;
	/**
	 * 用户编码
	 */
	private String userCode;
	/**
	 *用户名称 
	 */
	private String userName;
	/**
	 *创建时间
	 */
	private Date createTime;
	/**
	 *创建人 
	 */
	private String createUser;
	/**
	 *修改人用户编码 
	 */
    private String modifyUserCode;
    /**
     *修改人用户名称 
     */
    private String modifyUerName;
    /**
     *附件
     */
	private String uploadFile;
	/**
     *附件名称
     */
    private String uploadFileFileName;
    /**
     *附件格式
     */
    private String uploadFileContentType;
    /**
     *审核意见
     */
    private String codAuditSuggestion;
	/**
     *资金部审核意见  
     */
    private String fundSuggestion;
	/**
     *复合会计审核意见  
     */
    private String reviewauditSuggestion;
    /**
     *附件路径
     */
	private String filePath;
	/**
	 * 资金部审核附件地址
	 * */
	private String fundAttachment;
	/**
	 *复合会计组审核附件地址 
	 * */
	private String reviewauditAttachment;
	

    public String getModifyUserCode() {
        return modifyUserCode;
    }

    public void setModifyUserCode(String modifyUserCode) {
        this.modifyUserCode = modifyUserCode;
    }

    public String getModifyUerName() {
        return modifyUerName;
    }

    public void setModifyUerName(String modifyUerName) {
        this.modifyUerName = modifyUerName;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getLockCount() {
        return lockCount;
    }

    public void setLockCount(int lockCount) {
        this.lockCount = lockCount;
    }

    public int getUnlockCount() {
        return unlockCount;
    }

    public void setUnlockCount(int unlockCount) {
        this.unlockCount = unlockCount;
    }

    public List<String> getWaybillNos() {
        return waybillNos;
    }

    public void setWaybillNos(List<String> waybillNos) {
        this.waybillNos = waybillNos;
    }

    public String getCodType() {
        return codType;
    }

    public void setCodType(String codType) {
        this.codType = codType;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public BigDecimal getCodAmountBegin() {
        return codAmountBegin;
    }

    public void setCodAmountBegin(BigDecimal codAmountBegin) {
        this.codAmountBegin = codAmountBegin;
    }

    public BigDecimal getCodAmountEnd() {
        return codAmountEnd;
    }

    public void setCodAmountEnd(BigDecimal codAmountEnd) {
        this.codAmountEnd = codAmountEnd;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }


    public BigDecimal getSignBillDiffer() {
		return signBillDiffer;
	}

	public void setSignBillDiffer(BigDecimal signBillDiffer) {
		this.signBillDiffer = signBillDiffer;
	}

	public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    public String getDestOrgCod() {
        return destOrgCod;
    }

    public void setDestOrgCod(String destOrgCod) {
        this.destOrgCod = destOrgCod;
    }

    public BigDecimal getCodAmountDiffer() {
        return codAmountDiffer;
    }

    public void setCodAmountDiffer(BigDecimal codAmountDiffer) {
        this.codAmountDiffer = codAmountDiffer;
    }

    public String getHasTrack() {
        return hasTrack;
    }

    public void setHasTrack(String hasTrack) {
        this.hasTrack = hasTrack;
    }

    public String getDestEqOrig() {
        return destEqOrig;
    }

    public void setDestEqOrig(String destEqOrig) {
        this.destEqOrig = destEqOrig;
    }

	public Date getBillTime() {
		return billTime;
	}

	public void setBillTime(Date billTime) {
		this.billTime = billTime;
	}
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	public List<String> getBankList() {
		return bankList;
	}

	public void setBankList(List<String> bankList) {
		this.bankList = bankList;
	}
	public String getManageDepartment() {
		return manageDepartment;
	}

	public void setManageDepartment(String manageDepartment) {
		this.manageDepartment = manageDepartment;
	}
	
	
	 public String getUploadFile() {
			return uploadFile;
		}

		public void setUploadFile(String uploadFile) {
			this.uploadFile = uploadFile;
		}

		public String getUploadFileFileName() {
			return uploadFileFileName;
		}

		public void setUploadFileFileName(String uploadFileFileName) {
			this.uploadFileFileName = uploadFileFileName;
		}

		public String getUploadFileContentType() {
			return uploadFileContentType;
		}

		public void setUploadFileContentType(String uploadFileContentType) {
			this.uploadFileContentType = uploadFileContentType;
		}

		public String getCodAuditSuggestion() {
			return codAuditSuggestion;
		}

		public void setCodAuditSuggestion(String codAuditSuggestion) {
			this.codAuditSuggestion = codAuditSuggestion;
		}
		
	    public String getFilePath() {
			return filePath;
		}

		public void setFilePath(String filePath) {
			this.filePath = filePath;
		}
		public String getUserCode() {
			return userCode;
		}

		public void setUserCode(String userCode) {
			this.userCode = userCode;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public Date getCreateTime() {
			return createTime;
		}

		public void setCreateTime(Date createTime) {
			this.createTime = createTime;
		}

		public String getCreateUser() {
			return createUser;
		}

		public void setCreateUser(String createUser) {
			this.createUser = createUser;
		}
		public String getFundSuggestion() {
			return fundSuggestion;
		}

		public void setFundSuggestion(String fundSuggestion) {
			this.fundSuggestion = fundSuggestion;
		}

		public String getReviewauditSuggestion() {
			return reviewauditSuggestion;
		}

		public void setReviewauditSuggestion(String reviewauditSuggestion) {
			this.reviewauditSuggestion = reviewauditSuggestion;
		}
		public String getFundAttachment() {
			return fundAttachment;
		}

		public void setFundAttachment(String fundAttachment) {
			this.fundAttachment = fundAttachment;
		}

		public String getReviewauditAttachment() {
			return reviewauditAttachment;
		}

		public void setReviewauditAttachment(String reviewauditAttachment) {
			this.reviewauditAttachment = reviewauditAttachment;
		}
	    public String getManageDepartmentList() {
			return manageDepartmentList;
		}

		public void setManageDepartmentList(String manageDepartmentList) {
			this.manageDepartmentList = manageDepartmentList;
		}
		public String getWaybillNo() {
			return waybillNo;
		}

		public void setWaybillNo(String waybillNo) {
			this.waybillNo = waybillNo;
		}
		public List<String> getBankNameList() {
			return bankNameList;
		}

		public void setBankNameList(List<String> bankNameList) {
			this.bankNameList = bankNameList;
		}
	    public int getShortFreeze() {
			return shortFreeze;
		}

		public void setShortFreeze(int shortFreeze) {
			this.shortFreeze = shortFreeze;
		}

		public int getLongFreeze() {
			return longFreeze;
		}

		public void setLongFreeze(int longFreeze) {
			this.longFreeze = longFreeze;
		}
}
