package com.qlee.code_challenge.repository;

import static com.qlee.code_challenge.TestConstants.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qlee.code_challenge.TestConfig;
import com.qlee.code_challenge.model.Location;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class LocationRepositoryIT {
    @Autowired
    private LocationRepository repository;

    @Test
    public void testFindAllByFilters_emtpyFilters() throws Exception {
        List<Location> result = repository.findAllByFilters(null, null, null, null, null, DEFAULT_LIMIT, null, false, false,
                false);

        assertThat(result).isNotNull()
                .allMatch(e -> e.getConsumerFeatures() != null
                        && e.getContactInfo() != null
                        && e.getMerchantInfo() != null);
    }

    @Test
    public void testFindAllByFilters_withSearchText() throws Exception {
        List<Location> result = repository.findAllByFilters(SEARCH_TEXT, null, null, null, null, DEFAULT_LIMIT, null, false, false,
                false);

        assertThat(result).isNotNull().hasSize(1);
    }

    @Test
    public void testFindAllByFilters_withGidList() throws Exception {
        List<String> gidList = Lists.newArrayList(GLOBAL_ID);
        List<Location> result = repository.findAllByFilters(null, null, null, null, gidList, DEFAULT_LIMIT, null, false, false,
                false);

        assertThat(result).isNotNull().hasSize(1);
    }

    @Test
    public void testFindAllByFilters_withHasMobileClientAccess_true() throws Exception {
        List<Location> result = repository.findAllByFilters(null, null, null, null, null, DEFAULT_LIMIT, true, false, false,
                false);

        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getConsumerFeatures().isHasMobileAccess()).isTrue();
    }

    @Test
    public void testFindAllByFilters_withHasMobileClientAccess_false() throws Exception {
        List<Location> result = repository.findAllByFilters(null, null, null, null, null, DEFAULT_LIMIT, false, false, false,
                false);

        assertThat(result).isNotNull().hasSize(1);
        assertThat(result.get(0).getConsumerFeatures().isHasMobileAccess()).isFalse();
    }

    @Test
    public void testFindAllByFilters_ommitMerchantInfo() throws Exception {
        List<Location> result = repository.findAllByFilters(null, null, null, null, null, DEFAULT_LIMIT, null, true, false,
                false);

        assertThat(result).isNotNull()
                .allMatch(e -> e.getMerchantInfo() == null);
    }

    @Test
    public void testFindAllByFilters_ommitContactInfo() throws Exception {
        List<Location> result = repository.findAllByFilters(null, null, null, null, null, DEFAULT_LIMIT, null, false, true,
                false);

        assertThat(result).isNotNull()
                .allMatch(e -> e.getContactInfo() == null);
    }

    @Test
    public void testFindAllByFilters_ommitConsumerFeatures() throws Exception {
        List<Location> result = repository.findAllByFilters(null, null, null, null, null, DEFAULT_LIMIT, null, false, false,
                true);

        assertThat(result).isNotNull()
                .allMatch(e -> e.getConsumerFeatures() == null);
    }

    public void testFindAllByFilters_ommitAllFields() throws Exception {
        List<Location> result = repository.findAllByFilters(null, null, null, null, null, DEFAULT_LIMIT, null, true, true,
                true);

        assertThat(result).isNotNull()
                .allMatch(e -> e.getConsumerFeatures() == null
                    && e.getContactInfo() == null
                    && e.getMerchantInfo() == null);
    }

    @Test
    public void testFindBySourceGlobalId() throws Exception {
        Location result = repository.findBySourceGlobalId(GLOBAL_ID, null, false, false, false);

        assertThat(result).isNotNull();
        assertThat(result.getSource().getGlobalId()).isEqualTo(GLOBAL_ID);
        assertThat(result.getConsumerFeatures()).isNotNull();
        assertThat(result.getContactInfo()).isNotNull();
        assertThat(result.getMerchantInfo()).isNotNull();
    }

    @Test
    public void testFindBySourceGlobalId_withMobileClientAccess_true() throws Exception {
        Location result = repository.findBySourceGlobalId(GLOBAL_ID, true, false, false, false);

        assertThat(result).isNotNull();
        assertThat(result.getSource().getGlobalId()).isEqualTo(GLOBAL_ID);
    }

    @Test
    public void testFindBySourceGlobalId_withMobileClientAccess_false() throws Exception {
        Location result = repository.findBySourceGlobalId(GLOBAL_ID, false, false, false, false);

        assertThat(result).isNull();
    }

    @Test
    public void testFindBySourceGlobalId_ommitMerchantInfo() throws Exception {
        Location result = repository.findBySourceGlobalId(GLOBAL_ID, null, true, false, false);

        assertThat(result).isNotNull();
        assertThat(result.getMerchantInfo()).isNull();
    }

    @Test
    public void testFindBySourceGlobalId_ommitContactInfo() throws Exception {
        Location result = repository.findBySourceGlobalId(GLOBAL_ID, null, false, true, false);

        assertThat(result).isNotNull();
        assertThat(result.getContactInfo()).isNull();
    }

    @Test
    public void testFindBySourceGlobalId_ommitConsumerFeatures() throws Exception {
        Location result = repository.findBySourceGlobalId(GLOBAL_ID, null, false, false, true);

        assertThat(result).isNotNull();
        assertThat(result.getConsumerFeatures()).isNull();
    }

    @Test
    public void testFindBySourceGlobalId_ommitAllFields() throws Exception {
        Location result = repository.findBySourceGlobalId(GLOBAL_ID, null, true, true, true);

        assertThat(result).isNotNull();
        assertThat(result.getConsumerFeatures()).isNull();
        assertThat(result.getContactInfo()).isNull();
        assertThat(result.getMerchantInfo()).isNull();
    }
}