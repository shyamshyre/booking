package com.shyam.booking.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.shyam.booking.web.rest.TestUtil;

public class FileMetaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileMeta.class);
        FileMeta fileMeta1 = new FileMeta();
        fileMeta1.setId(1L);
        FileMeta fileMeta2 = new FileMeta();
        fileMeta2.setId(fileMeta1.getId());
        assertThat(fileMeta1).isEqualTo(fileMeta2);
        fileMeta2.setId(2L);
        assertThat(fileMeta1).isNotEqualTo(fileMeta2);
        fileMeta1.setId(null);
        assertThat(fileMeta1).isNotEqualTo(fileMeta2);
    }
}
