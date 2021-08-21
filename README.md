# Stack Lunch Core Library & Starter
Core spring dependency for Stack Lunch projects to import boilerplate code and bootstrap a pre-configured microservice

## Usage Instructions
### Import Dependency
--TODO: Once S3 Bucket is set up and configure for acting as a maven repository, instructions will be here for importing as a dependency

### Creating a Microservice
This Stack Lunch starter dependency comes with an abstract class that configures the Application Context with everything you need to
get started, including application properties, security configurations, Swagger endpoint configuration, Entity Scans, and Bean imports for any
special service - all ready to be injected or used without any boiler plate code. Using this tool is simple and intuitive!

1. In the class with your `@SpringBootApplication` and the main method, extend the `StackLunchApplication` abstract class.

2. Replace the invocation `SpringBootApplication.run` in the main method with an initialization and a call to the instance method `run` like so:

`
public class MyMicroservice extends StackLunchApplication { 

	public static void main(String[] args) {
		MyMicroservice app = new MyMicroservice();
		app.run(args);
	}
}
`

3. The abstract class will also require implementing the following methods: `String baseEndpoint()`, `Boolean implementsLoginAndLogout()`,
`Boolean integratesEmailNotificiation()`, `Boolean integratesTextNotification()`, (TODO: and `Map<TBD, TBD> endPointSecurity()`)

	* `baseEndpoint()` - String - the main endpoint the loadbalancer will be configured with. This sets up the proper endpoints for 
	swagger docs, healthchecks, and login/logout (if they're implemented) to extend from.
	
	* `implementsLoginAndLogout()` - Boolean - configures whether or not login and logout endpoints are enabled with the built-in logic.
	If true, the microservice will have endpoints `baseEndpoint/login` and `baseEndpoint/logout` for returning and blacklisting JWT tokens
	respectively. If false, each microservice will only include the Authorization Filter for reading Tokens and determining authority of already-authenticated users
	
	* `integratesEmailNotification()` - Boolean - A boolean flag to determine whether the application registers an Autowireable bean, EmailService,
	for quick and easy email notifications in the application logic. (TODO: See the documentation for EmailService below)
	
	* `integratesTextNotification()` - Boolean - A boolean flag to determine whether the application registers an Autowireable bean, TextMessageService,
	for quick and easy text notifications in the application logic. (TODO: See the documentation for TextMessageService below).
	
	* TODO: `endPointSecurity()` - Map<TBD, TBD> - IMPLEMENTATION NOT FULLY FIGURED OUT YET: return a map of the security configurations and AntMatchers for
	the application to configure automatically for you!
	
4. Get started with your coding logic! This starter & library is minimize the need to worry about configuration properties, database communication, and security
concerns. Just extend whatever repositories you need to use (with @Repository annotation for component scan) with worry about desiging the entities and add your
custom query methods! If you want to implement custom DTOs for your RestController, you can design your own mapper to translate from the built-in DTOs without
having to worry about being coupled to the database!
 
 
### Configuring Endpoint Security
TODO: IMPLEMENTATION DETAILS NOT FULLY FLESHED OUT YET
 
## The StackLunch Library
### Repositories & Entities
	This library already contains a repository for every table in the database (that isn't simply a join table or a view) and a collection of Entities pre-registered
 with the JPA implementation to work out-of-box (and fully tested). Each Entity has Getters and Setters for all of it's fields and implements ToString, HashCode,
 and Equals methods as you might expect. Each entity is also mapped to it's own DTO(s) with internal mappings for quick and easy database decoupling - with 
 mappings intuitively configured as Read/Write, Read-Only, or Write-Only respectively (see below).
 
 To use an autowireable repository in your microservice, just extend the pre-built repository like you would a JpaRepository so it can be picked up by 
 component scan an implement your own query methods. The library should handle the Entity logic on your behalf so you can keep your mind on the business logic.
 `
 @Repository
 public interface RestaurantDao extends RestaurantRepository {
 	Restaurant findByName(String name);
 	Page<Restaurant findByNameContaining(String query, Pageable pageable);
 }
 `
 Note: If implementing Login and Logout, beans for a UserRepository and AuthorizationRepository will already be registered in the application, but with
 minimal logic to them that can't really be added to. Because there may be some repositories working in the background, it's important to autowire using
 the specific type you declare when you extend the repository, rather than the base type like RestaurantRepository. This will avoid accidentally injecting
 the wrong bean and not being able to use your implementation.
 
 REPOSITORY AND ENTITY CATALOG (CURRENT):
 
* Authorization & AuthorizationRepository		
* Confirmation & ConfirmationRepository			
* Food & FoodRepository
* Location & Location Repository				
* PasswordChange & PasswordChangeRepository		
* Restaurant & RestaurantRepository
* Role & RoleRepository							
* User & UserRepository

TODO: More will be added as the full database representation gets completed.

### DTOs and Mapping Methods
All Existing DTOs are meant to be decoupled objects with the Entities and, for the most part, are identical representations of them. They all contain
Getters and Setters for their fields and implement ToString, HashCode, and Equals methods. Each DTO also internally utilizes a mapper to transform back
and forth from DTOs depending on whether the DTO is Read-Only, Write-Only, or Read/Write: these come in the form of convert methods like the following examples:

- `static RestaurantDto convert(Restaurant entity)` : static method for creating a DTO from a method. Called like RestaurantDto.convert(myEntity)
- `Restaurant convert()` : instance method for turning an existing Dto into an entity. Called like myRestaurantDto.convert()

Since these are intended as a starting point for decoupling the database, none of the DTOs contain any validations - as the needs may vary from microservice
to microservice. Validations can be done on custom DTO implements (designed to map the default DTO or directly with the Entity) or through business logic
implemented at the service level.

Read-Only and Write-Only does not refer to database permissions. All entities can be used for full CRUD operations, with cascading implemented where 
appropriate. Since DTOs are primarily for interactions between the servlet and the client, these permissions only apply for to how easily a specific JSON can
be directly put in the database. For example, a Read/Write DTO with all fields correctly filled could in theory be put straight in the database and have the
full record returned over the server with the following code: `return repository.save(myDto.convert())`. Service logic can, and in many cases should, still
directly create and configure an entity and interact with the database. These terms also do not apply to updating records, only creating new ones entirely,
since Hibernate uses the ID field to identify records (which every dto contains), but since the convert method is limited this will have to be applied more
programatically.

FULL READ/WRITE DTOS
The following DTOs contain both static and instance methods for translating from Entity -> DTO and DTO -> Entity respectively.

* AuthorizationDto
* FoodDto
* LocationDto
* Restaurant
* RoleDto

TODO: More to be added to this list as coding continues.

READ-ONLY DTOS:
The following only have a static convert method for transalating from Entity -> DTO. To save to the database, a corresponding write-only DTO should be used,
or direct interactions with the Entity itself is necessary. (this is mostly due to a difficulty with the user password field, but also applies for general
security concerns).

* ConfirmationDto
* PasswordChangeDto
* UserDto (does not include password field - so can't be directly saved to database as a new record)

WRITE-ONLY DTOS:
Only one DTO is limited specifically to translating from DTO -> Entity.

* UserRegistrationDTO (identical to UserDto, but also includes a password for creating new user accounts)


### Email Service Specifications
TODO: Automatically registered & injectable beans. Usage details will be added once implementation is included

### Text Message Service Specification
TODO: Automatically registered & injectable beans. Usage details will be added once implementation is includeed