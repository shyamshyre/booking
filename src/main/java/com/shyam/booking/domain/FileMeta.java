package com.shyam.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A FileMeta.
 */
@Entity
@Table(name = "file_meta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FileMeta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "size")
    private Integer size;

    @Column(name = "path")
    private String path;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "fileMetas", allowSetters = true)
    private Booking booking;

    @ManyToOne
    @JsonIgnoreProperties(value = "fileMetas", allowSetters = true)
    private CustomerInfo customer;

    @ManyToOne
    @JsonIgnoreProperties(value = "fileMetas", allowSetters = true)
    private EmployeeInfo employee;

    @ManyToOne
    @JsonIgnoreProperties(value = "fileMetas", allowSetters = true)
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

    public FileMeta name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public FileMeta type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public FileMeta size(Integer size) {
        this.size = size;
        return this;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getPath() {
        return path;
    }

    public FileMeta path(String path) {
        this.path = path;
        return this;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public FileMeta createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public FileMeta updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public FileMeta createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public FileMeta updatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Booking getBooking() {
        return booking;
    }

    public FileMeta booking(Booking booking) {
        this.booking = booking;
        return this;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public FileMeta customer(CustomerInfo customerInfo) {
        this.customer = customerInfo;
        return this;
    }

    public void setCustomer(CustomerInfo customerInfo) {
        this.customer = customerInfo;
    }

    public EmployeeInfo getEmployee() {
        return employee;
    }

    public FileMeta employee(EmployeeInfo employeeInfo) {
        this.employee = employeeInfo;
        return this;
    }

    public void setEmployee(EmployeeInfo employeeInfo) {
        this.employee = employeeInfo;
    }

    public User getUser() {
        return user;
    }

    public FileMeta user(User user) {
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
        if (!(o instanceof FileMeta)) {
            return false;
        }
        return id != null && id.equals(((FileMeta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileMeta{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", size=" + getSize() +
            ", path='" + getPath() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
