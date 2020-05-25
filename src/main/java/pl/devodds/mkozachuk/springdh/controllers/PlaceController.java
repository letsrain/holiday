package pl.devodds.mkozachuk.springdh.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import pl.devodds.mkozachuk.springdh.models.Country;
import pl.devodds.mkozachuk.springdh.models.Holiday;
import pl.devodds.mkozachuk.springdh.models.Place;
import pl.devodds.mkozachuk.springdh.models.User;
import pl.devodds.mkozachuk.springdh.repositories.PlaceRepository;
import pl.devodds.mkozachuk.springdh.repositories.UserRepository;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/place")
@Controller
@Slf4j
@SessionAttributes("holiday")
public class PlaceController {

    private CountryController countryController;
    private PlaceRepository placeRepository;
    private UserRepository userRepository;

    public PlaceController(CountryController countryController, PlaceRepository placeRepository, UserRepository userRepository){
        this.countryController = countryController;
        this.placeRepository = placeRepository;
        this.userRepository = userRepository;
    }

    @ModelAttribute(name = "holiday")
    public Holiday holiday() {
        return new Holiday();
    }

    @ModelAttribute(name = "design")
    public Place design() {
        return new Place();
    }

    @GetMapping("/new-place")
    public String showDesignForm(Model model, Principal principal) {
        model.addAttribute("design", design());
        List<Country> allCounties = new ArrayList<>(countryController.getAllCounties());
        model.addAttribute("countyList", allCounties);

        String username = principal.getName();
        User user = userRepository.findByUsername(username);
        model.addAttribute("user", user);

        return "design";
    }

    @PostMapping("/new-place")
    public String processDesign(@Valid @ModelAttribute("design") Place design, Errors errors, @ModelAttribute Holiday holiday, @AuthenticationPrincipal User user) {
        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Processing design: " + design);
        design.setUser(user);
        Place saved = placeRepository.save(design);
        holiday.addDesign(saved);

        if(design.isDreamed()){
            return "redirect:/holidays/dreamed";
        }else {
            return "redirect:/holidays/planed";
        }
    }

}
