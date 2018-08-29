package com.qlee.code_challenge.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.qlee.code_challenge.model.APIError;
import com.qlee.code_challenge.model.Location;
import com.qlee.code_challenge.repository.LocationRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/merchant/location")
public class LocationController {

    Logger log = LoggerFactory.getLogger(LocationController.class);

    private static final String DEFAULT_SEARCH_RADIUS = "50000";

    private static final String DEFAULT_LIMIT = "10";

    private final LocationRepository repository;

    @Autowired
    public LocationController(LocationRepository repository) {
        this.repository = repository;
    }

    /**
     * This method identifies valid locations that match ALL provided search criteria. Simpler queries work best for
     * experimentation and early development, whereas multi-parameter queries permit strict optimization of client
     * data usage.
     *
     * @param searchText
     * @param longitude
     * @param latitude
     * @param searchRadius
     * @param gidList
     * @param maximumResults
     * @param mobileClientAccess
     * @param omitMerchantInfo
     * @param omitContactInfo
     * @param omitConsumerFeatures
     * @return all the locations matching given parameters
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Location>> getAllLocations(
            @RequestParam(value = "searchText", required = false) String searchText,
            @RequestParam(value = "longitude", required = false) Double longitude,
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "searchRadius", defaultValue = DEFAULT_SEARCH_RADIUS) Integer searchRadius,
            @RequestParam(value = "gid", required = false) List<String> gidList,
            @RequestParam(value = "maximumResults", defaultValue = DEFAULT_LIMIT) Integer maximumResults,
            @RequestParam(value = "mobileClientAccess", required = false) Boolean mobileClientAccess,
            @RequestParam(value = "omitMerchantInfo", defaultValue = "false") boolean omitMerchantInfo,
            @RequestParam(value = "omitContactInfo", defaultValue = "false") boolean omitContactInfo,
            @RequestParam(value = "omitConsumerFeatures", defaultValue = "false") boolean omitConsumerFeatures) {

        log.info(
                "Receiveing get all locations request searchText={} longitude={} latitude={} searchRadius={} gid={} " +
                        "mobileClientAccess={} omitMerchantInfo={} omitContactInfo={} omitConsumerFeatures={}",
                searchText, longitude, latitude, searchRadius, gidList, mobileClientAccess, omitMerchantInfo,
                omitContactInfo, omitConsumerFeatures);
        List<Location> locations = repository.findAllByFilters(searchText, longitude, latitude, searchRadius, gidList,
                maximumResults, mobileClientAccess, omitMerchantInfo, omitContactInfo, omitConsumerFeatures);

        if (locations.isEmpty()) {
            log.info("No records found");
            return new ResponseEntity(new APIError("No records found."), HttpStatus.NOT_FOUND);
        }
        log.info("Returning location list with size={}", locations.size());
        return ResponseEntity.ok(locations);
    }

    /**
     * This method returns a single merchant location that matches a unique global identifier. The global identifier
     * is returned in a NetworkSource entity with a ‘location’ type, usually in response to a location search query.
     *
     * @param locationGlobalId
     * @param mobileClientAccess
     * @param omitMerchantInfo
     * @param omitContactInfo
     * @param omitConsumerFeatures
     * @return the location with the given location global id
     */
    @RequestMapping(value = "/{location_gid}", method = RequestMethod.GET)
    public ResponseEntity<Location> getLocation(@PathVariable("location_gid") String locationGlobalId,
                                                @RequestParam(value = "mobileClientAccess", required = false) Boolean
                                                        mobileClientAccess,
                                                @RequestParam(value = "omitMerchantInfo", defaultValue = "false")
                                                boolean omitMerchantInfo,
                                                @RequestParam(value = "omitContactInfo", defaultValue = "false")
                                                boolean omitContactInfo,
                                                @RequestParam(value = "omitConsumerFeatures", defaultValue = "false")
                                                boolean omitConsumerFeatures) {
        log.info(
                "Receiveing get locations request for location_gid={} mobileClientAccess={} omitMerchantInfo={} " +
                        "omitContactInfo={} omitConsumerFeatures={}",
                locationGlobalId, mobileClientAccess, omitMerchantInfo, omitContactInfo, omitConsumerFeatures);

        Location location = repository.findBySourceGlobalId(locationGlobalId, mobileClientAccess, omitMerchantInfo,
                omitContactInfo, omitConsumerFeatures);

        if (location == null) {
            log.info("No records found");
            return new ResponseEntity(new APIError("No records found."), HttpStatus.NOT_FOUND);
        }
        log.info("Returning location location_gid={}", locationGlobalId);
        return ResponseEntity.ok(location);
    }

}
