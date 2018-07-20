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

    static traefikEdgeService = new RESTClient("http://localhost:5001/")

    def randomId () {
        return (Math.abs(new Random().nextInt() % 100) + 1).toString()
    }

    @Unroll
//    @Ignore
    "User #username submits successfully #problemId problem solution"(String problemId, String sourceFileName, String username, String team) {
        given: "User creates account if empty and log in"
            def user = createOrFindUser(username, team)
            def token = logInUser(username)

        and: "User judges solution for $problemId problem"
            def sourceCode = Resources.toString(Resources.getResource("${sourceFileName}.java"), Charsets.UTF_8)
            def judgeResult = judgeProblem(sourceCode, user, token, problemId)

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
                    submissionResult = traefikEdgeService.get(
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
            submissionResult.submissionId == judgeResult.submissionId
            submissionResult.errorMessage == null || submissionResult.errorMessage == ""

        where:
        problemId                   | sourceFileName            | username  | team
        "2-sum"                     | "TwoSum"                  | "mikolaj" + randomId() | "Team B"
        "fib"                       | "FibFast"                 | "mikolaj" + randomId() | "Team B"
        "stoi"                      | "MyStoi"                  | "mikolaj" + randomId() | "Team B"
        "word-ladder"               | "WordLadder"              | "mikolaj" + randomId() | "Team B"
        "is-string-unique"          | "IsStringUnique2"         | "mikolaj" + randomId() | "Team B"
        "check-perm"                | "CheckPerm"               | "mikolaj" + randomId() | "Team B"
        "palindrome-perm"           | "PalindromePerm"          | "mikolaj" + randomId() | "Team B"
        "one-away"                  | "OneAway"                 | "mikolaj" + randomId() | "Team B"
        "string-compress"           | "StringCompress"          | "mikolaj" + randomId() | "Team B"
        "rotate-matrix"             | "RotateMatrix"            | "mikolaj" + randomId() | "Team B"
        "zero-matrix"               | "ZeroMatrix"              | "mikolaj" + randomId() | "Team B"
        "remove-dups"               | "RemoveDups"              | "mikolaj" + randomId() | "Team B"
        "kth-to-last"               | "KThToLast"               | "mikolaj" + randomId() | "Team B"
        "string-rotation"           | "StringRotation"          | "mikolaj" + randomId() | "Team B"
        "sum-lists"                 | "SumLists"                | "mikolaj" + randomId() | "Team B"
        "sum-lists-2"               | "SumLists2"               | "mikolaj" + randomId() | "Team B"
        "palindrome-list"           | "PalindromeList"          | "mikolaj" + randomId() | "Team B"
        "binary-search"             | "BinarySearch"            | "mikolaj" + randomId() | "Team B"
        "delete-tail-node"          | "DeleteTailNode"          | "mikolaj" + randomId() | "Team B"
        "repeated-elements"         | "RepeatedElements"        | "mikolaj" + randomId() | "Team B"
        "first-non-repeated-char"   | "FirstNonRepeatedChar"    | "mikolaj" + randomId() | "Team B"
        "find-middle-node"          | "FindMiddleNode"          | "mikolaj" + randomId() | "Team B"
        "horizontal-flip"           | "HorizontalFlip"          | "mikolaj" + randomId() | "Team B"
        "vertical-flip"             | "VerticalFlip"            | "mikolaj" + randomId() | "Team B"
        "single-number"             | "SingleNumber"            | "mikolaj" + randomId() | "Team B"
        "preorder-traversal"        | "PreorderTraversal"       | "mikolaj" + randomId() | "Team B"
        "inorder-traversal"         | "InorderTraversal"        | "mikolaj" + randomId() | "Team B"
        "postorder-traversal"       | "PostorderTraversal"      | "mikolaj" + randomId() | "Team B"
        "height-binary-tree"        | "HeightOfBinaryTree"      | "mikolaj" + randomId() | "Team B"
        "sum-binary-tree"           | "SumBinaryTree"           | "mikolaj" + randomId() | "Team B"
        "insert-stars"              | "InsertStars"             | "mikolaj" + randomId() | "Team B"
        "transpose-matrix"          | "TransposeMatrix"         | "mikolaj" + randomId() | "Team B"
        "2-sum"                     | "TwoSum"                  | "julia" + randomId()  | "Team A"
        "fib"                       | "FibFast"                 | "julia" + randomId()  | "Team A"
        "stoi"                      | "MyStoi"                  | "julia" + randomId()  | "Team A"
        "word-ladder"               | "WordLadder"              | "julia" + randomId()  | "Team A"
        "is-string-unique"          | "IsStringUnique2"         | "julia" + randomId()  | "Team A"
        "check-perm"                | "CheckPerm"               | "julia" + randomId()  | "Team A"
        "palindrome-perm"           | "PalindromePerm"          | "julia" + randomId()  | "Team A"
        "one-away"                  | "OneAway"                 | "julia" + randomId()  | "Team A"
        "string-compress"           | "StringCompress"          | "julia" + randomId()  | "Team A"
        "rotate-matrix"             | "RotateMatrix"            | "julia" + randomId()  | "Team A"
        "zero-matrix"               | "ZeroMatrix"              | "julia" + randomId()  | "Team A"
        "remove-dups"               | "RemoveDups"              | "julia" + randomId()  | "Team A"
        "kth-to-last"               | "KThToLast"               | "julia" + randomId()  | "Team A"
        "string-rotation"           | "StringRotation"          | "julia" + randomId()  | "Team A"
        "sum-lists"                 | "SumLists"                | "julia" + randomId()  | "Team A"
        "sum-lists-2"               | "SumLists2"               | "julia" + randomId()  | "Team A"
        "palindrome-list"           | "PalindromeList"          | "julia" + randomId()  | "Team A"
        "binary-search"             | "BinarySearch"            | "julia" + randomId()  | "Team A"
        "delete-tail-node"          | "DeleteTailNode"          | "julia" + randomId()  | "Team A"
        "repeated-elements"         | "RepeatedElements"        | "julia" + randomId()  | "Team A"
        "first-non-repeated-char"   | "FirstNonRepeatedChar"    | "julia" + randomId()  | "Team A"
        "find-middle-node"          | "FindMiddleNode"          | "julia" + randomId()  | "Team A"
        "horizontal-flip"           | "HorizontalFlip"          | "julia" + randomId()  | "Team A"
        "vertical-flip"             | "VerticalFlip"            | "julia" + randomId()  | "Team A"
        "single-number"             | "SingleNumber"            | "julia" + randomId()  | "Team A"
        "preorder-traversal"        | "PreorderTraversal"       | "julia" + randomId()  | "Team A"
        "inorder-traversal"         | "InorderTraversal"        | "julia" + randomId()  | "Team A"
        "postorder-traversal"       | "PostorderTraversal"      | "julia" + randomId()  | "Team A"
        "height-binary-tree"        | "HeightOfBinaryTree"      | "julia" + randomId()  | "Team A"
        "sum-binary-tree"           | "SumBinaryTree"           | "julia" + randomId()  | "Team A"
        "insert-stars"              | "InsertStars"             | "julia" + randomId()  | "Team A"
        "transpose-matrix"          | "TransposeMatrix"         | "julia" + randomId()  | "Team A"
        "2-sum"                     | "TwoSum"                  | "madzia" + randomId()  | "Team C"
        "fib"                       | "FibFast"                 | "madzia" + randomId()  | "Team C"
        "stoi"                      | "MyStoi"                  | "madzia" + randomId()  | "Team C"
        "word-ladder"               | "WordLadder"              | "madzia" + randomId()  | "Team C"
        "is-string-unique"          | "IsStringUnique2"         | "madzia" + randomId()  | "Team C"
        "check-perm"                | "CheckPerm"               | "madzia" + randomId()  | "Team C"
        "palindrome-perm"           | "PalindromePerm"          | "madzia" + randomId()  | "Team C"
        "one-away"                  | "OneAway"                 | "madzia" + randomId()  | "Team C"
        "string-compress"           | "StringCompress"          | "madzia" + randomId()  | "Team C"
        "rotate-matrix"             | "RotateMatrix"            | "madzia" + randomId()  | "Team C"
        "zero-matrix"               | "ZeroMatrix"              | "madzia" + randomId()  | "Team C"
        "remove-dups"               | "RemoveDups"              | "madzia" + randomId()  | "Team C"
        "kth-to-last"               | "KThToLast"               | "madzia" + randomId()  | "Team C"
        "string-rotation"           | "StringRotation"          | "madzia" + randomId()  | "Team C"
        "sum-lists"                 | "SumLists"                | "madzia" + randomId()  | "Team C"
        "sum-lists-2"               | "SumLists2"               | "madzia" + randomId()  | "Team C"
        "palindrome-list"           | "PalindromeList"          | "madzia" + randomId()  | "Team C"
        "binary-search"             | "BinarySearch"            | "madzia" + randomId()  | "Team C"
        "delete-tail-node"          | "DeleteTailNode"          | "madzia" + randomId()  | "Team C"
        "repeated-elements"         | "RepeatedElements"        | "madzia" + randomId()  | "Team C"
        "first-non-repeated-char"   | "FirstNonRepeatedChar"    | "madzia" + randomId()  | "Team C"
        "find-middle-node"          | "FindMiddleNode"          | "madzia" + randomId()  | "Team C"
        "horizontal-flip"           | "HorizontalFlip"          | "madzia" + randomId()  | "Team C"
        "vertical-flip"             | "VerticalFlip"            | "madzia" + randomId()  | "Team C"
        "single-number"             | "SingleNumber"            | "madzia" + randomId()  | "Team C"
        "preorder-traversal"        | "PreorderTraversal"       | "madzia" + randomId()  | "Team C"
        "inorder-traversal"         | "InorderTraversal"        | "madzia" + randomId()  | "Team C"
        "postorder-traversal"       | "PostorderTraversal"      | "madzia" + randomId()  | "Team C"
        "height-binary-tree"        | "HeightOfBinaryTree"      | "madzia" + randomId()  | "Team C"
        "sum-binary-tree"           | "SumBinaryTree"           | "madzia" + randomId()  | "Team C"
        "insert-stars"              | "InsertStars"             | "madzia" + randomId()  | "Team C"
        "transpose-matrix"          | "TransposeMatrix"         | "madzia" + randomId()  | "Team C"
    }

    def createOrFindUser(String username, String team) {
        handleHttpException {
            def users = traefikEdgeService.get(
                    path: "/auth/users",
                    contentType: ContentType.JSON
            ).data

            def user = users.find { it.username == username }

            if (user == null) {
                user = createUser(username, team)
            } else {
                log.info("User already created: ${user}")
            }

            user
        }
    }

    def judgeProblem(String sourceCode, user, token, problemId) {
        log.info("Step 3 - Judge Solution for $problemId")

        def judgeRequestJson = """{
    "sourceCode": "${StringEscapeUtils.escapeJava(sourceCode)}",
    "userId": "${user.id}"
}
"""

        handleHttpException {
            traefikEdgeService.post(
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
            traefikEdgeService.post(
                    path: "auth/login",
                    body: loginRequestJson,
                    requestContentType: ContentType.JSON,
                    contentType: ContentType.JSON
            ).data.token
        }
    }

    def createUser(username, team) {
        def signupRequestJson = """{
  "username": "${username}",
  "password": "blabla",
  "email": "${username}@email.com",
  "region": "Kraków",
  "team": "$team",
  "role": "USER"
}
"""

        log.info("Step 1 - Creating User")

        traefikEdgeService.post(
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
