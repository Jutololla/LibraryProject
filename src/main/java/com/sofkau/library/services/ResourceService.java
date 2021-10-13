package com.sofkau.library.services;

import com.sofkau.library.dtos.ResourceDto;
import com.sofkau.library.utils.Utils;
import com.sofkau.library.models.Resource;
import com.sofkau.library.repositories.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ResourceService {

    private ResourceRepository repository;

    @Autowired
    public ResourceService(ResourceRepository repository, Utils mapper) {
        this.repository = repository;
    }

    public List<ResourceDto> getAll() {
        List<ResourceDto> resourceDtos = new ArrayList<>();
        repository.findAll().forEach(resource -> resourceDtos.add(Utils.resourceToDto(resource)));
        return resourceDtos;
    }

    public ResourceDto save(ResourceDto resourceDTO) {
        if (resourceDTO.isEmpty()) {
            throw new NoSuchElementException("The object to create can not be empty");
        }
        Resource resource=repository.save(Utils.convertToResource(resourceDTO));
        return Utils.resourceToDto(resource);
    }

    public ResourceDto getById(String id) {
        Optional<Resource> resource = repository.findById(id);
        if (resource.isEmpty()) {
            throw new NoSuchElementException("The element does not exist in the database ");
        }
        return Utils.resourceToDto(resource.get());
    }

    public void delete(String id) {
        Optional<Resource> resource = repository.findById(id);
        if (resource.isEmpty()) {
            throw new NoSuchElementException("The element does not exist in the database ");
        }
        repository.delete(Utils.convertToResource(getById(id)));
    }

    public ResourceDto update(ResourceDto resourceDTO) {
        Optional<Resource> resource = repository.findById(resourceDTO.getId());
        if (resource.isEmpty()) {
            throw new NoSuchElementException("The element does not exist in the database ");
        }
        return Utils.resourceToDto((repository.save(resource.get())));
    }

    public String isAvailableToBeLent(String id) {
        ResourceDto dto = getById(id);
        if (dto.getQuantityAvailable() >0) {
            return "The resource " + dto.getName() + "is available. There are currently "+(dto.getQuantityAvailable()+" items in stock");
        }
        return "The resource " + dto.getName() + " is not available. The last item was lent on "+ dto.getLoanDate();
    }

    private boolean isAvailable(ResourceDto dto) {
        return dto.getQuantityAvailable() > dto.getQuantityBorrowed();
    }

    public String lend(String id) {
        ResourceDto dto = getById(id);
        if (dto.getQuantityAvailable() >0) {
            dto.setLoanDate(LocalDate.now());
            dto.setQuantityBorrowed(dto.getQuantityBorrowed() + 1);
            dto.setQuantityAvailable(dto.getQuantityAvailable() - 1);
            update(dto);
            return "The resource " + dto.getName() + " was lent succesfully";
        }
        return "The resource " + dto.getName() + " is not available. The last item was lent on "+ dto.getLoanDate();
    }

    public String returnResource(String id) {
        ResourceDto dto = getById(id);
        if (dto.getQuantityBorrowed() > 0) {
            dto.setQuantityBorrowed(dto.getQuantityBorrowed() - 1);
            dto.setQuantityAvailable(dto.getQuantityAvailable() + 1);
            update(dto);
            return "The resource " + dto.getName() + " was returned succesfully";
        }
        return "The resource " + dto.getName() + " was not returned, because there are not items in lend";
    }

    public List<ResourceDto> recommendByType(String type) {
        List<ResourceDto> resourceDtos = new ArrayList<>();
        repository.findByType(type).forEach(resource -> resourceDtos.add(Utils.resourceToDto(resource)));
        return resourceDtos;
    }

    public List<ResourceDto> recommendByTheme(String theme) {
        List<ResourceDto> resourceDtos = new ArrayList<>();
        repository.findByThematic(theme).forEach(resource -> resourceDtos.add(Utils.resourceToDto(resource)));
        return resourceDtos;
    }

    public List<ResourceDto> recommendByTypeAndTheme(String type, String theme) {
        List<ResourceDto> resourceDtos = new ArrayList<>();
        List<ResourceDto> resourceAux = new ArrayList<>();
        resourceAux.addAll(recommendByTheme(theme));
        resourceAux.addAll(recommendByType(type));
        resourceAux.stream().distinct().forEach(resourceDto -> resourceDtos.add(resourceDto));
        return resourceDtos;
    }
}
