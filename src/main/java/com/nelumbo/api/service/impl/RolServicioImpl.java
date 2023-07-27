package com.nelumbo.api.service.impl;
import com.nelumbo.api.entity.Rol;
import com.nelumbo.api.repository.RolRepository;
import com.nelumbo.api.service.RolService;
import com.nelumbo.api.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolServicioImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Rol obtenerPorPorId(long id) {
        Rol rol = rolRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Rol con ID: " + id + " no encontrado."));
        return rol;
    }
}