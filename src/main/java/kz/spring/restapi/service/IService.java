package kz.spring.restapi.service;

import java.util.Collection;
import java.util.Optional;

public interface IService<F> {
    Collection<F> findAll();
    Optional<F> findById(Long id);
    F saveOrUpdate(F f);
    String deleteById(Long id);
}
