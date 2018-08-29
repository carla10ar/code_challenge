package com.qlee.code_challenge.model;

public class NetworkSource {

    private String type;

    private String globalId;

    private String hostId;

    private String indirectId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGlobalId() {
        return globalId;
    }

    public void setGlobalId(String globalId) {
        this.globalId = globalId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getIndirectId() {
        return indirectId;
    }

    public void setIndirectId(String indirectId) {
        this.indirectId = indirectId;
    }
}
