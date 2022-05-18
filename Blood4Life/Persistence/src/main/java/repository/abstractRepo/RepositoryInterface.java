package repository.abstractRepo;

import domain.IEntity;

import java.util.List;

public interface RepositoryInterface<ID, ENTITY extends IEntity<ID>> {
    ENTITY findOne(ID id);
    List<ENTITY> findAll();
    void save(ENTITY entity);
    void delete(ID id);
    void update(ENTITY entity);
}
