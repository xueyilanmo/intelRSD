/*
 * Copyright (c) 2017 Intel Corporation
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

package com.intel.podm.client.actions;

import com.intel.podm.client.actions.requests.NetworkDeviceFunctionRequest;
import com.intel.podm.client.api.ExternalServiceApiActionException;
import com.intel.podm.client.api.ExternalServiceApiReaderException;
import com.intel.podm.client.api.WebClient;
import com.intel.podm.client.api.actions.NetworkDeviceFunctionActions;
import com.intel.podm.client.api.resources.redfish.NetworkDeviceFunctionResource;
import com.intel.podm.common.types.actions.NetworkDeviceFunctionUpdateDefinition;

import java.net.URI;

public class NetworkDeviceFunctionActionsImpl implements NetworkDeviceFunctionActions {

    private WebClient webClient;

    public NetworkDeviceFunctionActionsImpl(WebClient webClient) {
        this.webClient = webClient;
    }

    @Override
    public NetworkDeviceFunctionResource update(URI networkDeviceFunctionUri, NetworkDeviceFunctionUpdateDefinition updateDefinition)
            throws ExternalServiceApiActionException, ExternalServiceApiReaderException {

            return (NetworkDeviceFunctionResource) webClient.patchAndRetrieve(networkDeviceFunctionUri, new NetworkDeviceFunctionRequest(updateDefinition));
    }

    @Override
    public void close() {
        webClient.close();
    }
}
