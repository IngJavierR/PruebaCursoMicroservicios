package mx.com.curso.services.feign;

import mx.com.curso.commons.to.UserTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient("microservicio-service-two")
public interface ServiceTwoClient {

    @RequestMapping(method = RequestMethod.GET, value = "/users")
    List<UserTO> getUsers();

}
