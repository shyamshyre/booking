package com.shyam.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.shyam.booking.domain.enumeration.GenderType;

import com.shyam.booking.domain.enumeration.MStatus;

/**
 * A CustomerInfo.
 */
@Entity
@Table(name = "customer_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CustomerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "photo")
    private String photo;

    @Column(name = "age")
    private Integer age;

    @Column(name = "phno")
    private Long phno;

    @Column(name = "email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MStatus status;

    @Column(name = "addressproof")
    private String addressproof;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "customerInfos", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public CustomerInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public CustomerInfo photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getAge() {
        return age;
    }

    public CustomerInfo age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getPhno() {
        return phno;
    }

    public CustomerInfo phno(Long phno) {
        this.phno = phno;
        return this;
    }

    public void setPhno(Long phno) {
        this.phno = phno;
    }

    public String getEmail() {
        return email;
    }

    public CustomerInfo email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GenderType getGender() {
        return gender;
    }

    public CustomerInfo gender(GenderType gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public MStatus getStatus() {
        return status;
    }

    public CustomerInfo status(MStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(MStatus status) {
        this.status = status;
    }

    public String getAddressproof() {
        return addressproof;
    }

    public CustomerInfo addressproof(String addressproof) {
        this.addressproof = addressproof;
        return this;
    }

    public void setAddressproof(String addressproof) {
        this.addressproof = addressproof;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public CustomerInfo createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public CustomerInfo updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public CustomerInfo createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public CustomerInfo updatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public User getUser() {
        return user;
    }

    public CustomerInfo user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CustomerInfo)) {
            return false;
        }
        return id != null && id.equals(((CustomerInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CustomerInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", age=" + getAge() +
            ", phno=" + getPhno() +
            ", email='" + getEmail() + "'" +
            ", gender='" + getGender() + "'" +
            ", status='" + getStatus() + "'" +
            ", addressproof='" + getAddressproof() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
