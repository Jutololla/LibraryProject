package com.sofkau.library.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResourceDto {
    private String id;
    private String name;
    private LocalDate loanDate;
    private int quantityAvailable;
    private int quantityBorrowed;
    private String type;
    private String thematic;

    public boolean isEmpty(){
        return name.isEmpty() || type.isEmpty() || thematic.isEmpty();
    }
}

