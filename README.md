# Photo_Search

# Overview

Designed and created an android Kotlin app that allows the user to find all images for a specific search term and shows the results in a scrollable view. The user can also download an image or save it in the favorites list.

# Features

- The app consists of 4 screens: Main Screen that displays all searched photos, Detail Screen to show and download a selected photo, 
an About Screen that can be displayed from the navigation drawer and a Favorites Screen:

![image](https://user-images.githubusercontent.com/35550711/134509058-493a090a-2ae2-4a12-9415-f9c5ef2b1d15.png) ![image](https://user-images.githubusercontent.com/35550711/134513322-25741ea2-93ad-411b-ac90-4b6d0fd0d0b2.png)
![image](https://user-images.githubusercontent.com/35550711/134513399-7ee71c4f-7b89-4375-aee4-e511994af6e9.png) ![image](https://user-images.githubusercontent.com/35550711/134513454-f503aa05-d88e-4a7e-b81f-11c9c1f67ef3.png)

- A Custom navigation drawer with Motion Layout was implemented to display an animation of the items when opening or closing the drawer:

![image](https://user-images.githubusercontent.com/35550711/134513801-4be5a28c-920d-4cab-9da5-6bfdf75c7a0c.png)

- Search results are fetched from the Flickr Api using Retrofit and Moshi Converter Library. The results are transformed into images with Picasso Library.
Searched images are displayed in a RecyclerView:

![image](https://user-images.githubusercontent.com/35550711/134513970-23fcd646-3e67-49fc-9f0b-40a5190b6b59.png)
![image](https://user-images.githubusercontent.com/35550711/134514071-441d9a50-cccb-4501-ac01-c08794dd3c93.png)

- When the user is offline, a message and a retry button will be shown
When the user is online, clicking on the retry button displays the corresponding images for the searched query:

![image](https://user-images.githubusercontent.com/35550711/134514246-25ae25df-f74a-4d26-9d8f-e0c59070e7f5.png)

- Paging Library was used to add endless scroll to the RecyclerView. When the internet connection goes off while scrolling, a message and a retry button are displayed:

![image](https://user-images.githubusercontent.com/35550711/134514421-a9191630-2549-467a-9b4b-214977e728f7.png)

- The user can store the image locally on the device with shared storage by clicking on the download button. When the image is downloaded, the user receives a notification to notify him that download is completed
When the user clicks on the ShineButton, the image is stored on a local database. When the user unchecks the ShineButton, the image is deleted from the database:

![image](https://user-images.githubusercontent.com/35550711/134514524-2d3a0bbc-69b5-4714-9105-f544471a3859.png)
![image](https://user-images.githubusercontent.com/35550711/134514583-8b109b01-dbfe-4625-9100-0761ab5c2bc7.png)

- MVVM architecture was used to separate responsibilities amongst classes. When the user rotates the screen, the UI data does not change. 
When the user rotates the screen, the scroll position of the RecyclerView does not change.

# License

Copyright 2021 Mariem Mezghani

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
