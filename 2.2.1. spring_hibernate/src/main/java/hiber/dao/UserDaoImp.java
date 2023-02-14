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
   private final SessionFactory sessionFactory;

   @Autowired
   public UserDaoImp(SessionFactory sessionFactory) {
      this.sessionFactory = sessionFactory;
   }

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User", User.class);
      return query.getResultList();
   }

   @Override
   public Optional<User> findUserByCarModelAndSeries(String model, int series) {
      Query<User> query = sessionFactory.getCurrentSession()
              .createQuery("from User where car.model like :model and car.series = :series", User.class)
              .setParameter("model", model)
              .setParameter("series", series)
              .setMaxResults(1);

      return Optional.ofNullable(query.getSingleResult());
   }
}
