package zin.settleapp.controller;

import java.util.ArrayList;
import java.util.List;

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
import zin.settleapp.dao.SpendRep;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class SpendController {

	@Autowired
	SpendRep repository;

	@Autowired
	OweRep oweRepository;

	@GetMapping("/spends")
	public List<Spend> getSpends() {
		List<Spend> users = new ArrayList<>();
		repository.findAll().forEach(users::add);
		return users;
	}

	@PostMapping(value = "/spends/create")
	public Spend postUser(@RequestBody Spend spend) {
		repository.save(spend);
		for(Owe owe : spend.getOwe()) {
			owe.setSpendId(spend.getSpendId());
			oweRepository.save(owe);
		}
		return spend;
	}
	
	@DeleteMapping("/spends/{id}")
	public ResponseEntity<String> deleteSpend(@PathVariable("id") String id) {
		repository.deleteById(id);
 
		return new ResponseEntity<>("Spend has been deleted!", HttpStatus.OK);
	}

}
