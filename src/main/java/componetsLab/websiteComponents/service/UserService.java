package componetsLab.websiteComponents.service;

import componetsLab.websiteComponents.entity.User;
import componetsLab.websiteComponents.entity.enumeration.UserRole;
import componetsLab.websiteComponents.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public User getByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public void addNewPatient(User user) {
        userRepository.save(user);
    }

    public List<User> getAllPatients() {
        return userRepository.findAll();
    }

    public void update(User user) {
        userRepository.save(user);
    }

    public void delete(User user) {
        userRepository.delete(user);

    }

    public void setPassword(User user, String password) {
        user.setPassword(password);
    }

    public User getCurrentUser(String currentUserEmail) {
        if (currentUserEmail == null) {
            return null;

        }
        return userRepository.getUserByEmail(currentUserEmail);
    }

    public List<User> getBySpecialization(String specialization){
       List<User> doctors = new ArrayList<>();

       userRepository.findAll().forEach(user ->{
           if(user.getUserRole().equals(UserRole.DOCTOR)){
               doctors.add(user);
           }
       });

       doctors.forEach(doctor ->{
           if(!doctor.getSpecialization().equals(specialization)){
               doctors.remove(doctor);
           }
       });

       return doctors;
    }

    public User getById(long id){
        return userRepository.getOne(id);
    }


}
