package com.bhrother.github.resources

import com.bhrother.github.services.ExternalSystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
/**
 * Created by brunohenriquerother on 22/05/2017.
 */
@RestController
class ExternalSystemResource {

  @Autowired
  ExternalSystemService externalSystemService

  @GetMapping("/callExternal")
  int callExternalService(){
    externalSystemService.call()
  }
}
