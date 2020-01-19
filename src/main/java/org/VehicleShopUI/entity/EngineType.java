package org.VehicleShopUI.entity;

public class EngineType {
    private Long engineTypeId;
    private String engineTypeTitle;

    public Long getEngineTypeId() {
        return engineTypeId;
    }

    public void setEngineTypeId(Long engineTypeId) {
        this.engineTypeId = engineTypeId;
    }

    public String getEngineTypeTitle() {
        return engineTypeTitle;
    }

    public void setEngineTypeTitle(String engineTypeTitle) {
        this.engineTypeTitle = engineTypeTitle;
    }

    @Override
    public String toString() {
        return "EngineType{" +
                "engineTypeId=" + engineTypeId +
                ", engineTypeTitle='" + engineTypeTitle + '\'' +
                '}';
    }
}
