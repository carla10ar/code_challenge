package com.qlee.code_challenge.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.qlee.code_challenge.model.Location;

@Repository
public class LocationRepositoryImpl implements LocationRepository {

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public List<Location> findAllByFilters(String searchText, Double longitude, Double latitude,
                                           Integer searchRadius, List<String> gidList, Integer maximumResults,
                                           Boolean mobileClientAccess, boolean omitMerchantInfo,
                                           boolean omitContactInfo, boolean omitConsumerFeatures) {

        List<Criteria> criterias = new ArrayList<>();
        Query query = new Query();
        query.limit(maximumResults);

        if (!StringUtils.isEmpty(searchText)) {
            searchText=".*" + searchText + ".*";
            criterias.add(new Criteria("description").regex(searchText,"i"));
        }

        if (gidList != null && !gidList.isEmpty()) {
            criterias.add(new Criteria("source.globalId").in(gidList));
        }

        if (mobileClientAccess != null) {
            criterias.add(new Criteria("consumerFeatures.hasMobileAccess").is(mobileClientAccess));
        }

        criterias.stream()
                .forEach(query::addCriteria);

        excludeFields(query, omitMerchantInfo, omitContactInfo, omitConsumerFeatures);
        return mongoTemplate.find(query, Location.class);
    }

    @Override
    public Location findBySourceGlobalId(String globalId, Boolean mobileClientAccess,
                                         boolean omitMerchantInfo, boolean omitContactInfo,
                                         boolean omitConsumerFeatures) {
        Query query = new Query();
        query.addCriteria(new Criteria("source.globalId").is(globalId));

        if (mobileClientAccess != null) {
            query.addCriteria(new Criteria("consumerFeatures.hasMobileAccess").is(mobileClientAccess));
        }

        excludeFields(query, omitMerchantInfo, omitContactInfo, omitConsumerFeatures);
        return mongoTemplate.findOne(query, Location.class);
    }

    private void excludeFields(Query query, boolean omitMerchantInfo,
                               boolean omitContactInfo, boolean omitConsumerFeatures) {
        if (omitMerchantInfo) {
            query.fields().exclude("merchantInfo");
        }
        if (omitContactInfo) {
            query.fields().exclude("contactInfo");
        }
        if (omitConsumerFeatures) {
            query.fields().exclude("consumerFeatures");
        }
    }
}
