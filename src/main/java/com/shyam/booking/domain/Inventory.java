package com.shyam.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Inventory.
 */
@Entity
@Table(name = "inventory")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_date")
    private Integer purchaseDate;

    @Column(name = "itemdescription")
    private String itemdescription;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "available")
    private Integer available;

    @Column(name = "itemcat")
    private String itemcat;

    @Column(name = "purchase_amount")
    private Integer purchaseAmount;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "inventories", allowSetters = true)
    private User user;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPurchaseDate() {
        return purchaseDate;
    }

    public Inventory purchaseDate(Integer purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public void setPurchaseDate(Integer purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getItemdescription() {
        return itemdescription;
    }

    public Inventory itemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
        return this;
    }

    public void setItemdescription(String itemdescription) {
        this.itemdescription = itemdescription;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Inventory quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getAvailable() {
        return available;
    }

    public Inventory available(Integer available) {
        this.available = available;
        return this;
    }

    public void setAvailable(Integer available) {
        this.available = available;
    }

    public String getItemcat() {
        return itemcat;
    }

    public Inventory itemcat(String itemcat) {
        this.itemcat = itemcat;
        return this;
    }

    public void setItemcat(String itemcat) {
        this.itemcat = itemcat;
    }

    public Integer getPurchaseAmount() {
        return purchaseAmount;
    }

    public Inventory purchaseAmount(Integer purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
        return this;
    }

    public void setPurchaseAmount(Integer purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public String getPurpose() {
        return purpose;
    }

    public Inventory purpose(String purpose) {
        this.purpose = purpose;
        return this;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Inventory createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Inventory updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Inventory createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public Inventory updatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public User getUser() {
        return user;
    }

    public Inventory user(User user) {
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
        if (!(o instanceof Inventory)) {
            return false;
        }
        return id != null && id.equals(((Inventory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Inventory{" +
            "id=" + getId() +
            ", purchaseDate=" + getPurchaseDate() +
            ", itemdescription='" + getItemdescription() + "'" +
            ", quantity=" + getQuantity() +
            ", available=" + getAvailable() +
            ", itemcat='" + getItemcat() + "'" +
            ", purchaseAmount=" + getPurchaseAmount() +
            ", purpose='" + getPurpose() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
