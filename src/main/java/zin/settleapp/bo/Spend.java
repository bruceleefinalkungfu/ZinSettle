package zin.settleapp.bo;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

public class Spend {
	
	public String spendId = UUID.randomUUID().toString();

	public double total;
	
	public String createdBy;

	public static final int TYPE_AMT = 0;
	public static final int TYPE_PERCENTAGE = 1;
	
	public int splitType;
	
	public HashMap<String, Double> userWithTheirShare;
		
	public int getSplitType() {
		return splitType;
	}
	public void setSplitType(int splitType) {
		this.splitType = splitType;
	}
	public HashMap<String, Double> getUserWithTheirShare() {
		return userWithTheirShare;
	}
	public void setUserWithTheirShare(HashMap<String, Double> userWithTheirShare) {
		this.userWithTheirShare = userWithTheirShare;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getSpendId() {
		return spendId;
	}
	public void setSpendId(String spendId) {
		this.spendId = spendId;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
}
