/*
 * Copyright (C) 2014 The Calrissian Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.calrissian.flowmix.aggregator;

import org.apache.commons.lang.StringUtils;
import org.calrissian.flowmix.support.AggregatedEvent;
import org.calrissian.flowmix.support.Aggregator;
import org.calrissian.flowmix.support.WindowItem;
import org.calrissian.mango.domain.BaseEvent;
import org.calrissian.mango.domain.Event;
import org.calrissian.mango.domain.Tuple;

import java.util.*;

import static java.lang.System.currentTimeMillis;
import static java.util.UUID.randomUUID;

public class CountAggregator implements Aggregator {

  public static final String OUTPUT_FIELD = "outputField";

  public static final String DEFAULT_OUTPUT_FIELD = "count";

  private String outputField = DEFAULT_OUTPUT_FIELD;
  private Map<String,Collection<Tuple>> groupedValues;

  private String[] groupByFields;
  private long count = 0;

  @Override
  public void configure(Map<String, String> configuration) {

    if(configuration.get(GROUP_BY) != null)
      groupByFields = StringUtils.splitPreserveAllTokens(configuration.get(GROUP_BY), GROUP_BY_DELIM);

    if(configuration.get(OUTPUT_FIELD) != null)
      outputField = configuration.get(OUTPUT_FIELD);
  }

  @Override
  public void added(WindowItem item) {

    if(groupedValues == null && groupByFields != null) {
      groupedValues = new HashMap<String, Collection<Tuple>>();
      for(String group : groupByFields)
        groupedValues.put(group, item.getEvent().getAll(group));
    }

    count++;
  }

  @Override
  public void evicted(WindowItem item) {
    count--;
  }

  @Override
  public List<AggregatedEvent> aggregate() {
    Event event = new BaseEvent(randomUUID().toString(), currentTimeMillis());
    if(groupedValues != null && groupByFields != null) {
      for(Collection<Tuple> tuples : groupedValues.values())
        event.putAll(tuples);
    }

    event.put(new Tuple(outputField, count));
    return Collections.singletonList(new AggregatedEvent(event));
  }
}
