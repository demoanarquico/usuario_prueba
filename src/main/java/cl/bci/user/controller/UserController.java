package cl.bci.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cl.bci.user.dto.ReqInfoUser;
import cl.bci.user.dto.ReqUser;
import cl.bci.user.dto.RespInfoUser;
import cl.bci.user.dto.RespUser;
import cl.bci.user.dto.Response;
import cl.bci.user.exception.BadRequestException;
import cl.bci.user.exception.InternalServerErrorException;
import cl.bci.user.exception.NotFoundException;
import cl.bci.user.service.ApiService;
import cl.bci.user.validator.UserValidator;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
//import io.swagger.v3.oas.models.Components;
//import io.swagger.v3.oas.models.OpenAPI;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.annotations.security.SecurityScheme;

@OpenAPIDefinition(
	    info = @Info(
	        title = "APi User",
	        description = "API desarrollada para prueba técnica BCI",
	        version = "1.0.0",
	        contact = @Contact(
					email = "salvadormorenoramos@gmail.com"
				)
  
	    )
	    
	)


@Tag(name = "UserRequest", description = "API Users")
@RestController
@RequestMapping(path = "/api/user")
@Transactional
//@Configuration
//@SecurityScheme(
//		  name = "Bearer Authentication",
//		  type = SecuritySchemeType.HTTP,
//		  bearerFormat = "JWT",
//		  scheme = "bearer"
//		)
public class UserController {
	
	@Autowired
    private UserValidator userValidator;
	
	@Autowired
	private ApiService service;
	
//	@Bean
//	public OpenAPI customizeOpenAPI() {
//	    final String securitySchemeName = "bearerAuth";
//	    return new OpenAPI()
//	      .addSecurityItem(new SecurityRequirement()
//	        .addList(securitySchemeName))
//	      .components(new Components()
//	        .addSecuritySchemes(securitySchemeName, new SecurityScheme()
//	          .name(securitySchemeName)
//	          .type(SecurityScheme.Type.HTTP)
//	          .scheme("bearer")
//	          .bearerFormat("JWT")));
//	    }
	
	@Operation(summary = "Servicio para crear un nuevo usuario.",
			description = "Permite crear un nuevo usuario en la base de datos.")
	@PutMapping( path="/create", consumes = "application/json", produces = "application/json" )
	public ResponseEntity< RespUser > createUser( @RequestBody ReqUser user ) throws BadRequestException, InternalServerErrorException{
		
		userValidator.validateReqUser( user );
		userValidator.validateEmailUser( user.getEmail() );
		userValidator.validatePassUser( user.getPassword() );
		
		RespUser respUser = service.createUser( user );
		return new ResponseEntity<>( respUser, HttpStatus.OK );
		
	}
	
	
	@Operation(summary = "Servicio para consultar por un usuario.",
			description = "Permite consultar por id del usuario.")
	@GetMapping( path="/get", produces = "application/json" )
	public ResponseEntity< RespInfoUser > getUser(  @RequestParam(required = true, name = "idUser" ) Integer idUser ) throws  InternalServerErrorException, NotFoundException, BadRequestException{
		
		if( idUser <= 0 ) {
			throw new BadRequestException("Valor Id debe ser mayor a cero.");
		}
		
		RespInfoUser respInfoUser = service.getUser( idUser );
		return new ResponseEntity<>( respInfoUser, HttpStatus.OK );
		
	}
	
	
	@Operation(summary = "Servicio para modificar un usuario.",
			description = "Permite modificar un usuario en la base de datos.")
	@PostMapping( path="/change", consumes = "application/json", produces = "application/json" )
	public ResponseEntity< Response > changeUser( @RequestBody ReqInfoUser user ) throws BadRequestException, InternalServerErrorException, NotFoundException{
		
		userValidator.validateReqInfoUser( user );
        userValidator.validateEmailUser( user.getEmail() );
		userValidator.validatePassUser( user.getPassword() );
		
		Response resp = service.changeUser( user );
		return new ResponseEntity<>( resp, HttpStatus.OK );
		
	}

	
	@Operation(summary = "Servicio para eliminar un usuario.",
			description = "Permite eliminar un usuario de la base de datos.")
	@DeleteMapping( path="/delete", produces = "application/json" )
	public ResponseEntity< Response > deleteUser( @RequestParam(required = true, name = "idUser" ) Integer idUser ) throws BadRequestException, InternalServerErrorException, NotFoundException{
		
		if( idUser <= 0 ) {
			throw new BadRequestException("Valor Id debe ser mayor a cero.");
		}
		
		Response resp = service.delUser( idUser );
		return new ResponseEntity<>( resp, HttpStatus.OK );
		
	}
	

	
//	@Operation(summary = "Servicio para identificar a un usuario.",
//			description = "Permite identificar un usuario en la base de datos.")
//	@PostMapping( path="/login", consumes = "application/json", produces = "application/json" )
//	public ResponseEntity< RespUser > loginUser( @RequestBody ReqUser user ) throws BadRequestException, InternalServerErrorException{
//		
//		RespUser respUser;
//		
//		return new ResponseEntity<>( respUser, HttpStatus.OK );
//		
//	}

}
