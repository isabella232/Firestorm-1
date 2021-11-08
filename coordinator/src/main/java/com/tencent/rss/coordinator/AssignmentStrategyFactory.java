/*
 * Tencent is pleased to support the open source community by making
 * Firestorm-Spark remote shuffle server available. 
 *
 * Copyright (C) 2021 THL A29 Limited, a Tencent company.  All rights reserved. 
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use
 * this file except in compliance with the License. You may obtain a copy of the
 * License at
 *
 * https://opensource.org/licenses/Apache-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.tencent.rss.coordinator;

public class AssignmentStrategyFactory {

  private CoordinatorConf conf;
  private ClusterManager clusterManager;

  public AssignmentStrategyFactory(CoordinatorConf conf, ClusterManager clusterManager) {
    this.conf = conf;
    this.clusterManager = clusterManager;
  }

  public AssignmentStrategy getAssignmentStrategy() {
    String strategy = conf.getString(CoordinatorConf.COORDINATOR_ASSIGNMENT_STRATEGY);
    if (StrategyName.BASIC.name().equals(strategy)) {
      return new BasicAssignmentStrategy(clusterManager);
    } else if (StrategyName.PARTITION_BALANCE.name().equals(strategy)) {
      return new PartitionBalanceAssignmentStrategy(clusterManager);
    } else {
      throw new UnsupportedOperationException("Unsupported assignment strategy.");
    }
  }

  private enum StrategyName {
    BASIC,
    PARTITION_BALANCE
  }

}
