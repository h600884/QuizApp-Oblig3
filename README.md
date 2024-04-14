# Test results for group 10:

## APKs used during testing:

	App APK:
		Location: /Users/daniel1/Desktop/Skole/SemesterV2024/DAT153/QuizAppOblig3/app/build/outputs/apk/debug/app-debug.apk
  
	Test APK:
		Location: /Users/daniel1/Desktop/Skole/SemesterV2024/DAT153/QuizAppOblig3/app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk
    
## Test cases:

### 1. Clicking a button in the main-menu takes you to the right sub-activity (i.e. to the Quiz or the Database; testing one button is enough):
   - The test checks if you click on the gallery button in the main menu, you actually get sent to the gallery. This is expected to pass.
<img width="683" alt="Skjermbilde 2024-04-14 kl  16 35 35" src="https://github.com/h600884/QuizApp-Oblig3/assets/89258811/3c688d14-38b2-48cd-a817-8fc245a449a4">

### 2. Is the score updated correctly in the quiz (submit right/wrong answer and check if the score is correct afterwards):
  - The test checks if you click on the first button in the quiz, and the answer is wrong, the test will pass. There is a 33% chance that the test will fail since the options is randomized. 
<img width="684" alt="Skjermbilde 2024-04-14 kl  16 35 58" src="https://github.com/h600884/QuizApp-Oblig3/assets/89258811/97b9d1c9-7b91-42db-880b-a16f22234bfe">

### 3. A test that checks that the number of registered pictures/persons is correct after adding/deleting an entry. 

Testing deleting an image from the gallery:
- If you click on an image and delete the image, the image will no longer be in the database and should not appear in the gallery. This is expected to pass.
<img width="683" alt="Skjermbilde 2024-04-14 kl  16 36 21" src="https://github.com/h600884/QuizApp-Oblig3/assets/89258811/2b123cf1-76b2-492c-99cc-54b4ac9f92f5">


Testing adding an image to the gallery:
- If you add an image and the image has a description it should be added to the database and appear in the gallery. This is expected to  pass.
<img width="684" alt="Skjermbilde 2024-04-14 kl  16 36 42" src="https://github.com/h600884/QuizApp-Oblig3/assets/89258811/a072ea5b-00f9-4566-bd7a-c6119b763a7c">






   

