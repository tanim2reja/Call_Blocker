# **Call Blocker App**  
🚀 **Block unwanted calls effortlessly!**

## 📋 Table of Contents
1. [About the Project](#about-the-project)  
2. [Features](#features)  
3. [Tech Stack](#tech-stack)  
4. [Getting Started](#getting-started)  
5. [Screenshots](#screenshots)  
6. [Contributing](#contributing)  
7. [License](#license)  

---

## 📖 About the Project

**Call Blocker** is a lightweight Android application that allows users to block unwanted calls and manage their blocked contacts with ease. It’s built using **Jetpack Compose** for an elegant UI and **Room Database** for efficient data handling.  

This project showcases the use of modern Android development practices, dependency injection with **Hilt**, and robust architecture.

---

## 🌟 Features

- Block incoming calls from unwanted numbers.  
- Maintain a **list of blocked numbers** stored locally.  
- Simple and clean **user interface** using Jetpack Compose.  
- Swipe to block or unblock numbers.  
- Randomized UI elements for a personalized feel.  

---

## 🛠 Tech Stack

- **Kotlin**: Main programming language.  
- **Jetpack Compose**: Modern UI toolkit.  
- **Hilt**: Dependency injection.  
- **Room Database**: Local database to store blocked numbers.  
- **Dagger-Hilt**: Simplify dependency injection across the app.  

---

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed:  
- **Android Studio** (Latest version recommended)  
- **Java Development Kit (JDK)** 11 or higher  

### Dependencies
Ensure you add the following dependecies to your gradle.
```kotlin
plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

dependencies {
    implementation("androidx.compose.ui:ui:1.5.0")
    implementation("androidx.room:room-runtime:2.5.1")
    kapt("androidx.room:room-compiler:2.5.1")
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-compiler:2.44")
}
```
### Example of Room database
Database Definition:
```kotlin
@Database(entities = [BlockedNumber::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun blockedNumberDao(): BlockedNumberDao
}
```

Database configuration:
```kotlin
class MyApplication : Application(){
    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        // Initialize Room database
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "app_call_blocker_database"
        ).build()
    }
}
```

Define the Data Model:
```kotlin
@Entity(tableName = "BlockedNumber")
data class BlockedNumber(
    @PrimaryKey val number: String
)
```

Create DAO Interface:
```kotlin
@Dao
interface BlockedNumberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBlockedNumber(blockedNumber: BlockedNumber)

    @Query("SELECT * FROM BlockedNumber")
    fun getBlockedNumbers(): Flow<List<BlockedNumber>>
}
```
We are using repository pattern: here is your repository class
```kotlin
class BlockedNumberRepository @Inject constructor (private val dao: BlockedNumberDao){
    fun getBlockedNumbers(): Flow<List<BlockedNumber>> = dao.getBlockedNumbers()

    suspend fun insertBlockedNumber(number: String) {
        dao.insertBlockedNumber(BlockedNumber(number = number))
    }
}
```
How to call your database: we are calling this method from viewModel
```kotlin
fun blockNumber(number: String) {
        viewModelScope.launch {
            repository.insertBlockedNumber(number)
        }
}
```
### Hilt intigration
Hilt configuration with your application:
```kotlin
@HiltAndroidApp
class MyApplication : Application(){
// your code goes here 
}
```
and you must add this ```@AndroidEntryPoint``` to your Launcher Activity
```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() { }
```
App module
```kotlin
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "call_blocker_db"
        ).build()
    }

    @Provides
    fun provideBlockedNumberDao(db: AppDatabase): BlockedNumberDao {
        return db.blockedNumberDao()
    }
}
```
viewmodel with injected repository
```kotlin
@HiltViewModel
class CallBlockerViewModel @Inject constructor(
    private val repository: BlockedNumberRepository
) : ViewModel() {

```

### Call Blocking code
create a service: add this code to manifest file
```xml
 <service android:name="com.jolpai.callblocker.service.CallBlockService"
            android:permission="android.permission.BIND_SCREENING_SERVICE"
            android:exported="true">
            <intent-filter>
                <action android:name="android.telecom.CallScreeningService"/>
            </intent-filter>
</service>       
```
and this is your service class
```kotlin
override fun onScreenCall(details: Call.Details) {

        val incomingNumber = details.handle.schemeSpecificPart
        Log.e("TAG", incomingNumber)
        val appDatabase = (applicationContext as MyApplication).database
        // Run database query on a background thread
        GlobalScope.launch(Dispatchers.IO) {
            // Get the blocked number from the database
            appDatabase.blockedNumberDao().getBlockedNumber(incomingNumber).collect { blockedNumbers ->

                // Check if the incoming number is in the blocked list
                val isBlocked = blockedNumbers.any { it.number == incomingNumber }

                if (isBlocked) {
                    // Reject the call if the number is blocked
                    val response = CallResponse.Builder()
                        .setDisallowCall(true)
                        .setRejectCall(true)
                        .setSkipCallLog(true)
                        .setSkipNotification(true)
                        .build()
                    respondToCall(details, response)
                } else {
                    // Allow the call if the number is not blocked
                    val response = CallResponse.Builder().build()
                    respondToCall(details, response)
                }
            }
        }
    }
```
### Installation

1. Clone the repository:  
   ```bash
   git clone https://github.com/yourusername/call-blocker.git
