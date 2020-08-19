package com.shyam.booking.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.shyam.booking.web.rest.TestUtil;

public class CustomerInfoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CustomerInfo.class);
        CustomerInfo customerInfo1 = new CustomerInfo();
        customerInfo1.setId(1L);
        CustomerInfo customerInfo2 = new CustomerInfo();
        customerInfo2.setId(customerInfo1.getId());
        assertThat(customerInfo1).isEqualTo(customerInfo2);
        customerInfo2.setId(2L);
        assertThat(customerInfo1).isNotEqualTo(customerInfo2);
        customerInfo1.setId(null);
        assertThat(customerInfo1).isNotEqualTo(customerInfo2);
    }
}
