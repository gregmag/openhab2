/**
 * Copyright (c) 2014-2015 openHAB UG (haftungsbeschraenkt) and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.homematic.internal.communicator.parser;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.openhab.binding.homematic.internal.model.HmDevice;
import org.openhab.binding.homematic.internal.model.TclScriptDataEntry;
import org.openhab.binding.homematic.internal.model.TclScriptDataList;

/**
 * Parses a TclRega script result containing names for devices.
 *
 * @author Gerhard Riegler - Initial contribution
 */
public class CcuLoadDeviceNamesParser extends CommonRpcParser<TclScriptDataList, Void> {
    private Collection<HmDevice> devices;

    public CcuLoadDeviceNamesParser(Collection<HmDevice> devices) {
        this.devices = devices;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Void parse(TclScriptDataList resultList) throws IOException {
        if (resultList.getEntries() != null) {
            Map<String, HmDevice> devicesByAddress = new HashMap<String, HmDevice>();
            for (HmDevice device : devices) {
                devicesByAddress.put(device.getAddress(), device);
            }

            for (TclScriptDataEntry entry : resultList.getEntries()) {
                HmDevice device = devicesByAddress.get(getAddress(entry.name));
                if (device != null) {
                    device.setName(entry.value);
                }
            }
        }
        return null;
    }
}
