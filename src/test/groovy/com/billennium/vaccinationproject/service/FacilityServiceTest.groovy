package com.billennium.vaccinationproject.service

import com.billennium.vaccinationproject.VaccinationprojectApplication
import com.billennium.vaccinationproject.dto.FacilityDto
import com.billennium.vaccinationproject.entity.FacilityEntity
import com.billennium.vaccinationproject.entity.VisitEntity
import com.billennium.vaccinationproject.exceptionhandler.ApiRequestException
import com.billennium.vaccinationproject.repository.FacilityRepository
import org.modelmapper.ModelMapper
import org.spockframework.spring.SpringSpy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification
import spock.lang.Subject

@SpringBootTest(classes = [VaccinationprojectApplication.class,FacilityService.class])
class FacilityServiceTest extends Specification{

    @SpringSpy
    FacilityService facilityService

    def "should add #mockname facility to h2 database"() {
        setup:
        def newfacility = new FacilityDto()
        newfacility.setCity(mockname)
        newfacility.setContactPhone(mockname)
        newfacility.setCountry(mockname)
        newfacility.setName(mockname)
        newfacility.setStreet(mockname)
        facilityService.addFacility(newfacility)

        when:
        def facility = facilityService.findFacilityById(mockid)

        then:
        facility.getCity() == mockname

        where:
        mockname << ["Koza","pakka","makka","dappa","rappa"]
        mockid << [1,2,3,4,5]
    }

    def "should update facility in database"() {
        setup:
        def facilityToUpdate = new FacilityDto()
        facilityToUpdate.setCity("kappa")
        facilityToUpdate.setContactPhone("kappa")
        facilityToUpdate.setCountry("kappa")
        facilityToUpdate.setName("kappa")
        facilityToUpdate.setStreet("kappa")
        facilityService.updateFacilityById(1,facilityToUpdate)

        when:
        def facility = facilityService.findFacilityById(1)

        then:
        facility.getCity() == "kappa"
    }

    def "should delete Facility from database by id"() {
        given:
        facilityService.deleteFacilityById(1)

        when:
        def facility = facilityService.findFacilityById(1)

        then:
        thrown(ApiRequestException)
    }

    def "should get all facilities from database"() {
        when:
        def facilityRespEnt = facilityService.getAllFacilities(0,10,"id")

        then:
        facilityRespEnt.getBody().stream().forEach(facility -> println facility)
    }
}
