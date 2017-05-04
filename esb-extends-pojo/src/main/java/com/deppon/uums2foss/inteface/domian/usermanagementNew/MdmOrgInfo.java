
package com.deppon.uums2foss.inteface.domian.usermanagementNew;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.deppon.com/uums/inteface/domain/usermanagement" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="MdmOrgInfo">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:string" name="orgChangeId" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:int" name="orgId" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="orgCode" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="orgName" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="deptAttribute" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="orgBenchmarkCode" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="orgManager" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="orgPhone" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="orgFax" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:int" name="parentOrgId" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="parentOrgStandCode" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="subCompName" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="orgCompanyCode" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="orgZipCode" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="orgEmail" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="orgAddress" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:int" name="orgStatus" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="isDivision" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="deptSeq" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:int" name="orgLevel" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="deptDesc" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:boolean" name="isLeaf" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="finDeptAttribute" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="airDispatch" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="arrangeBizType" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="arrangeOutField" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="billingGroup" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="complementSimpleName" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="countryRegion" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="deliverOutField" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="depCoordinate" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:double" name="deptArea" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="cityCode" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="countyCode" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="provCode" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="dispatchTeam" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="doAirDispatch" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="isExpressRegion" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="isExpressBranch" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="isExpressPart" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="isvirualLading" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="isExpressSorting" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="isArrangeGoods" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="isDeliverSchedule" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="isEntityFinance" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="bigRegion" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="isSecurity" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="smallRegion" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="pinYin" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="salesDepartment" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="transDepartment" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="transTeam" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="transferCenter" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="active" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="beginTime" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="createUser" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="createDate" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="endTime" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="modifyDate" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="modifyUser" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="parentOrgCode" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="parentOrgName" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="deptShortName" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:int" name="deptAttributeNo" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="divisionCode" minOccurs="0" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class MdmOrgInfo
{
    private String orgChangeId;
    private int orgId;
    private String orgCode;
    private String orgName;
    private String deptAttribute;
    private String orgBenchmarkCode;
    private String orgManager;
    private String orgPhone;
    private String orgFax;
    private int parentOrgId;
    private String parentOrgStandCode;
    private String subCompName;
    private String orgCompanyCode;
    private String orgZipCode;
    private String orgEmail;
    private String orgAddress;
    private int orgStatus;
    private String isDivision;
    private String deptSeq;
    private int orgLevel;
    private String deptDesc;
    private boolean isLeaf;
    private String finDeptAttribute;
    private String airDispatch;
    private String arrangeBizType;
    private String arrangeOutField;
    private String billingGroup;
    private String complementSimpleName;
    private String countryRegion;
    private String deliverOutField;
    private String depCoordinate;
    private Double deptArea;
    private String cityCode;
    private String countyCode;
    private String provCode;
    private String dispatchTeam;
    private String doAirDispatch;
    private String isExpressRegion;
    private String isExpressBranch;
    private String isExpressPart;
    private String isvirualLading;
    private String isExpressSorting;
    private String isArrangeGoods;
    private String isDeliverSchedule;
    private String isEntityFinance;
    private String bigRegion;
    private String isSecurity;
    private String smallRegion;
    private String pinYin;
    private String salesDepartment;
    private String transDepartment;
    private String transTeam;
    private String transferCenter;
    private String active;
    private String beginTime;
    private String createUser;
    private String createDate;
    private String endTime;
    private String modifyDate;
    private String modifyUser;
    private String parentOrgCode;
    private String parentOrgName;
    private String deptShortName;
    private int deptAttributeNo;
    private String divisionCode;

    /** 
     * Get the 'orgChangeId' element value. ��֯�䶯ID
     * 
     * @return value
     */
    public String getOrgChangeId() {
        return orgChangeId;
    }

    /** 
     * Set the 'orgChangeId' element value. ��֯�䶯ID
     * 
     * @param orgChangeId
     */
    public void setOrgChangeId(String orgChangeId) {
        this.orgChangeId = orgChangeId;
    }

    /** 
     * Get the 'orgId' element value. UUMS����(OA����)
     * 
     * @return value
     */
    public int getOrgId() {
        return orgId;
    }

    /** 
     * Set the 'orgId' element value. UUMS����(OA����)
     * 
     * @param orgId
     */
    public void setOrgId(int orgId) {
        this.orgId = orgId;
    }

    /** 
     * Get the 'orgCode' element value. ��֯����
     * 
     * @return value
     */
    public String getOrgCode() {
        return orgCode;
    }

    /** 
     * Set the 'orgCode' element value. ��֯����
     * 
     * @param orgCode
     */
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    /** 
     * Get the 'orgName' element value. ��֯���
     * 
     * @return value
     */
    public String getOrgName() {
        return orgName;
    }

    /** 
     * Set the 'orgName' element value. ��֯���
     * 
     * @param orgName
     */
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    /** 
     * Get the 'deptAttribute' element value. Ehr��֯����
     * 
     * @return value
     */
    public String getDeptAttribute() {
        return deptAttribute;
    }

    /** 
     * Set the 'deptAttribute' element value. Ehr��֯����
     * 
     * @param deptAttribute
     */
    public void setDeptAttribute(String deptAttribute) {
        this.deptAttribute = deptAttribute;
    }

    /** 
     * Get the 'orgBenchmarkCode' element value. ��֯��˱���
     * 
     * @return value
     */
    public String getOrgBenchmarkCode() {
        return orgBenchmarkCode;
    }

    /** 
     * Set the 'orgBenchmarkCode' element value. ��֯��˱���
     * 
     * @param orgBenchmarkCode
     */
    public void setOrgBenchmarkCode(String orgBenchmarkCode) {
        this.orgBenchmarkCode = orgBenchmarkCode;
    }

    /** 
     * Get the 'orgManager' element value. ��֯�����˹���
     * 
     * @return value
     */
    public String getOrgManager() {
        return orgManager;
    }

    /** 
     * Set the 'orgManager' element value. ��֯�����˹���
     * 
     * @param orgManager
     */
    public void setOrgManager(String orgManager) {
        this.orgManager = orgManager;
    }

    /** 
     * Get the 'orgPhone' element value. ������ϵ�绰
     * 
     * @return value
     */
    public String getOrgPhone() {
        return orgPhone;
    }

    /** 
     * Set the 'orgPhone' element value. ������ϵ�绰
     * 
     * @param orgPhone
     */
    public void setOrgPhone(String orgPhone) {
        this.orgPhone = orgPhone;
    }

    /** 
     * Get the 'orgFax' element value. ���Ŵ���
     * 
     * @return value
     */
    public String getOrgFax() {
        return orgFax;
    }

    /** 
     * Set the 'orgFax' element value. ���Ŵ���
     * 
     * @param orgFax
     */
    public void setOrgFax(String orgFax) {
        this.orgFax = orgFax;
    }

    /** 
     * Get the 'parentOrgId' element value. �ϼ���֯ID(����UUMS����)
     * 
     * @return value
     */
    public int getParentOrgId() {
        return parentOrgId;
    }

    /** 
     * Set the 'parentOrgId' element value. �ϼ���֯ID(����UUMS����)
     * 
     * @param parentOrgId
     */
    public void setParentOrgId(int parentOrgId) {
        this.parentOrgId = parentOrgId;
    }

    /** 
     * Get the 'parentOrgStandCode' element value. �ϼ���֯��˱���
     * 
     * @return value
     */
    public String getParentOrgStandCode() {
        return parentOrgStandCode;
    }

    /** 
     * Set the 'parentOrgStandCode' element value. �ϼ���֯��˱���
     * 
     * @param parentOrgStandCode
     */
    public void setParentOrgStandCode(String parentOrgStandCode) {
        this.parentOrgStandCode = parentOrgStandCode;
    }

    /** 
     * Get the 'subCompName' element value. �����ӹ�˾���
     * 
     * @return value
     */
    public String getSubCompName() {
        return subCompName;
    }

    /** 
     * Set the 'subCompName' element value. �����ӹ�˾���
     * 
     * @param subCompName
     */
    public void setSubCompName(String subCompName) {
        this.subCompName = subCompName;
    }

    /** 
     * Get the 'orgCompanyCode' element value. �����ӹ�˾����
     * 
     * @return value
     */
    public String getOrgCompanyCode() {
        return orgCompanyCode;
    }

    /** 
     * Set the 'orgCompanyCode' element value. �����ӹ�˾����
     * 
     * @param orgCompanyCode
     */
    public void setOrgCompanyCode(String orgCompanyCode) {
        this.orgCompanyCode = orgCompanyCode;
    }

    /** 
     * Get the 'orgZipCode' element value. �ʱ����
     * 
     * @return value
     */
    public String getOrgZipCode() {
        return orgZipCode;
    }

    /** 
     * Set the 'orgZipCode' element value. �ʱ����
     * 
     * @param orgZipCode
     */
    public void setOrgZipCode(String orgZipCode) {
        this.orgZipCode = orgZipCode;
    }

    /** 
     * Get the 'orgEmail' element value. ��֯����
     * 
     * @return value
     */
    public String getOrgEmail() {
        return orgEmail;
    }

    /** 
     * Set the 'orgEmail' element value. ��֯����
     * 
     * @param orgEmail
     */
    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    /** 
     * Get the 'orgAddress' element value. ���ŵ�ַ
     * 
     * @return value
     */
    public String getOrgAddress() {
        return orgAddress;
    }

    /** 
     * Set the 'orgAddress' element value. ���ŵ�ַ
     * 
     * @param orgAddress
     */
    public void setOrgAddress(String orgAddress) {
        this.orgAddress = orgAddress;
    }

    /** 
     * Get the 'orgStatus' element value. ��֯״̬
     * 
     * @return value
     */
    public int getOrgStatus() {
        return orgStatus;
    }

    /** 
     * Set the 'orgStatus' element value. ��֯״̬
     * 
     * @param orgStatus
     */
    public void setOrgStatus(int orgStatus) {
        this.orgStatus = orgStatus;
    }

    /** 
     * Get the 'isDivision' element value. �Ƿ���ҵ��
     * 
     * @return value
     */
    public String getIsDivision() {
        return isDivision;
    }

    /** 
     * Set the 'isDivision' element value. �Ƿ���ҵ��
     * 
     * @param isDivision
     */
    public void setIsDivision(String isDivision) {
        this.isDivision = isDivision;
    }

    /** 
     * Get the 'deptSeq' element value. ���������ϼ���֯��ID����.�����
     * 
     * @return value
     */
    public String getDeptSeq() {
        return deptSeq;
    }

    /** 
     * Set the 'deptSeq' element value. ���������ϼ���֯��ID����.�����
     * 
     * @param deptSeq
     */
    public void setDeptSeq(String deptSeq) {
        this.deptSeq = deptSeq;
    }

    /** 
     * Get the 'orgLevel' element value. ���Ų㼶
     * 
     * @return value
     */
    public int getOrgLevel() {
        return orgLevel;
    }

    /** 
     * Set the 'orgLevel' element value. ���Ų㼶
     * 
     * @param orgLevel
     */
    public void setOrgLevel(int orgLevel) {
        this.orgLevel = orgLevel;
    }

    /** 
     * Get the 'deptDesc' element value. ��������
     * 
     * @return value
     */
    public String getDeptDesc() {
        return deptDesc;
    }

    /** 
     * Set the 'deptDesc' element value. ��������
     * 
     * @param deptDesc
     */
    public void setDeptDesc(String deptDesc) {
        this.deptDesc = deptDesc;
    }

    /** 
     * Get the 'isLeaf' element value. �Ƿ�Ҷ�ӽڵ�
     * 
     * @return value
     */
    public boolean isIsLeaf() {
        return isLeaf;
    }

    /** 
     * Set the 'isLeaf' element value. �Ƿ�Ҷ�ӽڵ�
     * 
     * @param isLeaf
     */
    public void setIsLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    /** 
     * Get the 'finDeptAttribute' element value. ����������
     * 
     * @return value
     */
    public String getFinDeptAttribute() {
        return finDeptAttribute;
    }

    /** 
     * Set the 'finDeptAttribute' element value. ����������
     * 
     * @param finDeptAttribute
     */
    public void setFinDeptAttribute(String finDeptAttribute) {
        this.finDeptAttribute = finDeptAttribute;
    }

    /** 
     * Get the 'airDispatch' element value. �Ƿ�����ܵ�
     * 
     * @return value
     */
    public String getAirDispatch() {
        return airDispatch;
    }

    /** 
     * Set the 'airDispatch' element value. �Ƿ�����ܵ�
     * 
     * @param airDispatch
     */
    public void setAirDispatch(String airDispatch) {
        this.airDispatch = airDispatch;
    }

    /** 
     * Get the 'arrangeBizType' element value. ���ҵ������
     * 
     * @return value
     */
    public String getArrangeBizType() {
        return arrangeBizType;
    }

    /** 
     * Set the 'arrangeBizType' element value. ���ҵ������
     * 
     * @param arrangeBizType
     */
    public void setArrangeBizType(String arrangeBizType) {
        this.arrangeBizType = arrangeBizType;
    }

    /** 
     * Get the 'arrangeOutField' element value. ������ŷ����ⳡ��֯����
     * 
     * @return value
     */
    public String getArrangeOutField() {
        return arrangeOutField;
    }

    /** 
     * Set the 'arrangeOutField' element value. ������ŷ����ⳡ��֯����
     * 
     * @param arrangeOutField
     */
    public void setArrangeOutField(String arrangeOutField) {
        this.arrangeOutField = arrangeOutField;
    }

    /** 
     * Get the 'billingGroup' element value. �Ƿ��п�����
     * 
     * @return value
     */
    public String getBillingGroup() {
        return billingGroup;
    }

    /** 
     * Set the 'billingGroup' element value. �Ƿ��п�����
     * 
     * @param billingGroup
     */
    public void setBillingGroup(String billingGroup) {
        this.billingGroup = billingGroup;
    }

    /** 
     * Get the 'complementSimpleName' element value. ������
     * 
     * @return value
     */
    public String getComplementSimpleName() {
        return complementSimpleName;
    }

    /** 
     * Set the 'complementSimpleName' element value. ������
     * 
     * @param complementSimpleName
     */
    public void setComplementSimpleName(String complementSimpleName) {
        this.complementSimpleName = complementSimpleName;
    }

    /** 
     * Get the 'countryRegion' element value. ��ҵ���
     * 
     * @return value
     */
    public String getCountryRegion() {
        return countryRegion;
    }

    /** 
     * Set the 'countryRegion' element value. ��ҵ���
     * 
     * @param countryRegion
     */
    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }

    /** 
     * Get the 'deliverOutField' element value. �����ŵ������ⳡ��֯����
     * 
     * @return value
     */
    public String getDeliverOutField() {
        return deliverOutField;
    }

    /** 
     * Set the 'deliverOutField' element value. �����ŵ������ⳡ��֯����
     * 
     * @param deliverOutField
     */
    public void setDeliverOutField(String deliverOutField) {
        this.deliverOutField = deliverOutField;
    }

    /** 
     * Get the 'depCoordinate' element value. ���ŷ����������
     * 
     * @return value
     */
    public String getDepCoordinate() {
        return depCoordinate;
    }

    /** 
     * Set the 'depCoordinate' element value. ���ŷ����������
     * 
     * @param depCoordinate
     */
    public void setDepCoordinate(String depCoordinate) {
        this.depCoordinate = depCoordinate;
    }

    /** 
     * Get the 'deptArea' element value. �������
     * 
     * @return value
     */
    public Double getDeptArea() {
        return deptArea;
    }

    /** 
     * Set the 'deptArea' element value. �������
     * 
     * @param deptArea
     */
    public void setDeptArea(Double deptArea) {
        this.deptArea = deptArea;
    }

    /** 
     * Get the 'cityCode' element value. ���б���
     * 
     * @return value
     */
    public String getCityCode() {
        return cityCode;
    }

    /** 
     * Set the 'cityCode' element value. ���б���
     * 
     * @param cityCode
     */
    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    /** 
     * Get the 'countyCode' element value. ���ر���
     * 
     * @return value
     */
    public String getCountyCode() {
        return countyCode;
    }

    /** 
     * Set the 'countyCode' element value. ���ر���
     * 
     * @param countyCode
     */
    public void setCountyCode(String countyCode) {
        this.countyCode = countyCode;
    }

    /** 
     * Get the 'provCode' element value. ʡ�ݱ���
     * 
     * @return value
     */
    public String getProvCode() {
        return provCode;
    }

    /** 
     * Set the 'provCode' element value. ʡ�ݱ���
     * 
     * @param provCode
     */
    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    /** 
     * Get the 'dispatchTeam' element value. �Ƿ񳵶ӵ�����
     * 
     * @return value
     */
    public String getDispatchTeam() {
        return dispatchTeam;
    }

    /** 
     * Set the 'dispatchTeam' element value. �Ƿ񳵶ӵ�����
     * 
     * @param dispatchTeam
     */
    public void setDispatchTeam(String dispatchTeam) {
        this.dispatchTeam = dispatchTeam;
    }

    /** 
     * Get the 'doAirDispatch' element value. �Ƿ�ɿ�������
     * 
     * @return value
     */
    public String getDoAirDispatch() {
        return doAirDispatch;
    }

    /** 
     * Set the 'doAirDispatch' element value. �Ƿ�ɿ�������
     * 
     * @param doAirDispatch
     */
    public void setDoAirDispatch(String doAirDispatch) {
        this.doAirDispatch = doAirDispatch;
    }

    /** 
     * Get the 'isExpressRegion' element value. �Ƿ��ݴ���
     * 
     * @return value
     */
    public String getIsExpressRegion() {
        return isExpressRegion;
    }

    /** 
     * Set the 'isExpressRegion' element value. �Ƿ��ݴ���
     * 
     * @param isExpressRegion
     */
    public void setIsExpressRegion(String isExpressRegion) {
        this.isExpressRegion = isExpressRegion;
    }

    /** 
     * Get the 'isExpressBranch' element value. �Ƿ��ݷֲ�
     * 
     * @return value
     */
    public String getIsExpressBranch() {
        return isExpressBranch;
    }

    /** 
     * Set the 'isExpressBranch' element value. �Ƿ��ݷֲ�
     * 
     * @param isExpressBranch
     */
    public void setIsExpressBranch(String isExpressBranch) {
        this.isExpressBranch = isExpressBranch;
    }

    /** 
     * Get the 'isExpressPart' element value. �Ƿ��ݵ㲿
     * 
     * @return value
     */
    public String getIsExpressPart() {
        return isExpressPart;
    }

    /** 
     * Set the 'isExpressPart' element value. �Ƿ��ݵ㲿
     * 
     * @param isExpressPart
     */
    public void setIsExpressPart(String isExpressPart) {
        this.isExpressPart = isExpressPart;
    }

    /** 
     * Get the 'isvirualLading' element value. �Ƿ�������Ӫҵ��
     * 
     * @return value
     */
    public String getIsvirualLading() {
        return isvirualLading;
    }

    /** 
     * Set the 'isvirualLading' element value. �Ƿ�������Ӫҵ��
     * 
     * @param isvirualLading
     */
    public void setIsvirualLading(String isvirualLading) {
        this.isvirualLading = isvirualLading;
    }

    /** 
     * Get the 'isExpressSorting' element value. �Ƿ��ݷּ�
     * 
     * @return value
     */
    public String getIsExpressSorting() {
        return isExpressSorting;
    }

    /** 
     * Set the 'isExpressSorting' element value. �Ƿ��ݷּ�
     * 
     * @param isExpressSorting
     */
    public void setIsExpressSorting(String isExpressSorting) {
        this.isExpressSorting = isExpressSorting;
    }

    /** 
     * Get the 'isArrangeGoods' element value. �Ƿ����
     * 
     * @return value
     */
    public String getIsArrangeGoods() {
        return isArrangeGoods;
    }

    /** 
     * Set the 'isArrangeGoods' element value. �Ƿ����
     * 
     * @param isArrangeGoods
     */
    public void setIsArrangeGoods(String isArrangeGoods) {
        this.isArrangeGoods = isArrangeGoods;
    }

    /** 
     * Get the 'isDeliverSchedule' element value. �Ƿ������ŵ�
     * 
     * @return value
     */
    public String getIsDeliverSchedule() {
        return isDeliverSchedule;
    }

    /** 
     * Set the 'isDeliverSchedule' element value. �Ƿ������ŵ�
     * 
     * @param isDeliverSchedule
     */
    public void setIsDeliverSchedule(String isDeliverSchedule) {
        this.isDeliverSchedule = isDeliverSchedule;
    }

    /** 
     * Get the 'isEntityFinance' element value. �Ƿ�ʵ�����
     * 
     * @return value
     */
    public String getIsEntityFinance() {
        return isEntityFinance;
    }

    /** 
     * Set the 'isEntityFinance' element value. �Ƿ�ʵ�����
     * 
     * @param isEntityFinance
     */
    public void setIsEntityFinance(String isEntityFinance) {
        this.isEntityFinance = isEntityFinance;
    }

    /** 
     * Get the 'bigRegion' element value. �Ƿ�Ӫҵ����
     * 
     * @return value
     */
    public String getBigRegion() {
        return bigRegion;
    }

    /** 
     * Set the 'bigRegion' element value. �Ƿ�Ӫҵ����
     * 
     * @param bigRegion
     */
    public void setBigRegion(String bigRegion) {
        this.bigRegion = bigRegion;
    }

    /** 
     * Get the 'isSecurity' element value. �Ƿ񱣰���
     * 
     * @return value
     */
    public String getIsSecurity() {
        return isSecurity;
    }

    /** 
     * Set the 'isSecurity' element value. �Ƿ񱣰���
     * 
     * @param isSecurity
     */
    public void setIsSecurity(String isSecurity) {
        this.isSecurity = isSecurity;
    }

    /** 
     * Get the 'smallRegion' element value. �Ƿ�ӪҵС��
     * 
     * @return value
     */
    public String getSmallRegion() {
        return smallRegion;
    }

    /** 
     * Set the 'smallRegion' element value. �Ƿ�ӪҵС��
     * 
     * @param smallRegion
     */
    public void setSmallRegion(String smallRegion) {
        this.smallRegion = smallRegion;
    }

    /** 
     * Get the 'pinYin' element value. ��֯ƴ��
     * 
     * @return value
     */
    public String getPinYin() {
        return pinYin;
    }

    /** 
     * Set the 'pinYin' element value. ��֯ƴ��
     * 
     * @param pinYin
     */
    public void setPinYin(String pinYin) {
        this.pinYin = pinYin;
    }

    /** 
     * Get the 'salesDepartment' element value. �Ƿ�Ӫҵ�����Ͳ�
     * 
     * @return value
     */
    public String getSalesDepartment() {
        return salesDepartment;
    }

    /** 
     * Set the 'salesDepartment' element value. �Ƿ�Ӫҵ�����Ͳ�
     * 
     * @param salesDepartment
     */
    public void setSalesDepartment(String salesDepartment) {
        this.salesDepartment = salesDepartment;
    }

    /** 
     * Get the 'transDepartment' element value. �Ƿ񳵶�
     * 
     * @return value
     */
    public String getTransDepartment() {
        return transDepartment;
    }

    /** 
     * Set the 'transDepartment' element value. �Ƿ񳵶�
     * 
     * @param transDepartment
     */
    public void setTransDepartment(String transDepartment) {
        this.transDepartment = transDepartment;
    }

    /** 
     * Get the 'transTeam' element value. �Ƿ񳵶���
     * 
     * @return value
     */
    public String getTransTeam() {
        return transTeam;
    }

    /** 
     * Set the 'transTeam' element value. �Ƿ񳵶���
     * 
     * @param transTeam
     */
    public void setTransTeam(String transTeam) {
        this.transTeam = transTeam;
    }

    /** 
     * Get the 'transferCenter' element value. �Ƿ��ⳡ
     * 
     * @return value
     */
    public String getTransferCenter() {
        return transferCenter;
    }

    /** 
     * Set the 'transferCenter' element value. �Ƿ��ⳡ
     * 
     * @param transferCenter
     */
    public void setTransferCenter(String transferCenter) {
        this.transferCenter = transferCenter;
    }

    /** 
     * Get the 'active' element value. �Ƿ�����
     * 
     * @return value
     */
    public String getActive() {
        return active;
    }

    /** 
     * Set the 'active' element value. �Ƿ�����
     * 
     * @param active
     */
    public void setActive(String active) {
        this.active = active;
    }

    /** 
     * Get the 'beginTime' element value. ��ʼʱ��yyyy-MM-dd HH:mm:ss SSS
     * 
     * @return value
     */
    public String getBeginTime() {
        return beginTime;
    }

    /** 
     * Set the 'beginTime' element value. ��ʼʱ��yyyy-MM-dd HH:mm:ss SSS
     * 
     * @param beginTime
     */
    public void setBeginTime(String beginTime) {
        this.beginTime = beginTime;
    }

    /** 
     * Get the 'createUser' element value. ������
     * 
     * @return value
     */
    public String getCreateUser() {
        return createUser;
    }

    /** 
     * Set the 'createUser' element value. ������
     * 
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    /** 
     * Get the 'createDate' element value. ����ʱ��yyyy-MM-dd HH:mm:ss SSS
     * 
     * @return value
     */
    public String getCreateDate() {
        return createDate;
    }

    /** 
     * Set the 'createDate' element value. ����ʱ��yyyy-MM-dd HH:mm:ss SSS
     * 
     * @param createDate
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /** 
     * Get the 'endTime' element value. ����ʱ��yyyy-MM-dd HH:mm:ss SSS
     * 
     * @return value
     */
    public String getEndTime() {
        return endTime;
    }

    /** 
     * Set the 'endTime' element value. ����ʱ��yyyy-MM-dd HH:mm:ss SSS
     * 
     * @param endTime
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /** 
     * Get the 'modifyDate' element value. ����޸�ʱ�� yyyy-MM-dd HH:mm:ss SSS
     * 
     * @return value
     */
    public String getModifyDate() {
        return modifyDate;
    }

    /** 
     * Set the 'modifyDate' element value. ����޸�ʱ�� yyyy-MM-dd HH:mm:ss SSS
     * 
     * @param modifyDate
     */
    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    /** 
     * Get the 'modifyUser' element value. ����޸���
     * 
     * @return value
     */
    public String getModifyUser() {
        return modifyUser;
    }

    /** 
     * Set the 'modifyUser' element value. ����޸���
     * 
     * @param modifyUser
     */
    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    /** 
     * Get the 'parentOrgCode' element value. �ϼ���֯����
     * 
     * @return value
     */
    public String getParentOrgCode() {
        return parentOrgCode;
    }

    /** 
     * Set the 'parentOrgCode' element value. �ϼ���֯����
     * 
     * @param parentOrgCode
     */
    public void setParentOrgCode(String parentOrgCode) {
        this.parentOrgCode = parentOrgCode;
    }

    /** 
     * Get the 'parentOrgName' element value. �ϼ���֯���
     * 
     * @return value
     */
    public String getParentOrgName() {
        return parentOrgName;
    }

    /** 
     * Set the 'parentOrgName' element value. �ϼ���֯���
     * 
     * @param parentOrgName
     */
    public void setParentOrgName(String parentOrgName) {
        this.parentOrgName = parentOrgName;
    }

    /** 
     * Get the 'deptShortName' element value. ���ż��
     * 
     * @return value
     */
    public String getDeptShortName() {
        return deptShortName;
    }

    /** 
     * Set the 'deptShortName' element value. ���ż��
     * 
     * @param deptShortName
     */
    public void setDeptShortName(String deptShortName) {
        this.deptShortName = deptShortName;
    }

    /** 
     * Get the 'deptAttributeNo' element value. ��֯���ʱ���
     * 
     * @return value
     */
    public int getDeptAttributeNo() {
        return deptAttributeNo;
    }

    /** 
     * Set the 'deptAttributeNo' element value. ��֯���ʱ���
     * 
     * @param deptAttributeNo
     */
    public void setDeptAttributeNo(int deptAttributeNo) {
        this.deptAttributeNo = deptAttributeNo;
    }

    /** 
     * Get the 'divisionCode' element value. ��ҵ������
     * 
     * @return value
     */
    public String getDivisionCode() {
        return divisionCode;
    }

    /** 
     * Set the 'divisionCode' element value. ��ҵ������
     * 
     * @param divisionCode
     */
    public void setDivisionCode(String divisionCode) {
        this.divisionCode = divisionCode;
    }
}
