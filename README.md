# Test results for group 10:

## APKs used during testing:

	App APK:
		Location: /Users/daniel1/Desktop/Skole/SemesterV2024/DAT153/QuizAppOblig3/app/build/outputs/apk/debug/app-debug.apk
  
	Test APK:
		Location: /Users/daniel1/Desktop/Skole/SemesterV2024/DAT153/QuizAppOblig3/app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
    
## Test cases:

### 1. Clicking a button in the main-menu takes you to the right sub-activity (i.e. to the Quiz or the Database; testing one button is enough):
   - The test checks that if you click on the gallery button in the main menu, you actually get sent to the gallery. This case is expected to pass.
<img width="681" alt="Skjermbilde 2024-04-19 kl  14 11 43" src="https://github.com/h600884/QuizApp-Oblig3/assets/89258811/5d132ca8-47ab-4969-bc3d-77013fa0faab">


### 2. Is the score updated correctly in the quiz (submit right/wrong answer and check if the score is correct afterwards):
  - The test checks that if you click on the first button in the quiz, and the answer is wrong, the score is updated to zero correct of one. This case has a 33% chance that the test will not pass, because the options buttons is randomized. 
<img width="681" alt="Skjermbilde 2024-04-19 kl  14 11 48" src="https://github.com/h600884/QuizApp-Oblig3/assets/89258811/de0c991f-f76f-4ab2-98cb-edd3ba891cec">

### 3. A test that checks that the number of registered pictures/persons is correct after adding/deleting an entry. 

Testing deleting an image from the gallery:
- If you click on an image and delete the image, the image will no longer be in the database and should not appear in the gallery. This is expected to pass.

Testing adding an image to the gallery:
- If you add an image and the image has a description it should be added to the database and appear in the gallery. This is expected to  pass. 
<img width="680" alt="Skjermbilde 2024-04-19 kl  14 11 36" src="https://github.com/h600884/QuizApp-Oblig3/assets/89258811/90a22fb8-9493-4e91-a217-a0464cad8560">






   

