package zin.settleapp.bo;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Owe {

	@Id
	String oweId = UUID.randomUUID().toString();

	@Column
	String owedUserId;
	@Column
	String userGettingMoneyId;
	@Column
	double share;

	@Column
	String spendId;
	
	public String getOweId() {
		return oweId;
	}
	public void setOweId(String oweId) {
		this.oweId = oweId;
	}
	public String getSpendId() {
		return spendId;
	}
	public void setSpendId(String spendId) {
		this.spendId = spendId;
	}
	public String getOwedUserId() {
		return owedUserId;
	}
	public void setOwedUserId(String owedUserId) {
		this.owedUserId = owedUserId;
	}
	public String getUserGettingMoneyId() {
		return userGettingMoneyId;
	}
	public void setUserGettingMoneyId(String userGettingMoneyId) {
		this.userGettingMoneyId = userGettingMoneyId;
	}
	public double getShare() {
		return share;
	}
	public void setShare(double spent) {
		this.share = spent;
	}
	
}
