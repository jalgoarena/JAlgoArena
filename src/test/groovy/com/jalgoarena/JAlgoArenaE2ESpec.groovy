package com.jalgoarena

import com.google.common.base.Charsets
import com.google.common.io.Resources
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.slf4j.LoggerFactory
import spock.lang.Specification

class JAlgoArenaE2ESpec extends Specification {

    static log = LoggerFactory.getLogger(JAlgoArenaJudgeSpecification.class)

    static jalgoJudgeApiClient = new RESTClient("https://jalgoarena-api.herokuapp.com/")

    def setupSpec() {

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

    def healthCheck(String url) {

        log.info("$url - checking...")
        def status = new RESTClient(url)
                .get(path: "health")
                .data.status.toString()

        log.info("$url - $status")
    }

    def "User submits successfully fib problem solution"() {
        given: "User creates account if empty and log in"
            def user = null
            handleHttpException {
                def users = jalgoJudgeApiClient.get(
                        path: "/auth/users",
                        contentType: ContentType.JSON
                ).data

                user = users.find { it.username == "mikołaj" }

                if (user == null) {
                    user = createUser()
                } else {
                    log.info("User already created: ${user}")
                }
            }
            def token = logInUser()

        and: "User judges solution for Fibonacci problem"
            def judgeResult = submitFibProblemInKotlin()

        and: "User submits solution"
            def submission = sentSubmission(judgeResult, user, token)
            log.info("Submission saved: ${submission}")

        expect:
            token != null
            submission != null
            judgeResult != null

        when: "We check user submissions"
            def userSubmissions
            handleHttpException {
                userSubmissions = jalgoJudgeApiClient.get(
                        path: "/submissions/api/submissions/${user.id}",
                        contentType: ContentType.JSON,
                        headers: ["X-Authorization": "Bearer ${token}"]
                ).data

                log.info("User Submissions: ${userSubmissions}")
            }

        then: "We can see saved submission on user profile"
            userSubmissions != null
            def fibSubmission = userSubmissions.find { it.problemId == "fib" }

            with (fibSubmission) {
                problemId == "fib"
                level == 1
                elapsedTime > 0.0
                sourceCode == "dummy"
                statusCode == "ACCEPTED"
                userId == user.id
                language == "kotlin"
            }
    }

    def sentSubmission(judgeResult, user, token) {
        log.info("Step 4 - Submit Solution")

        def submissionRequestJson = """{
    "problemId": "fib",
    "level": 1,
    "elapsedTime": ${judgeResult.elapsedTime},
    "sourceCode": "dummy",
    "statusCode": "${judgeResult.statusCode}",
    "userId": "${user.id}",
    "language": "kotlin"
}
"""

        handleHttpException {
            def submission = jalgoJudgeApiClient.put(
                    path: "/submissions/api/submissions",
                    body: submissionRequestJson,
                    requestContentType: ContentType.JSON,
                    contentType: ContentType.JSON,
                    headers: ["X-Authorization": "Bearer ${token}"]
            ).data
            submission
        }
    }

    def submitFibProblemInKotlin() {
        log.info("Step 3 - Submit Fib Solution (kotlin)")

        def sourceCode = Resources.toString(Resources.getResource("FibFast.kt"), Charsets.UTF_8)

        handleHttpException {
            def judgeResult = jalgoJudgeApiClient.post(
                    path: "judge/api/problems/fib/submit",
                    body: sourceCode,
                    requestContentType: ContentType.TEXT,
                    contentType: ContentType.JSON
            ).data
            log.info("Judge result: ${judgeResult}")
            judgeResult
        }
    }

    def logInUser() {
        log.info("Step 2 - Log in")

        def loginRequestJson = """{
    "username": "mikołaj",
    "password": "blabla"
}
"""

        handleHttpException {
            def token = jalgoJudgeApiClient.post(
                    path: "auth/login",
                    body: loginRequestJson,
                    requestContentType: ContentType.JSON,
                    contentType: ContentType.JSON
            ).data.token

            log.info("Received token: ${token}")
            token
        }
    }

    def createUser() {
        def signupRequestJson = """{
  "username": "mikołaj",
  "password": "blabla",
  "email": "mikolaj@email.com",
  "region": "Kraków",
  "team": "Tyniec Team",
  "role": "USER"
}
"""

        log.info("Step 1 - Creating User")

        def user = jalgoJudgeApiClient.post(
                path: "auth/signup",
                body: signupRequestJson,
                requestContentType: ContentType.JSON,
                contentType: ContentType.JSON
        ).data

        log.info("User created: ${user}")
        user
    }

    def handleHttpException(block) {
        try {
            block()
        } catch (HttpResponseException e) {
            log.error("Status: ${e.response.status}, Message: ${e.response.data}")
            throw e
        }
    }
}
