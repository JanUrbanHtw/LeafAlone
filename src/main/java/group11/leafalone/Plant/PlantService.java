package group11.leafalone.Plant;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PlantService {
@Autowired
    private static SessionFactory factory;

    //create a PlantCare in database
    public Long addPlantCare(){
        Session session = factory.openSession();
        Transaction tx = null;
        Long plantCareID = null;

        try {
            tx = session.beginTransaction();
            PlantCare plantcare = new PlantCare();
            //employeeID = (Integer) session.save(employee);
            plantCareID = plantcare.getId();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return plantCareID;
    }
}
