# TestSuitMedia

TestSuitMedia is an Android application developed to implement a case study in mobile application development. The app is built using Kotlin and leverages modern components such as Retrofit for network communication, and Room for local storage.

## Table of Contents

- [Features](#features)
- [Screenshots](#screenshots)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Contact](#contact)

## Features

- **Home Screen**: Provides an input form to check if the entered username is a palindrome.
- **Second Screen**: Displays the username entered on the Home Screen.
- **Third Screen**: Shows a list of users fetched from an API.

## Screenshots
<img height="400px" src="https://github.com/user-attachments/assets/76c0cca7-e1c3-4f4c-90ed-afcde96c30c8" alt="home" />
<img height="400px" src="https://github.com/user-attachments/assets/d0ac31d4-0234-450b-b149-204ffe6eb0cc" alt="homeNotPalindrome" />
<img height="400px" src="https://github.com/user-attachments/assets/2df38611-ceb9-4509-91ad-20ffe86cd24c" alt="secondScreen" />
<img height="400px" src="https://github.com/user-attachments/assets/cf3675fb-bfbf-40b8-bce5-e531fe9ec120" alt="thirdScreen" />
<img height="400px" src="https://github.com/user-attachments/assets/cfc0b876-589f-4be5-83cd-361956ac6eb0" alt="secondScreenSelected" />


## Technologies Used

- **Kotlin**: The programming language used for application development.
- **Retrofit**: A library for making HTTP requests and managing REST APIs.
- **Room**: A library to efficiently manage SQLite databases in a structured way.
- **ViewModel**: A component to manage UI-related data in a lifecycle-conscious way.
- **Pager**: A component for handling pagination of data in the UI.
- **Remote Mediator**: A component to mediate between local and remote data sources, ensuring the UI always has the latest data.

## Project Structure
1. ui/: Contains UI components.
2. data/: Contains data models and remote mediators.
3. data/local: Contains local data.
4. data/remote: Contains Retrofit configuration for network communication.
5. viewmodel/: Contains ViewModel classes for managing and preparing data for the UI.

## Contact
Raafi Hilmi - [Email](mailto:raafihilmi90@gmail.com) - [LinkedIn](https://www.linkedin.com/in/raafi-hilmi)

Project Link: https://github.com/raafihilmi/TestSuitMedia.

