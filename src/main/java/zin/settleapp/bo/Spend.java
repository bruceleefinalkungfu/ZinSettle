package zin.settleapp.bo;

import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class Spend {
	@Id
	public String spendId = UUID.randomUUID().toString();
	@Column
	public double total;
	
	@Column
	public String createdBy;
	
	@Transient	
	List<Owe> owe;
	
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public List<Owe> getOwe() {
		return owe;
	}
	public void setOwe(List<Owe> owe) {
		this.owe = owe;
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
