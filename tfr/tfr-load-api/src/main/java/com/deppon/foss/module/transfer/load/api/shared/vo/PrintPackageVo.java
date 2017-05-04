package com.deppon.foss.module.transfer.load.api.shared.vo;

import java.util.List;

public class PrintPackageVo {
	/**包号**/
	private String packageNo;
	/**包号是否正确**/
	private String isRight;
	/**工号**/
	private String empCode;
	/**密码**/
	private String empPassword;
	/**份数**/
	private int numbers;
	/**包号list**/
	private List<String> printPackageNoList;

	public int getNumbers() {
		return numbers;
	}

	public void setNumbers(int numbers) {
		this.numbers = numbers;
	}

	public List<String> getPrintPackageNoList() {
		return printPackageNoList;
	}

	public void setPrintPackageNoList(List<String> printPackageNoList) {
		this.printPackageNoList = printPackageNoList;
	}

	public String getEmpCode() {
		return empCode;
	}

	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	public String getEmpPassword() {
		return empPassword;
	}

	public void setEmpPassword(String empPassword) {
		this.empPassword = empPassword;
	}

	public String getPackageNo() {
		return packageNo;
	}

	public void setPackageNo(String packageNo) {
		this.packageNo = packageNo;
	}

	public String getIsRight() {
		return isRight;
	}

	public void setIsRight(String isRight) {
		this.isRight = isRight;
	}
	
}
