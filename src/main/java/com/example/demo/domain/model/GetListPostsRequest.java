package com.example.demo.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class GetListPostsRequest {

    private Long id;

    private Long last_id;

    private Integer index;

    private Integer count;

    private Integer in_campaign;

    private Long campaign_id;

    private String latitude;

    private String longitude;

    public Integer getIn_campaign() {
        return in_campaign;
    }

    public void setIn_campaign(Integer in_campaign) {
        this.in_campaign = in_campaign;
    }

    public Long getCampaign_id() {
        return campaign_id;
    }

    public void setCampaign_id(Long campaign_id) {
        this.campaign_id = campaign_id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLast_id() {
        return last_id;
    }

    public void setLast_id(Long last_id) {
        this.last_id = last_id;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
