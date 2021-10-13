package com.sofkau.library.services;

import com.sofkau.library.dtos.ResourceDto;
import com.sofkau.library.models.Resource;
import com.sofkau.library.repositories.ResourceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest
class ResourceServiceTest {
    @MockBean
    private ResourceRepository repository;
    @Autowired
    private ResourceService resourceService;


    private List<Resource> resources() {

        var resource1 = new Resource();
        resource1.setId("R-111");
        resource1.setName("Revista xyz");
        resource1.setQuantityAvailable(2);
        resource1.setLoanDate(null);
        resource1.setQuantityBorrowed(0);
        resource1.setType("Revista");
        resource1.setThematic("Farandula");
        var resource2 = new Resource();
        resource2.setId("L-111");
        resource2.setName("Libro xyz");
        resource2.setQuantityAvailable(2);
        resource2.setLoanDate(LocalDate.now());
        resource2.setQuantityBorrowed(1);
        resource2.setType("Libro");
        resource2.setThematic("Terror");//
        var resources = new ArrayList<Resource>();
        resources.add(resource1);
        resources.add(resource2);
        return resources;
    }

    @Test
    @DisplayName("getAll - Test")
    void getAll() {
        var resource = new Resource();

        Mockito.when(repository.findAll()).thenReturn(resources());

        var resultado = resourceService.getAll();

        Assertions.assertEquals(2, resultado.size());
        Assertions.assertEquals("R-111", resultado.get(0).getId());
        Assertions.assertEquals("Revista xyz", resultado.get(0).getName());
        Assertions.assertEquals(2, resultado.get(0).getQuantityAvailable());
        Assertions.assertEquals(null, resultado.get(0).getLoanDate());
        Assertions.assertEquals(0, resultado.get(0).getQuantityBorrowed());
        Assertions.assertEquals("Revista", resultado.get(0).getType());
        Assertions.assertEquals("Farandula", resultado.get(0).getThematic());
        Assertions.assertEquals("L-111", resultado.get(1).getId());
        Assertions.assertEquals("Libro xyz", resultado.get(1).getName());
        Assertions.assertEquals(2, resultado.get(1).getQuantityAvailable());
        Assertions.assertEquals(LocalDate.now(), resultado.get(1).getLoanDate());
        Assertions.assertEquals(1, resultado.get(1).getQuantityBorrowed());
        Assertions.assertEquals("Libro", resultado.get(1).getType());
        Assertions.assertEquals("Terror", resultado.get(1).getThematic());
    }

    @Test
    @DisplayName("save - success")
    void save() {
        var resourceDto = new ResourceDto();
        resourceDto.setName("Revista xyz");
        resourceDto.setQuantityAvailable(2);
        resourceDto.setLoanDate(null);
        resourceDto.setQuantityBorrowed(0);
        resourceDto.setType("Revista");
        resourceDto.setThematic("Farandula");

        Mockito.when(repository.save(Mockito.any())).thenReturn(resources().get(0));

        var result = resourceService.save(resourceDto);

        Assertions.assertNotNull(result);

        Assertions.assertEquals("Revista xyz", result.getName());
        Assertions.assertEquals(2, result.getQuantityAvailable());
        Assertions.assertEquals(null, result.getLoanDate());
        Assertions.assertEquals(0, result.getQuantityBorrowed());
        Assertions.assertEquals("Revista", result.getType());
        Assertions.assertEquals("Farandula", result.getThematic());

    }
    @Test
    @DisplayName("save - failure")
    void save_failure(){
        var resourceDto = new ResourceDto();
        resourceDto.setName("");
        resourceDto.setQuantityAvailable(2);
        resourceDto.setLoanDate(null);
        resourceDto.setQuantityBorrowed(0);
        resourceDto.setType("");
        resourceDto.setThematic("");

        Assertions.assertThrows(NoSuchElementException.class, () ->resourceService.save(resourceDto));
    }

    @Test
    void getById() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void checkAvailability() {
    }

    @Test
    void lend() {
    }

    @Test
    void recommendByType() {
    }

    @Test
    void recommendByTheme() {
    }

    @Test
    void recommendByTypeAndTheme() {
    }

    @Test
    void returnResource() {
    }
}