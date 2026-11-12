package org.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ar")
public class Ar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sutiid", nullable = false)
    private Integer sutiId;

    @Column(name = "ertek", nullable = false)
    private Integer ertek;

    @Column(name = "egyseg", length = 30)
    private String egyseg;

    public Ar() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getSutiId() { return sutiId; }
    public void setSutiId(Integer sutiId) { this.sutiId = sutiId; }
    public Integer getErtek() { return ertek; }
    public void setErtek(Integer ertek) { this.ertek = ertek; }
    public String getEgyseg() { return egyseg; }
    public void setEgyseg(String egyseg) { this.egyseg = egyseg; }
}
