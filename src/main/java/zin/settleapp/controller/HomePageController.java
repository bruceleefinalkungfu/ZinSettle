package zin.settleapp.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import zin.settleapp.bo.Owe;
import zin.settleapp.bo.Spend;
import zin.settleapp.bo.User;
import zin.settleapp.util.ApplicationContextProvider;
import zin.settleapp.util.ZinSettleAppProcessor;

@RestController
public class HomePageController {

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", "hard coded messg");
		return "index";
	}

	class Data {
		public List<User> users = new ArrayList<>();
		public List<Owe> o = new ArrayList<>();
		public Data() {
			case2();
		}
		private void case1() {
			User u1 = getUser("A");
			User u2 = getUser("B");
			User u3 = getUser("C");
			users.add(u1);
			users.add(u2);
			users.add(u3);
			o.add(getOwe(u1, u2, 90));
			o.add(getOwe(u1, u3, 10));
			o.add(getOwe(u2, u1, 20));
			o.add(getOwe(u2, u3, 70));
			o.add(getOwe(u3, u1, 20));
			o.add(getOwe(u3, u2, 10));
		}
		private void case2() {
			User u1 = getUser("A");
			User u2 = getUser("B");
			User u3 = getUser("C");
			User u4 = getUser("D");
			users.add(u1);
			users.add(u2);
			users.add(u3);
			users.add(u4);
			o.add(getOwe(u1, u2, 90));
			o.add(getOwe(u1, u4, 30));
			o.add(getOwe(u2, u4, 20));
			o.add(getOwe(u2, u3, 90));
			o.add(getOwe(u3, u1, 20));
			o.add(getOwe(u3, u2, 10));
		}
		private User getUser(String name) {
			User u = new User();
			u.setName(name);
			u.setUserId(name);
			return u;
		}
		private Owe getOwe(User from, User to, double amt) {
			Owe owe = new Owe();
			owe.setUserGettingMoneyId(from.getName());
			owe.setOwedUserId(to.getName());
			owe.setShare(amt);
			owe.setSpendId(owe.getOweId());
			return owe;
		}
	}
	
	@GetMapping("/z")
	public void contextLoads() {
		UserController userController = ApplicationContextProvider.getBean(UserController.class);
		OweController oweController = ApplicationContextProvider.getBean(OweController.class);
		ZinSettleAppProcessor processor = ApplicationContextProvider.getBean(ZinSettleAppProcessor.class);
		Data data = new Data();
		userController.deleteALl();
		oweController.deleteAll();
		for(User u : data.users)
			userController.postUser(u);
		for(Owe owe : data.o)
			oweController.postOwe(owe);
		processor.calculateFinalTakeAmount();
		for(User u : data.users) {
			System.out.println(userController.getUser(u.userId));
		}
	}
	
	@GetMapping("/s")
	public void addSpends() {
		String json = "[ {  \"total\":150,  \"createdBy\":\"A\",  \"splitType\":0,  \"userWithTheirShare\": {  \"A\": 50,  \"B\": 90,  \"C\": 10  } }, {  \"total\":100,  \"createdBy\":\"B\",  \"splitType\":0,  \"userWithTheirShare\": {  \"A\": 20,  \"B\": 10,  \"C\": 70  } }, {  \"total\":60,  \"createdBy\":\"C\",  \"splitType\":0,  \"userWithTheirShare\": {  \"A\": 20,  \"B\": 10,  \"C\": 30  } } ]";
		Spend[] spends = new Gson().fromJson(json, Spend[].class);
		ApplicationContextProvider.getBean(SpendController.class).postSpend(Arrays.asList(spends));
	}

}
