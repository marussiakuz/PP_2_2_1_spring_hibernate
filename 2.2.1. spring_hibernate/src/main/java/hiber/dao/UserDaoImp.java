package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   public Optional<User> findUserByCarModelAndSeries(String model, int series) {
      Query<User> query = sessionFactory.getCurrentSession()
              .createQuery("select u from User u left join Car c on u.car.id = c.id where lower(c.model) " +
                      "like lower(concat('%', :model, '%')) and c.series = :series", User.class)
              .setParameter("model", model)
              .setParameter("series", series)
              .setMaxResults(1);

      return Optional.ofNullable(query.getSingleResult());
   }
}
