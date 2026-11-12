package org.example.demo.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "suti")
public class Suti {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nev", length = 100, nullable = false)
    private String nev;

    @Column(name = "tipus", length = 50, nullable = false)
    private String tipus;

    // Díjazott volt-e a Magyarország Tortája versenyen
    @Column(name = "dijazott", nullable = false)
    private boolean dijazott;

    public Suti() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNev() { return nev; }
    public void setNev(String nev) { this.nev = nev; }
    public String getTipus() { return tipus; }
    public void setTipus(String tipus) { this.tipus = tipus; }
    public boolean isDijazott() { return dijazott; }
    public void setDijazott(boolean dijazott) { this.dijazott = dijazott; }
}
