package com.javanc.service;

import java.io.IOException;
import java.util.List;

public interface IService <T, R, I>{
    List<R> getAll();
    R getById(I id);
    void delete(I id);
    void create(T data);
    void update(I id, T data);
}
