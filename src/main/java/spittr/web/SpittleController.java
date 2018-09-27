package spittr.web;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import spittr.Spitter;
import spittr.Spittle;
import spittr.data.SpitterRepository;
import spittr.data.SpittleRepository;

@Controller
@RequestMapping("/spittles")
public class SpittleController {
	
	private static final String MAX_LONG_AS_STRING = "" + Long.MAX_VALUE;
	private SpittleRepository spittleRepository;
	private SpitterRepository spitterRepository;
	
	@Autowired
	public SpittleController (SpittleRepository spittleRepository, SpitterRepository spitterRepository) {
		this.spittleRepository = spittleRepository;
		this.spitterRepository = spitterRepository;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public String spittles ( 
			Model model,
			@RequestParam(value="max", defaultValue=MAX_LONG_AS_STRING ) long max, 
			@RequestParam(value="count", defaultValue="20" ) int count
			) {
		List<Spittle> spittles = spittleRepository.findSpittles(max, count);
		model.addAttribute("spittleList", spittles);
		return "spittles";
	}
	
	@RequestMapping(value="/{spittleId}", method=RequestMethod.GET)
	public String spittles (
			@PathVariable long spittleId, //Can be more informative as @PathVariable value="/{spittleId}" long spittleId,
			Model model
			) {
		model.addAttribute(spittleRepository.findOne(spittleId));
		return "spittle";
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public String addSpittle(Spittle spittle) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if( principal instanceof UserDetails ) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		
		Spitter spitter = spitterRepository.findByUsername(username);
		
		spittleRepository.addSpittle( new Spittle(spittle.getMessage(), spittle.getLongitude(), spittle.getLatitude()), spitter );
		return ("redirect:/spittles");
	}
	
}
