//package com.HappyScrolls.config.dbReplica;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.stereotype.Component;
//
//import javax.sql.DataSource;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Component
//@ConfigurationProperties(prefix = "spring.datasource.hikari.replica")
//public class Replicas {
//
//    List<Replica> replicaList = new ArrayList<>();
//
//    public <D extends DataSource> Map<String, DataSource> replicaDataSources(Class<D> type) {
//        return this.replicaList.stream()
//                .collect(Collectors.toMap(Replica::getName, replica -> replica.createDataSource(type)));
//    }
//
//    public List<Replica> getReplicaList() {
//        return replicaList;
//    }
//
//    @Getter
//    @Setter
//    public static class Replica {
//        private String name;
//        private String username;
//        private String password;
//        private String jdbcUrl;
//
//        public <D extends DataSource> DataSource createDataSource(Class<D> type) {
//            return DataSourceBuilder.create()
//                    .type(type)
//                    .url(this.getJdbcUrl())
//                    .username(this.getUsername())
//                    .password(this.getPassword())
//                    .build();
//        }
//
//    }
//}
