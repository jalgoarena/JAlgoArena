package com.jalgoarena

import com.google.common.base.Charsets
import com.google.common.io.Resources
import groovy.json.StringEscapeUtils
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Unroll

class JAlgoArenaE2ESpec extends Specification {

    static log = LoggerFactory.getLogger(JAlgoArenaE2ESpec.class)

    static jalgoJudgeApiClient = new RESTClient("https://jalgoarena-api.herokuapp.com/")

    def setupSpec() {

        def services = [
                "jalgoarena-eureka",
                "jalgoarena",
                "jalgoarena-judge-agent-1",
                "jalgoarena-judge-agent-2",
                "jalgoarena-judge-agent-3",
                "jalgoarena-judge-agent-4",
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

    @Unroll
    "User #username submits successfully #problemId problem solution in #language"(String problemId, String sourceFileName, String language, String username) {
        given: "User creates account if empty and log in"
            def user = createOrFindUser(username)
            def token = logInUser(username)

        and: "User judges solution for $problemId problem"
            def fileExtension = "java" == language ? "java" : "kt"
            def sourceCode = Resources.toString(Resources.getResource("$sourceFileName.$fileExtension"), Charsets.UTF_8)
            def judgeResult = judgeProblem(sourceCode, problemId)

        and: "User submits solution for $problemId problem"
            def submission = sentSubmission(judgeResult, user, token, problemId, language, sourceCode)

        expect:
            token != null
            submission != null
            judgeResult != null
            judgeResult.statusCode == "ACCEPTED"

        when: "We check user submissions"
            def userSubmissions
            handleHttpException {
                userSubmissions = jalgoJudgeApiClient.get(
                        path: "/submissions/api/submissions/${user.id}",
                        contentType: ContentType.JSON,
                        headers: ["X-Authorization": "Bearer ${token}"]
                ).data
            }

        then: "We can see saved submission on user profile"
            userSubmissions != null
            def problemSubmission = userSubmissions.find { it.problemId == problemId }

            problemSubmission.problemId == problemId
            problemSubmission.elapsedTime > 0.0
            problemSubmission.sourceCode == sourceCode
            problemSubmission.statusCode == "ACCEPTED"
            problemSubmission.userId == user.id
            problemSubmission.language == language


        where:
        problemId                   | sourceFileName            | language  | username
        "2-sum"                     | "TwoSum"                  | "java"    | "mikołaj"
        "fib"                       | "FibFast"                 | "java"    | "mikołaj"
        "stoi"                      | "MyStoi"                  | "java"    | "mikołaj"
        "word-ladder"               | "WordLadder"              | "java"    | "mikołaj"
        "is-string-unique"          | "IsStringUnique2"         | "java"    | "mikołaj"
        "check-perm"                | "CheckPerm"               | "java"    | "mikołaj"
        "palindrome-perm"           | "PalindromePerm"          | "java"    | "mikołaj"
        "one-away"                  | "OneAway"                 | "java"    | "mikołaj"
        "string-compress"           | "StringCompress"          | "java"    | "mikołaj"
        "rotate-matrix"             | "RotateMatrix"            | "java"    | "mikołaj"
        "zero-matrix"               | "ZeroMatrix"              | "java"    | "mikołaj"
        "remove-dups"               | "RemoveDups"              | "java"    | "mikołaj"
        "kth-to-last"               | "KThToLast"               | "java"    | "mikołaj"
        "string-rotation"           | "StringRotation"          | "java"    | "mikołaj"
        "sum-lists"                 | "SumLists"                | "java"    | "mikołaj"
        "sum-lists-2"               | "SumLists2"               | "java"    | "mikołaj"
        "palindrome-list"           | "PalindromeList"          | "java"    | "mikołaj"
        "binary-search"             | "BinarySearch"            | "java"    | "mikołaj"
        "delete-tail-node"          | "DeleteTailNode"          | "java"    | "mikołaj"
        "repeated-elements"         | "RepeatedElements"        | "java"    | "mikołaj"
        "first-non-repeated-char"   | "FirstNonRepeatedChar"    | "java"    | "mikołaj"
        "find-middle-node"          | "FindMiddleNode"          | "java"    | "mikołaj"
        "horizontal-flip"           | "HorizontalFlip"          | "java"    | "mikołaj"
        "vertical-flip"             | "VerticalFlip"            | "java"    | "mikołaj"
        "single-number"             | "SingleNumber"            | "java"    | "mikołaj"
        "preorder-traversal"        | "PreorderTraversal"       | "java"    | "mikołaj"
        "inorder-traversal"         | "InorderTraversal"        | "java"    | "mikołaj"
        "postorder-traversal"       | "PostorderTraversal"      | "java"    | "mikołaj"
        "height-binary-tree"        | "HeightOfBinaryTree"      | "java"    | "mikołaj"
        "sum-binary-tree"           | "SumBinaryTree"           | "java"    | "mikołaj"
        "insert-stars"              | "InsertStars"             | "java"    | "mikołaj"
        "transpose-matrix"          | "TransposeMatrix"         | "java"    | "mikołaj"
        "2-sum"                     | "TwoSum"                  | "kotlin"  | "julia"
        "fib"                       | "FibFast"                 | "kotlin"  | "julia"
        "stoi"                      | "MyStoi"                  | "kotlin"  | "julia"
        "word-ladder"               | "WordLadder"              | "kotlin"  | "julia"
        "is-string-unique"          | "IsStringUnique2"         | "kotlin"  | "julia"
        "check-perm"                | "CheckPerm"               | "kotlin"  | "julia"
        "palindrome-perm"           | "PalindromePerm"          | "kotlin"  | "julia"
        "one-away"                  | "OneAway"                 | "kotlin"  | "julia"
        "string-compress"           | "StringCompress"          | "kotlin"  | "julia"
        "rotate-matrix"             | "RotateMatrix"            | "kotlin"  | "julia"
        "zero-matrix"               | "ZeroMatrix"              | "kotlin"  | "julia"
        "remove-dups"               | "RemoveDups"              | "kotlin"  | "julia"
        "kth-to-last"               | "KThToLast"               | "kotlin"  | "julia"
        "string-rotation"           | "StringRotation"          | "kotlin"  | "julia"
        "sum-lists"                 | "SumLists"                | "kotlin"  | "julia"
        "sum-lists-2"               | "SumLists2"               | "kotlin"  | "julia"
        "palindrome-list"           | "PalindromeList"          | "kotlin"  | "julia"
        "binary-search"             | "BinarySearch"            | "kotlin"  | "julia"
        "delete-tail-node"          | "DeleteTailNode"          | "kotlin"  | "julia"
        "repeated-elements"         | "RepeatedElements"        | "kotlin"  | "julia"
        "first-non-repeated-char"   | "FirstNonRepeatedChar"    | "kotlin"  | "julia"
        "find-middle-node"          | "FindMiddleNode"          | "kotlin"  | "julia"
        "horizontal-flip"           | "HorizontalFlip"          | "kotlin"  | "julia"
        "vertical-flip"             | "VerticalFlip"            | "kotlin"  | "julia"
        "single-number"             | "SingleNumber"            | "kotlin"  | "julia"
        "preorder-traversal"        | "PreorderTraversal"       | "kotlin"  | "julia"
        "inorder-traversal"         | "InorderTraversal"        | "kotlin"  | "julia"
        "postorder-traversal"       | "PostorderTraversal"      | "kotlin"  | "julia"
        "height-binary-tree"        | "HeightOfBinaryTree"      | "kotlin"  | "julia"
        "sum-binary-tree"           | "SumBinaryTree"           | "kotlin"  | "julia"
        "insert-stars"              | "InsertStars"             | "kotlin"  | "julia"
        "transpose-matrix"          | "TransposeMatrix"         | "kotlin"  | "julia"
    }

    def createOrFindUser(String username) {
        handleHttpException {
            def users = jalgoJudgeApiClient.get(
                    path: "/auth/users",
                    contentType: ContentType.JSON
            ).data

            def user = users.find { it.username == username }

            if (user == null) {
                user = createUser(username)
            } else {
                log.info("User already created: ${user}")
            }

            user
        }
    }

    def sentSubmission(judgeResult, user, token, problemId, language, String sourceCode) {
        log.info("Step 4 - Submit Solution for $problemId")

        def submissionRequestJson = """{
    "problemId": "$problemId",
    "elapsedTime": ${judgeResult.elapsedTime},
    "sourceCode": "${StringEscapeUtils.escapeJava(sourceCode)}",
    "statusCode": "${judgeResult.statusCode}",
    "userId": "${user.id}",
    "language": "$language"
}
"""

        handleHttpException {
            jalgoJudgeApiClient.put(
                    path: "/submissions/api/submissions",
                    body: submissionRequestJson,
                    requestContentType: ContentType.JSON,
                    contentType: ContentType.JSON,
                    headers: ["X-Authorization": "Bearer ${token}"]
            ).data
        }
    }

    def judgeProblem(sourceCode, problemId) {
        log.info("Step 3 - Judge Solution for $problemId")

        handleHttpException {
            jalgoJudgeApiClient.post(
                    path: "judge/api/problems/$problemId/submit",
                    body: sourceCode,
                    requestContentType: ContentType.TEXT,
                    contentType: ContentType.JSON
            ).data
        }
    }

    def logInUser(username) {
        log.info("Step 2 - Log in")

        def loginRequestJson = """{
    "username": "$username",
    "password": "blabla"
}
"""

        handleHttpException {
            jalgoJudgeApiClient.post(
                    path: "auth/login",
                    body: loginRequestJson,
                    requestContentType: ContentType.JSON,
                    contentType: ContentType.JSON
            ).data.token
        }
    }

    def createUser(username) {
        def signupRequestJson = """{
  "username": "${username}",
  "password": "blabla",
  "email": "${username}@email.com",
  "region": "Kraków",
  "team": "Team A",
  "role": "USER"
}
"""

        log.info("Step 1 - Creating User")

        jalgoJudgeApiClient.post(
                path: "auth/signup",
                body: signupRequestJson,
                requestContentType: ContentType.JSON,
                contentType: ContentType.JSON
        ).data
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
