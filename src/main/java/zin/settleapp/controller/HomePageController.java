package zin.settleapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import zin.settleapp.bo.Owe;
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
			case1();
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
		private User getUser(String name) {
			User u = new User();
			u.setName(name);
			u.setUserId(name);
			return u;
		}
		private Owe getOwe(User from, User to, double amt) {
			Owe owe = new Owe();
			owe.setOwedUserId(from.getName());
			owe.setUserGettingMoneyId(to.getName());
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
		for(User u : data.users)
			userController.postUser(u);
		for(Owe owe : data.o)
			oweController.postOwe(owe);
		processor.calculateFinalTakeAmount();
		for(User u : data.users) {
			System.out.println(userController.getUser(u.userId));
		}
	}

}
