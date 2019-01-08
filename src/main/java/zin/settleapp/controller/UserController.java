package zin.settleapp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import zin.settleapp.bo.User;
import zin.settleapp.bo.UserFinalTake;
import zin.settleapp.constants.Const;
import zin.settleapp.dao.OweRep;
import zin.settleapp.dao.UserFinalTakeRepository;
import zin.settleapp.dao.UserRep;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = Const.ALLOW_CROSS_ORIGIN_URL)
public class UserController {

	@Autowired
	UserRep repository;
	
	@Autowired
	OweRep oweRep;
	
	@Autowired
	UserFinalTakeRepository  finalRep;

	@GetMapping("/users")
	public List<User> getUsers() {
		List<User> users = new ArrayList<>();
		repository.findAll().forEach(users::add);
		return users;
	}

	@PostMapping(value = "/users/create")
	public User postUser(@RequestBody User user) {
		repository.save(user);
		return user;
	}
	
	@DeleteMapping("/users/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") String id) {
		repository.deleteById(id);
 
		return new ResponseEntity<>("User has been deleted!", HttpStatus.OK);
	}

	@GetMapping("/users/get/{id}")
	public ResponseEntity<User> getUser(@PathVariable("id") String id) {
		User user = processUser(id, new HashSet<>());;
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	public void deleteALl() {
		repository.deleteAll();
	}
	
	public User processUser(String id, Set<String> ids) {
		Optional<User> optional = repository.findById(id);
		User user = null;
		if(optional.isPresent())
			user = optional.get();
		else
			return user;
		HashMap<String, Double> takeMoney = new HashMap<>();
		List<UserFinalTake> finalTakes = finalRep.findByUserTakingMoney(user.userId);
		if(finalTakes != null)
			finalTakes.forEach(e -> takeMoney.put(e.getUserGivingMoney(), e.getAmount()));
		user.setTakeFromMap(takeMoney);
		return user;
	}
	
}
