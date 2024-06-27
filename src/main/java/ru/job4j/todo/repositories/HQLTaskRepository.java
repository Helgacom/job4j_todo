package ru.job4j.todo.repositories;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HQLTaskRepository implements TaskRepository {

    private final SessionFactory sf;

    @Override
    public Optional<Task> create(Task task) {
        Session session = this.sf.openSession();
        try {
            session.beginTransaction();
            session.save(task);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.ofNullable(task);
    }

    @Override
    public Collection<Task> findAll() {
        Session session = this.sf.openSession();
        List<Task> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task", Task.class).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Optional<Task> findById(int id) {
        Session session = this.sf.openSession();
        Optional<Task> rsl = Optional.empty();
        try {
            session.beginTransaction();
            Query<Task> query = session.createQuery(
                    "FROM Task as t where t.id = :fId", Task.class);
            query.setParameter("fId", id);
            rsl = Optional.of(query.uniqueResult());
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public boolean update(Task tasks) {
        boolean rsl = false;
        Session session = this.sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Task SET description = :description WHERE id = :fId")
                    .setParameter("description", tasks.getDescription())
                    .setParameter("fId", tasks.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            rsl = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public boolean updateState(Task tasks) {
        boolean rsl = false;
        Session session = this.sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "UPDATE Task SET done = :done WHERE id = :fId")
                    .setParameter("done", tasks.isDone())
                    .setParameter("fId", tasks.getId())
                    .executeUpdate();
            session.getTransaction().commit();
            rsl = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public boolean deleteById(int id) {
        boolean rsl = false;
        Session session = this.sf.openSession();
        try {
            session.beginTransaction();
            session.createQuery(
                            "DELETE Task WHERE id = :fId")
                    .setParameter("fId", id)
                    .executeUpdate();
            session.getTransaction().commit();
            rsl = true;
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Collection<Task> findNew() {
        Session session = this.sf.openSession();
        Collection<Task> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task t where done = :fDone order by t.id", Task.class)
                    .setParameter("fDone", false)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }

    @Override
    public Collection<Task> findDone() {
        Session session = this.sf.openSession();
        Collection<Task> result = new ArrayList<>();
        try {
            session.beginTransaction();
            result = session.createQuery("FROM Task t where done = :fDone order by t.id", Task.class)
                    .setParameter("fDone", true)
                    .list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return result;
    }
}
