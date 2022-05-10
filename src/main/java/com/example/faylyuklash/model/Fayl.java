package com.example.faylyuklash.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Fayl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String arginalFaylNomi;
    @Column(nullable = false)
    private long hajmi;
    @Column(nullable = false)
    private String contentType;
    private  String newname;

}
