package zin.settleapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zin.settleapp.bo.Owe;
import zin.settleapp.bo.Spend;
import zin.settleapp.dao.OweRep;
import zin.settleapp.util.ApplicationContextProvider;
import zin.settleapp.util.ZinSettleAppProcessor;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class SpendController {

	@Autowired
	OweRep oweRepository;

	@PostMapping(value = "/spends/create")
	public Spend postSpend(@RequestBody Spend spend) {
		oweRepository.saveAll(convertToOwe(spend));
		ApplicationContextProvider.getBean(ZinSettleAppProcessor.class).calculateFinalTakeAmount();
		return spend;
	}

	@PostMapping(value = "/spends/create/all")
	public void postSpend(@RequestBody List<Spend> spend) {
		spend.forEach(e -> postSpend(e));
	}
	
	private List<Owe> convertToOwe(Spend spend) {
		List<Owe> owes = new ArrayList<>();
		String createdBy = spend.createdBy;
		for(Map.Entry<String, Double> entry : spend.getUserWithTheirShare().entrySet()) {
			String owedUser = entry.getKey();
			if(createdBy.equals(owedUser))
				continue;
			Owe owe = new Owe();
			owe.setOwedUserId(owedUser);
			owe.setUserGettingMoneyId(createdBy);
			owe.setSpendId(spend.spendId);
			owe.setShare(getOwedAmount(owedUser, spend));
			owes.add(owe);
		}
		return owes;
	}
	
	private double getOwedAmount(String user, Spend spend) {
		Double val = spend.getUserWithTheirShare().get(user);
		if(val != null) {
			if(spend.splitType == Spend.TYPE_AMT)
				return val;
			else if(spend.splitType == Spend.TYPE_PERCENTAGE)
				return (spend.total * val)/100.0;
			else
				throw new RuntimeException("No logic written yet");
		}
		return 0;
	}
	
	@DeleteMapping("/spends/{id}")
	public ResponseEntity<String> deleteSpend(@PathVariable("id") String id) {
		// TODO
		return new ResponseEntity<>("Spend has been deleted!", HttpStatus.OK);
	}

}
