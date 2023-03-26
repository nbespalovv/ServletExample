package org.example.orm;

import org.example.domain.UserProfile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.Collections;
import java.util.List;

public class UserProfileDAOImpl implements UserProfileDAO {

    public UserProfileDAOImpl() {
    }

    @Override
    public UserProfile getByLogin(String login) {
        try {
            return (UserProfile) HibernateSessionFactory.getSessionFactory().openSession().createCriteria(UserProfile.class)
                    .add(Restrictions.eq("login", login)).uniqueResult();
        } catch (HibernateException e) {
            return null;
        }
    }

    @Override
    public UserProfile get(long id) {
        return (UserProfile) HibernateSessionFactory.getSessionFactory().openSession().get(UserProfile.class, id);
    }

    @Override
    public List<UserProfile> getAll() {
        try {
            return HibernateSessionFactory.getSessionFactory().openSession().createCriteria(UserProfile.class).list();
        } catch (HibernateException e) {
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    public void add(UserProfile dataSet) {
        Session session = HibernateSessionFactory.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(dataSet);
        transaction.commit();
        session.close();
    }
}
