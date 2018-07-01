package com.jalgoarena

import com.google.common.base.Charsets
import com.google.common.io.Resources
import groovy.json.StringEscapeUtils
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import spock.lang.Ignore
import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Unroll

class LocalJAlgoArenaE2ESpec extends Specification {

    static log = LoggerFactory.getLogger(LocalJAlgoArenaE2ESpec.class)

    static jalgoApiClient = new RESTClient("http://localhost:5001/")

    @Unroll
    @Ignore
    "User #username submits successfully #problemId problem solution in #language"(String problemId, String sourceFileName, String language, String username) {
        given: "User creates account if empty and log in"
            def user = createOrFindUser(username)
            def token = logInUser(username)

        and: "User judges solution for $problemId problem"
            def fileExtension = "java" == language ? "java" : "kt"
            def sourceCode = Resources.toString(Resources.getResource("$sourceFileName.$fileExtension"), Charsets.UTF_8)
            def judgeResult = judgeProblem(sourceCode, user, token, problemId, language)

        expect:
            token != null
            judgeResult != null
            judgeResult.submissionId != null
            judgeResult.problemId == problemId

        when: "We check user submissions"
            log.info("Step 4 - Check Submission for $problemId")
            sleep(1000)

            def submissionResult
            for (iteration in 1..20) {
                handleHttpException {
                    submissionResult = jalgoApiClient.get(
                            path: "/submissions/api/submissions/find/${user.id}/${judgeResult.submissionId}",
                            contentType: ContentType.JSON,
                            headers: ["X-Authorization": "Bearer ${token}"]
                    ).data
                }

                if (submissionResult != null && submissionResult.statusCode != "NOT_FOUND") {
                    break
                }
                log.info("No response, retrying [iteration=$iteration]")
                sleep(2000)
            }


        then: "We can see saved submission on user profile"
            submissionResult != null
            submissionResult.problemId == problemId
            submissionResult.elapsedTime > 0.0
            submissionResult.sourceCode == sourceCode
            submissionResult.statusCode == "ACCEPTED"
            submissionResult.language == language
            submissionResult.submissionId == judgeResult.submissionId
            submissionResult.errorMessage == null || submissionResult.errorMessage == ""

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
        "sum-lists"                 | "SumLists"                | "java"    | "julia"
        "sum-lists-2"               | "SumLists2"               | "java"    | "julia"
        "palindrome-list"           | "PalindromeList"          | "java"    | "julia"
        "binary-search"             | "BinarySearch"            | "java"    | "julia"
        "delete-tail-node"          | "DeleteTailNode"          | "java"    | "julia"
        "repeated-elements"         | "RepeatedElements"        | "java"    | "julia"
        "first-non-repeated-char"   | "FirstNonRepeatedChar"    | "java"    | "julia"
        "find-middle-node"          | "FindMiddleNode"          | "java"    | "julia"
        "horizontal-flip"           | "HorizontalFlip"          | "java"    | "julia"
        "vertical-flip"             | "VerticalFlip"            | "java"    | "julia"
        "single-number"             | "SingleNumber"            | "java"    | "julia"
        "preorder-traversal"        | "PreorderTraversal"       | "java"    | "julia"
        "inorder-traversal"         | "InorderTraversal"        | "java"    | "julia"
        "postorder-traversal"       | "PostorderTraversal"      | "java"    | "julia"
        "height-binary-tree"        | "HeightOfBinaryTree"      | "java"    | "julia"
        "sum-binary-tree"           | "SumBinaryTree"           | "java"    | "julia"
        "insert-stars"              | "InsertStars"             | "java"    | "julia"
        "transpose-matrix"          | "TransposeMatrix"         | "java"    | "mikołaj"
    }

    def createOrFindUser(String username) {
        handleHttpException {
            def users = jalgoApiClient.get(
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

    def judgeProblem(String sourceCode, user, token, problemId, language) {
        log.info("Step 3 - Judge Solution for $problemId")

        def judgeRequestJson = """{
    "sourceCode": "${StringEscapeUtils.escapeJava(sourceCode)}",
    "userId": "${user.id}",
    "language": "$language"
}
"""

        handleHttpException {
            jalgoApiClient.post(
                    path: "queue/api/problems/$problemId/publish",
                    body: judgeRequestJson,
                    requestContentType: ContentType.JSON,
                    contentType: ContentType.JSON,
                    headers: ["X-Authorization": "Bearer ${token}"]
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
            jalgoApiClient.post(
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

        jalgoApiClient.post(
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
