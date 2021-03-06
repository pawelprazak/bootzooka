package com.softwaremill.bootzooka.routes

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.softwaremill.bootzooka.api.RoutesBase
import com.softwaremill.bootzooka.http.model.headers.`X-Content-Type-Options`.`nosniff`
import com.softwaremill.bootzooka.http.model.headers.`X-Frame-Options`.`DENY`
import com.softwaremill.bootzooka.http.model.headers.`X-XSS-Protection`.`1; mode=block`
import com.softwaremill.bootzooka.http.model.headers.{`X-Content-Type-Options`, `X-Frame-Options`, `X-XSS-Protection`}
import org.scalatest.{FlatSpec, Matchers}

class RoutesBaseSpec extends FlatSpec with Matchers with ScalatestRouteTest {

  it should "return a response wirth security headers" in {
    val routes = new RoutesBase {}.base {
      get {
        complete("ok")
      }
    }

    Get() ~> routes ~> check {
      response.header[`X-Frame-Options`].get shouldBe `X-Frame-Options`(`DENY`)
      response.header[`X-Content-Type-Options`].get shouldBe `X-Content-Type-Options`(`nosniff`)
      response.header[`X-XSS-Protection`].get shouldBe `X-XSS-Protection`(`1; mode=block`)
    }
  }
}
