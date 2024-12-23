package core.basesyntax.dao.ma;

import core.basesyntax.model.ma.Mentor;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

public class MentorDaoImpl extends PersonDaoImpl implements MentorDao {
    public MentorDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Mentor> findByAgeGreaterThan(int age) {
        try (Session session = sessionFactory.openSession()) {
            Query<Mentor> findByAgeGreaterThanQuery
                    = session.createQuery("from Mentor m where m.age > :age", Mentor.class);
            findByAgeGreaterThanQuery.setParameter("age", age);
            return findByAgeGreaterThanQuery.getResultList();
        } catch (Exception e) {
            throw new EntityNotFoundException("Cannot find mentor by age greater than: " + age, e);
        }
    }
}
