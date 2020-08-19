package com.shyam.booking.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.shyam.booking.domain.enumeration.FeedbackStatus;

/**
 * A FeedBack.
 */
@Entity
@Table(name = "feed_back")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FeedBack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "comments")
    private String comments;

    @Enumerated(EnumType.STRING)
    @Column(name = "feedback_status")
    private FeedbackStatus feedbackStatus;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public FeedBack comments(String comments) {
        this.comments = comments;
        return this;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public FeedbackStatus getFeedbackStatus() {
        return feedbackStatus;
    }

    public FeedBack feedbackStatus(FeedbackStatus feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
        return this;
    }

    public void setFeedbackStatus(FeedbackStatus feedbackStatus) {
        this.feedbackStatus = feedbackStatus;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeedBack)) {
            return false;
        }
        return id != null && id.equals(((FeedBack) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeedBack{" +
            "id=" + getId() +
            ", comments='" + getComments() + "'" +
            ", feedbackStatus='" + getFeedbackStatus() + "'" +
            "}";
    }
}
