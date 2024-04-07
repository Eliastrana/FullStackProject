# Backend

Springboot backend with MySQL database.
___
## Table of Contents

- [Basic Setup Guide](#basic-setup-guide)
- [Recommended IDE Setup](#recommended-ide-setup)
  - [Useful tools](#useful-tools)
- [Project Setup](#project-setup)
  - [Clone the project](#clone-the-project)
  - [Commands](#commands)
  - [compile and run the project](#compile-and-run-the-project)
  - [Run tests](#run-tests)
- [Test Users](#test-users)
  - [Admin](#admin)
  - [User](#user)
- [Endpoints](#endpoints)
- [Contact Information](#contact-information)

---

## Basic Setup Guide

If you want to run the project individually, you can follow the steps below:
## Recommended IDE Setup

To run the backend individually, you will need:

- [Java 17](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL](https://dev.mysql.com/downloads/mysql/)
- [Docker Desktop](https://www.docker.com/products/docker-desktop/)

### Useful tools

- [Postman](https://www.postman.com/downloads/)
- [MySQL Workbench](https://dev.mysql.com/downloads/workbench/)
- [VsCode](https://code.visualstudio.com/Download)
- [Git](https://git-scm.com/downloads)
---
## Project Setup
## Commands
If you want to run the backend locally, you can follow the steps below:

### Clone the project
```bash
git clone git@github.com:Eliastrana/FullStackProject.git
```
```bash
cd SpringbootBackend
```

### Compile and run the project
```bash
mvn clean install
```
```bash
mvn spring-boot:run
```
### Run tests
```bash
mvn clean test
```
---
## Test Users

### Admin
- Username: admin
- Password: admin

### User
- Username: user
- Password: user
---
# Endpoints


## Difficulty Management:
 - ### GET /api/difficulties/

Retrieves a list of all difficulty levels available for quizzes.

- **Authorization**: None required.
- **Parameters**: None
- **Success Response**:
  - **Code**: 200 OK
  - **Content Example**:
    ```json
    [
      "Easy",
      "Medium",
      "Hard"
    ]
## Role Management:
- ### GET /api/roles

Retrieves a list of all roles available for users.

- **Authorization**: None required.
- **Parameters**: None
- **Success Response**:
  - **Code**: 200 Successfully retrieved list
  - **Content Example**:
    ```json  
    {                         
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "role": "string",
      "userRoles": [    
        {
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "user": {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "username": "string",
            "password": "string",
            "email": "string",
            "accountNonExpired": true,
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "enabled": true,
            "userRoles": [
              "string"
            ],
            "authorities": [
              {
                "authority": "string"
              }          
            ]
          },
          "role": "string"
        }
      ],
      "authority": "string"
    }
    ```
- ### POST /api/roles**

Create a new role in the system

- **Authorization**: Admin
- **Parameters**: None
- **Success Response**:
  - **Code**: 200 Successfully created new role
  - **Content Example**:
    ```json
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "role": "string",
      "userRoles": [
        {
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "user": {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "username": "string",
            "password": "string",
            "email": "string",
            "accountNonExpired": true,
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "enabled": true,
            "userRoles": [
              "string"
            ],
            "authorities": [
              {
                "authority": "string"
              }
            ]
          },
          "role": "string"
        }
      ],
      "authority": "string"
    }
    ```
## User Role Management:
- ### GET /api/user-roles/

Retrieve roles for a specified user
                                          
- **Authorization: Admin**
- **Parameters: Username**
- **Success Response**:
  - **Code**: 200 Roles found
  - 400 User not found or bad request
  - **Content Example**:
    ```json
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "role": "string",
      "userRoles": [
        {
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "user": {
            "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            "username": "string",
            "password": "string",
            "email": "string",
            "accountNonExpired": true,
            "accountNonLocked": true,
            "credentialsNonExpired": true,
            "enabled": true,
            "userRoles": [
              "string"
            ],
            "authorities": [
              {
                "authority": "string"
              }
            ]
          },
          "role": "string"
        }
      ],
      "authority": "string"
    }
    ```
- ### POST /api/user-roles/

Create a new role for a user

- **Authorization**: Admin
- **Parameters**: Username, Role
- **Code**: 200 Successfully created new role
- **Code**: 400 User not found or bad request

- ### POST /api/user-roles/

Assign a new role to a specified user

- **Authorization**: Admin
- **Parameters**: Username, Role
- **Code**: 200 Successfully assigned role
- **Code**: Could not assign role to user or bad request

- DELETE /api/user-roles/

Remove a role from a user

- **Authorization**: Admin
- **Parameters**: Username, Role
- **Code**: 200 Role removed successfully
- **Code**: 400 Could not remove role from user or bad request

## UserQuizAttempt Management:
- ## GET /api/userQuizAttempts/{id}

Retrieves a specific user quiz attempt by its ID

- **Authorization**: Admin
- **Parameters**: ID
- **Code:** 200 User Quiz Attempt fetched successfully
- **Content Example**:
    ```json    
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "userId": "123e4567-e89b-12d3-a456-426614174000",
    "quizId": "123e4567-e89b-12d3-a456-426614174000",
    "score": 8,
    "status": "completed"
  }
    ```       
- ## PUT /api/userQuizAttempts/{id}

Updates an existing user quiz attempt

- **Authorization**: Admin
- **Parameters**: ID
- **Code:** 200 User Quiz Attempt updated successfully
- **Content Example**:
    ```json    
   {
     "id": "123e4567-e89b-12d3-a456-426614174000",
     "userId": "123e4567-e89b-12d3-a456-426614174000",
     "quizId": "123e4567-e89b-12d3-a456-426614174000",
     "score": 8,
     "status": "completed"
   }
    ```
  
- ## DELETE /api/userQuizAttempts/{id}

Deletes a specific user quiz attempt by its ID

- **Authorization**: Admin
- **Parameters**: ID
- **Code:** 204 User Quiz Attempt deleted successfully

- ## POST /api/userQuizAttempts

Creates a new user quiz attempt
- **Authorization**: Admin
- **Parameters**: None
- **Code:** 201 User Quiz Attempt created successfully
- **Content Example**:
    ```json    
   {
     "id": "123e4567-e89b-12d3-a456-426614174000",
     "userId": "123e4567-e89b-12d3-a456-426614174000",
     "quizId": "123e4567-e89b-12d3-a456-426614174000",
     "score": 8,
     "status": "completed"
   }
    ```
- ## GET /api/userQuizAttempts/user/{userId}

Retrieves all quiz attempts for a specific user
- **Authorization**: Admin
- **Parameters**: userID
- **Code:** 200 User Quiz Attempts fetched successfully
- **Content Example**:
    ```json    
   [
     {
       "id": "123e4567-e89b-12d3-a456-426614174000",
       "userId": "123e4567-e89b-12d3-a456-426614174000",
       "quizId": "123e4567-e89b-12d3-a456-426614174000",
       "score": 8,
       "status": "completed"
     }
   ]
    ```
## Tag Management:
- ### GET /api/tags/{id}

Retrieves a single tag by its unique identifier

- **Authorization**: None required
- **Parameters**: ID
- **Code:** 200 Tag fetched successfully
- **Content Example**:
    ```json    
  {
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "name": "string",
  "questions": [
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "quiz": {
        "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "title": "string",
        "description": "string",
        "difficulty": "EASY",
        "category": {
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "categoryName": "string",
          "description": "string"
        },
        "creator": {
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "username": "string",
          "password": "string",
          "email": "string",
          "accountNonExpired": true,
          "accountNonLocked": true,
          "credentialsNonExpired": true,
          "enabled": true,
          "userRoles": [
            "string"
          ],
          "authorities": [
            {
              "authority": "string"
            }
          ]
        },
        "questions": [
          "string"
        ],
        "image": {
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "filename": "string",
          "fileType": "string",
          "size": 0,
          "data": "string"
        },
        "public": true
      },
      "text": "string",
      "questionType": "MULTIPLE_CHOICE",
      "multimediaLink": "string",
      "image": {
        "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "filename": "string",
        "fileType": "string",
        "size": 0,
        "data": "string"
      },
      "creationDate": "2024-04-07T17:52:14.988Z",
      "tags": [
        "string"
      ],
      "answers": [
        {
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "text": "string",
          "question": "string",
          "correct": true
        }
      ]
    }
  ]
}
    ```

- ### PUT /api/tags/{id}

Updates an existing tag with new information

- **Authorization**: Admin
- **Parameters**: ID
- **Code:** 200 Tag updated successfully
- **Content Example**:
    ```json    
  {
  "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "name": "string",
  "questions": [
    {
      "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "quiz": {
        "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "title": "string",
        "description": "string",
        "difficulty": "EASY",
        "category": {
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "categoryName": "string",
          "description": "string"
        },
        "creator": {
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "username": "string",
          "password": "string",
          "email": "string",
          "accountNonExpired": true,
          "accountNonLocked": true,
          "credentialsNonExpired": true,
          "enabled": true,
          "userRoles": [
            "string"
          ],
          "authorities": [
            {
              "authority": "string"
            }
          ]
        },
        "questions": [
          "string"
        ],
        "image": {
          "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
          "filename": "string",
          "fileType": "string",
          "size": 0,
          "data": "string"
        },
        "public": true
      },
      "text": "string",
      "questionType": "MULTIPLE_CHOICE",
      "multimediaLink": "string",
      "image": {
        "id": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
        "filename": "string",
        "fileType": "string",
        "size": 0,
        "data": "string"
      },
      "creationDate": "2024-04-07T17:52:14.988Z",
        "tags": [
        "string"
        ],

- ### DELETE /api/tags/{id}

Deletes a tag by its ID

- **Authorization**: Admin
- **Parameters**: ID
- **Code:** 204 Tag deleted successfully
- **Code:** 404 Tag not found
- 
- ### GET /api/tags/

Retrieves a list of all tags available in the system

- **Authorization**: None required
- **Parameters**: None
- **Code:** 200 Tags fetched successfully
- **Content Example**:
    ```json    
  [
  {
    "id": "d7b8df5e-4567-4f1e-8c2f-3de0b6b6f1a4",
    "name": "History",
    "questionIds": [
      "9fa1aef0-4b3b-4eda-9c1a-2a1d9eda1e2d",
      "5f8d3a7d-6e3e-4c9b-9775-35e9a2ecde2b"
    ]
  }
]
    ```

- ### POST /api/tags/

Creates a new tag in the system

- **Authorization**: Admin
- **Parameters**: None
- **Code:** 201 Tag created successfully
- **Content Example**:
    ```json    
  {
  "id": "d7b8df5e-4567-4f1e-8c2f-3de0b6b6f1a4",
  "name": "History",
  "questionIds": [
    "9fa1aef0-4b3b-4eda-9c1a-2a1d9eda1e2d",
    "5f8d3a7d-6e3e-4c9b-9775-35e9a2ecde2b"
  ]
}
    ```
## Quiz Management:
- ### GET /api/quiz/{id}

Retrieves a single quiz by its unique identifier

- **Authorization**: None required
- **Parameters**: ID
- **Code:** 200 Successfully fetched the quiz
- **Content Example**:
    ```json    
  {
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "title": "General Knowledge",
  "description": "A quiz covering a wide range of topics.",
  "difficulty": "EASY",
  "categoryId": "023e4567-e89b-12d3-a456-426614174000",
  "creatorId": "123e4567-e89b-12d3-a456-426614174000",
  "imageId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "public": true
}
    ```


- ### PUT /api/quiz/{id}

Updates an existing quiz with new information

- **Authorization**: Admin
- **Parameters**: ID
- **Code:** 200 Quiz updated successfully
- **Content Example**:
    ```json    
  {
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "title": "General Knowledge",
  "description": "A quiz covering a wide range of topics.",
  "difficulty": "EASY",
  "categoryId": "023e4567-e89b-12d3-a456-426614174000",
  "creatorId": "123e4567-e89b-12d3-a456-426614174000",
  "imageId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "public": true
}
    ```
- ### DELETE /api/quiz/{id}

Deletes a quiz by its ID

- **Authorization**: Admin
- **Parameters**: ID
- **Code:** 204 Quiz deleted successfully
- **Code:** 404 Quiz not found

- ### GET /api/quiz

Retrieves all available quizzes

- **Authorization**: None required
- **Parameters**: None
- **Code:** 200 Quizzes fetched successfully
- **Content Example**:
    ```json    
  [
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "title": "General Knowledge",
    "description": "A quiz covering a wide range of topics.",
    "difficulty": "EASY",
    "categoryId": "023e4567-e89b-12d3-a456-426614174000",
    "creatorId": "123e4567-e89b-12d3-a456-426614174000",
    "imageId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
    "public": true
  }
]
    ```
- ### POST /api/quiz

Creates a new quiz with the provided detail

- **Authorization**: Admin
- **Parameters**: None
- **Code:** 200 Quiz created successfully
- **Content Example**:
    ```json    
  {
  "title": "General Knowledge",
  "description": "A quiz covering a wide range of topics.",
  "difficulty": "EASY",
  "creatorId": "123e4567-e89b-12d3-a456-426614174000",
  "categoryId": "023e4567-e89b-12d3-a456-426614174000",
  "questions": [
    {
      "text": "What is the capital of France?",
      "questionType": "MULTIPLE_CHOICE",
      "multimediaLink": "https://example.com/image.png",
      "tags": [
        "d290f1ee-6c54-4b01-90e6-d701748f0851",
        "3f6c6af7-9b81-4c60-b4e0-5fbb7c478e88"
      ],
      "answers": [
        {
          "text": "Paris",
          "isCorrect": true
        },
        {
          "text": "London",
          "isCorrect": false
        }
      ],
      "imageId": "d290f1ee-6c54-4b01-90e6-d701748f0851"
    }
  ],
  "imageId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "public": true
}
    ```
## Question Management:
- ### GET /api/questions/{id}

Retrieves a single question by its unique identifier

- **Authorization**: None required
- **Parameters**: ID
- **Code:** 200 Successfully created the quiz
- **Content Example**:
    ```json    
  {
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "title": "General Knowledge",
  "description": "A quiz covering a wide range of topics.",
  "difficulty": "EASY",
  "categoryId": "023e4567-e89b-12d3-a456-426614174000",
  "creatorId": "123e4567-e89b-12d3-a456-426614174000",
  "imageId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "public": true
}
    ```

- ### PUT /api/questions/{id}

Update a question by its unique identifier

- **Authorization**: Admin
- **Parameters**: ID
- **Code:** 200 Question updated successfully
- **Content Example**:
    ```json    
  {
  "id": "a3b8d425-2b60-4ad7-becc-bedf2ef860bd",
  "quizId": "e8f5e7f3-7ed3-4faa-9889-7161d2b55633",
  "text": "Who wrote 'To Kill a Mockingbird'?",
  "questionType": "MULTIPLE_CHOICE",
  "multimediaLink": "https://example.com/video.mp4",
  "creationDate": "2024-04-07T18:09:34.354Z",
  "tags": [
    "e97aaa14-9a84-4e28-957c-76e8fcb4c321",
    "5fbdd9e3-eded-4980-9a4b-1d8df815b851"
  ],
  "imageId": "f5d8a8ff-9b4d-4c67-8e48-5e9d5e7485df"
}
    ```

- ### DELETE /api/questions/{id}

Deletes a question by its unique identifier

- **Authorization**: Admin
- **Parameters**: ID
- **Code:** 204 Question deleted
- **Code:** 404 Question not found

- ### POST /api/questions

Creates a new question

- **Authorization**: Admin
- **Parameters**: quizId
- **Code:** 201 Question created
- **Content Example**:
    ```json    
  {
  "id": "a3b8d425-2b60-4ad7-becc-bedf2ef860bd",
  "quizId": "e8f5e7f3-7ed3-4faa-9889-7161d2b55633",
  "text": "Who wrote 'To Kill a Mockingbird'?",
  "questionType": "MULTIPLE_CHOICE",
  "multimediaLink": "https://example.com/video.mp4",
  "creationDate": "2024-04-07T18:11:54.269Z",
  "tags": [
    "e97aaa14-9a84-4e28-957c-76e8fcb4c321",
    "5fbdd9e3-eded-4980-9a4b-1d8df815b851"
  ],
  "imageId": "f5d8a8ff-9b4d-4c67-8e48-5e9d5e7485df"
}
    ```

- ### GET /api/questions/quiz/{quizId}@

Retrieves all questions for a specific quiz

- **Authorization**: None required
- **Parameters**: quizId
- **Code:** 200 Questions found
- **Content Example**:
    ```json    
  {
  "id": "a3b8d425-2b60-4ad7-becc-bedf2ef860bd",
  "quizId": "e8f5e7f3-7ed3-4faa-9889-7161d2b55633",
  "text": "Who wrote 'To Kill a Mockingbird'?",
  "questionType": "MULTIPLE_CHOICE",
  "multimediaLink": "https://example.com/video.mp4",
  "creationDate": "2024-04-07T18:12:53.103Z",
  "tags": [
    "e97aaa14-9a84-4e28-957c-76e8fcb4c321",
    "5fbdd9e3-eded-4980-9a4b-1d8df815b851"
  ],
  "imageId": "f5d8a8ff-9b4d-4c67-8e48-5e9d5e7485df"
}
    ```

## Complete Quiz Management:
- ### GET /api/completeQuiz/{quizId}

Fetches a complete quiz with questions and answers.

- **Authorization**: None required
- **Parameters**: quizId
- **Code:** 200 Quiz fetched successfully
- **Code:** 404 Quiz not found

- ### PUT /api/completeQuiz/{quizId}

Updates an existing quiz along with questions and answers.

- **Authorization**: Admin
- **Parameters**: quizId
- **Code:** 200 Quiz updated successfully
- **Code:** 404 Quiz not found
- **Code:** 400 Bad request

- ### DELETE /api/completeQuiz/{quizId}

Deletes a quiz by its unique identifier

- **Authorization**: Admin
- **Parameters**: quizId
- **Code:** 204 Quiz deleted successfully
- **Code:** 404 Quiz not found@

- ### POST /api/completeQuiz

Creates a new quiz with questions and answers

- **Authorization**: Admin
- **Parameters**: None
- **Code:** 201 Quiz created successfully
- **Code:** 400 Bad request
- **Code:** 403 Forbidden - user not authorized to perform this action

- ### GET /api/completeQuiz/tag/{tag}

Retrieves all quizzes with a specific tag

- **Authorization**: None required
- **Parameters**: tag
- **Code:** 200 Complete quiz fetched successfully.
- **Code:** 404 Tag not found

## Category Management:
- ### GET /api/categories/{id}

Retrieves a category by its unique identifier

- **Authorization**: None required
- **Parameters**: ID
- **Code:** 200 Category fetched successfully
- **Content Example**:
    ```json
  {
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "categoryName": "Science",
  "description": "Questions related to scientific facts, including physics, chemistry, and biology."
}
    ```

- ### PUT /api/categories/{id}

Updates an existing category with new information

- **Authorization**: Admin
- **Parameters**: ID
- **Code:** 200 Category updated successfully 
- **Content Example**:
    ```json
  {
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "categoryName": "Science",
  "description": "Questions related to scientific facts, including physics, chemistry, and biology."
}
    ```

- ### DELETE /api/categories/{id}

Deletes a category by its unique identifier 

- **Authorization**: Admin
- **Parameters**: ID
- **Code:** 204 Category deleted successfully
- **Code:** 404 Category not found

- ### GET /api/categories/

Retrieves a list of all categories available in the system

- **Authorization**: None required
- **Parameters**: None
- **Code:** 200 Categories fetched successfully
- **Content Example**:
    ```json
  [
  {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "categoryName": "Science",
    "description": "Questions related to scientific facts, including physics, chemistry, and biology."
  }
]
    ```

- ### POST /api/categories/

Creates a new category in the system

- **Authorization**: Admin
- **Parameters**: None
- **Code:** 201 Category created successfully
- **Content Example**:
    ```json
  {
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "categoryName": "Science",
  "description": "Questions related to scientific facts, including physics, chemistry, and biology."
}
    ``` 
## Answer Management:
- ### GET /api/answers/{answerId}

Retrieves an answer by its unique identifier
  
- **Authorization**: None required
- **Parameters**: answerId
- **Code:** 200 Answer fetched successfully
- **Content Example**:
    ```json
  {
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "text": "Norway",
  "correct": true
}
    ```

- ### PUT /api/answers/{answerId}

Updates an existing answer with new information

- **Authorization**: Admin  
- **Parameters**: answerId
- **Code:** 200 Answer updated successfully
- **Content Example**:
    ```json
  {
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "text": "Norway",
  "correct": true
}
    ```

- ### DELETE /api/answers/{answerId}

Deletes an answer by its unique identifier

- **Authorization**: Admin
- **Parameters**: answerId
- **Code:** 204 Answer deleted successfully
- **Code:** 404 Answer not found

- ### POST /api/answers/{questionId}

Creates a new answer for a specific question

- **Authorization**: Admin
- **Parameters**: questionId
- **Code:** 201 Answer created successfully
- **Content Example**:
    ```json
  {
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "text": "Norway",
  "correct": true
}
    ```

## User Authentication:
- ### POST /api/user/register

Registers a user and returns a JWT token for authentication

- **Authorization**: None required
- **Parameters**: None
- **Code:** 200 Successfully registered user
- **Content Example**:
    ```json
  {
  "token": "string"
}
    ```
- ### POST /api/user/login

Logs in a user and returns a JWT token for authentication

- **Authorization**: None required
- **Parameters**: None
- **Code:** 200 User logged in successfully
- **Content Example**:
    ```json
  {
  "token": "string"
}
    ``` 
- ### GET /api/user/details

Retrieves the details of the currently logged-in user

- **Authorization**: User
- **Parameters**: None
- **Code:** 200 User details fetched successfully
- **Content Example**:
    ```json
  {
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "username": "johnDoe",
  "email": "example@mail.com"
}
    ``` 
## Image Management:
- ### POST /api/quizzes/{quizId}/image

Uploads an image to associate with a quiz

- **Authorization**: Admin
- **Parameters**: quizId
- **Code:** 200 Image uploaded successfully

- ### POST /api/questions/{questionId}/image

Uploads an image to associate with a question

- **Authorization**: Admin
- **Parameters**: questionId
- **Code:** 200 Image uploaded successfully
- **Code:** 500 Internal server error

- ### GET /api/images/{imageId}

Retrieves an image by its unique identifier

- **Authorization**: None required
- **Parameters**: imageId
- **Code:** 200 Successfully fetched the image
- **Code:** 404 Image not found
- **Code:** 500 Internal server error

## Contact Information

If You have trouble running the project, questions or feedback, feel free to contact us at:

- [Support](mailto:support@quand.no)
- [Feedback](mailto:support@quand.no)


