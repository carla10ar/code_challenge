package com.qlee.code_challenge.controller;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.assertj.core.util.Lists;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.qlee.code_challenge.model.APIError;
import com.qlee.code_challenge.repository.LocationRepository;

public class LocationControllerTest {

    private static final String NOT_FOUND_MESSAGE = "No records found.";
    private static final String GLOBAL_ID = "globalId";

    @Mock
    private LocationRepository repository;
    @InjectMocks
    private LocationController locationController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        locationController = null;
    }

    @Test
    public void test_getAllLocations_empty() {
        when(repository.findAllByFilters(null, null, null, null, null, null, false, false, false, false)).thenReturn(Lists.emptyList());

        ResponseEntity responseEntity = locationController.getAllLocations(null, null, null, null, null, null, false, false, false, false);

        assertNotFoundResponseEntity(responseEntity);
    }

    @Test
    public void test_getLocation_empty() {
        when(repository.findBySourceGlobalId(GLOBAL_ID, false, false, false, false)).thenReturn(null);

        ResponseEntity responseEntity = locationController.getLocation(GLOBAL_ID, false, false, false, false);

        assertNotFoundResponseEntity(responseEntity);
    }

    private void assertNotFoundResponseEntity(ResponseEntity responseEntity) {
        assertThat(responseEntity).isNotNull();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(((APIError)responseEntity.getBody()).getMessage()).isEqualTo(NOT_FOUND_MESSAGE);
    }
}
