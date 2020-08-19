package com.shyam.booking.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

import com.shyam.booking.domain.enumeration.BookingType;

import com.shyam.booking.domain.enumeration.RoomType;

/**
 * A Booking.
 */
@Entity
@Table(name = "booking")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Booking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "booking_type")
    private BookingType bookingType;

    @Column(name = "noof_people")
    private Integer noofPeople;

    @Column(name = "coming_from")
    private String comingFrom;

    @Column(name = "visit_purpose")
    private String visitPurpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_category")
    private RoomType roomCategory;

    @Column(name = "total_amount")
    private Integer totalAmount;

    @Column(name = "advance")
    private Integer advance;

    @Column(name = "balance_amount")
    private Integer balanceAmount;

    @Column(name = "gst")
    private Float gst;

    @Column(name = "booking_from")
    private Instant bookingFrom;

    @Column(name = "booking_to")
    private Instant bookingTo;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_date")
    private Instant updatedDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    @ManyToOne
    @JsonIgnoreProperties(value = "bookings", allowSetters = true)
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = "bookings", allowSetters = true)
    private CustomerInfo customer;

    @ManyToOne
    @JsonIgnoreProperties(value = "bookings", allowSetters = true)
    private EmployeeInfo employee;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BookingType getBookingType() {
        return bookingType;
    }

    public Booking bookingType(BookingType bookingType) {
        this.bookingType = bookingType;
        return this;
    }

    public void setBookingType(BookingType bookingType) {
        this.bookingType = bookingType;
    }

    public Integer getNoofPeople() {
        return noofPeople;
    }

    public Booking noofPeople(Integer noofPeople) {
        this.noofPeople = noofPeople;
        return this;
    }

    public void setNoofPeople(Integer noofPeople) {
        this.noofPeople = noofPeople;
    }

    public String getComingFrom() {
        return comingFrom;
    }

    public Booking comingFrom(String comingFrom) {
        this.comingFrom = comingFrom;
        return this;
    }

    public void setComingFrom(String comingFrom) {
        this.comingFrom = comingFrom;
    }

    public String getVisitPurpose() {
        return visitPurpose;
    }

    public Booking visitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
        return this;
    }

    public void setVisitPurpose(String visitPurpose) {
        this.visitPurpose = visitPurpose;
    }

    public RoomType getRoomCategory() {
        return roomCategory;
    }

    public Booking roomCategory(RoomType roomCategory) {
        this.roomCategory = roomCategory;
        return this;
    }

    public void setRoomCategory(RoomType roomCategory) {
        this.roomCategory = roomCategory;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public Booking totalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getAdvance() {
        return advance;
    }

    public Booking advance(Integer advance) {
        this.advance = advance;
        return this;
    }

    public void setAdvance(Integer advance) {
        this.advance = advance;
    }

    public Integer getBalanceAmount() {
        return balanceAmount;
    }

    public Booking balanceAmount(Integer balanceAmount) {
        this.balanceAmount = balanceAmount;
        return this;
    }

    public void setBalanceAmount(Integer balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Float getGst() {
        return gst;
    }

    public Booking gst(Float gst) {
        this.gst = gst;
        return this;
    }

    public void setGst(Float gst) {
        this.gst = gst;
    }

    public Instant getBookingFrom() {
        return bookingFrom;
    }

    public Booking bookingFrom(Instant bookingFrom) {
        this.bookingFrom = bookingFrom;
        return this;
    }

    public void setBookingFrom(Instant bookingFrom) {
        this.bookingFrom = bookingFrom;
    }

    public Instant getBookingTo() {
        return bookingTo;
    }

    public Booking bookingTo(Instant bookingTo) {
        this.bookingTo = bookingTo;
        return this;
    }

    public void setBookingTo(Instant bookingTo) {
        this.bookingTo = bookingTo;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public Booking createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedDate() {
        return updatedDate;
    }

    public Booking updatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(Instant updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public Booking createdBy(Long createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public Booking updatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public User getUser() {
        return user;
    }

    public Booking user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CustomerInfo getCustomer() {
        return customer;
    }

    public Booking customer(CustomerInfo customerInfo) {
        this.customer = customerInfo;
        return this;
    }

    public void setCustomer(CustomerInfo customerInfo) {
        this.customer = customerInfo;
    }

    public EmployeeInfo getEmployee() {
        return employee;
    }

    public Booking employee(EmployeeInfo employeeInfo) {
        this.employee = employeeInfo;
        return this;
    }

    public void setEmployee(EmployeeInfo employeeInfo) {
        this.employee = employeeInfo;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Booking)) {
            return false;
        }
        return id != null && id.equals(((Booking) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Booking{" +
            "id=" + getId() +
            ", bookingType='" + getBookingType() + "'" +
            ", noofPeople=" + getNoofPeople() +
            ", comingFrom='" + getComingFrom() + "'" +
            ", visitPurpose='" + getVisitPurpose() + "'" +
            ", roomCategory='" + getRoomCategory() + "'" +
            ", totalAmount=" + getTotalAmount() +
            ", advance=" + getAdvance() +
            ", balanceAmount=" + getBalanceAmount() +
            ", gst=" + getGst() +
            ", bookingFrom='" + getBookingFrom() + "'" +
            ", bookingTo='" + getBookingTo() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", createdBy=" + getCreatedBy() +
            ", updatedBy=" + getUpdatedBy() +
            "}";
    }
}
