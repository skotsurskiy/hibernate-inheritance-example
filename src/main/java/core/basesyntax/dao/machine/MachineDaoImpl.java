package core.basesyntax.dao.machine;

import core.basesyntax.dao.AbstractDao;
import core.basesyntax.model.machine.Machine;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class MachineDaoImpl extends AbstractDao implements MachineDao {
    public MachineDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Machine save(Machine machine) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.persist(machine);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Cannot insert machine to DB" + machine, e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return machine;
    }

    @Override
    public List<Machine> findByAgeOlderThan(int age) {
        try (Session session = sessionFactory.openSession()) {
            Query<Machine> findByAgeOlderThanQuery
                    = session.createQuery("from Machine m where m.year < :age", Machine.class);
            findByAgeOlderThanQuery.setParameter("age", LocalDate.now().getYear() - age);
            return findByAgeOlderThanQuery.getResultList();
        } catch (Exception e) {
            throw new EntityNotFoundException("Cannot find machine by age older than: " + age, e);
        }
    }
}
