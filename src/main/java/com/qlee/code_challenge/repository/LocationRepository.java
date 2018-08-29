package com.qlee.code_challenge.repository;

import java.util.List;

import com.qlee.code_challenge.model.Location;

public interface LocationRepository {

    List<Location> findAllByFilters(String searchText, Double longitude, Double latitude, Integer searchRadius,
                                    List<String> gidList, Integer maximumResults, Boolean mobileClientAccess,
                                    boolean omitMerchantInfo, boolean omitContactInfo,
                                    boolean omitConsumerFeatures);

    Location findBySourceGlobalId(String globalId, Boolean mobileClientAccess,
                                  boolean omitMerchantInfo, boolean omitContactInfo,
                                  boolean omitConsumerFeatures);

}
