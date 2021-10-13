package com.sofkau.library.utils;

import com.sofkau.library.dtos.ResourceDto;
import com.sofkau.library.models.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class Utils {

    public static ResourceDto resourceToDto(Resource resource){
        ResourceDto resourceDto = new ResourceDto();
        BeanUtils.copyProperties(resource,resourceDto);
        return resourceDto;
    }
    public static Resource convertToResource(ResourceDto resourceDTO){
        Resource resource= new Resource();
        BeanUtils.copyProperties(resourceDTO,resource);
        return resource;
    }
}
