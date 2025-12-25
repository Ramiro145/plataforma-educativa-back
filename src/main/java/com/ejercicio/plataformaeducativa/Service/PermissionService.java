package com.ejercicio.plataformaeducativa.Service;

import com.ejercicio.plataformaeducativa.Model.Permission;
import com.ejercicio.plataformaeducativa.Repository.IPermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


//utilizamos un servicio e interface generica para operaciones crud normales
@Service
public class PermissionService extends BaseServiceImp<Permission, Long> implements IPermissionService {


    public PermissionService(IPermissionRepository repository){
        super(repository);
    }



}
