package com.HappyScrolls.config.dbReplica;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    public static final String DATASOURCE_SOURCE_KEY = "source";

    private ReplicaDataSourceNames replicaDataSourceNames;

    public void setReplicaDataSourceNames(List<String> names) {
        this.replicaDataSourceNames = new ReplicaDataSourceNames(names);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        final boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (isReadOnly) {
            String nextReplicaDataSourceName = replicaDataSourceNames.getNextName();
            System.out.print("Using Replica DB Name : ");
            System.out.println(nextReplicaDataSourceName);

            return nextReplicaDataSourceName;
        }
        System.out.println("Using Source DB");
        return DATASOURCE_SOURCE_KEY;
    }

    public static class ReplicaDataSourceNames {

        private final List<String> values;
        private int counter = 0;

        public ReplicaDataSourceNames(List<String> values) {
            this.values = values;
        }

        public String getNextName() {
            if (counter == values.size()) {
                counter = 0;
            }
            return values.get(counter++);
        }
    }
}
