package com.hlprmnky.vapor_spring_benchmark;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    public List<User> findAll();
}
