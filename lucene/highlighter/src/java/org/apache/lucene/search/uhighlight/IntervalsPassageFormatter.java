/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.lucene.search.uhighlight;

/**
 * Creates a formatted snippet from the top passages.
 * <p>
 * The default implementation marks the query terms as bold, and places
 * ellipses between unconnected passages.
 */
public class IntervalsPassageFormatter extends PassageFormatter {
  /**
   * Creates a new IntervalsPassageFormatter.
   */
  public IntervalsPassageFormatter() {
  }

  @Override
  public String format(Passage passages[], String content) {
    StringBuilder sb = new StringBuilder();
    boolean first = true;
    sb.append("[");
    for (Passage passage : passages) {
      sb.append((first ? "{" : ",{") + "\"score\":" + passage.getScore() + ",\"passage\":[" + passage.getStartOffset() + "," + passage.getEndOffset() + "],\"intervals\":[");
      first = false;
      for (int i = 0; i < passage.getNumMatches(); i++) {
        int start = passage.getMatchStarts()[i];
        //append content before this start
        int end = passage.getMatchEnds()[i];
        assert end > start;
        sb.append(((i == 0) ? "" : ",") + "[" + start + "," + end + "]");
      }
      sb.append("]}");
    }
    sb.append("]");
    return sb.toString();
  }
}
