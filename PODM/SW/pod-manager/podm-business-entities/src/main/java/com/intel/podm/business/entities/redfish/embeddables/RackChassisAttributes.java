/*
 * Copyright (c) 2016-2017 Intel Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.intel.podm.business.entities.redfish.embeddables;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.UUID;

@Embeddable
public class RackChassisAttributes {
    @Column(name = "rack_uuid")
    private UUID rackUuid;

    @Column(name = "geo_tag")
    private String geoTag;

    @Column(name = "rack_supports_disaggregated_power_cooling")
    private Boolean rackSupportsDisaggregatedPowerCooling;

    public UUID getUuid() {
        return rackUuid;
    }

    public void setUuid(UUID rackUuid) {
        this.rackUuid = rackUuid;
    }

    public String getGeoTag() {
        return geoTag;
    }

    public void setGeoTag(String geoTag) {
        this.geoTag = geoTag;
    }

    public Boolean getRackSupportsDisaggregatedPowerCooling() {
        return rackSupportsDisaggregatedPowerCooling;
    }

    public void setRackSupportsDisaggregatedPowerCooling(Boolean rackSupportsDisaggregatedPowerCooling) {
        this.rackSupportsDisaggregatedPowerCooling = rackSupportsDisaggregatedPowerCooling;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RackChassisAttributes that = (RackChassisAttributes) o;
        return new EqualsBuilder()
            .append(rackUuid, that.rackUuid)
            .append(geoTag, that.geoTag)
            .append(rackSupportsDisaggregatedPowerCooling, that.rackSupportsDisaggregatedPowerCooling)
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(rackUuid)
            .append(geoTag)
            .append(rackSupportsDisaggregatedPowerCooling)
            .toHashCode();
    }
}
