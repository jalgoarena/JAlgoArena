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

    @Unroll
    "User #username submits successfully #problemId problem solution in #language"(String problemId, String sourceFileName, String language, String username, Integer level) {
        given: "User creates account if empty and log in"
            def user = createOrFindUser(username)
            def token = logInUser(username)

        and: "User judges solution for $problemId problem"
            def fileExtension = "java" == language ? "java" : "kt"
            def sourceCode = Resources.toString(Resources.getResource("$sourceFileName.$fileExtension"), Charsets.UTF_8)
            def judgeResult = judgeProblem(sourceCode, problemId)

        and: "User submits solution for $problemId problem"
            def submission = sentSubmission(judgeResult, user, token, problemId, language, sourceCode, level)
            log.info("Submission saved: ${submission}")

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

                log.info("User Submissions: ${userSubmissions}")
            }

        then: "We can see saved submission on user profile"
            userSubmissions != null
            def problemSubmission = userSubmissions.find { it.problemId == problemId }

            problemSubmission.problemId == problemId
            problemSubmission.level == level
            problemSubmission.elapsedTime > 0.0
            problemSubmission.sourceCode == sourceCode
            problemSubmission.statusCode == "ACCEPTED"
            problemSubmission.userId == user.id
            problemSubmission.language == language


        where:
        problemId                   | sourceFileName            | language  | username  | level
        "2-sum"                     | "TwoSum"                  | "java"    | "mikołaj" | 2
        "fib"                       | "FibFast"                 | "java"    | "mikołaj" | 1
        "stoi"                      | "MyStoi"                  | "java"    | "mikołaj" | 2
        "word-ladder"               | "WordLadder"              | "java"    | "mikołaj" | 3
        "is-string-unique"          | "IsStringUnique2"         | "java"    | "mikołaj" | 1
        "check-perm"                | "CheckPerm"               | "java"    | "mikołaj" | 1
        "palindrome-perm"           | "PalindromePerm"          | "java"    | "mikołaj" | 1
        "one-away"                  | "OneAway"                 | "java"    | "mikołaj" | 2
        "string-compress"           | "StringCompress"          | "java"    | "mikołaj" | 1
        "rotate-matrix"             | "RotateMatrix"            | "java"    | "mikołaj" | 1
        "zero-matrix"               | "ZeroMatrix"              | "java"    | "mikołaj" | 1
        "remove-dups"               | "RemoveDups"              | "java"    | "mikołaj" | 1
        "kth-to-last"               | "KThToLast"               | "java"    | "mikołaj" | 2
        "string-rotation"           | "StringRotation"          | "java"    | "mikołaj" | 1
        "sum-lists"                 | "SumLists"                | "java"    | "mikołaj" | 3
        "sum-lists-2"               | "SumLists2"               | "java"    | "mikołaj" | 3
        "palindrome-list"           | "PalindromeList"          | "java"    | "mikołaj" | 1
        "binary-search"             | "BinarySearch"            | "java"    | "mikołaj" | 1
        "delete-tail-node"          | "DeleteTailNode"          | "java"    | "mikołaj" | 1
        "repeated-elements"         | "RepeatedElements"        | "java"    | "mikołaj" | 1
        "first-non-repeated-char"   | "FirstNonRepeatedChar"    | "java"    | "mikołaj" | 1
        "find-middle-node"          | "FindMiddleNode"          | "java"    | "mikołaj" | 1
        "horizontal-flip"           | "HorizontalFlip"          | "java"    | "mikołaj" | 2
        "vertical-flip"             | "VerticalFlip"            | "java"    | "mikołaj" | 2
        "single-number"             | "SingleNumber"            | "java"    | "mikołaj" | 1
        "preorder-traversal"        | "PreorderTraversal"       | "java"    | "mikołaj" | 1
        "inorder-traversal"         | "InorderTraversal"        | "java"    | "mikołaj" | 1
        "postorder-traversal"       | "PostorderTraversal"      | "java"    | "mikołaj" | 1
        "height-binary-tree"        | "HeightOfBinaryTree"      | "java"    | "mikołaj" | 1
        "sum-binary-tree"           | "SumBinaryTree"           | "java"    | "mikołaj" | 1
        "insert-stars"              | "InsertStars"             | "java"    | "mikołaj" | 2
        "transpose-matrix"          | "TransposeMatrix"         | "java"    | "mikołaj" | 1
        "2-sum"                     | "TwoSum"                  | "kotlin"  | "julia"   | 2
        "fib"                       | "FibFast"                 | "kotlin"  | "julia"   | 1
        "stoi"                      | "MyStoi"                  | "kotlin"  | "julia"   | 2
        "word-ladder"               | "WordLadder"              | "kotlin"  | "julia"   | 3
        "is-string-unique"          | "IsStringUnique2"         | "kotlin"  | "julia"   | 1
        "check-perm"                | "CheckPerm"               | "kotlin"  | "julia"   | 1
        "palindrome-perm"           | "PalindromePerm"          | "kotlin"  | "julia"   | 1
        "one-away"                  | "OneAway"                 | "kotlin"  | "julia"   | 2
        "string-compress"           | "StringCompress"          | "kotlin"  | "julia"   | 1
        "rotate-matrix"             | "RotateMatrix"            | "kotlin"  | "julia"   | 1
        "zero-matrix"               | "ZeroMatrix"              | "kotlin"  | "julia"   | 1
        "remove-dups"               | "RemoveDups"              | "kotlin"  | "julia"   | 1
        "kth-to-last"               | "KThToLast"               | "kotlin"  | "julia"   | 2
        "string-rotation"           | "StringRotation"          | "kotlin"  | "julia"   | 1
        "sum-lists"                 | "SumLists"                | "kotlin"  | "julia"   | 3
        "sum-lists-2"               | "SumLists2"               | "kotlin"  | "julia"   | 3
        "palindrome-list"           | "PalindromeList"          | "kotlin"  | "julia"   | 1
        "binary-search"             | "BinarySearch"            | "kotlin"  | "julia"   | 1
        "delete-tail-node"          | "DeleteTailNode"          | "kotlin"  | "julia"   | 1
        "repeated-elements"         | "RepeatedElements"        | "kotlin"  | "julia"   | 1
        "first-non-repeated-char"   | "FirstNonRepeatedChar"    | "kotlin"  | "julia"   | 1
        "find-middle-node"          | "FindMiddleNode"          | "kotlin"  | "julia"   | 1
        "horizontal-flip"           | "HorizontalFlip"          | "kotlin"  | "julia"   | 2
        "vertical-flip"             | "VerticalFlip"            | "kotlin"  | "julia"   | 2
        "single-number"             | "SingleNumber"            | "kotlin"  | "julia"   | 1
        "preorder-traversal"        | "PreorderTraversal"       | "kotlin"  | "julia"   | 1
        "inorder-traversal"         | "InorderTraversal"        | "kotlin"  | "julia"   | 1
        "postorder-traversal"       | "PostorderTraversal"      | "kotlin"  | "julia"   | 1
        "height-binary-tree"        | "HeightOfBinaryTree"      | "kotlin"  | "julia"   | 1
        "sum-binary-tree"           | "SumBinaryTree"           | "kotlin"  | "julia"   | 1
        "insert-stars"              | "InsertStars"             | "kotlin"  | "julia"   | 2
        "transpose-matrix"          | "TransposeMatrix"         | "kotlin"  | "julia"   | 1
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

    def sentSubmission(judgeResult, user, token, problemId, language, String sourceCode, level) {
        log.info("Step 4 - Submit Solution for $problemId")

        def submissionRequestJson = """{
    "problemId": "$problemId",
    "level": $level,
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
