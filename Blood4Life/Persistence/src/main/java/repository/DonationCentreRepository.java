package repository;

import domain.Address;
import domain.DonationCentre;
import exception.DatabaseException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import repository.abstractRepo.AddressRepositoryInterface;
import repository.abstractRepo.DonationCentreRepositoryInterface;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class DonationCentreRepository implements DonationCentreRepositoryInterface {
    private final String databaseURL;
    private final String databaseUsername;
    private final String databasePassword;
    private SessionFactory sessionFactory;
    private final AddressRepositoryInterface addressRepository;
    private final Logger logger = Logger.getLogger("logging.txt");

    public DonationCentreRepository(String databaseURL, String databaseUsername, String databasePassword, AddressRepositoryInterface addressRepository) {
        this.databaseURL = databaseURL;
        this.databaseUsername = databaseUsername;
        this.databasePassword = databasePassword;
        this.addressRepository = addressRepository;

        Configuration configuration = new Configuration().configure();
        sessionFactory = configuration.buildSessionFactory();

        logger.info("Initializing DonationCentreRepository");
    }

    @Override
    public List<DonationCentre> findDonationCentresByCapacity(int capacity) {
        List<DonationCentre> donationCentres;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<DonationCentre> query = session.createQuery("select donationCentre from DonationCentres as donationCentre where donationCentre.Capacity=?1",
                    DonationCentre.class);
            query.setParameter(1, capacity);
            donationCentres = query.list();
            session.getTransaction().commit();
            return donationCentres;
        }
    }

    @Override
    public List<DonationCentre> findDonationCentresByProgram(Time openHour, Time closeHour) {
        List<DonationCentre> donationCentres;
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<DonationCentre> query = session.createQuery("select donationCentre from DonationCentres as donationCentre where donationCentre.OpenHour=?1 and donationCentre.closeHour=?2",
                    DonationCentre.class);
            query.setParameter(1, openHour);
            query.setParameter(2, closeHour);
            donationCentres = query.list();
            session.getTransaction().commit();
            return donationCentres;
        }
    }

    @Override
    public DonationCentre findDonationCentreByAddress(Address address) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<DonationCentre> query = session.createQuery("select donationCentre from DonationCentres as donationCentre where donationCentre.address=?1",
                    DonationCentre.class);
            query.setParameter(1, address);
            DonationCentre donationCentre = query.uniqueResult();
            session.getTransaction().commit();
            return donationCentre;
        }
    }

    @Override
    public DonationCentre findOne(Long id) {
        try(Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query<DonationCentre> query = session.createQuery("select donationCentre from DonationCentres as donationCentre where donationCentre.Id=?1",
                    DonationCentre.class);
            query.setParameter(1, id);
            DonationCentre donationCentre = query.uniqueResult();
            session.getTransaction().commit();
            return donationCentre;
        }
    }

    @Override
    public List<DonationCentre> findAll() {
        List<DonationCentre> donationCentres;
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            donationCentres = session.createQuery("from DonationCentres", DonationCentre.class).list();
            session.getTransaction().commit();
        }
        return donationCentres;
    }

    @Override
    public void save(DonationCentre donationCentre) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.save(donationCentre);
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
    public void update(DonationCentre donationCentre) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.update(donationCentre);
            session.getTransaction().commit();
        }
    }
}
