package com.plantaccion.alartapp.rch.service;

public interface DeleteICOService {
    void softDelete(String id);
    void activate(String id);
    void hardDelete(String id);
}
