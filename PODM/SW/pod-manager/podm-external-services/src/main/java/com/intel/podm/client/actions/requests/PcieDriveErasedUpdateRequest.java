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

package com.intel.podm.client.actions.requests;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PcieDriveErasedUpdateRequest {
    @JsonProperty("Oem")
    private final Oem oem = new Oem();

    public PcieDriveErasedUpdateRequest(boolean driveErased) {
        this.oem.rackScaleOem.driveErased = driveErased;
    }

    private static final class Oem {
        @JsonProperty("Intel_RackScale")
        private final RackScaleOem rackScaleOem = new RackScaleOem();

        private class RackScaleOem {
            @JsonProperty("DriveErased")
            private boolean driveErased;
        }
    }
}
