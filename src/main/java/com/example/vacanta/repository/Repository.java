package com.example.vacanta.repository;

import com.example.vacanta.domain.Client;
import com.example.vacanta.domain.Entity;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>>{
    List<E> getAll();
    void add(E entity);
}
