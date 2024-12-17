# UserRepository

## Description
the `UserRepository` interface provides methods to interact with the user table in the database. It extends the `CrudRepository` interface, which offers methods to interact with the database.
## Methods

- **findByUsername(String username)**:  
  Finds a `User` entity by its username. If the user exists, the corresponding `User` object is returned, otherwise it returns `null`.

- **findByEmail(String email)**:  
  Finds a `User` entity by its email address. If the user exists, the corresponding `User` object is returned, otherwise it returns `null`.

