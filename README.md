# User Authentication and List App

This Android application provides user authentication and access to a user list fetched from a remote API. It allows users to log in securely and browse through a list of users, displaying their details.

## Features

- **User Authentication**: Users can securely log in using their email and password.
- **User List Display**: Upon successful login, users can view a list of users fetched from the remote API.
- **Efficient API Integration**: Utilizes the Volley library for efficient HTTP requests and seamless integration with remote APIs.

## Technologies Used

- Java
- Android SDK
- Volley library for network requests
- Authentication and Session Handling with Remote API

## Getting Started

1. Clone this repository.
2. Open the project in Android Studio.
3. Build and run the application on an Android device or emulator.

## API Details

- **Login API**
  - URL: `https://reqres.in/api/login`
  - Method: POST
  - Body: `{"email": "example@example.com", "password": "password123"}`
- **Users List API**
  - URL: `https://reqres.in/api/users?page=2`
  - Method: GET
 
## Output

- Application Design using Figma

  <img width="539" alt="Design" src="https://github.com/NithinU2802/APIHub_MobileApp/assets/106614289/200959ca-c900-44ba-82e7-c231c85e2ad6">

- Implementation of Authentication and Session Handling using Remote API

  https://github.com/NithinU2802/APIHub_MobileApp/assets/106614289/cd4f7a76-ad0e-4709-8d72-24067818b13e




## Contributors
  -  Nithin U (Candidate)
  -  IndiaSkill Team

## License

This project is licensed under the [MIT License](https://github.com/NithinU2802/APIHub_MobileApp?tab=MIT-1-ov-file#readme).
