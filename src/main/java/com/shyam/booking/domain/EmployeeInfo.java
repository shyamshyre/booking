package com.shyam.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.shyam.booking.domain.enumeration.GenderType;

import com.shyam.booking.domain.enumeration.Status;

/**
 * A EmployeeInfo.
 */
@Entity
@Table(name = "employee_info")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Integer age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private GenderType gender;

    @Column(name = "addressproof")
    private String addressproof;

    @Column(name = "photo")
    private String photo;

    @Column(name = "doj")
    private Instant doj;

    @Column(name = "education")
    private String education;

    @Column(name = "referredby")
    private String referredby;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "employeeInfos", allowSetters = true)
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

    public EmployeeInfo name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public EmployeeInfo age(Integer age) {
        this.age = age;
        return this;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public GenderType getGender() {
        return gender;
    }

    public EmployeeInfo gender(GenderType gender) {
        this.gender = gender;
        return this;
    }

    public void setGender(GenderType gender) {
        this.gender = gender;
    }

    public String getAddressproof() {
        return addressproof;
    }

    public EmployeeInfo addressproof(String addressproof) {
        this.addressproof = addressproof;
        return this;
    }

    public void setAddressproof(String addressproof) {
        this.addressproof = addressproof;
    }

    public String getPhoto() {
        return photo;
    }

    public EmployeeInfo photo(String photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Instant getDoj() {
        return doj;
    }

    public EmployeeInfo doj(Instant doj) {
        this.doj = doj;
        return this;
    }

    public void setDoj(Instant doj) {
        this.doj = doj;
    }

    public String getEducation() {
        return education;
    }

    public EmployeeInfo education(String education) {
        this.education = education;
        return this;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getReferredby() {
        return referredby;
    }

    public EmployeeInfo referredby(String referredby) {
        this.referredby = referredby;
        return this;
    }

    public void setReferredby(String referredby) {
        this.referredby = referredby;
    }

    public Status getStatus() {
        return status;
    }

    public EmployeeInfo status(Status status) {
        this.status = status;
        return this;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public EmployeeInfo createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public EmployeeInfo updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public EmployeeInfo createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public EmployeeInfo updatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public User getUser() {
        return user;
    }

    public EmployeeInfo user(User user) {
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
        if (!(o instanceof EmployeeInfo)) {
            return false;
        }
        return id != null && id.equals(((EmployeeInfo) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeInfo{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", age=" + getAge() +
            ", gender='" + getGender() + "'" +
            ", addressproof='" + getAddressproof() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", doj='" + getDoj() + "'" +
            ", education='" + getEducation() + "'" +
            ", referredby='" + getReferredby() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
