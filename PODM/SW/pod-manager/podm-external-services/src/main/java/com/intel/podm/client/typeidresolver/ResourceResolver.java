/*
 * Copyright (c) 2015-2017 Intel Corporation
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

package com.intel.podm.client.typeidresolver;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.None;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.impl.TypeIdResolverBase;
import com.intel.podm.client.OdataTypes;
import com.intel.podm.common.logger.Logger;
import org.apache.commons.lang.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.CUSTOM;
import static com.fasterxml.jackson.databind.type.TypeFactory.defaultInstance;
import static com.intel.podm.common.logger.LoggerFactory.getLogger;
import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.Collections.synchronizedList;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public final class ResourceResolver extends TypeIdResolverBase {

    private static final Logger LOGGER = getLogger(ResourceResolver.class);
    private static final List<OdataTypeMatcher> MATCHERS = synchronizedList(new ArrayList<>());
    private JavaType baseType;

    private static void registerResource(Class clazz, String odataType) {
        register(OdataTypeMatcher.odataTypePatternMatcher(odataType, clazz));
    }

    public static void register(OdataTypeMatcher matcher) {
        MATCHERS.add(matcher);
    }

    static void registerResource(Class clazz) {
        List<String> types = extractOdataTypes(clazz);
        for (String type : types) {
            registerResource(clazz, type);
        }
    }

    private static List<String> extractOdataTypes(Class clazz) {
        OdataTypes odataTypes = (OdataTypes) clazz.getAnnotation(OdataTypes.class);
        if (odataTypes == null) {
            throw new IllegalArgumentException(format("Provided class: %s should use %s annotation.", clazz, OdataTypes.class));
        }

        return stream(odataTypes.value())
                .collect(toList());
    }

    @Override
    public void init(JavaType baseType) {
        this.baseType = baseType;
    }

    @Override
    public String idFromValue(Object o) {
        return idFromValueAndType(o, o.getClass());
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return null;
    }

    @Override
    public String idFromBaseType() {
        return idFromValueAndType(null, baseType.getRawClass());
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) {
        for (OdataTypeMatcher matcher : MATCHERS) {
            Optional<Class> matchedClass = matcher.match(id);

            if (matchedClass.isPresent()) {
                return defaultInstance().constructSpecializedType(baseType, matchedClass.get());
            }
        }

        LOGGER.e("Encountered an unknown @odata.type: {}", id);

        if (defaultTypeRequired()) {
            LOGGER.i("Creating default instance for @odata.type: {}", id);
            return createDefaultType(id);
        }

        throw new NotImplementedException(id);
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return CUSTOM;
    }

    private boolean defaultTypeRequired() {
        JsonTypeInfo typeInfo = baseType.getRawClass().getAnnotation(JsonTypeInfo.class);

        // defaultImpl by default may be set to None.class or Void.class
        return nonNull(typeInfo)
                && nonNull(typeInfo.defaultImpl())
                && !None.class.isAssignableFrom(typeInfo.defaultImpl())
                && !Void.class.isAssignableFrom(typeInfo.defaultImpl());
    }

    private JavaType createDefaultType(String id) {
        JsonTypeInfo typeInfo = baseType.getRawClass().getAnnotation(JsonTypeInfo.class);
        LOGGER.w("Creating '{}' as default type for @odata.type: {}", typeInfo.defaultImpl().getCanonicalName(), id);
        return defaultInstance().constructFromCanonical(typeInfo.defaultImpl().getName());
    }
}
