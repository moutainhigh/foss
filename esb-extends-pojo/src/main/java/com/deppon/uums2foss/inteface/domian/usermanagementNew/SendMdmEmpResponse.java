
package com.deppon.uums2foss.inteface.domian.usermanagementNew;

import java.util.ArrayList;
import java.util.List;

/** 
 * Schema fragment(s) for this class:
 * <pre>
 * &lt;xs:complexType xmlns:ns="http://www.deppon.com/uums/inteface/domain/usermanagement" xmlns:xs="http://www.w3.org/2001/XMLSchema" name="SendMdmEmpResponse">
 *   &lt;xs:sequence>
 *     &lt;xs:element type="xs:int" name="successCount" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="xs:int" name="failedCount" minOccurs="1" maxOccurs="1"/>
 *     &lt;xs:element type="ns:SendMdmEmpProcessReult" name="processResult" minOccurs="1" maxOccurs="unbounded"/>
 *     &lt;xs:element type="xs:string" name="sysName" minOccurs="1" maxOccurs="1"/>
 *   &lt;/xs:sequence>
 * &lt;/xs:complexType>
 * </pre>
 */
public class SendMdmEmpResponse
{
    private int successCount;
    private int failedCount;
    private List<SendMdmEmpProcessReult> processResultList = new ArrayList<SendMdmEmpProcessReult>();
    private String sysName;

    /** 
     * Get the 'successCount' element value. �ɹ�����
     * 
     * @return value
     */
    public int getSuccessCount() {
        return successCount;
    }

    /** 
     * Set the 'successCount' element value. �ɹ�����
     * 
     * @param successCount
     */
    public void setSuccessCount(int successCount) {
        this.successCount = successCount;
    }

    /** 
     * Get the 'failedCount' element value. ʧ������
     * 
     * @return value
     */
    public int getFailedCount() {
        return failedCount;
    }

    /** 
     * Set the 'failedCount' element value. ʧ������
     * 
     * @param failedCount
     */
    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }

    /** 
     * Get the list of 'processResult' element items. ������ϸ
     * 
     * @return list
     */
    public List<SendMdmEmpProcessReult> getProcessResultList() {
        return processResultList;
    }

    /** 
     * Set the list of 'processResult' element items. ������ϸ
     * 
     * @param list
     */
    public void setProcessResultList(List<SendMdmEmpProcessReult> list) {
        processResultList = list;
    }

    /** 
     * Get the 'sysName' element value. ϵͳ���
     * 
     * @return value
     */
    public String getSysName() {
        return sysName;
    }

    /** 
     * Set the 'sysName' element value. ϵͳ���
     * 
     * @param sysName
     */
    public void setSysName(String sysName) {
        this.sysName = sysName;
    }
}
