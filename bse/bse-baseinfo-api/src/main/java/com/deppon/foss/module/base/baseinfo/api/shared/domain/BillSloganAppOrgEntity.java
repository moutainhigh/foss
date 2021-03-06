etSloganContent(String sloganContent) {
        this.sloganContent = sloganContent;
    }
    
    /**
     * 获取 广告语类型： DictionaryValueConstants.
     *
     * @return  the sloganSort
     */
    public String getSloganSort() {
        return sloganSort;
    }
    
    /**
     * 设置 广告语类型： DictionaryValueConstants.
     *
     * @param sloganSort the sloganSort to set
     */
    public void setSloganSort(String sloganSort) {
        this.sloganSort = sloganSort;
    }
    
    /**
     * 获取 是否启用.
     *
     * @return  the active
     */
    public String getActive() {
        return active;
    }
    
    /**
     * 设置 是否启用.
     *
     * @param active the active to set
     */
    public void setActive(String active) {
        this.active = active;
    }
    
    /**
     * 获取 所属广告语code.
     *
     * @return  the sloganCode
     */
    public String getSloganCode() {
        return sloganCode;
    }
    
    /**
     * 设置 所属广告语code.
     *
     * @param sloganCode the sloganCode to set
     */
    public void setSloganCode(String sloganCode) {
        this.sloganCode = sloganCode;
    }

	/**
	 * @return the orgName
	 */
	public String getOrgName() {
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

}
                                                              since
 * @version
 */
public class BillSloganAppOrgEntity extends BaseEntity {
    
    /**
     * serialVersionUID.
     */
    private static final long serialVersionUID = 900497952280929645L;
    
    /**
     * 适用部门Code.
     */
    private String orgCode;
    
    /**
     * 部门广告语内容.
     */
    private String sloganContent;
    /**
     * 广告语类型：
     * DictionaryValueConstants.BSE_SLOGAN_TYPE_SMS：短信广告语 
     * DictionaryValueConstants.BSE_SLOGAN_TYPE_BILL：单据广告语
     */
    private String sloganSort;
    
    /**
     * 是否启用.
     */
    private String active;
    
    /**
     * 所属广告语code.
     */
    private String sloganCode;
    /****字段增加***/
    /**
     * 适用部门name（扩展）.
     */
    private String orgName;
    /**
     * 获取 适用部门Code.
     *
     * @return  the orgCode
     */
    public String getOrgCode() {
        return orgCode;
    }
    
    /**
     * 设置 适用部门Code.
     *
     * @param orgCode the orgCode to set
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    
    /**
     * 获取 部门广告语内容.
     *
     * @return  the sloganContent
     */
    public String getSloganContent() {
        return sloganContent;
    }
    
    /**
     * 设置 部门广告语内容.
     *
     * @param sloganContent the sloganContent to set
     */
    public void s����������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������������