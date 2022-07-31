# Android-MVVM-Clean-Achitecture
Navigation (Note) : 
- Navigation refers to the interactions that allow users to navigate across, into, and back out from the different pieces of content within your app
- The mechanism of navigation like replaceFragment. Not has maschanism addFragment so when popbackstack when must handle holding data.
- Navigation belong Jetpack Android supported by Google 

Hilt :
- Hilt is library help DI for Android faster than when to do manual
- Dependency Injection is design pattern designed with purpose prevent dependencies between classes, to clean code ,easy maintenance and update code
- How does DI Work: provide containers for all class Android in project and auto manager their cycler.
  Ex : When we are in class B but want access method of class A so usual way we new instance class then access to method it. In such a way, it consumes memory, make difficult to write unittest. While if use DI then we just need @Inject to object or class we need to get.
Coroutine
- What is coroutine :Is lightweight threads for asynchronous programming, Coroutines not only open the doors to asynchronous programming, but also provide a wealth of other possibilities such as concurrency, actors, etc.
- How does coroutine concept works : 
Kotlin coroutine is a way of doing things asynchronously in a sequential manner. Creating a coroutine is a lot cheaper vs creating a thread.

 Design Pattern : MVVM ( Model - View - ViewModel)
- Model : Which is responsible for representing and holding the application data.( Model, Repository, Api Service .. )
- View : It represents the user interface of the application without any Application Logic. It observes the ViewModel
- ViewModel:  It is reponsible for preparing and handle data for UI( Activity or Fragment).
  * Note:
    - View - ViewModel comunicate 1-N ( View -> ViewModel, ViewModel done handle data will use Live data post value to View)
    - When call api so ViewModel call repository, then Repository call Api Service to get data then return reponse to ViewModel
    
    
![2db55392505ba8198e32e34ba2d01afa](https://user-images.githubusercontent.com/23168876/182007372-b91f272e-ad08-4873-a0d0-72c39c597355.png)

Explain each component in the project and how to use it: https://docs.google.com/spreadsheets/d/1v--lGjhraVjxokxzYzPiG4qAZnGHMbvDHOfjzvL2Z14/edit#gid=1300307021

