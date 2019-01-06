package zin.settleapp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zin.settleapp.bo.User;
import zin.settleapp.bo.UserFinalTake;
import zin.settleapp.controller.UserController;
import zin.settleapp.dao.OweRep;
import zin.settleapp.dao.UserFinalTakeRepository;
import zin.settleapp.dao.UserRep;

@Component
public class ZinSettleAppProcessor {

	@Autowired
	UserRep repository;
	
	@Autowired
	OweRep oweRep;
	
	@Autowired
	UserFinalTakeRepository  finalRep;
	
	public void calculateFinalTakeAmount() {
		Iterable<User> users = repository.findAll();
		List<UserFinalTake> userFinalTakes = new ArrayList<>();
		
		HashMap<String, List<String>> keyTakingValueGiving = new HashMap<>();
		HashMap<String, Double> finalTakeAmount = new HashMap<>();
		HashMap<String, Double> unmodifiedFinalTakeAmount = new HashMap<>();
		
		for(User user : users) {
			String id = user.getUserId();
			HashMap<String, Double> oweToMap = new HashMap<>();
			HashMap<String, Double> toTakeMoneyMap = new HashMap<>();
			
			user.setOweToMap(oweToMap);
			user.setTakeFromMap(toTakeMoneyMap);
			oweRep.findByUserGettingMoneyId(id).forEach(e -> { 
				//oweToMap.put(e.getOwedUserId(), oweToMap.get(e.getOwedUserId())==null ? e.getShare() : oweToMap.get(e.getOwedUserId()) + e.getShare());
					List<String> idsOweing = keyTakingValueGiving.get(e.getUserGettingMoneyId());
					if(idsOweing == null) idsOweing = new ArrayList<>(); 
					idsOweing.add(e.getOwedUserId());
					keyTakingValueGiving.put(e.getUserGettingMoneyId(), idsOweing);
			});
			/*
			oweRep.findByOwedUserId(id).forEach(e -> toTakeMoneyMap.put(e.getUserGettingMoneyId(), toTakeMoneyMap.get
					(e.getUserGettingMoneyId())==null ? e.getShare(): toTakeMoneyMap.get(e.getUserGettingMoneyId()) + e.getShare()));
			//*/
			double toTakeMoneyAmt = 0, owedAmt = 0;
			for(double d : oweToMap.values()) owedAmt += d;
			for(double d : toTakeMoneyMap.values()) toTakeMoneyAmt += d;
			
			finalTakeAmount.put(id, toTakeMoneyAmt - owedAmt);
		}
		for(Map.Entry<String, Double> entry : finalTakeAmount.entrySet()) {
			String userId = entry.getKey();
			double toTakeRemaining = entry.getValue();
			unmodifiedFinalTakeAmount.put(userId, toTakeRemaining);
			if (toTakeRemaining > 0) {
				for(String owedUser : keyTakingValueGiving.get(userId)) {
					double virtualSalary = finalTakeAmount.get(owedUser);
					if(virtualSalary < 0) {
						UserFinalTake userFinalTake = new UserFinalTake();
						userFinalTake.setUserTakingMoney(userId);
						userFinalTake.setUserGivingMoney(owedUser);
						double remaininSalryOfOwedUsr = Math.abs(virtualSalary);
						remaininSalryOfOwedUsr = remaininSalryOfOwedUsr - toTakeRemaining;
						if(remaininSalryOfOwedUsr < 0)
						{
							toTakeRemaining = remaininSalryOfOwedUsr * -1;
							remaininSalryOfOwedUsr = 0;
						} else {
							toTakeRemaining = 0;
						}
						double owedUserGaveMoney = Math.abs(virtualSalary) - remaininSalryOfOwedUsr;
						if(virtualSalary < 0)
							remaininSalryOfOwedUsr = remaininSalryOfOwedUsr * -1;
						userFinalTake.setAmount(owedUserGaveMoney);
						userFinalTakes.add(userFinalTake);
						
						finalTakeAmount.put(owedUser, remaininSalryOfOwedUsr);
						if(toTakeRemaining == 0) {
							break;
						}
					}
				}
			}
		}
		finalRep.saveAll(userFinalTakes);
	}
	
}
