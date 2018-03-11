#!/usr/bin/env groovy
import groovy.json.JsonSlurper
import groovy.xml.MarkupBuilder

println "Hello Groovy"


String query = "testcontainers"
String language = "java"

def githubRequestUrl = new URL("https://api.github.com/search/repositories?q=$query+language:$language&sort=stars&order=desc")

def response = new JsonSlurper().parse(githubRequestUrl)

response.items.each {
    println it.full_name
    println it.html_url
}


def writer = new StringWriter()
def builder = new MarkupBuilder(writer)
builder.html {
    table {
        tr {
            th("Full name")
            th("URL")
        }

        response.items.each { item ->

            tr {
                td(item.full_name)
                td {
                    a(href: item.html_url, item.html_url)
                }
            }

        }

    }
}


def outputFile = new File("index.html")
outputFile.text = ""
outputFile.text = writer.toString()
