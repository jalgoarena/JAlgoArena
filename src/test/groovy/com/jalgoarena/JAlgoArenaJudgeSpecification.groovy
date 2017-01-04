package com.jalgoarena

import com.google.common.base.Charsets
import com.google.common.io.Resources
import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import org.slf4j.LoggerFactory
import spock.lang.Specification
import spock.lang.Unroll

class JAlgoArenaJudgeSpecification extends Specification {
    static log = LoggerFactory.getLogger(JAlgoArenaJudgeSpecification.class)

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
    "[#language] Solution #sourceFileName passes judgement for #problemId problem"(String problemId, String sourceFileName, String language) {
        given: "A source code as a problem solution"
        def fileExtension = "java" == language ? "java" : "kt"
        def sourceCode = Resources.toString(Resources.getResource("$sourceFileName.$fileExtension"), Charsets.UTF_8)

        log.info("Processing: $problemId in $language")

        when: "We judge solution"

        def judgeResult = jalgoJudgeApiClient.post(
                path: "judge/api/problems/$problemId/submit",
                body: sourceCode,
                requestContentType: ContentType.TEXT,
                contentType: ContentType.JSON
        ).data

        then: "We receive successful judgement result"

        judgeResult.statusCode == "ACCEPTED"
        judgeResult.elapsedTime > 0

        where:
        problemId                   | sourceFileName            | language
        "2-sum"                     | "TwoSum"                  | "java"
        "fib"                       | "FibFast"                 | "java"
        "stoi"                      | "MyStoi"                  | "java"
        "word-ladder"               | "WordLadder"              | "java"
        "is-string-unique"          | "IsStringUnique2"         | "java"
        "check-perm"                | "CheckPerm"               | "java"
        "palindrome-perm"           | "PalindromePerm"          | "java"
        "one-away"                  | "OneAway"                 | "java"
        "string-compress"           | "StringCompress"          | "java"
        "rotate-matrix"             | "RotateMatrix"            | "java"
        "zero-matrix"               | "ZeroMatrix"              | "java"
        "remove-dups"               | "RemoveDups"              | "java"
        "kth-to-last"               | "KThToLast"               | "java"
        "string-rotation"           | "StringRotation"          | "java"
        "sum-lists"                 | "SumLists"                | "java"
        "sum-lists-2"               | "SumLists2"               | "java"
        "palindrome-list"           | "PalindromeList"          | "java"
        "binary-search"             | "BinarySearch"            | "java"
        "delete-tail-node"          | "DeleteTailNode"          | "java"
        "repeated-elements"         | "RepeatedElements"        | "java"
        "first-non-repeated-char"   | "FirstNonRepeatedChar"    | "java"
        "find-middle-node"          | "FindMiddleNode"          | "java"
        "horizontal-flip"           | "HorizontalFlip"          | "java"
        "vertical-flip"             | "VerticalFlip"            | "java"
        "single-number"             | "SingleNumber"            | "java"
        "preorder-traversal"        | "PreorderTraversal"       | "java"
        "inorder-traversal"         | "InorderTraversal"        | "java"
        "postorder-traversal"       | "PostorderTraversal"      | "java"
        "height-binary-tree"        | "HeightOfBinaryTree"      | "java"
        "sum-binary-tree"           | "SumBinaryTree"           | "java"
        "insert-stars"              | "InsertStars"             | "java"
        "transpose-matrix"          | "TransposeMatrix"         | "java"
        "2-sum"                     | "TwoSum"                  | "kotlin"
        "fib"                       | "FibFast"                 | "kotlin"
        "stoi"                      | "MyStoi"                  | "kotlin"
        "word-ladder"               | "WordLadder"              | "kotlin"
        "is-string-unique"          | "IsStringUnique2"         | "kotlin"
        "check-perm"                | "CheckPerm"               | "kotlin"
        "palindrome-perm"           | "PalindromePerm"          | "kotlin"
        "one-away"                  | "OneAway"                 | "kotlin"
        "string-compress"           | "StringCompress"          | "kotlin"
        "rotate-matrix"             | "RotateMatrix"            | "kotlin"
        "zero-matrix"               | "ZeroMatrix"              | "kotlin"
        "remove-dups"               | "RemoveDups"              | "kotlin"
        "kth-to-last"               | "KThToLast"               | "kotlin"
        "string-rotation"           | "StringRotation"          | "kotlin"
        "sum-lists"                 | "SumLists"                | "kotlin"
        "sum-lists-2"               | "SumLists2"               | "kotlin"
        "palindrome-list"           | "PalindromeList"          | "kotlin"
        "binary-search"             | "BinarySearch"            | "kotlin"
        "delete-tail-node"          | "DeleteTailNode"          | "kotlin"
        "repeated-elements"         | "RepeatedElements"        | "kotlin"
        "first-non-repeated-char"   | "FirstNonRepeatedChar"    | "kotlin"
        "find-middle-node"          | "FindMiddleNode"          | "kotlin"
        "horizontal-flip"           | "HorizontalFlip"          | "kotlin"
        "vertical-flip"             | "VerticalFlip"            | "kotlin"
        "single-number"             | "SingleNumber"            | "kotlin"
        "preorder-traversal"        | "PreorderTraversal"       | "kotlin"
        "inorder-traversal"         | "InorderTraversal"        | "kotlin"
        "postorder-traversal"       | "PostorderTraversal"      | "kotlin"
        "height-binary-tree"        | "HeightOfBinaryTree"      | "kotlin"
        "sum-binary-tree"           | "SumBinaryTree"           | "kotlin"
        "insert-stars"              | "InsertStars"             | "kotlin"
        "transpose-matrix"          | "TransposeMatrix"         | "kotlin"
    }
}
