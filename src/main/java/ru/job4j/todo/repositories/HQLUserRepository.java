package ru.job4j.todo.repositories;

import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class HQLUserRepository implements UserRepository {

    private final SessionFactory sf;

    @Override
    public Optional<User> save(User user) {
        Session session = this.sf.openSession();
        try {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmailAndPassword(String email, String password) {
        Session session = this.sf.openSession();
        Optional<User> rsl = Optional.empty();
        try {
            session.beginTransaction();
            rsl = session.createQuery(
                    "FROM User as u where u.email = :fEmail AND u.password = :fPassword", User.class)
                    .setParameter("fEmail", email)
                    .setParameter("fPassword", password)
                    .uniqueResultOptional();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
        return rsl;
    }
}
