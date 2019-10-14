package componetsLab.websiteComponents.controller;


import componetsLab.websiteComponents.entity.User;
import componetsLab.websiteComponents.entity.Visit;
import componetsLab.websiteComponents.entity.enumeration.Specializations;
import componetsLab.websiteComponents.entity.enumeration.Status;
import componetsLab.websiteComponents.entity.enumeration.UserRole;
import componetsLab.websiteComponents.service.UserService;
import componetsLab.websiteComponents.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

@Controller
@RequestMapping("/")
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private VisitService visitService;

    @GetMapping
    public ModelAndView getProfile(@RequestParam(value = "email", required = false) String email) {
        User currentUser = userService.getCurrentUser(email);
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
            view = new ModelAndView("security/register");

        }

        return view;
    }

    @GetMapping("/login")
    public ModelAndView getLogon(){
        return new ModelAndView("security/login");
    }

    @GetMapping("/register")
    public ModelAndView getRegister(@RequestParam(value = "error", required = false) Error error) {
        ModelAndView result = new ModelAndView("security/register");
        if (error != null) {
            result.addObject("error", error.disc);
        }
        return result;
    }


    @PostMapping("/register")
    public ModelAndView postRegister(@RequestParam(value = "name") String name,
                                     @RequestParam(value = "surname") String lastName,
                                     @RequestParam(value = "insurance") String insurance,
                                     @RequestParam(value = "email") String email,
                                     @RequestParam(value = "password1") String passOne,
                                     @RequestParam(value = "password2") String passTwo) {
        ModelAndView view = null;
        if (userService.getByEmail(email)!=null) {
            view = new ModelAndView("security/register");
            view.addObject("error", Error.IS_EMAIL.disc);

        } else {
            if (isValidEmail(email)) {
                if (passOne.equals(passTwo)) {
                    view = new ModelAndView("patient");
                    User user = new User();
                    user.setPassword(passOne);
                    user.setEmail(email);
                    user.setName(name);
                    user.setLastName(lastName);
                    if(insurance.equals("on")){
                        user.setInsurance(true);
                    }
                    else {
                        user.setInsurance(false);
                    }
                    userService.addNewPatient(user);
                    view.addObject("id", user.getId());
                } else {
                    view = new ModelAndView("security/register");
                    view.addObject("error", Error.NON_PASS.disc);
                }
            } else {
                view = new ModelAndView("security/register");
                view.addObject("error", Error.NON_EMAIL.disc);
            }
        }

        return view;


    }

    private enum Error{
        NON_PASS("The passwords are not equal. Retype the password"),
        NON_EMAIL("Invalid email, retype it"),
        IS_EMAIL("This email is already taken. Please use different one.");

        private String disc;

        Error(String disc) {
            this.disc = disc;
        }
    }

    public boolean isValidEmail(String email) {
        if (email.contains("@") && (!email.contains("_") || !email.contains("!"))) {
            return true;
        }

        return false;
    }

    @GetMapping("/create-visit")
    public ModelAndView createVisit(@RequestParam(value = "specialization", required = false) Specializations specialization,
                                    @RequestParam(value = "email", required = false) String email) {
        ModelAndView view = new ModelAndView("actions/create-visit");
        view.addObject("user", userService.getCurrentUser(email));
        if (specialization == null) {
            view.addObject("specializations", Specializations.values());
        } else {
            view.addObject("doctors", userService.getBySpecialization(specialization.name()));
        }

        return view;
    }

    @PostMapping("/create-visit")
    public ModelAndView createVisitPost(@RequestParam("doctor") User doctor,
                                        @RequestParam("date") Date date,
                                        @RequestParam(value = "email", required = false) String email) {
        User patient = userService.getCurrentUser(email);
        ModelAndView view = new ModelAndView("action/create-visit");
        if (visitService.getByISUserPlusDate(doctor, date)) {
            view.addObject("error", "already-exist");
        } else {
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
    public ModelAndView changeVisitGet(@RequestParam("id") long id,
                                       @RequestParam(value = "email", required = false) String email) {
        ModelAndView view = new ModelAndView("action/change");
        view.addObject("user", userService.getCurrentUser(email));
        view.addObject("visit", visitService.getById(id));
        return view;
    }

    @PostMapping("/change-visit")
    public ModelAndView changeVisitPost(
            @RequestParam("id") long id,
            @RequestParam("doctor") User doctor,
            @RequestParam("date") Date date,
            @RequestParam("status") Status status) {
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
    public ModelAndView deleteSubscription(@RequestParam("id") long id,
                                           @RequestParam(value = "email", required = false) String email) {
        ModelAndView view = new ModelAndView("action/delete");


        visitService.deleteById(id);
        view.addObject("user", userService.getCurrentUser(email));
        view.addObject("error", "success");
        view.addObject("visits", visitService.getByUser(userService.getCurrentUser(email)));


        return view;
    }

}
