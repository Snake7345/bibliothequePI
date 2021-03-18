package services;

import javax.persistence.EntityManager;
import java.util.List;


public interface IService<E> {

    public E getById(int id);

    public abstract E save(E e);

    public void delete(int id);
}
