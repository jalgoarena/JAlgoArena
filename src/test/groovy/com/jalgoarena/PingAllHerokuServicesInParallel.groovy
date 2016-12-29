package com.jalgoarena

import groovyx.net.http.RESTClient
import org.slf4j.LoggerFactory

class PingAllHerokuServicesInParallel {

    static log = LoggerFactory.getLogger(PingAllHerokuServicesInParallel.class)

    static void main(String[] args) {

        def services = [
            "jalgoarena-eureka",
            "jalgoarena",
            "jalgoarena-api",
            "jalgoarena-auth",
            "jalgoarena-problems",
            "jalgoarena-submissions",
        ]

        services.each {
            healthCheck("https://${it}.herokuapp.com/")
        }
    }

    static healthCheck(String url) {

        log.info("$url - checking...")

        def status = new RESTClient(url)
                .get(path: "health")
                .data.status.toString()

        log.info("$url - $status")
    }
}
