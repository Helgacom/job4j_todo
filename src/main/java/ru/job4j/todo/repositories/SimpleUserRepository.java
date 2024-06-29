package ru.job4j.todo.repositories;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class SimpleUserRepository implements UserRepository {

    private final SessionFactory sf;

    @Override
    public Optional<User> save(User user) {
        Session session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            rsl = Optional.of(user);
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Session session = sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            session.beginTransaction();
            session.beginTransaction();
            Query<User> query = session.createQuery(
                    "from User u where u.email = :fEmail and u.password = :fPassword", User.class);
            query.setParameter("fEmail", email);
            query.setParameter("fPassword", password);
            session.getTransaction().commit();
            rsl = query.uniqueResultOptional();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }
}
