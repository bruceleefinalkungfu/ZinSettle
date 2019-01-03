package zin.settleapp.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomePageController {

	@RequestMapping("/")
	public String welcome(Map<String, Object> model) {
		model.put("message", "hard coded messg");
		return "index";
	}

	
}
