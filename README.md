# To-Do App

## Overview

The To-Do App is a simple and intuitive Android application designed to help you manage your tasks efficiently. With this app, you can add, edit, delete tasks, and set alarm notifications to ensure you never miss an important deadline. The app uses Java for the backend logic, XML for the user interface, and Room Database for data persistence.

## Features

- **Add Tasks**: Quickly add new tasks with a title and description.
- **Edit Tasks**: Update the details of your existing tasks.
- **Delete Tasks**: Remove tasks that are no longer needed.
- **Alarm Notifications**: Set alarm notifications to remind you of upcoming tasks.
## Screenshots
### Interface
 <img src="screenshots/IMG_20240710_182824.jpg" alt="IMG_20240710_182824.jpg" width="270" height="540">
 ### Adding Task
 <img src="screenshots/IMG_20240710_182824.jpg" alt="IMG_20240710_182824.jpg" width="270" height="540">
 ### Scheduled Tasks
<img src="screenshots/IMG_20240710_182824.jpg" alt="IMG_20240710_182824.jpg" width="270" height="540">
 ### Scheduled Date
<img src="screenshots/IMG_20240710_182824.jpg" alt="IMG_20240710_182824.jpg" width="270" height="540">
 ### Notification
<img src="screenshots/IMG_20240710_182824.jpg" alt="IMG_20240710_182824.jpg" width="270" height="540">
 ### Task Completion
 <img src="screenshots/IMG_20240710_182824.jpg" alt="IMG_20240710_182824.jpg" width="270" height="540">
## Installation

### Prerequisites

- [Android Studio](https://developer.android.com/studio) (latest version)
- An Android device or emulator running Android 5.0 (Lollipop) or higher

### Steps

1. **Clone the Repository**

   ```bash
   git clone https://github.com/yourusername/todo-app.git
   ```

2. **Open in Android Studio**

   - Launch Android Studio.
   - Select `File > Open` and navigate to the cloned repository folder.
   - Open the project.

3. **Build the Project**

   - Click on the `Build` menu and select `Rebuild Project` to download all dependencies and compile the app.

4. **Run the App**

   - Connect your Android device or start an emulator.
   - Click on the `Run` button (green play button) in Android Studio.

## Usage

### Adding a Task

1. Open the app.
2. Tap on the `+` button to add a new task.
3. Enter the task title and description.
4. Set an alarm notification if needed.
5. Tap on the `Save` button.

### Editing a Task

1. Tap on the task you want to edit.
2. Modify the task details.
3. Tap on the `Save` button to save changes.

### Deleting a Task

1. Swipe left or right on the task you want to delete.
2. Confirm the deletion.

### Setting Alarm Notifications

1. While adding or editing a task, tap on the `Set Alarm` button.
2. Choose the date and time for the alarm.
3. Tap on `OK` to set the alarm.

## Database

The app uses Room Database for storing tasks locally on the device. This ensures that your tasks are available even when the device is offline.

## Contributing

We welcome contributions to improve the app. Please fork the repository and submit a pull request with your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.



