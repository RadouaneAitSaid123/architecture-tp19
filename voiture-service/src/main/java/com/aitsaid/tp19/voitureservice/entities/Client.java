package com.aitsaid.tp19.voitureservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author radouane
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    private Long id;
    private String nom;
    private Float age;
}
