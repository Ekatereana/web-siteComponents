package componetsLab.websiteComponents.service;

import componetsLab.websiteComponents.entity.User;
import componetsLab.websiteComponents.entity.Visit;
import componetsLab.websiteComponents.repository.VisitRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class VisitService {

    @Autowired
    private VisitRepository visitRepository;


    public List<Visit> getAll(){
        return visitRepository.findAll();
    }

    public void save(Visit visit){
        visitRepository.save(visit);
    }

    public Visit getById(long id){
        return visitRepository.getOne(id);
    }

    public void deleteById(long id){
        visitRepository.deleteById(id);
    }

    public void update(Visit visit){
        visitRepository.save(visit);
    }


    public boolean getByISUserPlusDate( User user, Date date){
        List<Visit> userVisits = getByUser(user);

        for (int i = 0; i < userVisits.size() ; i++) {
            if(userVisits.get(i).getDate().equals(date)){
                return true;

            }

        }


        return false;
    }

    public List<Visit> getByUser(User user){
        List<Visit> all = visitRepository.findAll();
        List<Visit> result=new ArrayList<>();

        all.forEach(visit -> {
            if(visit.getUsers().contains(user)){
                result.add(visit);
            }
        });

        return result.size()== 0 ? null: result;
    }
}
