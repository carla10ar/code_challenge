package com.qlee.code_challenge.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "locations")
public class Location {

    private NetworkSource source;

    private String name;

    private String description;

    private Merchant merchantInfo;

    private LocationContactInfo contactInfo;

    private ConsumerFeatures consumerFeatures;

    public NetworkSource getSource() {
        return source;
    }

    public void setSource(NetworkSource source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Merchant getMerchantInfo() {
        return merchantInfo;
    }

    public void setMerchantInfo(Merchant merchantInfo) {
        this.merchantInfo = merchantInfo;
    }

    public LocationContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(LocationContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ConsumerFeatures getConsumerFeatures() {
        return consumerFeatures;
    }

    public void setConsumerFeatures(ConsumerFeatures consumerFeatures) {
        this.consumerFeatures = consumerFeatures;
    }
}
