package mx.com.curso.services.facade.impl;

import mx.com.curso.commons.to.UserTO;
import mx.com.curso.services.facade.IMicroservicioFacade;
import mx.com.curso.services.feign.ServiceTwoClient;
import mx.com.curso.services.service.IMicroservicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class MicroservicioFacade implements IMicroservicioFacade {

    @Autowired
    private IMicroservicioService microservicioService;

    @Autowired
    private ServiceTwoClient serviceTwoClient;

    public List<UserTO> getAllUsers() {
        return this.serviceTwoClient.getUsers();
        //return this.microservicioService.getUsers();
    }
}
