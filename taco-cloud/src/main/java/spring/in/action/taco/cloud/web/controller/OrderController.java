package spring.in.action.taco.cloud.web.controller;

import lombok.extern.slf4j.Slf4j;
import spring.in.action.taco.cloud.domain.TacoOrder;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("tacoOrder")
public class OrderController {

	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}

	@PostMapping
	public String processOrder(TacoOrder order, SessionStatus sessionStatus) {
		log.info("Order submitted: {}", order);
		sessionStatus.setComplete();
		return "redirect:/";
	}
}