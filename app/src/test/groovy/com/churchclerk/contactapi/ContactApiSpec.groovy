import com.churchclerk.baseapi.model.ApiCaller
import com.churchclerk.contactapi.ContactApi
import com.churchclerk.contactapi.model.Contact
import com.churchclerk.contactapi.service.ContactService
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import spock.lang.Specification
import spock.lang.Subject

class ContactApiSpec extends Specification {

    @Subject
    ContactApi  testSubject;

    ContactService  contactService;

    def setup() {
        testSubject = new ContactApi();

        contactService = Mock()
        testSubject.service = contactService;

        testSubject.apiCaller = new ApiCaller("testadmin|", "SUPER,ADMIN,CLERK,OFFICIAL,MEMBER,NONMEMBER");

        setupMocks();
    }

    Page<? extends Contact> mockedPage;
    Pageable                savedPageable;
    Contact                 savedCriteria;

    def setupMocks() {
        mockedPage = null;
        savedPageable = null;
        savedCriteria = null;
        contactService.getResources(_,_) >> {
            savedPageable = it[0];
            savedCriteria = it[1];
            return mockedPage;
        }
    }

    def "createCriteria with null parameter(s)"() {
        given: "parameter(s)"
        testSubject.active          = testActive;

        when: "the method is executed"
        def actual = testSubject.createCriteria();

        then: "no exception should be thrown"
        noExceptionThrown()

        and: "the actual should match"
        actual != null
        actual.active == expectedActive
        actual.id == null

        where:
        testActive    | expectedActive    | expectedId
        null          | true              | null
        true          | true              | null
        false         | false             | null
    }

//    def "createCriteria with non super user role"() {
//        given: "parameter(s)"
//        testSubject.apiCaller       = new ApiCaller("testadmin|", "ADMIN,CLERK,OFFICIAL,MEMBER,NONMEMBER");
//
//        when: "the method is executed"
//        def actual = testSubject.createCriteria();
//
//        then: "no exception should be thrown"
//        noExceptionThrown()
//
//        and: "the actual should match"
//        actual != null
//        actual.active == true
//        actual.id != null
//        actual.testData == null
//    }
//
//    def "doGet with null parameters"() {
//        given: "parameter(s)"
//        Pageable pageable = null;
//
//        when: "the method is executed"
//        def actual = testSubject.doGet(pageable);
//
//        then: "no exception should be thrown"
//        noExceptionThrown()
//
//        and: "the actual should match"
//        actual == null
//
//        and: "the saved should match"
//        savedPageable == pageable;
//        savedCriteria != null
//        savedCriteria.testData == null
//        savedCriteria.id == null
//        savedCriteria.active == true
//    }
}