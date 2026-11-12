package org.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tartalom")
public class Tartalom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "sutiid", nullable = false)
    private Integer sutiId;

    // mely összetevőtől mentes a sütemény (pl. G = gluténmentes)
    @Column(name = "mentes", length = 10, nullable = false)
    private String mentes;

    public Tartalom() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getSutiId() { return sutiId; }
    public void setSutiId(Integer sutiId) { this.sutiId = sutiId; }
    public String getMentes() { return mentes; }
    public void setMentes(String mentes) { this.mentes = mentes; }
}
