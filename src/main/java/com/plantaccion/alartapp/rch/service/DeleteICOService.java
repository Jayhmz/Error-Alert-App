package com.plantaccion.alartapp.rch.service;

public interface DeleteICOService {
    void softDelete(Long id);
    void activate(Long id);
    void hardDelete(Long id);
}
