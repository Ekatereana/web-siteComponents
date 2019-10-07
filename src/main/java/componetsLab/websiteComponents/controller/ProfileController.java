package componetsLab.websiteComponents.controller;


import componetsLab.websiteComponents.entity.User;
import componetsLab.websiteComponents.entity.Visit;
import componetsLab.websiteComponents.entity.enumeration.Specializations;
import componetsLab.websiteComponents.entity.enumeration.Status;
import componetsLab.websiteComponents.entity.enumeration.UserRole;
import componetsLab.websiteComponents.service.UserService;
import componetsLab.websiteComponents.service.VisitService;
import lombok.Lombok;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/personal-cabinet")
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private VisitService visitService;

    @GetMapping
    public ModelAndView getProfile() {
        User currentUser = userService.getCurrentUser();
        /* If user role is doctor we sand doctor-form-page
         * else if user role is patient sand patient-form-page
         * if no user than sand form of registration */
        ModelAndView view = null;
        if (currentUser != null) {
            UserRole role = currentUser.getUserRole();
            view.addObject("user", currentUser);
            view.addObject("visits", visitService.getByUser(currentUser));
            switch (role) {
                case ADMIN:
                    view = new ModelAndView("security/for-admin");
                    break;
                case DOCTOR:
                    view = new ModelAndView("security/for-doctor");
                    break;
                case PATIENT:
                    view = new ModelAndView("security/for-patient");
                    break;
            }
        } else {
            view = new ModelAndView("security/register-login");

        }

        return view;
    }

    @GetMapping("/create-visit")
    public ModelAndView createVisit(@RequestParam(value = "specialization", required = false) Specializations specialization) {
        ModelAndView view = new ModelAndView("actions/create-visit");
        view.addObject("user", userService.getCurrentUser());
        if (specialization == null) {
            view.addObject("specializations", Specializations.values());
        }
        else {
            view.addObject("doctors", userService.getBySpecialization(specialization.name()));
        }

        return view;
    }

    @PostMapping("/create-visit")
    public ModelAndView createVisitPost(@RequestParam("doctor") User doctor,
                                        @RequestParam("date")Date date){
        User patient = userService.getCurrentUser();
        ModelAndView view = new ModelAndView("action/create-visit");
        if(visitService.getByISUserPlusDate(doctor, date)){
            view.addObject("error", "already-exist");
        }
        else {
            Visit visit = new Visit();
            visit.setDate(date);
            visit.addUser(doctor);
            visit.addUser(patient);
            visit.setStatus(Status.ACTIVE);
            visitService.save(visit);
            view.addObject("error", "success");

        }
        return view;
    }

    @GetMapping("/change-visit")
    public ModelAndView cnangeVisitGet(@RequestParam("id") long id){
        ModelAndView view = new ModelAndView("action/change");
        view.addObject("user", userService.getCurrentUser());
        view.addObject("visit", visitService.getById(id));
        return view;
    }

    @PostMapping("/change-visit")
    public ModelAndView changeVisitPost(
                                    @RequestParam("id") long id,
                                    @RequestParam("doctor") User doctor,
                                    @RequestParam("date")Date date,
                                    @RequestParam("status") Status status){
        ModelAndView view = new ModelAndView("action/change");
        Visit visit = visitService.getById(id);
        visit.setDate(date);
        visit.addUser(doctor);
        visit.setStatus(status);
        visitService.update(visit);
        view.addObject("error", "success");
        return view;
    }

    @GetMapping("/delete")
    public ModelAndView deleteSubscription(@RequestParam("id") long id) {
        ModelAndView view = new ModelAndView("action/delete");


            visitService.deleteById(id);
            view.addObject("user", userService.getCurrentUser());
            view.addObject("error", "success");
            view.addObject("visits", visitService.getByUser(userService.getCurrentUser()));


        return view;
    }

}
