
package com.deppon.uums2foss.inteface.domian.usermanagementNew;

import java.util.Date;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.deppon.com/uums/inteface/domain/usermanagement" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="MdmEmpInfo">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:string" name="employeeChangeId" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="deptId" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="empCode" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="empName" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:int" name="gender" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="deptName" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="deptStandCode" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="position" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="positionGrade" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="docNumber" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:double" name="withCode" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:double" name="waistCode" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:double" name="height" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:double" name="weight" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="birthDate" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:int" name="status" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="inDate" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:dateTime" name="outDate" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="officeTel" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="officeAddress" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="officeEmail" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="mobile" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="homeTel" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="homeAddress" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="personalEmail" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="workexp" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="jobCode" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="jobDuty" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="jobGroups" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="jobLevel" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="jobSequence" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:boolean" name="isTempEmp" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="createDate" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="createUser" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="modifyDate" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="modifyUser" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="beginTime" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="endTime" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="nationality" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="education" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="school" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="marital" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="characterRpr" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="deptCode" minOccurs="0" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class MdmEmpInfo
{
    private String employeeChangeId;
    private String deptId;
    private String empCode;
    private String empName;
    private int gender;
    private String deptName;
    private String deptStandCode;
    private String position;
    private String positionGrade;
    private String docNumber;
    private Double withCode;
    private Double waistCode;
    private Double height;
    private Double weight;
    private Date birthDate;
    private int status;
    private Date inDate;
    private Date outDate;
    private String officeTel;
    private String officeAddress;
    private String officeEmail;
    private String mobile;
    private String homeTel;
    private String homeAddress;
    private String personalEmail;
    private String workexp;
    private String jobCode;
    private String jobDuty;
    private String jobGroups;
    private String jobLevel;
    private String jobSequence;
    private boolean isTempEmp;
    private String createDate;
    private String createUser;
    private String modifyDate;
    private String modifyUser;
    private String beginTime;
    private String endTime;
    private String nationality;
    private String education;
    private String school;
    private String marital;
    private String characterRpr;
    private String deptCode;

    /** 
     * Get the 'employeeChangeId' element value. ��Ա����ID
     * 
     * @return value
     */
    public String getEmployeeChangeId() {
        return employeeChangeId;
    }

    /** 
     * Set the 'employeeChangeId' element value. ��Ա����ID
     * 
     * @param employeeChangeId
     */
    public void setEmployeeChangeId(String employeeChangeId) {
        this.employeeChangeId = employeeChangeId;
    }

    /** 
     * Get the 'deptId' element value. ��֯ID(OA����)
     * 
     * @return value
     */
    public String getDeptId() {
        return deptId;
    }

    /** 
     * Set the 'deptId' element value. ��֯ID(OA����)
     * 
     * @param deptId
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    /** 
     * Get the 'empCode' element value. ְԱ���
     * 
     * @return value
     */
    public String getEmpCode() {
        return empCode;
    }

    /** 
     * Set the 'empCode' element value. ְԱ���
     * 
     * @param empCode
     */
    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    /** 
     * Get the 'empName' element value. ��Ա����
     * 
     * @return value
     */
    public String getEmpName() {
        return empName;
    }

    /** 
     * Set the 'empName' element value. ��Ա����
     * 
     * @param empName
     */
    public void setEmpName(String empName) {
        this.empName = empName;
    }

    /** 
     * Get the 'gender' element value. �Ա�
     * 
     * @return value
     */
    public int getGender() {
        return gender;
    }

    /** 
     * Set the 'gender' element value. �Ա�
     * 
     * @param gender
     */
    public void setGender(int gender) {
        this.gender = gender;
    }

    /** 
     * Get the 'deptName' element value. ������֯���
     * 
     * @return value
     */
    public String getDeptName() {
        return deptName;
    }

    /** 
     * Set the 'deptName' element value. ������֯���
     * 
     * @param deptName
     */
    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    /** 
     * Get the 'deptStandCode' element value. ������֯��˱���
     * 
     * @return value
     */
    public String getDeptStandCode() {
        return deptStandCode;
    }

    /** 
     * Set the 'deptStandCode' element value. ������֯��˱���
     * 
     * @param deptStandCode
     */
    public void setDeptStandCode(String deptStandCode) {
        this.deptStandCode = deptStandCode;
    }

    /** 
     * Get the 'position' element value. ְλ
     * 
     * @return value
     */
    public String getPosition() {
        return position;
    }

    /** 
     * Set the 'position' element value. ְλ
     * 
     * @param position
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /** 
     * Get the 'positionGrade' element value. ְ��
     * 
     * @return value
     */
    public String getPositionGrade() {
        return positionGrade;
    }

    /** 
     * Set the 'positionGrade' element value. ְ��
     * 
     * @param positionGrade
     */
    public void setPositionGrade(String positionGrade) {
        this.positionGrade = positionGrade;
    }

    /** 
     * Get the 'docNumber' element value. ֤������
     * 
     * @return value
     */
    public String getDocNumber() {
        return docNumber;
    }

    /** 
     * Set the 'docNumber' element value. ֤������
     * 
     * @param docNumber
     */
    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    /** 
     * Get the 'withCode' element value. ��װ��
     * 
     * @return value
     */
    public Double getWithCode() {
        return withCode;
    }

    /** 
     * Set the 'withCode' element value. ��װ��
     * 
     * @param withCode
     */
    public void setWithCode(Double withCode) {
        this.withCode = withCode;
    }

    /** 
     * Get the 'waistCode' element value. ������
     * 
     * @return value
     */
    public Double getWaistCode() {
        return waistCode;
    }

    /** 
     * Set the 'waistCode' element value. ������
     * 
     * @param waistCode
     */
    public void setWaistCode(Double waistCode) {
        this.waistCode = waistCode;
    }

    /** 
     * Get the 'height' element value. ���
     * 
     * @return value
     */
    public Double getHeight() {
        return height;
    }

    /** 
     * Set the 'height' element value. ���
     * 
     * @param height
     */
    public void setHeight(Double height) {
        this.height = height;
    }

    /** 
     * Get the 'weight' element value. ����
     * 
     * @return value
     */
    public Double getWeight() {
        return weight;
    }

    /** 
     * Set the 'weight' element value. ����
     * 
     * @param weight
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /** 
     * Get the 'birthDate' element value. ��������
     * 
     * @return value
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /** 
     * Set the 'birthDate' element value. ��������
     * 
     * @param birthDate
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    /** 
     * Get the 'status' element value. ��Ա״̬
     * 
     * @return value
     */
    public int getStatus() {
        return status;
    }

    /** 
     * Set the 'status' element value. ��Ա״̬
     * 
     * @param status
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /** 
     * Get the 'inDate' element value. ��ְ����
     * 
     * @return value
     */
    public Date getInDate() {
        return inDate;
    }

    /** 
     * Set the 'inDate' element value. ��ְ����
     * 
     * @param inDate
     */
    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    /** 
     * Get the 'outDate' element value. ��ְ����
     * 
     * @return value
     */
    public Date getOutDate() {
        return outDate;
    }

    /** 
     * Set the 'outDate' element value. ��ְ����
     * 
     * @param outDate
     */
    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    /** 
     * Get the 'officeTel' element value. �칫�绰
     * 
     * @return value
     */
    public String getOfficeTel() {
        return officeTel;
    }

    /** 
     * Set the 'officeTel' element value. �칫�绰
     * 
     * @param officeTel
     */
    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    /** 
     * Get the 'officeAddress' element value. �칫��ַ
     * 
     * @return value
     */
    public String getOfficeAddress() {
        return officeAddress;
    }

    /** 
     * Set the 'officeAddress' element value. �칫��ַ
     * 
     * @param officeAddress
     */
    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    /** 
     * Get the 'officeEmail' element value. �칫����
     * 
     * @return value
     */
    public String getOfficeEmail() {
        return officeEmail;
    }

    /** 
     * Set the 'officeEmail' element value. �칫����
     * 
     * @param officeEmail
     */
    public void setOfficeEmail(String officeEmail) {
        this.officeEmail = officeEmail;
    }

    /** 
     * Get the 'mobile' element value. �ֻ����
     * 
     * @return value
     */
    public String getMobile() {
        return mobile;
    }

    /** 
     * Set the 'mobile' element value. �ֻ����
     * 
     * @param mobile
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /** 
     * Get the 'homeTel' element value. ��ͥ�绰
     * 
     * @return value
     */
    public String getHomeTel() {
        return homeTel;
    }

    /** 
     * Set the 'homeTel' element value. ��ͥ�绰
     * 
     * @param homeTel
     */
    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }

    /** 
     * Get the 'homeAddress' element value. ��ͥ��ַ
     * 
     * @return value
     */
    public String getHomeAddress() {
        return homeAddress;
    }

    /** 
     * Set the 'homeAddress' element value. ��ͥ��ַ
     * 
     * @param homeAddress
     */
    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    /** 
     * Get the 'personalEmail' element value. ˽������
     * 
     * @return value
     */
    public String getPersonalEmail() {
        return personalEmail;
    }

    /** 
     * Set the 'personalEmail' element value. ˽������
     * 
     * @param personalEmail
     */
    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    /** 
     * Get the 'workexp' element value. ��������
     * 
     * @return value
     */
    public String getWorkexp() {
        return workexp;
    }

    /** 
     * Set the 'workexp' element value. ��������
     * 
     * @param workexp
     */
    public void setWorkexp(String workexp) {
        this.workexp = workexp;
    }

    /** 
     * Get the 'jobCode' element value. ��λ����
     * 
     * @return value
     */
    public String getJobCode() {
        return jobCode;
    }

    /** 
     * Set the 'jobCode' element value. ��λ����
     * 
     * @param jobCode
     */
    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    /** 
     * Get the 'jobDuty' element value. ��λְ��
     * 
     * @return value
     */
    public String getJobDuty() {
        return jobDuty;
    }

    /** 
     * Set the 'jobDuty' element value. ��λְ��
     * 
     * @param jobDuty
     */
    public void setJobDuty(String jobDuty) {
        this.jobDuty = jobDuty;
    }

    /** 
     * Get the 'jobGroups' element value. ��λ��Ⱥ
     * 
     * @return value
     */
    public String getJobGroups() {
        return jobGroups;
    }

    /** 
     * Set the 'jobGroups' element value. ��λ��Ⱥ
     * 
     * @param jobGroups
     */
    public void setJobGroups(String jobGroups) {
        this.jobGroups = jobGroups;
    }

    /** 
     * Get the 'jobLevel' element value. ��λ�ȼ�
     * 
     * @return value
     */
    public String getJobLevel() {
        return jobLevel;
    }

    /** 
     * Set the 'jobLevel' element value. ��λ�ȼ�
     * 
     * @param jobLevel
     */
    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }

    /** 
     * Get the 'jobSequence' element value. ��λ����
     * 
     * @return value
     */
    public String getJobSequence() {
        return jobSequence;
    }

    /** 
     * Set the 'jobSequence' element value. ��λ����
     * 
     * @param jobSequence
     */
    public void setJobSequence(String jobSequence) {
        this.jobSequence = jobSequence;
    }

    /** 
     * Get the 'isTempEmp' element value. �Ƿ���ʱ��Ա
     * 
     * @return value
     */
    public boolean isIsTempEmp() {
        return isTempEmp;
    }

    /** 
     * Set the 'isTempEmp' element value. �Ƿ���ʱ��Ա
     * 
     * @param isTempEmp
     */
    public void setIsTempEmp(boolean isTempEmp) {
        this.isTempEmp = isTempEmp;
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
     * Get the 'modifyDate' element value. ����޸�ʱ��yyyy-MM-dd HH:mm:ss SSS
     * 
     * @return value
     */
    public String getModifyDate() {
        return modifyDate;
    }

    /** 
     * Set the 'modifyDate' element value. ����޸�ʱ��yyyy-MM-dd HH:mm:ss SSS
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
     * Get the 'nationality' element value. ����
     * 
     * @return value
     */
    public String getNationality() {
        return nationality;
    }

    /** 
     * Set the 'nationality' element value. ����
     * 
     * @param nationality
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /** 
     * Get the 'education' element value. ѧ��
     * 
     * @return value
     */
    public String getEducation() {
        return education;
    }

    /** 
     * Set the 'education' element value. ѧ��
     * 
     * @param education
     */
    public void setEducation(String education) {
        this.education = education;
    }

    /** 
     * Get the 'school' element value. ��ҵԺУ
     * 
     * @return value
     */
    public String getSchool() {
        return school;
    }

    /** 
     * Set the 'school' element value. ��ҵԺУ
     * 
     * @param school
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /** 
     * Get the 'marital' element value. ����״��
     * 
     * @return value
     */
    public String getMarital() {
        return marital;
    }

    /** 
     * Set the 'marital' element value. ����״��
     * 
     * @param marital
     */
    public void setMarital(String marital) {
        this.marital = marital;
    }

    /** 
     * Get the 'characterRpr' element value. ��������
     * 
     * @return value
     */
    public String getCharacterRpr() {
        return characterRpr;
    }

    /** 
     * Set the 'characterRpr' element value. ��������
     * 
     * @param characterRpr
     */
    public void setCharacterRpr(String characterRpr) {
        this.characterRpr = characterRpr;
    }

    /** 
     * Get the 'deptCode' element value. ������֯����
     * 
     * @return value
     */
    public String getDeptCode() {
        return deptCode;
    }

    /** 
     * Set the 'deptCode' element value. ������֯����
     * 
     * @param deptCode
     */
    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }
}
