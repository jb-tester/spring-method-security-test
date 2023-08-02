package com.mytests.spring.security.methodsecuritytest.data;

import jakarta.persistence.*;
import java.util.Objects;

/**
 * *
 * <p>Created by irina on 12/16/2021.</p>
 * <p>Project: spring-method-security-test</p>
 * *
 */
@Entity
@Table(name = "sample", schema = "jbtests")
public class SampleEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "version")
    private Integer version;
    @Basic
    @Column(name = "sample")
    private String sample;
    @Basic
    @Column(name = "color")
    private String color;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SampleEntity that = (SampleEntity) o;
        return id == that.id && Objects.equals(version, that.version) && Objects.equals(sample, that.sample) && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, version, sample, color);
    }

    @Override
    public String toString() {
        return "SampleEntity{" +
                "id=" + id +
                ", version=" + version +
                ", sample='" + sample + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}
