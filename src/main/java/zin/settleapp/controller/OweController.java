package zin.settleapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zin.settleapp.bo.Owe;
import zin.settleapp.bo.User;
import zin.settleapp.dao.OweRep;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class OweController {

	@Autowired
	OweRep oweRep;
	

	@PostMapping(value = "/owe/create")
	public Owe postOwe(@RequestBody Owe owe) {
		oweRep.save(owe);
		return owe;
	}
	
	public void deleteAll() {
		oweRep.deleteAll();
	}
	
	@GetMapping(value = "/owe/")
	public Iterable<Owe> getOwes() {
		return oweRep.findAll();
	}
	
}
