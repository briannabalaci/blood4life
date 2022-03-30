package repository;

import domain.Entity;

import java.util.List;

public interface RepositoryInterface<ID, ENTITY extends Entity<ID>> {
    ENTITY findOne(ID id);
    List<ENTITY> findAll();
    void save(ENTITY entity);
    void delete(ID id);
    void update(ENTITY entity);
}
