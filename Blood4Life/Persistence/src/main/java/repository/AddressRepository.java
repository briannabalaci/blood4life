package repository;

import domain.Address;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import repository.abstractRepo.AddressRepositoryInterface;

import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

public class AddressRepository implements AddressRepositoryInterface {
    private final String databaseURL;
    private final String databaseUsername;
    private final String databasePassword;
    private SessionFactory sessionFactory;
    private final Logger logger = Logger.getLogger("logging.txt");

    public AddressRepository(String databaseURL, String databaseUsername, String databasePassword) {
        this.databaseURL = databaseURL;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;

        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();

        logger.info("Initializing AddressRepository");
    }

    @Override
    public List<Address> findAddressesByCounty(String county) {
        List<Address> addresses;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Address> query = session.createQuery("select address from Addresses as address where address.County=?1",
                    Address.class);
            query.setParameter(1, county);
            addresses = query.list();
            session.getTransaction().commit();
            return addresses;
        }
    }

    @Override
    public List<Address> findAddressesByCity(String city) {
        List<Address> addresses;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Address> query = session.createQuery("select address from Addresses as address where address.City=?1",
                    Address.class);
            query.setParameter(1, city);
            addresses = query.list();
            session.getTransaction().commit();
            return addresses;
        }
    }

    @Override
    public Address findOne(String county, String city, String street, int number) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Address> query = session.createQuery("select address from Addresses as address where address.County=?1 and address.City=?2 and address.Street=?3 and address.Number=?4",
                    Address.class);
            query.setParameter(1, county);
            query.setParameter(2, city);
            query.setParameter(3, street);
            query.setParameter(4, number);
            Address address = query.uniqueResult();
            session.getTransaction().commit();
            return address;
        }
    }

    @Override
    public Address findOne(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<Address> query = session.createQuery("select address from Addresses as address where address.Id=?1",
                    Address.class);
            query.setParameter(1, id);
            Address address = query.uniqueResult();
            session.getTransaction().commit();
            return address;
        }
    }

    @Override
    public List<Address> findAll() {
        List<Address> addresses;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            addresses = session.createQuery("from Addresses", Address.class).list();
            session.getTransaction().commit();
        }
        return addresses;
    }

    @Override
    public void save(Address address) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(address);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(id);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Address address) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(address);
            session.getTransaction().commit();
        }
    }
}
