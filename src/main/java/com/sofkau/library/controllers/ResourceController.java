package com.sofkau.library.controllers;

import com.sofkau.library.dtos.ResourceDto;
import com.sofkau.library.services.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recursos")
public class ResourceController {
    Logger logger = LoggerFactory.getLogger(ResourceController.class);
    private ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @PostMapping("/agregar")
    public ResponseEntity<ResourceDto> add(@RequestBody ResourceDto resourceDTO){
        return new ResponseEntity(resourceService.save(resourceDTO), HttpStatus.CREATED);
    }
    @GetMapping()
    public ResponseEntity<ResourceDto> findAll(){
        return new ResponseEntity(resourceService.getAll(),HttpStatus.OK);
    }

    @PutMapping("/editar")
    public ResponseEntity<ResourceDto> edit(@RequestBody ResourceDto resourceDTO){
        if(!resourceDTO.getId().isEmpty()){
            return new ResponseEntity(resourceService.update(resourceDTO),HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id){
        try {
            resourceService.delete(id);
            return new ResponseEntity(HttpStatus.OK);
        }catch (Exception e){
            logger.error("ocurrio el siguiente error: "+e);
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/disponibilidad/{id}")
    public ResponseEntity availability(@PathVariable("id") String id){
        return new ResponseEntity(resourceService.isAvailableToBeLent(id), HttpStatus.OK);
    }

    @PutMapping("/prestar/{id}")
    public ResponseEntity lend(@PathVariable("id") String id){
        return  new ResponseEntity(resourceService.lend(id), HttpStatus.OK);
    }

    //el NA es "No Aplica" puesto que puede ser una de estas o ambas
    @GetMapping("/recomendar/{type}/{thematic}")
    public ResponseEntity<ResourceDto> recommendation(@PathVariable("type") String type, @PathVariable("thematic") String thematic){
        if(thematic.equals("NA")&&!type.equals("NA")){
            return new ResponseEntity(resourceService.recommendByType(type),HttpStatus.OK);
        }
        if(!thematic.equals("NA")&&type.equals("NA")){
            return new ResponseEntity(resourceService.recommendByTheme(thematic),HttpStatus.OK);
        }
        return new ResponseEntity(resourceService.recommendByTypeAndTheme(type,thematic),HttpStatus.OK);
    }
    @PutMapping("/devolver/{id}")
    public ResponseEntity returnResource(@PathVariable("id") String id){
        return  new ResponseEntity(resourceService.returnResource(id), HttpStatus.OK);
    }
}
