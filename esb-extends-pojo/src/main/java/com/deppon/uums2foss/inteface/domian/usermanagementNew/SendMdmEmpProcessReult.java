
package com.deppon.uums2foss.inteface.domian.usermanagementNew;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.deppon.com/uums/inteface/domain/usermanagement" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="SendMdmEmpProcessReult">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:string" name="employeeChangeId" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:boolean" name="result" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="reason" minOccurs="0" maxOccurs="1"/>
 *     &lt;xs:element type="xs:string" name="empCode" minOccurs="1" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SendMdmEmpProcessReult
{
    private String employeeChangeId;
    private boolean result;
    private String reason;
    private String empCode;

    /** 
     * Get the 'employeeChangeId' element value. 人员信息变动ID
     * 
     * @return value
     */
    public String getEmployeeChangeId() {
        return employeeChangeId;
    }

    /** 
     * Set the 'employeeChangeId' element value. 人员信息变动ID
     * 
     * @param employeeChangeId
     */
    public void setEmployeeChangeId(String employeeChangeId) {
        this.employeeChangeId = employeeChangeId;
    }

    /** 
     * Get the 'result' element value. 处理结果，是否成功[0: 失败, 1： 成功]
     * 
     * @return value
     */
    public boolean isResult() {
        return result;
    }

    /** 
     * Set the 'result' element value. 处理结果，是否成功[0: 失败, 1： 成功]
     * 
     * @param result
     */
    public void setResult(boolean result) {
        this.result = result;
    }

    /** 
     * Get the 'reason' element value. 失败原因
     * 
     * @return value
     */
    public String getReason() {
        return reason;
    }

    /** 
     * Set the 'reason' element value. 失败原因
     * 
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /** 
     * Get the 'empCode' element value. 职员编号
     * 
     * @return value
     */
    public String getEmpCode() {
        return empCode;
    }

    /** 
     * Set the 'empCode' element value. 职员编号
     * 
     * @param empCode
     */
    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }
}
